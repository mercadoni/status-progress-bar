package io.instaleap.statusprogressbar

import android.graphics.Color

sealed class StateColor {

    abstract val color: String

    object Added : StateColor() {
        override val color: String
            get() = ColorBar.GREEN.hexColor
    }

    object Pending : StateColor() {
        override val color: String
            get() = ColorBar.GRAY.hexColor
    }

    object Removed : StateColor() {
        override val color: String
            get() = ColorBar.YELLOW.hexColor
    }
}