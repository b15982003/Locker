package com.raychang.locker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.raychang.locker.domain.usecase.auth.ValidateSessionUseCase
import com.raychang.locker.presentation.navigation.AppNavHost
import com.raychang.locker.presentation.navigation.Routes
import com.raychang.locker.ui.theme.LockerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var validateSessionUseCase: ValidateSessionUseCase

    private var startRoute by mutableStateOf(Routes.SPLASH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LockerTheme {
                AppNavHost(startDestination = startRoute)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // Refresh session when app goes to background so the 5-min timer is based on last use
        runBlocking { validateSessionUseCase() }
    }

    override fun onRestart() {
        super.onRestart()
        // When coming back from background, check if session expired
        val result = runBlocking { validateSessionUseCase() }
        if (result == ValidateSessionUseCase.Result.Expired) {
            startRoute = Routes.LOGIN
            // Force recreate so AppNavHost gets the new startDestination
            recreate()
        }
    }
}
