package sudoku.userinterface;
import sudoku.problemdomain.SudokuGame;
import sudoku.problemdomain.Coordinates;

import javafx.scene.Group;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.HashMap;

// EventHandler and KeyEvent come from javafx and
// that is how we listen for input form the keyboard
public class UserInterfaceImpl implements IUserInterfaceContract.View,
        EventHandler<KeyEvent> {

    // Stage comes from JavaFX
    // Stage: The background window for the application
    // Group: Somewhat like a container
    //
    private final Stage stage;
    private final Group root;

    // How do we keep track of 81 different text fields??
    // One way to do it (the bad way)
    // is to have a reference for every single text field
    //
    // private SudokuTextField one, two, three, ... eightyOne;


    // Remember, we overrode the equals and hashCode functions for Coordinates
    // the sudoke text field we just created has an x and y value.
    // so, were gonna store references to all of these different textfiels withing a hash map
    // and use that hash function to retrieve and store them
    private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;

    //This EventLister is going to be like the 'controller' or the 'presenter'.
    //This is what we will pass events which the user causes.
    //The listener, which is basically a control logic class,
    //will basically interpret those events and pass messages between
    //the view, the frontend , the UI , and the backend.
    private IUserInterfaceContract.EventListener listener;

    // If this application has multiple different UI screens
    // we wouldn't put this style information here,
    // we would put it in a separate file and make it protected or public  access,
    // jut so we are redefining it in multiple different UI implementations.

    //style information
    //The x,y position in the monitor
    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    //Padding between the window and the actual sudoku board
    private static final double BOARD_PADDING = 50;
    //x,y values withing the window itself
    private static final double BOARD_X_AND_Y = 576;
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0,150,136);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224,242,241);
    private static final String SUDOKU = "Sudoku";


    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {

    }

    @Override
    public void updateSquare(int x, int y, int input) {

    }

    @Override
    public void updateBoard(SudokuGame game) {

    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void handle(KeyEvent event) {

    }

}
