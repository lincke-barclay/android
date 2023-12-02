package com.alth.events

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.ui.navigation.NavMain
import com.alth.events.ui.navigation.navigateToStart
import com.alth.events.ui.theme.EventsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Add authentication data source to observers
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
                val navHostController = rememberNavController()
                NavMain(navHostController = navHostController)

                // I hate this so much
                listenToAuthToNavigateToStart(navHostController)
            }
        }
    }

    private fun listenToAuthToNavigateToStart(navHostController: NavHostController) {
        lifecycleScope.launch {
            authenticationDataSource.currentlySignedInUser
                .onEach {
                    loggerFactory.getLogger(this).debug("HERHEHREHREHRE")
                    navHostController.navigateToStart()
                }
                .collect()
        }
    }
}
