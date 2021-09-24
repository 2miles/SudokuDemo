package sudoku.problemdomain;


import java.io.IOException;

// I decided on this interface in the problem domain analysis stage.
// A cool way to use interfaces is to design parts of your application
// upfront ahead of time, ie. design by contract, or code to an interface.

// anticipating io exceptions because this is an io device
public interface IStorage {
    void updateGameData(SudokuGame game) throws IOException;
    SudokuGame getGameData() throws IOException;
}
