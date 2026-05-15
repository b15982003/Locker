package com.raychang.locker.presentation.auth.setup

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raychang.locker.R
import com.raychang.locker.ui.theme.AppStyle

@Composable
fun SetupPasswordScreen(
    onSetupComplete: () -> Unit,
    viewModel: SetupPasswordViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focus = LocalFocusManager.current

    LaunchedEffect(uiState.isComplete) {
        if (uiState.isComplete) onSetupComplete()
    }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

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
            )
    ) {
        // Grid lines
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
            modifier = Modifier
                .fillMaxSize()
                .clickable { focus.clearFocus() }
                .padding(AppStyle.Spacing.size32),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Lock icon
            TerminalLockIcon(
                color = primary,
                modifier = Modifier.size(AppStyle.IconSize.size80)
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size20))

            Text(
                text = "LOCKER",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 6.sp
                ),
                color = primary
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size4))

            Text(
                text = stringResource(R.string.setup_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size40))

            // Password field
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                placeholder = {
                    Text(
                        text = "$ ${stringResource(R.string.password).lowercase()}_",
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = stringResource(if (passwordVisible) R.string.hide_password else R.string.show_password),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                shape = RoundedCornerShape(AppStyle.CornerRadius.size50),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = primary,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))

            // Confirm password field
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                placeholder = {
                    Text(
                        text = "$ ${stringResource(R.string.confirm_password).lowercase()}_",
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                },
                singleLine = true,
                visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmVisible = !confirmVisible }) {
                        Icon(
                            imageVector = if (confirmVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = stringResource(if (confirmVisible) R.string.hide_password else R.string.show_password),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                shape = RoundedCornerShape(AppStyle.CornerRadius.size50),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = primary,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                )
            )

            if (uiState.errorResId != null) {
                Spacer(modifier = Modifier.height(AppStyle.Spacing.size8))
                Text(
                    text = "> ${stringResource(uiState.errorResId!!)}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size20))

            Button(
                onClick = viewModel::onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppStyle.Height.size50),
                shape = RoundedCornerShape(AppStyle.CornerRadius.size50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                )
            ) {
                Text(
                    text = stringResource(R.string.set_password_button).uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
private fun TerminalLockIcon(
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeWidth = w * 0.06f

        // Outer circle ring
        drawCircle(
            color = color.copy(alpha = 0.15f),
            radius = w * 0.48f,
            center = center
        )
        drawCircle(
            color = color,
            radius = w * 0.48f,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        // Lock body (rounded rect)
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

        // Lock shackle (arc on top)
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
