package sudoku.buildlogic;

// Applying the principal of separating the configuration from the use.
// i.e.:
// An object which uses other objects should not also build those objects,
// You should pull the logic required to build those objects into a different class or method.
// So that's basically whats going on here,
// Were taking the logic required to wire up all the different actors in our application together
// just before they launch for the user to use.
// amd  were encapsulating that into an special little area in the application and
// The good thing about using interfaces is that we can use these interfaces within
// our build logic itself, as you can see teh interface is being revered to as or referenced as
// its interface, same with storage, etc..

import sudoku.computationlogic.GameLogic;
import sudoku.persistence.LocalStorageImpl;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.logic.ControlLogic;

import java.io.IOException;

// What this class does, is we attempt to get the gameData from storage, if it exists. If it
// doesn't exit, the storage class will throw an IOException. If thats the case then we just
// ask gameLogic for a new game, update storage, if possible, and then
public class SudokuBuildLogic {

    public  static void build(IUserInterfaceContract.View userInterface) throws IOException {
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try {
            initialState = storage.getGameData();
        } catch (IOException e) {
            initialState = GameLogic.getNewGame();
            storage.updateGameData(initialState);
        }

        IUserInterfaceContract.EventListener uiLogic =
                new ControlLogic(storage, userInterface);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
