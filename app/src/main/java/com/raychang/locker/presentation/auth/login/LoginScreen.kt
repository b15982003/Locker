package com.raychang.locker.presentation.auth.login

import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raychang.locker.R
import com.raychang.locker.ui.theme.AppStyle

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onResetComplete: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = remember(context) { context.findFragmentActivity() }
    val focus = LocalFocusManager.current

    var showResetDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) onLoginSuccess()
    }

    LaunchedEffect(uiState.isReset) {
        if (uiState.isReset) onResetComplete()
    }

    val canUseBiometric = remember(activity) {
        if (activity == null) false
        else {
            val result = BiometricManager.from(activity)
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            result == BiometricManager.BIOMETRIC_SUCCESS
        }
    }

    val currentOnSuccess by rememberUpdatedState(viewModel::onBiometricSuccess)
    val currentOnFailure by rememberUpdatedState(viewModel::onBiometricFailure)
    val authFailedMsg = stringResource(R.string.auth_failed)

    val biometricPrompt = remember(activity) {
        activity?.let { act ->
            BiometricPrompt(
                act, ContextCompat.getMainExecutor(act),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        currentOnSuccess()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        currentOnFailure()
                    }

                    override fun onAuthenticationFailed() {
                        Toast.makeText(act, authFailedMsg, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }

    val promptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.biometric_prompt_title))
            .setSubtitle(context.getString(R.string.biometric_prompt_subtitle))
            .setNegativeButtonText(context.getString(R.string.biometric_negative_button))
            .build()
    }

    var passwordVisible by remember { mutableStateOf(false) }

    val primary = MaterialTheme.colorScheme.primary

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.forgot_password_title),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.forgot_password_message),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppStyle.Spacing.size12)
                ) {
                    Button(
                        onClick = { showResetDialog = false },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Button(
                        onClick = {
                            showResetDialog = false
                            viewModel.resetApp()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.reset_app),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        )
    }

    // Full screen terminal background
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
            modifier = Modifier
                .fillMaxSize()
                .clickable { focus.clearFocus() }
                .padding(AppStyle.Spacing.size32),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Custom lock icon
            TerminalLockIcon(
                color = primary,
                modifier = Modifier.size(AppStyle.IconSize.size80)
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size20))

            // App name
            Text(
                text = "LOCKER",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 6.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size4))

            Text(
                text = stringResource(R.string.login_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size40))

            // Password field — rounded pill shape
            TextField(
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
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.fillMaxWidth()
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

            // Unlock button
            Button(
                onClick = viewModel::onLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppStyle.Height.size50),
                shape = RoundedCornerShape(AppStyle.CornerRadius.size50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.unlock_button).uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            if (uiState.biometricEnabled && canUseBiometric) {
                Spacer(modifier = Modifier.height(AppStyle.Spacing.size24))
                TextButton(onClick = {
                    biometricPrompt?.authenticate(promptInfo)
                }) {
                    Box(
                        modifier = Modifier
                            .size(AppStyle.IconSize.size48)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fingerprint,
                            contentDescription = stringResource(R.string.fingerprint),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(AppStyle.IconSize.size28)
                        )
                    }
                }
                Text(
                    text = stringResource(R.string.use_fingerprint),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size32))

            TextButton(onClick = { showResetDialog = true }) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

/**
 * Custom terminal-style lock icon drawn with Canvas:
 * A shield outline with a keyhole in the center.
 */
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
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.05f),
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

private fun Context.findFragmentActivity(): FragmentActivity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is FragmentActivity) return ctx
        ctx = ctx.baseContext
    }
    return null
}
