package sudoku.problemdomain;
import sudoku.constants.GameState;

public class SudokuGame implements serializable {
    private final GameState gameState; //ENUM
    private final int[][] gridState;  //Actual 2d grid for game
    public static final int GRID_BOUNDARY = 9;

    public SudokuGame(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int[][] getCopyOfGridState() {
        return SudokuUtilitios.copyToNewArray(gridState);
    }
}
