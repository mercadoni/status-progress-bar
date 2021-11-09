package io.instaleap.statusprogressbar

import android.graphics.Color

sealed class StateColor {

    abstract val color: Int

    object Added : StateColor() {
        override val color: Int
            get() = Color.GREEN
    }

    object Pending : StateColor() {
        override val color: Int
            get() = Color.GRAY
    }

    object Removed : StateColor() {
        override val color: Int
            get() = Color.YELLOW
    }
}