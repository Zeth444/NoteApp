package com.example.notas.util

import androidx.annotation.StringRes
import com.example.notas.R

/** Los textos viven en strings.xml, nunca en el código. */
@StringRes
fun ErrorType.toMessageRes(): Int = when (this) {
    ErrorType.NETWORK -> R.string.error_network
    ErrorType.UNAUTHORIZED -> R.string.error_unauthorized
    ErrorType.SERVER -> R.string.error_server
    ErrorType.UNKNOWN -> R.string.error_unknown
}