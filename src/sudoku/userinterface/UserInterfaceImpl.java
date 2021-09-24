package sudoku.userinterface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import sudoku.constants.GameState;
import sudoku.problemdomain.SudokuGame;
import sudoku.problemdomain.Coordinates;

import java.util.HashMap;


//NOTE
//use static final constatants instead of hard coded values whenever
//possible. This helps with legibility and performance?

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

    // This EventLister is going to be like the 'controller' or the 'presenter'.
    // This is what we will pass events which the user causes.
    // The listener, which is basically a control logic class,
    // will basically interpret those events and pass messages between
    // the view, the frontend , the UI , and the backend.
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

    //Constructor
    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface(); //helper method, to avoid giant methods
    }

    //for every user interface component im going to create a helper method
    private void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);

        //whats going to actually reveal the ui after everything has been
        //drawn and configured
        stage.show();
    }


    //some gridLines are thicker than others
    private void drawGridLines(Group group) {
        int xAndY  = 114;
        int index  = 0;

        while (index < 8) {
            int thickness;
            if (index == 2 || index == 5 ) {
                thickness = 3;
            } else {
                thickness = 2;
            }
            Rectangle verticalLine = getLine(
                    xAndY + 64 * index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness
            );
            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 * index,
                    thickness,
                    BOARD_X_AND_Y
            );
            //how we actually ass the ui elements to the group
            //he saddle function means se can slap any function in
            //the super class of the ui elemts is a node
            //so we can add a ny valid class that is a node
            root.getChildren().addAll(verticalLine, horizontalLine);
            ++index;
        }
    }


    private Rectangle getLine ( double x, double y,
                                double height, double width) {
        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);
        line.setFill(Color.BLACK);
        return line;
    }


    private  void drawTextFields(Group root) {
        final int xOrigin = 50;
        final int yOrigin = 50;
        final int xAndYDelta = 64;

        //O(n^2) Runtime Complexity
        //iterate through each of the 81 'cells' on the board
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; ++yIndex) {

                //get location (x,y of top left corner) of each cell
                int x = xOrigin + xIndex * xAndYDelta;
                int y = yOrigin + yIndex * xAndYDelta;

                //create text field for each 'cell'
                SudokuTextField tile = new SudokuTextField(xIndex, yIndex);
                styleSudokuTile(tile, x, y);
                //this implements EventHandler
                tile.setOnKeyPressed(this);
                textFieldCoordinates.put(new Coordinates(xIndex, yIndex), tile);
                root.getChildren().add(tile);
            }
        }
    }


    private void styleSudokuTile(SudokuTextField tile, double x, double y) {
        Font numberFont = new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);
        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);
        tile.setBackground(Background.EMPTY);
    }

    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);
        boardBackground.setFill(BOARD_BACKGROUND_COLOR);

        root.getChildren().addAll(boardBackground);
    }

    private void  drawTitle(Group root) {
        Text title = new Text(235, 690, SUDOKU);
        title.setFill(Color.WHITE);  //sets font color
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);  //add this particular UI element
    }

    //the turquoise background
    private void  drawBackground(Group root) {
        //Scene is something kinda like ViewGroup
        //'the background of the background'
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    // We will have our BuildLogic, which will assign our control
    // logic class to this particular view, obviously through the interface.
    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    // This is what will eventually be called when I just want to update a
    // single square after the user has input a number, rather than having to
    // update the whole board, which we do sometimes were just trying to update
    // a single UI element appropriately.
    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x,y));
        String value = Integer.toString( input );
        if (value.equals("0")) {
            value = "";
        }
        tile.textProperty().setValue(value);
    }


    //this will occur when the user finishes a game, or starts a new game
    @Override
    public void updateBoard(SudokuGame game) {

        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {

                //For each TextField were gonna grab it from the hashMap
                // I can grab them by creating a new coordinates object with a particular x,y value
                // The nice thing about the hash function is that i can recreate it anytime i want
                // As long as the calues are the same, or create the same hash values.
                TextField tile = textFieldCoordinates.get( new Coordinates(xIndex, yIndex));

                String value = Integer.toString( game.getCopyOfGridState()[xIndex][yIndex]);
                if (value.equals("0")) {
                    value = "";
                }
                tile.setText( value );
                if (game.getGameState() == GameState.NEW) {
                    if (value.equals("")) {
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    } else {
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);
                    }
                }

            }
        }

    }

    // called when the logic center of the game indicates that it is completed properly
    // and then weel show tis alert dialoguew, which basdicly will ask the user if they
    // want to star5t a new game.
    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert (Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        // pause things and wait for interaction from the user
        dialog.showAndWait();

        // once that occurs then we can do
        // the dialooge was cvli kces and the user awants to star ta new game
        if (dialog.getResult() == ButtonType.OK) listener.onDialogClick();

    }

    // if an error ocuurs duringread or write of information storage
    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }

    // if the user enters nu mber into a text box
    // that event will end up here
    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (
                    event.getText().matches("[0-9]")
            ) {
                // value is whatever the user entered
                int value = Integer.parseInt(event.getText());
                // source will the be the ui elemebt  the object that was
                // actual actually clicked on
                // I will get passes to handleInpu as an object
                handleInput(value, event.getSource());
                //so if a user wants to clear a tile by hitting 0 or backspace
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                handleInput(0,event.getSource());
            } else {
                ((TextField) event.getSource()).setText("");
            }
        }
        // wont let the internal conde porpage throughout he application?
        event.consume();
    }

    // once that input occurs, then were gonna pass that input to the control
    // logic class, which is the listerner here, and were gonna say
    // "hey, at this x,y value, this value is called"
    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                ((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                value
        );
    }
}
