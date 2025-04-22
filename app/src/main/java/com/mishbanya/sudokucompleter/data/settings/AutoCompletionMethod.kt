package com.mishbanya.sudokucompleter.data.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mishbanya.sudokucompleter.R
import kotlinx.serialization.Serializable

@Serializable
enum class AutoCompletionMethod {
    BACKTRACKING,
    CONSTRAINT_PROPAGATION,
    DANCING_LINKS_X;

    @Composable
    fun toStringRes(): String {
        return when(this){
            BACKTRACKING -> stringResource(R.string.method_backtracking)
            CONSTRAINT_PROPAGATION -> stringResource(R.string.method_propagation)
            DANCING_LINKS_X -> stringResource(R.string.method_x)
        }
    }
}