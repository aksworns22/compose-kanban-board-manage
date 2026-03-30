package woowacourse.kanban.board.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ColorPalette(
    val w50: Color,
    val w100: Color,
    val w200: Color,
    val w300: Color,
    val w400: Color,
    val w500: Color,
    val w600: Color,
    val w700: Color,
    val w800: Color,
    val w900: Color,
) {
    companion object {
        val unspecified = ColorPalette(
            w50 = Color.Unspecified,
            w100 = Color.Unspecified,
            w200 = Color.Unspecified,
            w300 = Color.Unspecified,
            w400 = Color.Unspecified,
            w500 = Color.Unspecified,
            w600 = Color.Unspecified,
            w700 = Color.Unspecified,
            w800 = Color.Unspecified,
            w900 = Color.Unspecified,
        )
    }
}

data class CustomColors(
    val white: Color,
    val black: Color,
    val blue: ColorPalette,
    val gray: ColorPalette,
    val green: ColorPalette,
    val orange: ColorPalette,
    val purple: ColorPalette,
    val red: ColorPalette,
)

val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        white = Color.Unspecified,
        black = Color.Unspecified,
        blue = ColorPalette.unspecified,
        gray = ColorPalette.unspecified,
        green = ColorPalette.unspecified,
        orange = ColorPalette.unspecified,
        purple = ColorPalette.unspecified,
        red = ColorPalette.unspecified,
    )
}

@Composable
fun CustomTheme(content: @Composable () -> Unit) {
    val customColors = CustomColors(
        white = Color.White,
        black = Color.Black,
        blue = ColorPalette(
            w50 = Color(0xffEFF6FF),
            w100 = Color(0xffEEF2FF),
            w200 = Color(0xffBEDBFF),
            w300 = Color(0xff155DFC),
            w400 = Color(0xff1447E6),
            w500 = Color(0xFF0000FF),
            w600 = Color(0xff4a5565),
            w700 = Color(0xff364153),
            w800 = Color.Unspecified,
            w900 = Color.Unspecified,
        ),
        gray = ColorPalette(
            w50 = Color(0xffF9FAFB),
            w100 = Color(0xffE5E7EB),
            w200 = Color(0xffAAAAAA),
            w300 = Color(0xff6A7282),
            w400 = Color(0xFF888888),
            w500 = Color(0xff49454F),
            w600 = Color(0xff101828),
            w700 = Color.Unspecified,
            w800 = Color.Unspecified,
            w900 = Color.Unspecified,
        ),
        green = ColorPalette(
            w50 = Color(0xffF0FDF4),
            w100 = Color(0xffB9F8CF),
            w200 = Color(0xff00A63E),
            w300 = Color.Unspecified,
            w400 = Color.Unspecified,
            w500 = Color.Unspecified,
            w600 = Color.Unspecified,
            w700 = Color.Unspecified,
            w800 = Color.Unspecified,
            w900 = Color.Unspecified,
        ),
        orange = ColorPalette(
            w50 = Color(0xffFFFBEB),
            w100 = Color(0xffFEE685),
            w200 = Color(0xffE17100),
            w300 = Color.Unspecified,
            w400 = Color.Unspecified,
            w500 = Color.Unspecified,
            w600 = Color.Unspecified,
            w700 = Color.Unspecified,
            w800 = Color.Unspecified,
            w900 = Color.Unspecified,
        ),
        purple = ColorPalette(
            w50 = Color(0xff432DD7),
            w100 = Color(0xFF4F39F6),
            w200 = Color(0xffA7A4BC),
            w300 = Color(0xff79747E),
            w400 = Color.Unspecified,
            w500 = Color.Unspecified,
            w600 = Color.Unspecified,
            w700 = Color.Unspecified,
            w800 = Color.Unspecified,
            w900 = Color.Unspecified,
        ),
        red = ColorPalette(
            w50 = Color(0xFFFF0000),
            w100 = Color(0xff838383),
            w200 = Color.Unspecified,
            w300 = Color.Unspecified,
            w400 = Color.Unspecified,
            w500 = Color.Unspecified,
            w600 = Color.Unspecified,
            w700 = Color.Unspecified,
            w800 = Color.Unspecified,
            w900 = Color.Unspecified,
        ),
    )
    CompositionLocalProvider(
        LocalCustomColors provides customColors,
        content = content,
    )
}

object CustomTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current
}
