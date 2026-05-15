package com.raychang.locker.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @see 集中管理 AppStyle ，如果有缺自行補上，
 * 特別命名的部分是針對 figma 命名，可以搭配著看
 */

object AppStyle {

    // 自定義顏色名
    object Colors {
        val primary = BasicColor.Green500
        val secondary = BasicColor.Green700
        val onPrimary = BasicColor.Basic0
        val primaryContainer = BasicColor.Green100
        val onPrimaryContainer = BasicColor.Green800
        val onPrimaryContainerVariant = BasicColor.Green600
        val surface = BasicColor.Basic50
        val surfaceContainerLow = BasicColor.BasicAlpha50
        val onSurface = BasicColor.Basic900

        val surfaceInverse = BasicColor.BasicAlpha900
        val surfaceInverseVariant = BasicColor.Basic300
        val onSurfaceInverse = BasicColor.Basic50
        val surfaceTranslucent = BasicColor.WhiteAlpha500
        val onSurfaceInverseVariant = BasicColor.Basic300
        val surfaceContainerLowest = BasicColor.BasicAlpha200
        val onSurfaceVariantLow = BasicColor.Basic300
        val onSurfaceVariant = BasicColor.Basic600
        val surfaceContainer = BasicColor.BasicAlpha100
        val error = BasicColor.Red500
        val errorContainer = BasicColor.Red100
        val warning = BasicColor.Yellow600
        val warningContainer = BasicColor.Yellow100
        val onWarningContainer = BasicColor.Yellow800
        val info = BasicColor.Blue500
        val onErrorContainer = BasicColor.Red800
        val transparent = Color.Transparent

        // BottomDialog IconColor
        val bottomDialogSuccess = BasicColor.Green500

        // SnackBarAlert IconBackgroundColor
        val alertSuccess = BasicColor.Green100
        val alertInfo = BasicColor.Blue500
        val alertDefault = BasicColor.Basic200

        // loading 透明用
        val loadingBackground = BasicColor.BasicAlpha500
        val disableButtonText = BasicColor.Basic300
        val InterviewInputBg: Color = Color(0x0F16385A)
        val outline = BasicColor.BasicAlpha100
        val outlineVariantHigh = BasicColor.BasicAlpha200

        // Material Light ColorScheme
        object LightScheme {
            val primary = BasicColor.AppGreen40
            val onPrimary = BasicColor.Basic0
            val primaryContainer = BasicColor.AppGreen90
            val onPrimaryContainer = BasicColor.AppGreen30

            val secondary = BasicColor.Cyan50
            val onSecondary = BasicColor.Basic0
            val secondaryContainer = BasicColor.Cyan90
            val onSecondaryContainer = BasicColor.CyanDark

            val tertiary = BasicColor.Amber50
            val onTertiary = BasicColor.Basic0
            val tertiaryContainer = BasicColor.AmberLight
            val onTertiaryContainer = BasicColor.AmberDark

            val error = BasicColor.Rose50
            val onError = BasicColor.Basic0
            val errorContainer = BasicColor.Rose90
            val onErrorContainer = BasicColor.RoseDark

            val background = BasicColor.Gray98
            val onBackground = BasicColor.Gray05
            val surface = BasicColor.Basic0
            val onSurface = BasicColor.Gray05
            val surfaceVariant = BasicColor.Gray95
            val onSurfaceVariant = BasicColor.Gray50

            val outline = BasicColor.Gray80
            val outlineVariant = BasicColor.Gray90

            val surfaceContainerLowest = BasicColor.Basic0
            val surfaceContainerLow = BasicColor.Gray97
            val surfaceContainer = BasicColor.Gray95
            val surfaceContainerHigh = BasicColor.Gray90
            val surfaceContainerHighest = BasicColor.Gray82
        }

        // Material Dark ColorScheme
        object DarkScheme {
            val primary = BasicColor.TermGreen
            val onPrimary = BasicColor.TermGreenDark
            val primaryContainer = BasicColor.TermGreenBg
            val onPrimaryContainer = BasicColor.TermGreen

            val secondary = BasicColor.TermCyan
            val onSecondary = BasicColor.TermCyanDark
            val secondaryContainer = BasicColor.TermCyanBg
            val onSecondaryContainer = BasicColor.TermCyan

            val tertiary = BasicColor.TermAmber
            val onTertiary = BasicColor.TermAmberDark
            val tertiaryContainer = BasicColor.TermAmberBg
            val onTertiaryContainer = BasicColor.TermAmber

            val error = BasicColor.TermRed
            val onError = BasicColor.TermRedDark
            val errorContainer = BasicColor.TermRedBg
            val onErrorContainer = BasicColor.TermRed

            val background = BasicColor.TermBg
            val onBackground = BasicColor.TermText
            val surface = BasicColor.TermSurface
            val onSurface = BasicColor.TermText
            val surfaceVariant = BasicColor.TermCard
            val onSurfaceVariant = BasicColor.TermSubtle

            val outline = BasicColor.TermBorder
            val outlineVariant = BasicColor.TermBorderDim

            val surfaceContainerLowest = BasicColor.TermBg
            val surfaceContainerLow = BasicColor.TermSurface
            val surfaceContainer = BasicColor.TermCard
            val surfaceContainerHigh = BasicColor.TermCardHigh
            val surfaceContainerHighest = BasicColor.TermBorder
        }
    }

