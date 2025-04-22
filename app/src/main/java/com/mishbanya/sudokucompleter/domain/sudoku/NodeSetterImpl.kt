package com.mishbanya.sudokucompleter.domain.sudoku

import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNodeType

class NodeSetterImpl(
    private val sudokuValidityChecker: SudokuValidityChecker
): NodeSetter {
    override fun setNode(sudokuField: SudokuField, row: Int, col: Int, value: Int?): SudokuField? {

        if(sudokuField.getNode(row, col).flag == SudokuNodeType.Initial) return null

        val newField = sudokuField.deepCopy()

        if(value==null){
            newField.setNode(row,col, SudokuNode(null,SudokuNodeType.Unfilled))
            return newField
        }

        if(value in 1..9){
            if(!sudokuValidityChecker.isValidMove(sudokuField.field, row, col, value)){
                return null
            }
            newField.setNode(row,col, SudokuNode(value,SudokuNodeType.FilledManually))
            return newField
        }
        return null
    }
}