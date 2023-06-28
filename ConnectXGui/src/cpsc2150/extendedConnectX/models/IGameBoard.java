package cpsc2150.extendedConnectX.models;
/**
 * Interface holds and maintains the information required for a connectX board and the operations that occur
 * @Defines: maxRowsCols: - The max amount of rows and columns on a gameboard
 *           minRowsCols: - The min amount of rows and columns on a gameboard
 *           numToWin: - The number of tokens in a row needed to win
 *           board: char[][]- An array contains the tokens for the player
 *           maxNumToWin: - the max number of tokens in a row to win a game
 *
 * @Initialization_Ensures: The Board size must be numRows by numCols in size
 *
 * @Constraints: numRows = getNumRows && numCols = getNumCols && numToWin = getNumToWin
 *
 **/
public interface IGameBoard {

    public static final int minRowsCols= 3;
    public static final int maxRowsCols = 100;
    public static final int maxNumToWin= 25;
    /**
     * This method checks to see if the column can accept a token
     *
     * @param c - the column we would like to place the token in
     * @return iff (c == ' ') then true AND iff else then false
     * @pre 0 <= c < MAX_COLUMN_NUM
     * @post iff (c == ' ') token is placed inside empty space ('X' OR 'O') AND
     * [i][j] Board include (' ' OR 'X' OR 'O') AND gameBoard = #gameBoard
     */

    public default boolean checkIfFree(int c) {
        BoardPosition val = null;
        for(int i = 0; i < this.getNumRows(); i++) {
            val = new BoardPosition(i, c);
            if(whatsAtPos(val) == (' ')) {
                return true;
            }
        }
        return false;
    }

    public void placeToken(char p, int c);

    /**
     * This method checks to see if the last token placed in the column won the game
     *
     * @param c - the column we would like to place the token in
     * @return iff c placed wins game then true AND iff else than false
     * @pre pos == [latest play/move]
     * @post iff checkHorizWin == true than win OR iff checkDiagWin == true than win
     * OR iff checkVertWin == true than win
     */

    public default boolean checkForWin(int c) {
        char character = ' ';
        for(int i = getNumRows() - 1; i >= 0; i--) {
            BoardPosition val = new BoardPosition(i, c);
            character = whatsAtPos(val);
            if(character == ' ') {
                continue;
            }
            if(checkHorizWin(val, character) || checkVertWin(val, character) || checkDiagWin(val, character)) {
                return true;
            }
        }
        return false;
    }


