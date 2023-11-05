package com.alth.events.authentication.sources.impl

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.exceptions.IllegalAuthenticationStateException
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.firebase.FirebaseAuthenticationState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class FirebaseAuthenticationDataSourceImpl @Inject constructor() :
    AuthenticationDataSource, DefaultLifecycleObserver {

    private val logger = loggerFactory.getLogger(this)

    /**
     * Stateful Data
     */
    private val _currentlySignedInUser =
        MutableStateFlow<FirebaseAuthenticationState>(FirebaseAuthenticationState.Unknown)
    override val currentlySignedInUser = _currentlySignedInUser.asStateFlow()

    /**
     * Initialized in launching activity's onCreate function
     */
    private lateinit var auth: FirebaseAuth
    private lateinit var owner: ComponentActivity

    override fun onCreate(owner: LifecycleOwner) {
        if (owner !is ComponentActivity) {
            throw IllegalArgumentException(
                "Cannot register firebase authentication" +
                        "datasource to a non component activity class"
            )
        }
        this.auth = Firebase.auth
        this.auth.addAuthStateListener {
            refresh()
        }
        this.owner = owner
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        refresh()
    }

    override fun signIn(email: String, password: String) {
        _currentlySignedInUser.value = FirebaseAuthenticationState.Unknown

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(owner) { task ->
                if (task.isSuccessful) {
                    logger.debug("signInWithEmail:success")
                    auth.currentUser ?: throw IllegalAuthenticationStateException(
                        "Successfully signed in but " +
                                "user is null! This is a firebase problem"
                    )
                } else {
                    logger.error(task.exception?.stackTraceToString() ?: "Error ")
                    TODO("login fails")
                }
            }
    }

    override fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(owner) { task ->
                if (task.isSuccessful) {
                    logger.debug("signInWithEmail:success")
                    auth.currentUser ?: throw IllegalAuthenticationStateException(
                        "Successfully signed in but " +
                                "user is null! This is a firebase problem"
                    )
                } else {
                    logger.error(task.exception?.stackTraceToString() ?: "Error ")
                    TODO("login fails")
                }
            }
    }

    override fun signOut() {
        auth.signOut()
    }

    private fun refresh() {
        _currentlySignedInUser.value = auth.currentUser?.let {
            FirebaseAuthenticationState.SignedIn(it)
        } ?: FirebaseAuthenticationState.SignedOut
    }

    override fun reload() {
        FirebaseAuth.getInstance().currentUser?.reload()
        refresh()
    }

    override fun sendVerificationEmailForCurrentlySignedInUser() {
        _currentlySignedInUser.value.let {
            when (it) {
                is FirebaseAuthenticationState.SignedIn -> {
                    it.user.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(this::class.java.simpleName, "Email Sent")
                            }
                        }
                }

                else -> throw IllegalAuthenticationStateException(
                    "Called send verification " +
                            "email but no user is signed in"
                )
            }
        }
    }

    override fun changeNameOfCurrentlySignedInUser(newName: String) {
        _currentlySignedInUser.value.let {
            when (it) {
                is FirebaseAuthenticationState.SignedIn -> {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = newName
                    }
                    it.user.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("Update", "User Profile Updated")
                                reload()
                            } else {
                                TODO("Handle Error on change Name")
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

    override suspend fun getAuthenticationTokenOrNull() =
        suspendCancellableCoroutine { continuation ->
            _currentlySignedInUser.value.let {
                when (it) {
                    is FirebaseAuthenticationState.SignedIn -> {
                        it.user.getIdToken(false)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    logger.info(task.result.token ?: "Nothing")
                                    continuation.resume(task.result.token)
                                } else {
                                    continuation.resume(null)
                                    TODO("App Says user is signed in but no firebase token")
                                }
                            }
                    }

                    else -> continuation.resume(null)
                }
            }
        }
}

