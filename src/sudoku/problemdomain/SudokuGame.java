package sudoku.problemdomain;

public class SudokuGame implements serializable {
    private final GameState gameState;
    private final int[][] gridState;

    public static final int GRID_BOUNDRY = 9;

    public GameState getGameState() {
        return gameState;
    }

    public int[][] getCopyOfGridState() {
        return SudokuUtilitios.copyToNewArray(gridState);
    }
}
