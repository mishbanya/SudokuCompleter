package com.mishbanya.sudokucompleter.data.settings

import kotlinx.serialization.Serializable

@Serializable
enum class AutoCompletionMethod {
    BACKTRACKING,
    CONSTRAINT_PROPAGATION,
    HEURISTIC_BASED_SEARCH,
    DANCING_LINKS
}