package sudoku.userinterface.logic;

import sudoku.constants.GameState;
import sudoku.constants.Messages;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;

import java.io.IOException;

// NOTE
// Whenever your dealing with a controller or a presenter or ViewModel
// or whatever. I STRONGLY recommend that you communicate, at least with
// the backend through interfaces.
// If your not going to use interfaces anywhere else, use them  across very
// large and important architectural boundaries across your application.
//  It also helps me do design the application up front without worrying about
// the implementation. If I need to change the storage implementation, I can,
// quickly and easily, without causing  any issues within the ControlLogic class.


// Kinda like a Controller or whatever
// manages the interactions between the user and the user interface
// and the backend of the application, or the computation logic
// Its going to extend the EventListener interface
public class ControlLogic implements IUserInterfaceContract.EventListener {

    private IStorage storage;
    private IUserInterfaceContract.View view;

    public ControlLogic (IStorage storage, IUserInterfaceContract.View view) {
        this.storage = storage;
        this.view = view;
    }

    @Override
    public void onSudokuInput(int x, int y, int input) {
        try {
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getCopyOfGridState();
            newGridState[x][y] = input;

            gameData = new SudokuGame(
                    GameLogic.checkForCompletion(newGridState),
                    newGridState
            );
            storage.updateGameData(gameData);
            view.updateSquare(x,y,input);
            if (gameData.getGameState() == GameState.COMPLETE) {
                view.showDialog(Messages.GAME_COMPLETE);
            }
        }
        catch (IOException e) {
           e.printStackTrace();
           view.showError(Messages.ERROR);
        }
    }

    // If the user clicks the OK dialogs this is where weel hadle that
    // and this will generate a new game for the user.

    // Were going to immediately update ethe storage first before we update
    // the user interface because the storage is the source of truth.
    @Override
    public void onDialogClick() {
        try {
            storage.updateGameData(
                    GameLogic.getNewGame()
            );
            view.updateBoard(storage.getGameData());
        } catch (IOException e) {
            view.showError(Messages.ERROR);
        }
    }
}
