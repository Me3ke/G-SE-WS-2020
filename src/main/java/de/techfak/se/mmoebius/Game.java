package de.techfak.se.mmoebius;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Game {
    String[] args;
    static final int SYS_EXIT_FAILED = 100;
    static final char[][] map = new char[7][15];
    Board board;

    public Game(String ... args) {
        this.args = args;
    }

    public void createBoard(){
        System.out.println("Welcome to encore");
        if (args.length == 0) {
            System.out.println("<100> No program arguments given. Type -f <filename>");
        } else if (args[0].equals("-f")) {
            File file = new File(args[1]);
            if (file.isFile() && file.canRead()) {
                String row;
                int rowCount = 0;
                int colCount = 0;
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                    while ((row = reader.readLine()) != null) {
                        if(row.length() != 15) {
                            throw new InvalidBoardLayout("Invalid Board Layout <101>");
                        }
                        for (colCount = 0; colCount < row.length(); colCount++) {
                            map[rowCount][colCount] = row.charAt(colCount);
                        }
                        rowCount++;
                    }
                    System.out.println("Row Count: " + rowCount);
                    System.out.println("Column Count: " + colCount);
                    System.out.println("The size of the board is therefore valid");
                } catch (IOException e) {
                    System.out.println("Source file is not valid. Check documentation for further information");
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidBoardLayout ("Invalid Board Layout <101>");
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Board board = new Board(map);
                this.board = board;
                board.printBoard();
                return;
            }
            System.out.println("<100> no valid file found with filename: " + file.getName()); // Exception here?
        } else {
            System.out.println("<100> unknown program argument. Type -f <filename>");
        }
        System.exit(SYS_EXIT_FAILED);
    }

    public void play() {
        Player player1 = new Player(1,board);
        int escParameter;
        do {
            System.out.println("Type in your play move Player"+ player1.getPlayerNumber() + ": ");
            escParameter = player1.playMove();
        }while(escParameter != 0);
    }
}

