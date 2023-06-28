package cpsc2150.extendedConnectX.models;

public abstract class AbsGameBoard implements IGameBoard {

    /**
     * This method creates a string to show row and column position
     *
     * @return one string that shows the entire game board
     * @pre none
     * @post toString = [ String representation of the game board ] and self = #self
     * @return a string representation of the GameBoard
     */
    @Override
    public String toString() {
        StringBuilder newBoard = new StringBuilder();
        for (int i = 0; i < getNumColumns(); i++)
        {
            if(i >= 10)
            {
                newBoard.append("|").append(i);
            }
            else
            {
                newBoard.append("|").append(" ").append(i);
            }
        }
        newBoard.append("|\n");
        for (int j = getNumRows() - 1; j >= 0; j--) {
            for (int k = 0; k < getNumColumns(); k++) {
                BoardPosition initialPosition = new BoardPosition(j, k);
                if(whatsAtPos(initialPosition) == ' ')
                {
                    newBoard.append("|" + ' ' + ' ');
                }
                else
                {
                    newBoard.append("|").append(whatsAtPos(initialPosition)).append(" ");
                }
            }
            newBoard.append("|\n");
        }
        return newBoard.toString();
    }
}