    /**
     * This method checks the board for open spots to see if there is a tie
     *
     * @return true iff no open spots left and false iff otherwise
     * @pre [iff game is not won]
     * @post iff all pos == 'X' OR 'O' AND check win functions != win then checkTie == true
     * AND theBoard = #theBoard
     */
    public default boolean checkTie() {
        BoardPosition val = null;
        for(int i = 0; i < getNumColumns(); i++) {
            for(int j = 0; j < getNumRows(); j++) {
                val = new BoardPosition(j, i);
                if(whatsAtPos(val) == (' ')) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method checks to see if the last token placed resulted in a horizontal win
     *
     * @param pos - position of the token on the board
     * @param p   - the character that would take up the spot
     * @return true iff NUM_TO_WIN in a row horizontally AND false iff otherwise
     * @pre LOWEST_COLUMN <= lastPos.getRow() < MAX_ROW_NUM AND LOWEST_COLUMN <= lastPos.getColumn() < MAX_COLUMN NUM
     * AND [lastPos was last token placement] AND [pos is within valid bounds]
     * @post [checkHorizWin == true iff NUM_TO_WIN in a row horizontally and player wins] AND
     * [checkHorizWin == false iff not NUM_TO_WIN in a row horizontally and next players turn] AND
     * theBoard = #theBoard
     */

    public default boolean checkHorizWin(BoardPosition pos, char p) {
        int count = 1; // start with 1 to count the current position
        int row = pos.getRow();
        int column = pos.getColumn();

        // check left and right directions
        int leftCount = 0;
        int rightCount = 0;
        for (int c = column - 1; c >= 0; c--) {
            if (whatsAtPos(new BoardPosition(row, c)) == p) {
                leftCount++;
                count++;
            } else {
                break;
            }
        }
        for (int c = column + 1; c < getNumColumns(); c++) {
            if (whatsAtPos(new BoardPosition(row, c)) == p) {
                rightCount++;
                count++;
            } else {
                break;
            }
        }

        return count >= getNumToWin();
    }



    /**
     * This method checks to see if the last token placed resulted in a vertical win
     *
     * @param pos - position of the token on the board
     * @param p   - the character that would take up the spot
     * @return true iff NUM_TO_WIN in a row vertically AND false iff otherwise
     * @pre LOWEST_COLUMN <= lastPos.getRow() < MAX_ROW_NUM AND LOWEST_COLUMN <= lastPos.getColumn() < MAX_COLUMN NUM
     * AND [lastPos was last token placement] AND [pos is within valid bounds]
     * @post [checkVertWin == true iff NUM_TO_WIN in a row vertically AND player wins] AND
     * [checkVertWin == false iff not NUM_TO_WIN in a row vertically and next players turn] AND
     * theBoard = #theBoard
     */

    public default boolean checkVertWin(BoardPosition pos, char p) {
        int count = 0;
        int row = pos.getRow();
        int column = pos.getColumn();

        // check down from pos
        while(row < getNumRows() && whatsAtPos(new BoardPosition(row, column)) == p) {
            count++;
            row++;
        }

        // check up from pos
        row = pos.getRow() - 1;
        while(row >= 0 && whatsAtPos(new BoardPosition(row, column)) == p) {
            count++;
            row--;
        }

        return count >= getNumToWin();
    }


    /**
     * This method checks to see if the last token placed resulted in a diagonal win
     *
     * @param pos - position of the token on the board
     * @param p   - the character that would take up the spot
     * @return true iff NUM_TO_WIN in a row diagonally AND false iff otherwise
     * @pre LOWEST_COLUMN <= lastPos.getRow() < MAX_ROW_NUM AND LOWEST_COLUMN <= lastPos.getColumn() < MAX_COLUMN NUM
     * AND [lastPos was last token placement] AND [pos is within valid bounds]
     * @post [checkDiagWin == true iff NUM_TO_WIN in a row diagonally AND player wins] AND
     * [checkDiagWin == false iff not NUM_TO_WIN in a row vertically and next players turn] AND
     * theBoard = #theBoard
     */

    public default boolean checkDiagWin(BoardPosition pos, char p) {
        int count1 = 1; // count up and to the right
        int count2 = 1; // count up and to the left
        int row = pos.getRow();
        int col = pos.getColumn();
        // count up and to the right
        while (++row < getNumRows() && ++col < getNumColumns() && whatsAtPos(new BoardPosition(row, col)) == p) {
            count1++;
        }
        // reset row and col to starting position
        row = pos.getRow();
        col = pos.getColumn();
        // count up and to the left
        while (++row < getNumRows() && --col >= 0 && whatsAtPos(new BoardPosition(row, col)) == p) {
            count2++;
        }
        // reset row and col to starting position
        row = pos.getRow();
        col = pos.getColumn();
        // count down and to the left
        while (--row >= 0 && --col >= 0 && whatsAtPos(new BoardPosition(row, col)) == p) {
            count1++;
        }
        // reset row and col to starting position
        row = pos.getRow();
        col = pos.getColumn();
        // count down and to the right
        while (--row >= 0 && ++col < getNumColumns() && whatsAtPos(new BoardPosition(row, col)) == p) {
            count2++;
        }
        return count1 >= getNumToWin() || count2 >= getNumToWin();
    }


    public char whatsAtPos(BoardPosition pos);

    /**
     * This method checks to see if player is at the position selected
     *
     * @param pos    - position of the token on the board
     * @param player - variable for player to move on board
     * @return true iff (player == pos) AND iff otherwise then false
     * @pre [pos is within valid bounds]
     * @post [function returns true if player is in the gameBoard at the specified position]
     */

    default boolean isPlayerAtPos(BoardPosition pos, char player) {
        return whatsAtPos(pos) == player;
    }

    public int getNumRows();
    public int getNumColumns();
    public int getNumToWin();


}