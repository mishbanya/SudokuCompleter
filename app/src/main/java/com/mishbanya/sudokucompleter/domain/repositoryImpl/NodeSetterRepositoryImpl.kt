package com.mishbanya.sudokucompleter.domain.repositoryImpl

import com.mishbanya.sudokucompleter.data.SudokuField
import com.mishbanya.sudokucompleter.data.SudokuNode
import com.mishbanya.sudokucompleter.data.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.repository.NodeSetterRepository
import com.mishbanya.sudokucompleter.domain.repository.SudokuValidityChecker

class NodeSetterRepositoryImpl(
    private val sudokuValidityChecker: SudokuValidityChecker
): NodeSetterRepository {
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
            newField.setNode(row,col, SudokuNode(value,SudokuNodeType.Filled))
            return newField
        }
        return null
    }
}