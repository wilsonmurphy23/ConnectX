package cpsc2150.extendedConnectX.models;

public class BoardPosition {
    private int row;
    private int column;

    /**
     * creates a new BoardPosition with the specified row and column numbers
     *
     * @param c column
     * @param r row
     *
     * @pre
     * row = r AND column = c
     *
     * @post gets the number of columns and rows for Board
     */

    public BoardPosition(int r, int c) {
        row = r;
        column = c;
    }

    /**
     * @ return the row for the board
     *
     * @return number of rows
     *
     * @pre none
     *
     * @post gets number of rows
     *  row = #row
     */
    public int getRow() {

        return row;
    }

    /**
     * @ return the row for the board
     *
     * @return number of columns
     *
     * @pre none
     *
     * @post gets number of columns
     * column = #column
     */

    public int getColumn() {

        return column;
    }

    /**
     * This method compares two string in BoardPosition to see if the position is equal
     *
     * <b>NOTE:</b> Connect 5 has a max of COLUMN columns
     *
     * @return true or false depending on iff two strings are equal
     *
     * @pre none
     *
     * @post
     * true iff [two string are equal] OR
     * false iff [two strings are not equal] AND
     * BoardPosition = #BoardPosition
     */
    public boolean equals(Object obj) {
        if (obj.getClass() != getClass())
        {
            return false;
        }
        BoardPosition temp = (BoardPosition)obj;
        return (temp.getRow() == getRow() && temp.getColumn() == getColumn());
    }
    public String toString() {

        return row + "," + column;
    }
}