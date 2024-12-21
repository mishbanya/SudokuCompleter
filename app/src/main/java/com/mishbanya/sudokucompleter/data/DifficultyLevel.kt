package com.mishbanya.sudokucompleter.data

enum class DifficultyLevel {
    EASY,
    MEDIUM,
    HARD;

    fun toIndicator(): String {
        return when (this) {
            EASY -> "E"
            MEDIUM -> "M"
            HARD -> "H"
        }
    }
    override fun toString(): String {
        return when (this) {
            EASY -> "легкий"
            MEDIUM -> "средний"
            HARD -> "сложный"
        }
    }
}