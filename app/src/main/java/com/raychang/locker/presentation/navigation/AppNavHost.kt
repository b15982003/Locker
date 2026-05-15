package com.raychang.locker.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raychang.locker.presentation.auth.login.LoginScreen
import com.raychang.locker.presentation.auth.setup.SetupPasswordScreen
import com.raychang.locker.presentation.backup.BackupRestoreScreen
import com.raychang.locker.presentation.credential.detail.CredentialDetailScreen
import com.raychang.locker.presentation.credential.edit.CredentialEditScreen
import com.raychang.locker.presentation.password.ChangePasswordScreen
import com.raychang.locker.presentation.policy.PrivacyPolicyScreen
import com.raychang.locker.presentation.splash.SplashScreen
import com.raychang.locker.presentation.tag.TagManagementScreen

private const val ANIM_DURATION = 300

@Composable
fun AppNavHost(startDestination: String) {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigate = { dest ->
                    rootNavController.navigate(dest) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.SETUP_PASSWORD) {
            SetupPasswordScreen(
                onSetupComplete = {
                    rootNavController.navigate(Routes.MAIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    rootNavController.navigate(Routes.MAIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onResetComplete = {
                    rootNavController.navigate(Routes.SETUP_PASSWORD) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.MAIN) {
            MainScaffold(rootNavController = rootNavController)
        }

        composable(
            route = Routes.CREDENTIAL_DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(ANIM_DURATION)
                )
            },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(ANIM_DURATION)
                )
            }
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: return@composable
            CredentialDetailScreen(
                credentialId = id,
                onNavigateBack = { rootNavController.popBackStack() },
                onNavigateToEdit = { credentialId ->
                    rootNavController.navigate(Routes.credentialEdit(credentialId))
                }
            )
        }

        composable(
            route = Routes.CREDENTIAL_EDIT,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(ANIM_DURATION)
                )
            },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(ANIM_DURATION)
                )
            }
        ) { backStackEntry ->
            val idString = backStackEntry.arguments?.getString("id")
            val id = idString?.toLongOrNull()
            CredentialEditScreen(
                credentialId = id,
                onNavigateBack = { rootNavController.popBackStack() }
            )
        }

        composable(
            Routes.TAG_MANAGEMENT,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(ANIM_DURATION)
                )
            },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(ANIM_DURATION)
                )
            }
        ) {
            TagManagementScreen(
                onNavigateBack = { rootNavController.popBackStack() }
            )
        }

        composable(
            Routes.CHANGE_PASSWORD,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(ANIM_DURATION)
                )
            },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(ANIM_DURATION)
                )
            }
        ) {
            ChangePasswordScreen(
                onNavigateBack = { rootNavController.popBackStack() }
            )
        }

        composable(
            Routes.PRIVACY_POLICY,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(ANIM_DURATION)
                )
            },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(ANIM_DURATION)
                )
            }
        ) {
            PrivacyPolicyScreen(
                onNavigateBack = { rootNavController.popBackStack() }
            )
        }

        composable(
            Routes.BACKUP_RESTORE,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(ANIM_DURATION)
                )
            },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(ANIM_DURATION)
                )
            }
        ) {
            BackupRestoreScreen(
                onNavigateBack = { rootNavController.popBackStack() }
            )
        }
    }
}
