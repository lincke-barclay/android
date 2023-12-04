package com.alth.events.authentication.sources.impl

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.alth.events.authentication.models.FirebaseAuthenticationState
import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.coroutines.ApplicationScope
import com.alth.events.exceptions.IllegalAuthenticationStateException
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.authentication.results.ChangeNameResult
import com.alth.events.models.authentication.results.ReloadResult
import com.alth.events.models.authentication.results.SendVerificationEmailResult
import com.alth.events.models.authentication.results.SignInResult
import com.alth.events.models.authentication.results.SignUpResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseAuthenticationDataSourceImpl @Inject constructor(
    @ApplicationScope private val appScope: CoroutineScope // Used for email verification stage to launch email scope in the background
) : AuthenticationDataSource, DefaultLifecycleObserver {

    private val logger = loggerFactory.getLogger(this)

    /**
     * Stateful Data
     * Pretty much copies of FirebaseAuth but in flow form
     */
    private val _currentlySignedInUser =
        MutableStateFlow<FirebaseAuthenticationState>(FirebaseAuthenticationState.Unknown)
    override val currentlySignedInUser = _currentlySignedInUser.asStateFlow()


    /**
     * Initialized in launching activity's onCreate function
     */
    private lateinit var auth: FirebaseAuth
    private lateinit var owner: ComponentActivity

    /**
     * Lifecycle listener overrides
     */
    override fun onCreate(owner: LifecycleOwner) {
        if (owner !is ComponentActivity) {
            throw IllegalArgumentException(
                "Cannot register firebase authentication" +
                        "datasource to a non component activity class"
            )
        }
        this.auth = Firebase.auth

        this.auth.addAuthStateListener { auth ->
            _currentlySignedInUser.value = auth.currentUser?.let { user ->
                logger.debug("Refresh: New state is: $user")
                FirebaseAuthenticationState.SignedIn(user)
            } ?: run {
                logger.debug("Refresh: Not signed in - nothing changed")
                FirebaseAuthenticationState.SignedOut
            }
        }

        this.owner = owner
    }

    /**
     * Authentication overrides
     */
    override suspend fun signIn(email: String, password: String): SignInResult =
        suspendCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(owner) { task ->
                    if (task.isSuccessful) {
                        logger.debug("signInWithEmail:success")
                        auth.currentUser ?: throw IllegalAuthenticationStateException(
                            "Successfully signed in but " +
                                    "user is null! This is a firebase problem"
                        )
                        cont.resume(SignInResult.Success)
                    } else {
                        logger.warn("Failed to sign in", task.exception)
                        when (task.exception) {
                            is FirebaseAuthInvalidUserException -> {
                                cont.resume(SignInResult.ThatUserDoesntExist)
                            }

                            is FirebaseAuthInvalidCredentialsException -> {
                                cont.resume(SignInResult.PasswordIsIncorrect)
                            }

                            else -> {
                                throw Exception(
                                    "Unaccounted for exception thrown in firebase signin.",
                                    task.exception,
                                )
                            }
                        }
                    }
                }
        }

    override suspend fun signUp(email: String, password: String) =
        suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(owner) { task ->
                    if (task.isSuccessful) {
                        logger.debug("signInWithEmail:success")
                        auth.currentUser ?: throw IllegalAuthenticationStateException(
                            "Successfully signed in but " +
                                    "user is null! This is a firebase problem"
                        )
                        appScope.launch {
                            sendVerificationEmailForCurrentlySignedInUser()
                        }
                        cont.resume(SignUpResult.Success)
                    } else {
                        logger.warn("Failed to create account", task.exception)
                        when (task.exception) {
                            is FirebaseAuthWeakPasswordException -> {
                                cont.resume(SignUpResult.WeakPasswordException)
                            }

                            is FirebaseAuthInvalidCredentialsException -> {
                                cont.resume(SignUpResult.EmailAddressMalformed)
                            }

                            is FirebaseAuthUserCollisionException -> {
                                cont.resume(SignUpResult.UserCollision)
                            }

                            else -> {
                                throw Exception(
                                    "Unaccounted for exception thrown in firebase signup.",
                                    task.exception,
                                )
                            }
                        }
                    }
                }
        }

    override fun signOut() {
        auth.signOut()
    }

    override suspend fun reload(): ReloadResult =
        suspendCoroutine { cont ->
            logger.debug("Reloading auth state, current state is: ${currentlySignedInUser.value}")

            FirebaseAuth.getInstance().currentUser?.reload()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    cont.resume(ReloadResult.Success)
                } else {
                    logger.warn("Failed to reload", task.exception)
                    when (task.exception) {
                        is FirebaseAuthInvalidUserException -> {
                            cont.resume(ReloadResult.InvalidAccount)
                        }

                        else -> {
                            throw Exception(
                                "Unaccounted for exception thrown in firebase reload.",
                                task.exception,
                            )
                        }
                    }
                }
            }
        }

    override suspend fun sendVerificationEmailForCurrentlySignedInUser() =
        suspendCoroutine { cont ->
            _currentlySignedInUser.value.let {
                logger.debug("Sending verification email to $it")
                when (it) {
                    is FirebaseAuthenticationState.SignedIn -> {
                        it.user.sendEmailVerification()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    cont.resume(SendVerificationEmailResult.Success)
                                } else {
                                    throw Exception(
                                        "Failed to send verification email for user.",
                                        task.exception,
                                    )
                                }
                            }
                    }

                    else -> throw IllegalAuthenticationStateException(
                        "Tried to call send verification email but user is not signed in, " +
                                "this is a bug or a race condition"
                    )
                }
            }
        }

    override suspend fun changeNameOfCurrentlySignedInUser(newName: String) =
        suspendCoroutine { cont ->
            _currentlySignedInUser.value.let {
                when (it) {
                    is FirebaseAuthenticationState.SignedIn -> {
                        val profileUpdates = userProfileChangeRequest {
                            displayName = newName
                        }
                        it.user.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    cont.resume(ChangeNameResult.Success)
                                } else {
                                    logger.warn(
                                        "Failed to change name of current user",
                                        task.exception
                                    )
                                    when (task.exception) {
                                        is FirebaseAuthInvalidUserException -> {
                                            cont.resume(ChangeNameResult.InvalidUserException)
                                        }

                                        else -> {
                                            throw Exception(
                                                "Unaccounted for exception thrown in firebase signup.",
                                                task.exception,
                                            )
                                        }
                                    }
                                }
                            }
                    }

                    else -> throw IllegalAuthenticationStateException(
                        "Called change name" +
                                "but no user is signed in"
                    )
                }
            }
        }

    override fun getSignedInUserIdOrNull(): String? {
        return _currentlySignedInUser.value.let {
            when (it) {
                is FirebaseAuthenticationState.SignedIn -> {
                    it.user.uid
                }

                else -> null
            }
        }
    }

    override suspend fun getAuthenticationTokenOrNull(forceRefresh: Boolean) =
        suspendCancellableCoroutine { continuation ->
            _currentlySignedInUser.value.let {
                when (it) {
                    is FirebaseAuthenticationState.SignedIn -> {
                        it.user.getIdToken(forceRefresh)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    continuation.resume(task.result.token)
                                } else {
                                    throw Exception(
                                        "Unexpected result from getIdToken ",
                                        task.exception,
                                    )
                                }
                            }
                    }

                    else -> continuation.resume(null)
                }
            }
        }
}

