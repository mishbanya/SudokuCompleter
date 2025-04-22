package com.mishbanya.sudokucompleter.data.sudoku

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
    fun toCellsCount(): Int {
        return when (this) {
            EASY -> 40
            MEDIUM -> 35
            HARD -> 30
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