package com.raychang.locker.ui.theme

import androidx.compose.ui.graphics.Color

// 基礎顏色
object BasicColor {
    // 黑色
    val Basic0: Color = Color(0xFFFFFFFF)
    val Basic50: Color = Color(0xFFF9FAFB)
    val Basic100: Color = Color(0xFFF1F3F5)
    val Basic200: Color = Color(0xFFE1E3E5)
    val Basic300: Color = Color(0xFFC4C7C9)
    val Basic500: Color = Color(0xFF8E9193)
    val Basic600: Color = Color(0xFF747779)
    val Basic700: Color = Color(0xFF5C5F61)
    val Basic900: Color = Color(0xFF1F2123)
    val Basic1000: Color = Color(0xFF000000)

    // 透明色
    val BasicAlpha50: Color = Color(0x0837597B)
    val BasicAlpha100: Color = Color(0x0F16385A)
    val BasicAlpha200: Color = Color(0x1F051524)
    val BasicAlpha300: Color = Color(0x3D09171F)
    val BasicAlpha500: Color = Color(0x73040C10)
    val BasicAlpha600: Color = Color(0x8C02090D)
    val BasicAlpha700: Color = Color(0xA300060A)
    val BasicAlpha900: Color = Color(0xE0000305)

    // 白色透明
    val WhiteAlpha100: Color = Color(0x3DFFFFFF)
    val WhiteAlpha500: Color = Color(0x8FFFFFFF)
    val WhiteAlpha700: Color = Color(0xB8FFFFFF)
    val WhiteAlpha800: Color = Color(0xCCFFFFFF)
    val WhiteAlpha900: Color = Color(0xE0FFFFFF)

    // 綠色
    val Green50: Color = Color(0xFFECF7F3)
    val Green100: Color = Color(0xFFC9E8DF)
    val Green300: Color = Color(0xFF81CAB7)
    val Green500: Color = Color(0xFF00AA90)
    val Green600: Color = Color(0xFF00886F)
    val Green700: Color = Color(0xFF00654F)
    val Green800: Color = Color(0xFF004330)
    val Green900: Color = Color(0xFF002414)

    // 紅色
    val Red50: Color = Color(0xFFFEEFEE)
    val Red100: Color = Color(0xFFFFD1D0)
    val Red400: Color = Color(0xFFFD747A)
    val Red500: Color = Color(0xFFF64B5C)
    val Red600: Color = Color(0xFFCE1D3C)
    val Red700: Color = Color(0xFFA4001B)
    val Red800: Color = Color(0xFF7A0000)

    val Red900: Color = Color(0xFF510000)

    // 黃色
    val Yellow50: Color = Color(0xFFFFF6E2)
    val Yellow100: Color = Color(0xFFFEF1CE)
    val Yellow400: Color = Color(0xFFFBD462)
    val Yellow500: Color = Color(0xFFFAC919)
    val Yellow600: Color = Color(0xFFCB9B00)
    val Yellow700: Color = Color(0xFF9B6C00)
    val Yellow800: Color = Color(0xFF6D3F00)
    val Yellow900: Color = Color(0xFF411400)

    // 藍色
    val Blue50: Color = Color(0xFFE9F1FF)
    val Blue100: Color = Color(0xFFC4D8FF)
    val Blue500: Color = Color(0xFF316AF6)
    val Blue600: Color = Color(0xFF164AD5)
    val Blue700: Color = Color(0xFF002DB2)
    val Blue800: Color = Color(0xFF00218F)

    // 橘色
    val Orange50: Color = Color(0xFFFFF2EC)
    val Orange100: Color = Color(0xFFFFDCCB)
    val Orange300: Color = Color(0xFFFFAD89)
    val Orange500: Color = Color(0xFFFF6500)
    val Orange600: Color = Color(0xFFCD5400)
    val Orange700: Color = Color(0xFFA22A00)
    val Orange800: Color = Color(0xFF770000)
    val Orange900: Color = Color(0xFF650000)

    // 紫色
    val Purple100: Color = Color(0XFFD8D2FF)
    val Purple800: Color = Color(0XFF380094)

    val YellowStops: Color = Color(0xFFF77A3B)

    // App 主題綠色 (與上方 Green 為不同色票)
    val AppGreen95: Color = Color(0xFFF0FDF4)
    val AppGreen90: Color = Color(0xFFDCFCE7)
    val AppGreen80: Color = Color(0xFFBBF7D0)
    val AppGreen50: Color = Color(0xFF22C55E)
    val AppGreen40: Color = Color(0xFF16A34A)
    val AppGreen30: Color = Color(0xFF15803D)

    // 青色
    val Cyan90: Color = Color(0xFFCFFAFE)
    val Cyan80: Color = Color(0xFFA5F3FC)
    val Cyan50: Color = Color(0xFF06B6D4)
    val CyanDark: Color = Color(0xFF084D56)

    // 琥珀色
    val Amber50: Color = Color(0xFFF59E0B)
    val AmberLight: Color = Color(0xFFFEF3C7)
    val AmberDark: Color = Color(0xFF78350F)

    // 玫瑰色
    val Rose50: Color = Color(0xFFF43F5E)
    val Rose90: Color = Color(0xFFFFE4E6)
    val RoseDark: Color = Color(0xFF9F1239)

    // 灰色
    val Gray05: Color = Color(0xFF0A0A0A)
    val Gray10: Color = Color(0xFF171717)
    val Gray50: Color = Color(0xFF6B7280)
    val Gray80: Color = Color(0xFFD1D5DB)
    val Gray82: Color = Color(0xFFDCDEE2)
    val Gray90: Color = Color(0xFFE5E7EB)
    val Gray95: Color = Color(0xFFF3F4F6)
    val Gray97: Color = Color(0xFFF7F7F8)
    val Gray98: Color = Color(0xFFFAFAFA)

    // Terminal 深色主題
    val TermBg: Color = Color(0xFF0D1117)
    val TermSurface: Color = Color(0xFF161B22)
    val TermCard: Color = Color(0xFF1C2128)
    val TermCardHigh: Color = Color(0xFF242930)
    val TermBorder: Color = Color(0xFF30363D)
    val TermBorderDim: Color = Color(0xFF21262D)
    val TermMuted: Color = Color(0xFF484F58)
    val TermText: Color = Color(0xFFE6EDF3)
    val TermSubtle: Color = Color(0xFF8B949E)
    val TermGreen: Color = Color(0xFF3FB950)
    val TermGreenDim: Color = Color(0xFF238636)
    val TermGreenBg: Color = Color(0xFF122117)
    val TermGreenDark: Color = Color(0xFF0A2009)
    val TermCyan: Color = Color(0xFF58D1DB)
    val TermCyanDark: Color = Color(0xFF0A2427)
    val TermCyanBg: Color = Color(0xFF112A2E)
    val TermAmber: Color = Color(0xFFD29922)
    val TermAmberDark: Color = Color(0xFF261A00)
    val TermAmberBg: Color = Color(0xFF2A2010)
    val TermRed: Color = Color(0xFFF85149)
    val TermRedBg: Color = Color(0xFF2D1214)
    val TermRedDark: Color = Color(0xFF2D0A0A)
}
