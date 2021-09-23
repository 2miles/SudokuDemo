package sudoku.userinterface;
import javafx.scene.control.TextField;

//a special custom text field, TextField comes form javafx
//its purpose is maintains an X,Y  coordinates
public class SudokuTextField extends TextField{

    private final int x;
    private final int y;

    public SudokuTextField(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    //override from TextField
    //get rid of the weird things when someone doesnt enter a number
    //Look up more on regex in Java
    //TRUE if arg matches 0-9
    @Override
    public void replaceText(int i, int i1, String s){
        if (!s.matches("[0-9]")) {
            super.replaceText(i, i1, s);
        }
    }
    @Override
    public void replaceSelection(String s) {
        if (!s.matches("[0-9]")) {
            super.replaceSelection(s);
        }
    }
}
