package io.instaleap.statusprogressbar

import android.graphics.Color

enum class StateColor(val color: Int) {
    ADDED(Color.GREEN),
    PENDING(Color.GRAY),
    REMOVED(Color.YELLOW)
}