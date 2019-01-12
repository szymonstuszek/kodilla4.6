package com.kodilla.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuGame {
   private Scanner scanner;
   private SudokuBoard sudokuBoard;
   private UserInput userInput;
   private Algorithm algorithm;
   private List<SudokuBoard> backtrack = new ArrayList<>();

   private boolean sudokuResolved = false;

    public SudokuGame() {
        this.scanner =  new Scanner(System.in);
        this.sudokuBoard = new SudokuBoard();
        this.userInput = new UserInput(scanner);
        this.algorithm = new Algorithm(sudokuBoard);

        runGame();
    }

    private void runGame() {
        sudokuBoard.initializeBoard();

        while(!sudokuResolved) {
            System.out.println(sudokuBoard.toString());
            System.out.println(Constants.INSTRUCTIONS);

            String input = scanner.nextLine();

            if (userInput.isInputValid(input)) {
                int action = userInput.chooseAction(input);

                switch (action) {
                    case 0:
                        algorithm.solveSudoku();
                        sudokuResolved = true;
                        break;

                    case 1:
                        int column = userInput.getColumnIndex(input);
                        int row = userInput.getRowIndex(input);
                        int value = userInput.getValue(input);

                        addIntoBacktrack();
                        sudokuBoard.setValueOnBoard(column, row, value);
                        algorithm.updateSudokuBoard();

                        sudokuResolved = algorithm.isSudokuResolved(sudokuBoard);
                        break;

                    case 2:
                        column = userInput.getColumnIndex(input);
                        row = userInput.getRowIndex(input);

                        sudokuBoard.checkAvailableValues(column, row);
                        break;

                    case 3:
                        System.out.println("Backtrack:");
                        SudokuBoard boardToDisplay = backtrack.get(backtrack.size()-1);
                        System.out.println(boardToDisplay);
                        break;

                        //test case how to write in tests?
                    case 4:
                        sudokuBoard = Helper.createFilledOutBoard();
                        sudokuBoard.getBoard().get(8).getElements().get(8).setValue(-1);
                        algorithm.setSudokuBoard(sudokuBoard);
                        algorithm.updateSudokuBoard();
                        break;

                    default:
                        System.out.println("No such action");
                        break;
                }
            }
        }

        System.out.println(sudokuBoard.toString());
        System.out.println(Constants.sudokuResolved);
    }

    private void addIntoBacktrack() {
        SudokuBoard backtrackBoard = null;
        try {
            backtrackBoard = sudokuBoard.deepCopy();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        backtrack.add(backtrackBoard);
    }

    //check if still playing
    //put into user input?
    public boolean finishGame() {
        System.out.println("Do you still want to play?");
        System.out.println("Type 'no' to quit");
        String response = scanner.nextLine();
        System.out.println("You have chosen: " + response);

        if(response.equals("no")) {
            return true;
        } else {
            return false;
        }
    }

    public void setSudokuBoard(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }
}