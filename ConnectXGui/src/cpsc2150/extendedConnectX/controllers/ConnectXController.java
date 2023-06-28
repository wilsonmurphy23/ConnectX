package cpsc2150.extendedConnectX.controllers;

import cpsc2150.extendedConnectX.models.*;
import cpsc2150.extendedConnectX.views.*;

/**
 * The controller class will handle communication between our View and our Model ({@link IGameBoard})
 * <p>
 * This is where you will write code
 * <p>
 * You will need to include your your {@link BoardPosition} class, {@link IGameBoard} interface
 * and both of the {@link IGameBoard} implementations from Project 4.
 * If your code was correct you will not need to make any changes to your {@link IGameBoard} implementation class.
 *
 * @version 2.0
 */
public class ConnectXController {

    /**
     * <p>
     * The current game that is being played
     * </p>
     */
    private IGameBoard curGame;

    /**
     * <p>
     * The screen that provides our view
     * </p>
     */
    private ConnectXView screen;

    /**
     * <p>
     * Constant for the maximum number of players.
     * </p>
     */
    public static final int MAX_PLAYERS = 10;
    
    /**
     * <p>
     * The number of players for this game. Note that our player tokens are hard coded.
     * </p>
     */
    private int numPlayers;
    private int player;
    private final char[] playerTokens = new char[] {'X', 'O', 'W', 'I', 'S', 'A', 'U', 'P', 'J', 'Z'};
    private boolean finishGame;

    /**
     * <p>
     * This creates a controller for running the Extended ConnectX game
     * </p>
     * 
     * @param model
     *      The board implementation
     * @param view
     *      The screen that is shown
     * @param np
     *      The number of players for this game.
     * 
     * @post [ the controller will respond to actions on the view using the model. ]
     */

    public ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        this.numPlayers = np;

        // Some code is needed here.
    }

    /**
     * <p>
     * This processes a button click from the view.
     * </p>
     * 
     * @param col 
     *      The column of the activated button
     * 
     * @post [ will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button ]
     */
    public void processButtonClick(int col) {
            int placedTokenRow = 0; // The row where the token will be placed
            if(finishGame) {
                this.newGame();
                finishGame = false;
            } else {
                BoardPosition pos;
                if(!curGame.checkIfFree(col)){ // Check if the selected column is full
                    screen.setMessage("Column full, choose a different column.");
                    return;
                }
                // Find the first empty row for the selected column
                for(int row = 0; row < curGame.getNumRows(); row++){
                    pos = new BoardPosition(row, col);
                    if(curGame.whatsAtPos(pos) == ' '){
                        placedTokenRow = row;
                        break;
                    }
                }
                BoardPosition newPos = new BoardPosition(placedTokenRow, col); // Create a new BoardPosition for the placed token
                curGame.placeToken(playerTokens[player], col); // Place the token for the current player in the selected column
                screen.setMarker(newPos.getRow(), col, playerTokens[player]); // Set the marker on the game board for the placed token
                if(curGame.checkForWin(col)){ // Check if the current player won the game
                    screen.setMessage("Player " + playerTokens[player] + " wins! Press any button to play another game.");
                    finishGame = true;
                } else if(curGame.checkTie()){ // Check if the game ended in a tie
                    screen.setMessage("It's a tie! Press any button to play another game.");
                    finishGame = true;
                } else { // Switch to the next player's turn
                    player++;
                    if(player >= numPlayers)
                        player = 0;
                    screen.setMessage(playerTokens[player] + "'s turn. ");
                }
            }
        }

    /**
     * <p>
     * This method will start a new game by returning to the setup screen and controller
     * </p>
     * 
     * @post [ a new game gets started ]
     */
    private void newGame() {
        //close the current screen
        screen.dispose();
        
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}