package sudoku.problemdomain;


import java.io.IOException;

//Not completely sure what(more like 'why') is going on here
public interface IStorage {
    void updateGameData(SudokuGame game) throws IOException;
    SudokuGame getGameData() throws IOException;
}
