package com.raychang.locker.presentation.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.raychang.locker.R
import com.raychang.locker.presentation.home.HomeScreen
import com.raychang.locker.presentation.settings.SettingsScreen
import com.raychang.locker.ui.theme.AppStyle

data class BottomNavItem(
    val route: String,
    @StringRes val labelResId: Int,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Routes.HOME, R.string.tab_home, Icons.Default.Home),
    BottomNavItem(Routes.SETTINGS, R.string.tab_settings, Icons.Default.Settings)
)

@Composable
fun MainScaffold(
    rootNavController: NavHostController
) {
    val tabNavController = rememberNavController()
    val context = LocalContext.current
    val backToHomeScreen: () -> Unit = {
        (context as? Activity)?.moveTaskToBack(true)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = tabNavController,
            startDestination = Routes.HOME,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            composable(Routes.HOME) {
                BackHandler(onBack = backToHomeScreen)
                HomeScreen(
                    onNavigateToDetail = { id ->
                        rootNavController.navigate(Routes.credentialDetail(id))
                    },
                    onNavigateToCreate = {
                        rootNavController.navigate(Routes.credentialEdit())
                    }
                )
            }
            composable(Routes.SETTINGS) {
                BackHandler(onBack = backToHomeScreen)
                SettingsScreen(
                    onNavigateToTagManagement = {
                        rootNavController.navigate(Routes.TAG_MANAGEMENT)
                    },
                    onNavigateToChangePassword = {
                        rootNavController.navigate(Routes.CHANGE_PASSWORD)
                    },
                    onNavigateToBackupRestore = {
                        rootNavController.navigate(Routes.BACKUP_RESTORE)
                    },
                    onNavigateToPrivacyPolicy = {
                        rootNavController.navigate(Routes.PRIVACY_POLICY)
                    },
                    onLogout = {
                        rootNavController.navigate(Routes.LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }

        FloatingBottomBar(
            tabNavController = tabNavController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = AppStyle.Spacing.size16, vertical = AppStyle.Spacing.size16)
        )
    }
}

@Composable
private fun FloatingBottomBar(
    tabNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(AppStyle.CornerRadius.size36),
        tonalElevation = AppStyle.ShadowElevation.size8,
        shadowElevation = AppStyle.ShadowElevation.size12,
        color = MaterialTheme.colorScheme.surfaceContainerHighest
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppStyle.Spacing.size6, vertical = AppStyle.Spacing.size6),
            horizontalArrangement = Arrangement.spacedBy(AppStyle.Spacing.size6),
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { item ->
                val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                val label = stringResource(item.labelResId)

                val bgColor by animateColorAsState(
                    targetValue = if (selected)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surfaceContainerHighest,
                    label = "navItemBg"
                )

                val iconTint by animateColorAsState(
                    targetValue = if (selected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    label = "navItemIcon"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(AppStyle.CornerRadius.size30))
                        .background(bgColor)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            tabNavController.navigate(item.route) {
                                popUpTo(tabNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .padding(vertical = AppStyle.Spacing.size12),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = label,
                        tint = iconTint,
                        modifier = Modifier.size(AppStyle.IconSize.size24)
                    )
                }
            }
        }
    }
}
