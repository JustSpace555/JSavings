package ru.jsavings.presentation.extension

import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Round double by precision = 2
 * @return Rounded double
 *
 * @author JustSpace
 */
fun Double.toUiView() = BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toString()

/**
 * Choose text color according to background color
 * @return True if text color must be black, false if text must be white
 *
 * @author JustSpace
 */
fun Int.isTextBlack() = red * 0.299 + green * 0.587 + blue * 0.114 > 186