package cpsc2150.extendedConnectX.models;

/**
 *@invariant   no token can be put on a full column
 *@invariant   tokens stack on top of each other
 *@invariant   tokens must be directly above or beneath each other when in the same column
 *@invariant   board size is ROW by COlUMN
 *@invariant   Gameboard begins on bottom left (0,0)
 *@invariant   No token can be placed outsize of gameboard size
 *@Correspondence maxRowsCols >= ROWS && >= COLUMNS
 *@Correspondence minRowsCols <= ROWS && <= COLUMNS
 *@Correspondence NUM_TO_WIN < ROW && NUM_TO_WIN < COLUMN
 */
public class GameBoard extends AbsGameBoard implements IGameBoard{

    private final char[][] Board;
    private final int ROW;
    private final int COLUMN;
    private final int NUM_TO_WIN;
    /**
     * @param rows  the amount of rows on the board
     * @param column    the amount of columns on the board
     * @param win   the amount of tokens required in a row to win
     * @pre     minRowsCols <= ROW <= maxRowsCols && minRowsCols <= COLUMN maxRowsCols && MIN_TO_WIN <= numToWin <= MAXNUMTOWIN
     * @post    every position in the 2D array is now filled with a blank space character and each of the rows, columns and number to win have been properly stored in the private variables.
     */
    public GameBoard(int rows, int column, int win){

        ROW = rows;
        COLUMN = column;
        NUM_TO_WIN = win;
        Board = new char[rows][column];

        for (int j = 0; j < rows; j++) {
            for (int k = 0; k < column; k++) {
                Board[j][k] = ' ';
            }
        }
    }

    /**
     * This method places the token in the lowest available row in each column
     *
     * @param p - the character that takes up the spot
     * @param c - the column we would like to place the token in
     * @return none
     * @pre check checkIfFree(c) AND LOWEST_COLUMN <= c < COLUMN
     * @post ([i][j] Board = = ' X ' OR ' O ') AND token is placed in the lowest row num that == ' '
     */

    public void placeToken(char p, int c) {
        if (Board[0][c] == ' ') {
            Board[0][c] = p;
        } else {
            int i;
            for (i = 0; i < ROW; i++) {
                if (Board[i][c] == (' ')) {
                    Board[i][c] = p;
                    break;
                }
            }
        }
    }


    /**
     * This method checks if position on board has a marker or blank space char
     *
     * @param pos - position of the token on the board
     * @return what is at position AND iff no marker than blank space char
     * @pre [pos is within the valid bounds]
     * @post [iff pos == 'X' OR 'O' space is occupied] AND
     * [iff pos == ' ' space is open for token placement]
     */

    public char whatsAtPos(BoardPosition pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        return Board[row][col];
    }


    /**
     * @return number of rows
     * @description returns the number of rows on the GameBoard
     * @pre none
     * @post gets number of rows
     * row = #row AND getNumRows = ROW
     */

    public int getNumRows(){

        return ROW;
    }

    /**
     * @return number of columns
     * @description returns the number of columns on the GameBoard
     * @pre none
     * @post gets number of columns
     * column = #column AND getNumColumns = COLUMN
     */
    public int getNumColumns(){

        return COLUMN;
    }

    /**
     * @return number of tokens in a row needed to win the game
     * @description returns the number of tokens needed to win the game
     * @pre none
     * @post gets number of tokens to win the game
     *  getNumToWin = NUM_TO_WIN
     */
    public int getNumToWin(){

        return NUM_TO_WIN;
    }
}