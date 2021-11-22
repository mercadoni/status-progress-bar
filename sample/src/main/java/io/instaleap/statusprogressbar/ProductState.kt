package io.instaleap.statusprogressbar

private const val PENDING: String = "pending"
private const val ADDED: String = "added"
private const val REMOVED: String = "removed"

sealed class ProductState {
    abstract val rawType: String

    object Added : ProductState() {
        override val rawType: String
            get() = ADDED
    }

    object Removed : ProductState() {
        override val rawType: String
            get() = REMOVED
    }

    object Pending : ProductState() {
        override val rawType: String
            get() = PENDING
    }
}