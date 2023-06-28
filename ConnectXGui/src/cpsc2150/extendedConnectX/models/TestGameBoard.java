package cpsc2150.extendedConnectX.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestGameBoard {
    //a helper method that creates a new instance of GameBoardMem with the specified dimensions.
    private IGameBoard gb(int r, int c, int w) {

        return new GameBoard(r, c, w);
    }
    //a helper method that takes a 2D char array representing the board and converts it to a string representation.
    private String boardArrayString(char[][] boardArray, int r, int c) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < c; i++) {
            if (i < 10) {
                output.append("| ").append(i);
            } else {
                output.append("|").append(i);
            }
        }

        output.append("|\n");

        for (int i = r - 1; i >= 0; i--) {
            for (int j = 0; j < c; j++) {
                output.append("|").append(boardArray[i][j]).append(" ");
            }

            output.append("|\n");
        }

        return output.toString();

    }
    private char[][] createEmptyBoardArray(int numRows, int numCols) {
        char[][] boardArray = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                boardArray[i][j] = ' ';
            }
        }
        return boardArray;
    }
    //test case that checks whether the GameBoardMem constructor can create a board with the smallest possible size (3 rows x 3 columns).
    @Test
    public void testConstructor_minimum_size() {
        char[][] boardArray = createEmptyBoardArray(3, 3);


        IGameBoard board = gb(3, 3, 3);

        assertEquals(board.toString(), boardArrayString(boardArray, 3, 3));
        assertEquals(3, board.getNumToWin());
    }
    //test case that checks whether the GameBoardMem constructor can create a board with the largest possible size (100 rows x 100 columns).
    @Test
    public void testConstructor_maximum_size() {
        char[][] boardArray;
        boardArray = new char[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                boardArray[i][j] = ' ';
            }
        }

        IGameBoard board = gb(100, 100, 25);

        assertEquals(board.toString(), boardArrayString(boardArray, 100, 100));
        assertEquals(25, board.getNumToWin());
    }
    //test case that checks whether the GameBoardMem constructor can create a board with unequal rows and columns (30 rows x 20 columns).
    @Test
    public void testConstructor_mismatch_rows_and_columns() {
        char[][] boardArray = createEmptyBoardArray(30, 20);


        IGameBoard board = gb(30, 20, 3);

        assertEquals(board.toString(), boardArrayString(boardArray, 30, 20));
        assertEquals(3, board.getNumToWin());
    }
    // test checks if the checkIfFree method correctly identifies a free column on an empty board. It initializes an empty board, places a token in a different column and checks that the method returns true for the empty column.
    @Test
    public void testCheckIfFree_empty() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        IGameBoard board = gb(5, 5, 3);
        assertTrue(board.checkIfFree(2));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test checks if the checkIfFree method correctly identifies a free column on a board with one token. It initializes an empty board, places a token in one column, checks that the method returns true for a different column and that the board has the expected token in the correct column.
    @Test
    public void testCheckIfFree_single_token() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        IGameBoard board = gb(5, 5, 3);
        board.placeToken('X', 4);
        boardArray[0][4] = 'X';
        assertTrue(board.checkIfFree(4));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test checks if the checkIfFree method correctly identifies a full column on a board with tokens. It initializes a board with a full column of tokens and checks that the method returns false for that column.
    @Test
    public void testCheckIfFree_full_column() {
        char[][] boardArray;
        boardArray = new char[5][5];
        IGameBoard board = gb(5, 5, 3);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 2) {
                    boardArray[i][2] = 'X';
                    board.placeToken('X', 2);
                } else {
                    boardArray[i][j] = ' ';
                }
            }
        }

        assertFalse(board.checkIfFree(2));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));

    }
    //test checks if the checkHorizWin method works correctly when there is no horizontal win on an empty game board. It creates an empty 5x5 game board, places no tokens, and asserts that checkHorizWin returns false and the board is still empty.
    @Test
    public void testCheckHorizontalWin_empty() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        IGameBoard board = gb(5, 5, 3);

        assertFalse(board.checkHorizWin(new BoardPosition(2, 2), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));

    }
    //test checks if the checkHorizWin method correctly detects a horizontal win on a game board with three X tokens in a row on the top row. It creates a 5x5 game board, places three X tokens on the top row, and asserts that checkHorizWin returns true and the game board matches the expected state with three X tokens in a row on the top row.
    @Test
    public void testCheckHorizontalWin_normal_test() {
        char[][] boardArray;
        boardArray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardArray[i][j] = ' ';
                if (i == 0 && j < 3)
                    boardArray[i][j] = 'X';
            }
        }

        IGameBoard board = gb(5, 5, 3);
        for (int i = 0; i < 3; i++) {
            board.placeToken('X', i);
        }

        assertTrue(board.checkHorizWin(new BoardPosition(0, 2), 'X'));
        assertTrue(board.toString().equals(boardArrayString(boardArray, 5, 5)));
    }
    //test checks if the checkHorizWin method correctly detects a horizontal win on a game board with four X tokens in a row on the top row, which is one more than the three required for a win. It creates a 5x5 game board, places four X tokens on the top row, and asserts that checkHorizWin returns true and the game board matches the expected state with four X tokens in a row on the top row.
    @Test
    public void testCheckHorizontalWin_more_than_needed() {
        char[][] boardArray;
        boardArray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardArray[i][j] = ' ';
                if (i == 0 && j < 4)
                    boardArray[i][j] = 'X';
            }
        }

        IGameBoard board = gb(5, 5, 3);
        for (int i = 0; i < 4; i++) {
            board.placeToken('X', i);
        }

        assertTrue(board.checkHorizWin(new BoardPosition(0, 3), 'X'));
        assertTrue(board.toString().equals(boardArrayString(boardArray, 5, 5)));
    }
    //test checks if the checkHorizWin method correctly detects an incomplete horizontal win on a game board with three X tokens in a row on the top row, but one token in the middle is an O instead of an X. It creates a 5x5 game board, places three X tokens and one O token on the top row, and asserts that checkHorizWin returns false and the game board matches the expected state with three X tokens in a row on the top row, but the middle token is an O.
    @Test
    public void testCheckHorizontalWin_incomplete() {
        char[][] boardArray;
        boardArray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardArray[i][j] = ' ';
                if (i == 0 && j < 4)
                    boardArray[i][j] = 'X';
            }
        }

        boardArray[0][2] = 'O';

        IGameBoard board = gb(5, 5, 3);
        for (int i = 0; i < 4; i++) {
            if (i == 2)
                board.placeToken('O', i);
            else
                board.placeToken('X', i);

        }

        assertTrue(!board.checkHorizWin(new BoardPosition(0, 3), 'X'));
        assertTrue(board.toString().equals(boardArrayString(boardArray, 5, 5)));
    }
    //test case checks if the checkVertWin method returns false when there is no vertical win on the game board. It creates a 5x5 game board with no tokens placed on it and then calls the checkVertWin method with a position in the middle of the board and a token 'X'. The expected outcome is that the method returns false and the game board remains unchanged.
    @Test
    public void testCheckVerticalWin_no_win() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        IGameBoard board = gb(5, 5, 3);

        assertTrue(!board.checkVertWin(new BoardPosition(2, 2), 'X'));
        assertTrue(board.toString().equals(boardArrayString(boardArray, 5, 5)));

    }
    // test case checks if the checkVertWin method correctly detects a vertical win on the game board. It creates a 5x5 game board with three 'X' tokens in a column and calls the checkVertWin method with the position of the middle token and a token 'X'. The expected outcome is that the method returns true and the game board remains unchanged.
    @Test
    public void testCheckVerticalWin_win() {
        char[][] boardArray;
        boardArray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardArray[i][j] = ' ';
                if (i < 3 && j == 2)
                    boardArray[i][j] = 'X';
            }
        }

        IGameBoard board = gb(5, 5, 3);
        for (int i = 0; i < 3; i++) {
            board.placeToken('X', 2);
        }

        assertTrue(board.checkVertWin(new BoardPosition(2, 2), 'X'));
        assertTrue(board.toString().equals(boardArrayString(boardArray, 5, 5)));
    }
    //test case checks if the checkVertWin method correctly detects a vertical win on the game board when there are more tokens in the column than needed for a win. It creates a 5x5 game board with four 'X' tokens in a column and calls the checkVertWin method with the position of the bottom token and a token 'X'. The expected outcome is that the method returns true and the game board remains unchanged.
    @Test
    public void testCheckVerticalWin_more_than_needed() {
        char[][] boardArray;
        boardArray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardArray[i][j] = ' ';
                if (i < 4 && j == 2)
                    boardArray[i][j] = 'X';
            }
        }

        IGameBoard board = gb(5, 5, 3);
        for (int i = 0; i < 4; i++) {
            board.placeToken('X', 2);
        }

        assertTrue(board.checkVertWin(new BoardPosition(3, 2), 'X'));
        assertTrue(board.toString().equals(boardArrayString(boardArray, 5, 5)));
    }
    //test case checks if the checkVertWin method correctly detects that there is no vertical win on the game board when the tokens in the column are not all the same. It creates a 5x5 game board with three 'X' tokens and one 'O' token in a column and calls the checkVertWin method with the position of the bottom token and a token 'X'. The expected outcome is that the method returns false and the game board remains unchanged.
    @Test
    public void testCheckVerticalWin_incomplete() {
        char[][] boardArray;
        boardArray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardArray[i][j] = ' ';
                if (i < 4 && j == 2)
                    boardArray[i][j] = 'X';
            }
        }

        boardArray[2][2] = 'O';

        IGameBoard board = gb(5, 5, 3);
        for (int i = 0; i < 4; i++) {
            if (i == 2)
                board.placeToken('O', 2);
            else
                board.placeToken('X', 2);

        }

        assertFalse(board.checkVertWin(new BoardPosition(3, 2), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case creates an empty board and checks whether checkDiagWin correctly identifies that there is no diagonal win for player 'X' starting from the top-left corner.
    @Test
    public void testCheckDiagonalWin_empty() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        IGameBoard board = gb(5, 5, 3);

        assertFalse(board.checkDiagWin(new BoardPosition(0, 0), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case sets up a board where player 'X' has made a diagonal sequence of three tokens starting from the top-left corner, and then checks whether checkDiagWin correctly identifies this as a win for 'X'.
    @Test
    public void testCheckDiagonalWin_start_left_barely_win() {
        char[][] boardArray = createEmptyBoardArray(4, 4);


        boardArray[0][0] = 'X';
        boardArray[0][1] = 'O';
        boardArray[1][1] = 'X';
        boardArray[0][2] = 'O';
        boardArray[1][2] = 'X';
        boardArray[1][0] = 'O';
        boardArray[2][2] = 'X';

        IGameBoard board = gb(4, 4, 3);

        board.placeToken('X', 0);
        board.placeToken('O', 1);
        board.placeToken('X', 1);
        board.placeToken('O', 2);
        board.placeToken('X', 2);
        board.placeToken('O', 0);
        board.placeToken('X', 2);

        assertTrue(board.checkDiagWin(new BoardPosition(2, 2), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 4, 4));

    }
    //test case sets up a board where player 'X' has made a diagonal sequence of four tokens starting from the top-left corner, and then checks whether checkDiagWin correctly identifies this as a win for 'X'. This is distinct from the previous test case because it checks that the method correctly handles a longer diagonal sequence.
    @Test
    public void testCheckDiagonalWin_start_left_more_than_needed() {
        char[][] boardArray = createEmptyBoardArray(4, 4);


        boardArray[0][0] = 'X';
        boardArray[0][1] = 'O';
        boardArray[1][1] = 'X';
        boardArray[0][2] = 'O';
        boardArray[1][2] = 'X';
        boardArray[1][0] = 'O';
        boardArray[2][2] = 'X';
        boardArray[0][3] = 'X';
        boardArray[1][3] = 'O';
        boardArray[2][3] = 'X';
        boardArray[3][3] = 'X';

        IGameBoard board = gb(4, 4, 3);

        board.placeToken('X', 0);
        board.placeToken('O', 1);
        board.placeToken('X', 1);
        board.placeToken('O', 2);
        board.placeToken('X', 2);
        board.placeToken('O', 0);
        board.placeToken('X', 2);
        board.placeToken('X', 3);
        board.placeToken('O', 3);
        board.placeToken('X', 3);
        board.placeToken('X', 3);

        assertTrue(board.checkDiagWin(new BoardPosition(3, 3), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 4, 4));
    }
    //test case sets up a board where player 'X' has made a diagonal sequence of two tokens starting from the top-left corner, but then 'O' has interrupted the sequence by playing a token. The test then checks whether checkDiagWin correctly identifies that there is no diagonal win for 'X' starting from the given position.
    @Test
    public void testCheckDiagonalWin_start_left_not_enough() {
        char[][] boardArray = createEmptyBoardArray(4, 4);


        boardArray[0][0] = 'X';
        boardArray[0][1] = 'O';
        boardArray[1][1] = 'X';
        boardArray[0][2] = 'O';
        boardArray[1][2] = 'X';
        boardArray[1][0] = 'O';
        boardArray[2][2] = 'O';
        boardArray[0][3] = 'X';
        boardArray[1][3] = 'O';
        boardArray[2][3] = 'X';
        boardArray[3][3] = 'O';

        IGameBoard board = gb(4, 4, 3);

        board.placeToken('X', 0);
        board.placeToken('O', 1);
        board.placeToken('X', 1);
        board.placeToken('O', 2);
        board.placeToken('X', 2);
        board.placeToken('O', 0);
        board.placeToken('O', 2);
        board.placeToken('X', 3);
        board.placeToken('O', 3);
        board.placeToken('X', 3);
        board.placeToken('O', 3);

        assertFalse(board.checkDiagWin(new BoardPosition(2, 3), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 4, 4));
    }
    //test case sets up a board where player 'X' has made a diagonal sequence of three tokens starting from the top-right corner, and then checks whether checkDiagWin correctly identifies this as a win for 'X'. This is distinct from the second test case because it checks that the method correctly handles diagonal sequences that start from the top-right corner instead of the top-left corner.
    @Test
    public void testCheckDiagonalWin_start_right_barely_win() {
        char[][] boardArray = createEmptyBoardArray(4, 4);


        boardArray[0][3] = 'X';
        boardArray[0][2] = 'O';
        boardArray[1][2] = 'X';
        boardArray[0][1] = 'O';
        boardArray[1][1] = 'O';
        boardArray[2][1] = 'X';

        IGameBoard board = gb(4, 4, 3);

        board.placeToken('X', 3);
        board.placeToken('O', 2);
        board.placeToken('X', 2);
        board.placeToken('O', 1);
        board.placeToken('O', 1);
        board.placeToken('X', 1);

        assertTrue(board.checkDiagWin(new BoardPosition(2, 1), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 4, 4));

    }
    //sets up a board where there is a diagonal win that starts to the right when there are more than enough tokens in a row, and then calls checkDiagWin() with the appropriate arguments to check if it returns true. Finally, it asserts that the board is in the expected state after the moves have been made.
    @Test
    public void testCheckDiagonalWin_start_right_extra_win() {
        char[][] boardArray = createEmptyBoardArray(4, 4);


        boardArray[0][3] = 'X';
        boardArray[0][2] = 'O';
        boardArray[1][2] = 'X';
        boardArray[0][1] = 'O';
        boardArray[1][1] = 'O';
        boardArray[2][1] = 'X';
        boardArray[0][0] = 'X';
        boardArray[1][0] = 'O';
        boardArray[2][0] = 'X';
        boardArray[3][0] = 'X';

        IGameBoard board = gb(4, 4, 3);

        board.placeToken('X', 3);
        board.placeToken('O', 2);
        board.placeToken('X', 2);
        board.placeToken('O', 1);
        board.placeToken('O', 1);
        board.placeToken('X', 1);
        board.placeToken('X', 0);
        board.placeToken('O', 0);
        board.placeToken('X', 0);
        board.placeToken('X', 0);

        assertTrue(board.checkDiagWin(new BoardPosition(3, 0), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 4, 4));

    }
    //sets up a board where the function checks to make sure a diagonal starting from the right has equivalent tokens in a row but there are not enough tokens in a row, and then calls checkDiagWin() with the appropriate arguments to check if it returns false. Finally, it asserts that the board is in the expected state after the moves have been made.
    @Test
    public void testCheckDiagonalWin_start_right_just_not_enough() {
        char[][] boardArray = createEmptyBoardArray(4, 4);


        boardArray[0][3] = 'X';
        boardArray[0][2] = 'O';
        boardArray[1][2] = 'X';
        boardArray[0][1] = 'O';
        boardArray[1][1] = 'O';
        boardArray[2][1] = 'X';
        boardArray[0][0] = 'X';
        boardArray[1][0] = 'O';
        boardArray[2][0] = 'X';
        boardArray[3][0] = 'O';

        IGameBoard board = gb(4, 4, 3);

        board.placeToken('X', 3);
        board.placeToken('O', 2);
        board.placeToken('X', 2);
        board.placeToken('O', 1);
        board.placeToken('O', 1);
        board.placeToken('X', 1);
        board.placeToken('X', 0);
        board.placeToken('O', 0);
        board.placeToken('X', 0);
        board.placeToken('O', 0);

        assertFalse(board.checkDiagWin(new BoardPosition(2, 0), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 4, 4));
    }
    // test case represents the standard case of there being no tie
    @Test
    public void testCheckTie_empty() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        IGameBoard board = gb(5, 5, 3);

        assertFalse(board.checkTie());
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case is for the checkTie() method of the game board class. It creates a 5x5 game board with all the cells filled with 'X'. Then it places 'X' tokens in all rows. This creates a tie game as there are no more empty cells to place tokens in. The test checks if the method checkTie() returns true for the tie game and if the actual game board matches the expected game board.
    @Test
    public void testCheckTie_full() {
        char[][] boardArray;
        boardArray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardArray[i][j] = 'X';
            }
        }

        IGameBoard board = gb(5, 5, 3);
        int rows = 0;
        while (rows < 5) {
            for (int i = 0; i < 5; i++) {
                board.placeToken('X', rows);
            }
            rows++;
        }

        assertTrue(board.checkTie());
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    // test case is for the checkTie() method of the game board class. It creates a 5x5 game board with all the cells filled with 'X', except for the cells in the last column. Then it places 'X' tokens in all but the last row. The game board is almost full, with only one cell empty. The test checks if the method checkTie() returns false for the almost full game board and if the actual game board matches the expected game board.
    @Test
    public void testCheckTie_almost_full() {
        char[][] boardArray;
        boardArray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardArray[i][j] = 'X';
                if (j == 4)
                    boardArray[i][j] = ' ';
            }
        }

        IGameBoard board = gb(5, 5, 3);
        int rows = 0;
        while (rows < 4) {
            for (int i = 0; i < 5; i++) {
                board.placeToken('X', rows);
            }
            rows++;
        }

        assertFalse(board.checkTie());
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case is for the checkTie() method of the game board class. It creates a 5x5 game board with all the cells filled with 'X', except for one cell in the last row and last column. Then it places 'X' tokens in all but the last row. It places four tokens in the last row to fill all cells except for the empty cell. The test checks if the method checkTie() returns false for the almost full game board and if the actual game board matches the expected game board.
    @Test
    public void testCheckTie_all_pos_but_one() {
        char[][] boardArray;
        boardArray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardArray[i][j] = 'X';
            }
        }

        boardArray[4][4] = ' ';

        IGameBoard board = gb(5, 5, 3);
        int rows = 0;
        while (rows < 4) {
            for (int i = 0; i < 5; i++) {
                board.placeToken('X', rows);
            }
            rows++;
        }

        board.placeToken('X', 4);
        board.placeToken('X', 4);
        board.placeToken('X', 4);
        board.placeToken('X', 4);

        assertFalse(board.checkTie());
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //creates an empty 5x5 game board. The test places no tokens on the game board and checks if the method whatsAtPos() returns an empty cell for the top-left position and if the actual game board matches the expected game board.
    @Test
    public void testWhatsAtPos_empty() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        IGameBoard board = gb(5, 5, 3);

        assertEquals(' ', board.whatsAtPos(new BoardPosition(0, 0)));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //creates an empty 5x5 game board. The test places an 'X' token in the top-left position and checks if the method whatsAtPos() returns 'X' for the top-left position and if the actual game board matches the expected game board.
    @Test
    public void testWhatAtPos_player_x() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        boardArray[0][0] = 'X';

        IGameBoard board = gb(5, 5, 3);

        board.placeToken('X', 0);

        assertEquals('X', board.whatsAtPos(new BoardPosition(0, 0)));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //creates an empty 5x5 game board. The test places a 'W' token in the top-left position and checks if the method whatsAtPos() returns 'W' for the top-left position and if the actual game board matches the expected game board.
    @Test
    public void testWhatsAtPos_player_not_x_or_o() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        boardArray[0][0] = 'W';

        IGameBoard board = gb(5, 5, 3);

        board.placeToken('W', 0);

        assertEquals('W', board.whatsAtPos(new BoardPosition(0, 0)));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //creates an empty 5x5 game board. The test places an 'X' token in the top-left position and checks if the method whatsAtPos() returns an empty cell for the position (1, 0) and if the actual game board matches the expected game board.
    @Test
    public void testWhatsAtPos_correct_position() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        boardArray[0][0] = 'X';

        IGameBoard board = gb(5, 5, 3);

        board.placeToken('X', 0);

        assertEquals(' ', board.whatsAtPos(new BoardPosition(1, 0)));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case ensures that the whatsAtPos() method returns the correct character when there are two players on the board at different positions.
    @Test
    public void testWhatsAtPos_two_players_correct_character() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        boardArray[0][0] = 'X';
        boardArray[0][1] = 'O';

        IGameBoard board = gb(5, 5, 3);

        board.placeToken('X', 0);
        board.placeToken('O', 1);

        assertEquals('O', board.whatsAtPos(new BoardPosition(0, 1)));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case represents the standard case of an empty position on the board and ensures that the isPlayerAtPos() method returns false for a position without a player.
    @Test
    public void testIsPlayerAtPos_empty() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        IGameBoard board = gb(5, 5, 3);

        assertFalse(board.isPlayerAtPos(new BoardPosition(0, 0), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case represents the standard case of a player X being present on the board and ensures that the isPlayerAtPos() method returns true for a position with player X.
    @Test
    public void testIsPlayerAtPos_player_x() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        boardArray[0][0] = 'X';

        IGameBoard board = gb(5, 5, 3);

        board.placeToken('X', 0);

        assertTrue(board.isPlayerAtPos(new BoardPosition(0, 0), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case ensures that the isPlayerAtPos() method recognizes characters other than X and O, and returns true for the specified character.
    @Test
    public void testIsPlayerAtPos_not_x_or_o() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        boardArray[0][0] = 'W';

        IGameBoard board = gb(5, 5, 3);

        board.placeToken('W', 0);

        assertTrue(board.isPlayerAtPos(new BoardPosition(0, 0), 'W'));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case ensures that the isPlayerAtPos() method checks the correct position on the board and returns false for a different position with the same player as the one placed.
    @Test
    public void testIsPlayerAtPos_check_correct_position() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        boardArray[0][0] = 'X';

        IGameBoard board = gb(5, 5, 3);

        board.placeToken('X', 0);

        assertFalse(board.isPlayerAtPos(new BoardPosition(0, 1), 'X'));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case ensures that the isPlayerAtPos() method looks for the correct character on the board and returns true for the specified character.
    @Test
    public void testIsPlayerAtPos_correct_characters() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        boardArray[0][0] = 'X';
        boardArray[0][1] = 'O';

        IGameBoard board = gb(5, 5, 3);

        board.placeToken('X', 0);
        board.placeToken('O', 1);

        assertTrue(board.isPlayerAtPos(new BoardPosition(0, 1), 'O'));
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case tests if the placeToken() method can add a token to the first available column on the board and returns the correct board configuration.
    @Test
    public void testPlaceToken_bottom_left_position() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        boardArray[0][0] = 'X';

        IGameBoard board = gb(5, 5, 3);
        board.placeToken('X', 0);
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case tests if the placeToken() method can add a token to the last available column on the board and returns the correct board configuration.
    @Test
    public void test_PlaceToken_bottom_right_position() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        boardArray[0][4] = 'X';

        IGameBoard board = gb(5, 5, 3);
        board.placeToken('X', 4);
        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //test case tests if the placeToken() method can add many tokens to the board repeatedly until the board is completely filled, and returns the correct board configuration.
    @Test
    public void testPlaceToken_fill_entire_board() {
        char[][] boardArray;
        boardArray = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardArray[i][j] = 'X';
            }
        }

        IGameBoard board = gb(5, 5, 3);
        int rows = 0;
        while (rows < 5) {
            for (int i = 0; i < 5; i++) {
                board.placeToken('X', rows);
            }
            rows++;
        }

        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //makes sure that the function can add different tokens (represented by letters like 'X' and 'O') to the board.
    @Test
    public void testPlaceToken_different_characters() {
        char[][] boardArray = createEmptyBoardArray(5, 5);


        boardArray[0][0] = 'X';
        boardArray[0][1] = 'O';

        IGameBoard board = gb(5, 5, 3);

        board.placeToken('X', 0);
        board.placeToken('O', 1);

        assertEquals(board.toString(), boardArrayString(boardArray, 5, 5));
    }
    //makes sure that the function can add a pattern of tokens to the board, where each row has a different letter.
    @Test
    public void testPlaceToken_fill_different_characters() {
        char[][] boardArray;
        boardArray = new char[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                boardArray[i][0] = 'W';
                boardArray[i][1] = 'I';
                boardArray[i][2] = 'L';
                boardArray[i][3] = 'S';
                boardArray[i][4] = 'O';
                boardArray[i][5] = 'N';
            }
        }

        IGameBoard board = gb(6, 6, 3);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board.placeToken('W', 0);
                board.placeToken('I', 1);
                board.placeToken('L', 2);
                board.placeToken('S', 3);
                board.placeToken('O', 4);
                board.placeToken('N', 5);
            }
        }

        assertEquals(board.toString(), boardArrayString(boardArray, 6, 6));
    }
}
