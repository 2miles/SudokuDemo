package sudoku.userinterface;
import sudoku.problemdomain.SudokuGame;

// Using the parent interface like a namespace, or a way of differentiating
// different interfaces.
// So, if we had multiple different user interface components, or features,
// this is how we would differentiate them.
public interface IUserInterfaceContract {



    //something like a controller or a presenter
    interface EventListener {
        void onSudokuInput(int x, int y, int input);
        void onDialogClick();
    }

    //look up 3-layer software architecture
    //part of the application that binds to the user interface
    interface View {
        void setListener(IUserInterfaceContract.EventListener listener);
        void updateSquare(int x, int y, int input);
        void updateBoard(SudokuGame game);
        void showDialog(String message);
        void showError(String message);
    }
}
