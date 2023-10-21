package com.alth.events

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import com.alth.events.authentication.viewmodels.AuthenticationViewModel
import com.alth.events.authentication.views.AuthenticationStateEntryPoint
import com.alth.events.data.authentication.FirebaseAuthenticationDataSource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * Firebase initializes on `onCreate` so needs to be a listener
     * Still uses dependency injection - only checks if this interface is
     * a LifecycleObserver
     */
    @Inject
    lateinit var firebaseAuthenticationDataSource: FirebaseAuthenticationDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (firebaseAuthenticationDataSource is DefaultLifecycleObserver) {
            lifecycle.addObserver(firebaseAuthenticationDataSource as DefaultLifecycleObserver)
        }

        setContent {
            val authVM: AuthenticationViewModel by viewModels()
            AuthenticationStateEntryPoint(authVM)
        }
    }
}

