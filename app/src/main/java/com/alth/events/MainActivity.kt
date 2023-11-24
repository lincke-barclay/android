package com.alth.events

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.ui.features.authentication.AuthenticationStateEntryPoint
import com.alth.events.ui.viewmodels.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authenticationDataSource: AuthenticationDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (authenticationDataSource is DefaultLifecycleObserver) {
            lifecycle.addObserver(authenticationDataSource as DefaultLifecycleObserver)
        }

        setContent {
            val authVM: AuthenticationViewModel by viewModels()
            AuthenticationStateEntryPoint(authVM)
        }
    }
}
