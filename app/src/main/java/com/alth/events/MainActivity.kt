package com.alth.events

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.DefaultLifecycleObserver
import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.ui.features.authentication.AuthenticationGuard
import com.alth.events.ui.theme.EventsTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Add authentication data source to observers
     *
     */
    @Inject
    lateinit var authenticationDataSource: AuthenticationDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (authenticationDataSource is DefaultLifecycleObserver) {
            lifecycle.addObserver(authenticationDataSource as DefaultLifecycleObserver)
        }

        setContent {
            EventsTheme {
                AuthenticationGuard()
            }
        }
    }
}