    // 常用高度
    object Height {
        val size0 = 0.dp
        val size1 = 1.dp
        val size2 = 2.dp
        val size3 = 3.dp
        val size4 = 4.dp
        val size6 = 6.dp
        val size8 = 8.dp
        val size10 = 10.dp
        val size12 = 12.dp
        val size14 = 14.dp
        val size16 = 16.dp
        val size20 = 20.dp
        val size24 = 24.dp
        val size30 = 30.dp
        val size32 = 32.dp
        val size40 = 40.dp
        val size48 = 48.dp
        val size50 = 50.dp
        val size56 = 56.dp
        val size60 = 60.dp
        val size64 = 64.dp
        val size72 = 72.dp
        val size80 = 80.dp
        val size88 = 88.dp
        val size140 = 140.dp
        val size144 = 144.dp
        val size240 = 240.dp
        val size250 = 250.dp
        val size400 = 400.dp
    }

    // 常用寬度
    object Width {
        val size1 = 1.dp
        val size2 = 2.dp
        val size4 = 4.dp
        val size6 = 6.dp
        val size8 = 8.dp
        val size10 = 10.dp
        val size12 = 12.dp
        val size16 = 16.dp
        val size20 = 20.dp
        val size24 = 24.dp
        val size30 = 30.dp
        val size32 = 32.dp
        val size42 = 42.dp
        val size52 = 52.dp
        val size56 = 56.dp
        val size64 = 64.dp
        val size66 = 66.dp
        val size76 = 76.dp
        val size80 = 80.dp
        val size96 = 96.dp
        val size160 = 160.dp
    }

    // 常用間距
    object Spacing {
        val size0 = 0.dp
        val size1 = 1.dp
        val size2 = 2.dp
        val size3 = 3.dp
        val size4 = 4.dp
        val size5 = 5.dp
        val size6 = 6.dp
        val size7 = 7.dp
        val size8 = 8.dp
        val size10 = 10.dp
        val size12 = 12.dp
        val size13_5 = 13.5.dp
        val size14 = 14.dp
        val size16 = 16.dp
        val size18 = 18.dp
        val size20 = 20.dp
        val size22 = 22.dp
        val size24 = 24.dp
        val size28 = 28.dp
        val size30 = 30.dp
        val size32 = 32.dp
        val size40 = 40.dp
        val size48 = 48.dp
        val size56 = 56.dp
        val size64 = 64.dp
        val size72 = 72.dp
        val size80 = 80.dp
        val size88 = 88.dp
        val size100 = 100.dp
        val size101 = 101.dp
        val size104 = 104.dp
        val size108 = 108.dp
        val size120 = 120.dp
        val size128 = 128.dp
        val size133 = 133.dp
    }

    // 文字行高度
    object LightHeight {
        val size2 = 2.sp
        val size3 = 3.sp
        val size4 = 4.sp
        val size5 = 5.sp
        val size6 = 6.sp
        val size7 = 7.sp
        val size8 = 8.sp
        val size9 = 9.sp
        val size10 = 10.sp
        val size16 = 16.sp
        val size18 = 18.sp
        var size20 = 20.sp
        val size24 = 24.sp
        val size28 = 28.sp
        val size30 = 30.sp
        val size32 = 32.sp
        val size36 = 36.sp
        val size42 = 42.sp
        val size48 = 48.sp
    }

    // 文字寬度
    object FontWeightSize {
        val size400 = FontWeight(400)
        val size500 = FontWeight(500)
        val size700 = FontWeight(700)
        val size800 = FontWeight(800)
    }

    // 文字大小
    object FontSize {
        val size4 = 4.sp
        val size6 = 6.sp
        val size8 = 8.sp
        val size10 = 10.sp
        val size12 = 12.sp
        val size14 = 14.sp
        val size16 = 16.sp
        val size18 = 18.sp
        val size20 = 20.sp
        val size22 = 22.sp
        val size24 = 24.sp
        val size26 = 26.sp
        val size28 = 28.sp
        val size30 = 30.sp
        val size32 = 32.sp
        val size34 = 34.sp
        val size40 = 40.sp
        val size56 = 56.sp
    }

    // Icon 大小
    object IconSize {
        val size2 = 2.dp
        val size16 = 16.dp
        val size18 = 18.dp
        val size20 = 20.dp
        val size24 = 24.dp
        val size28 = 28.dp
        val size32 = 32.dp
        val size34 = 34.dp
        val size36 = 36.dp
        val size40 = 40.dp
        val size44 = 44.dp
        val size48 = 48.dp
        val size52 = 52.dp
        val size54 = 54.dp
        val size56 = 56.dp
        val size80 = 80.dp
        val size90 = 90.dp
        val size96 = 96.dp
        val size100 = 100.dp
        val size120 = 120.dp
    }

    // 圓角
    object CornerRadius {
        val size0 = 0.dp
        val size2 = 2.dp
        val size4 = 4.dp
        val size8 = 8.dp
        val size10 = 10.dp
        val size12 = 12.dp
        val size14 = 14.dp
        val size16 = 16.dp
        val size20 = 20.dp
        val size24 = 24.dp
        val size28 = 28.dp
        val size30 = 30.dp
        val size32 = 32.dp
        val size36 = 36.dp
        val size40 = 40.dp
        val size48 = 48.dp
        val size50 = 50.dp
        val size100 = 100.dp
        val size999 = 999.dp
    }

    // 陰影
    object ShadowElevation {
        val size1 = 1.dp
        val size2 = 2.dp
        val size4 = 4.dp
        val size8 = 8.dp
        val size12 = 12.dp
        val size16 = 16.dp
    }

    // 外筐
    object BorderWidth {
        val size1 = 1.dp
        val size2 = 2.dp
        val size4 = 4.dp
        val size8 = 8.dp
        val size16 = 16.dp
    }

    // 常用線寬
    object LineWidth {
        val size1 = 1.dp
        val size2 = 2.dp
        val size4 = 4.dp
        val size6 = 6.dp
        val size8 = 8.dp
        val size10 = 10.dp
        val size12 = 12.dp
        val size16 = 16.dp
    }
}
