package cpsc2150.extendedConnectX.models;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
 *@Correspondence this = Map<Character, List<BoardPosition>> board
 **/

public class GameBoardMem extends AbsGameBoard implements IGameBoard{
    Map<Character, List<BoardPosition>> Board_Map = new HashMap<Character, List<BoardPosition>>();
    int ROW;
    int COLUMN;
    int NUM_TO_WIN;
    /**
     * @param Row  the amount of rows on the board
     * @param Col    the amount of columns on the board
     * @param Win   the amount of tokens required in a row to win
     * @pre     minRowsCols <= ROW <= maxRowsCols && minRowsCols <= COLUMN maxRowsCols && MIN_TO_WIN <= numToWin <= MAXNUMTOWIN
     * @post    every position in the 2D array is now filled with a blank space character and each of the rows, columns and number to win have been properly stored in the private variables.
     */
    public GameBoardMem(int Row, int Col, int Win) {
        ROW = Row;
        COLUMN = Col;
        NUM_TO_WIN = Win;
    }
    /**
     * @return row length
     */
    public int getNumRows() {
        return ROW;
    }

    /**
     * @return column height
     */
    public int getNumColumns() {
        return COLUMN;
    }

    /**
     * @return number needs for a win
     */
    public int getNumToWin() {
        return NUM_TO_WIN;
    }


    public void placeToken(char p, int c){
        if(!Board_Map.containsKey(p)){
            Board_Map.put(p, new ArrayList<>());
        }
        for(int i = 0; i < getNumRows();i++){
            BoardPosition board = new BoardPosition(i, c);
            if(whatsAtPos(board)== ' '){
                Board_Map.get(p).add(board);
                break;
            }
        }
    }

    /**
     * @param pos the position being stored or read
     * @return [returns the char that is in position pos of the game board. If there is no token at the spot it should return a blank space character ? ?.]
     * @pre none
     * @post empty the 1st player and the 2nd player
     */
    public char whatsAtPos(BoardPosition pos) {
        for (HashMap.Entry<Character, List<BoardPosition>> map : Board_Map.entrySet()) {
            if (isPlayerAtPos(pos, map.getKey())) {
                return map.getKey();
            }
        }
        return ' ';
    }

    /**
     * @param pos is position being read
     * @param player is character of a player
     * @pre [pos has a row and a column, and the character exist on the board]
     * @post [true iff pos on the board is available otherwise false]
     * @return returns true if the player is at that position, or else return false.]
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player){
        if(!Board_Map.containsKey(player)){
            return false;
        }
        for(BoardPosition bp : Board_Map.get(player)){
            if(bp.equals(pos)){
                return true;
            }
        }
        return false;
    }
}