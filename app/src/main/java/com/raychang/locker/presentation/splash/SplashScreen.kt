package com.raychang.locker.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raychang.locker.ui.theme.AppStyle

@Composable
fun SplashScreen(
    onNavigate: (String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val destination by viewModel.destination.collectAsStateWithLifecycle()

    val iconAlpha = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        iconAlpha.animateTo(1f, tween(600, easing = FastOutSlowInEasing))
        textAlpha.animateTo(1f, tween(400, easing = FastOutSlowInEasing))
    }

    LaunchedEffect(destination) {
        destination?.let { onNavigate(it) }
    }

    val primary = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceContainerLow,
                        MaterialTheme.colorScheme.background
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Decorative grid lines
        Canvas(modifier = Modifier.fillMaxSize()) {
            val gridColor = primary.copy(alpha = 0.04f)
            val step = AppStyle.Spacing.size40.toPx()
            var x = 0f
            while (x < size.width) {
                drawLine(gridColor, Offset(x, 0f), Offset(x, size.height))
                x += step
            }
            var y = 0f
            while (y < size.height) {
                drawLine(gridColor, Offset(0f, y), Offset(size.width, y))
                y += step
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Lock icon
            SplashLockIcon(
                color = primary,
                modifier = Modifier
                    .size(AppStyle.IconSize.size100)
                    .alpha(iconAlpha.value)
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size24))

            // App name
            Text(
                modifier = Modifier.alpha(textAlpha.value),
                text = "LOCKER",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 8.sp
                ),
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size8))

            Text(
                modifier = Modifier.alpha(textAlpha.value),
                text = "SECURE  CREDENTIAL  MANAGER",
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 3.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun SplashLockIcon(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeWidth = w * 0.06f

        // Outer circle ring
        val circleRadius = w * 0.5f - strokeWidth * 0.5f
        drawCircle(
            color = color.copy(alpha = 0.15f),
            radius = circleRadius,
            center = center
        )
        drawCircle(
            color = color,
            radius = circleRadius,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        // Lock body
        val bodyLeft = w * 0.3f
        val bodyTop = h * 0.42f
        val bodyWidth = w * 0.4f
        val bodyHeight = h * 0.3f
        drawRoundRect(
            color = color,
            topLeft = Offset(bodyLeft, bodyTop),
            size = Size(bodyWidth, bodyHeight),
            cornerRadius = CornerRadius(w * 0.05f),
            style = Stroke(width = strokeWidth)
        )

        // Lock shackle
        val shackleRect = Size(w * 0.24f, h * 0.22f)
        val shackleOffset = Offset(w * 0.38f, h * 0.24f)
        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = shackleOffset,
            size = shackleRect,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Keyhole dot
        drawCircle(
            color = color,
            radius = w * 0.04f,
            center = Offset(w * 0.5f, h * 0.54f)
        )

        // Keyhole line
        drawLine(
            color = color,
            start = Offset(w * 0.5f, h * 0.56f),
            end = Offset(w * 0.5f, h * 0.63f),
            strokeWidth = strokeWidth * 0.8f,
            cap = StrokeCap.Round
        )
    }
}
