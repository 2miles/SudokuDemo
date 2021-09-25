package sudoku.computationlogic;
import sudoku.problemdomain.Coordinates;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameGenerator {

    public static int[][] getNewGameGrid() {
        return unsolveGame(getSolvedGame());
    }

    //how do we actually know if a sudoku game is solvable or not
    private static int[][] unsolveGame(int[][] solvedGame) {
        Random random = new Random(System.currentTimeMillis());
        boolean solvable = false;
        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        while (solvable == false) {
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);
            //remove 40 numbers from an already solved game
            int index = 0;
            while (index < 40) {
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);
                if (solvableArray[xCoordinate][yCoordinate] != 0) {
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }
            }
            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);
            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);
        }
        return solvableArray;
    }

    // This algorithm is not necessarily the most efficient
    // A good project would be to implement a more efficient
    // algorithm. For example, one as a graph coloring problem
    //
    // Note: Every sudoku puzzle has 9 ones, 9 twos, ... , 9 nines.
    // Generate a solved game
    private static  int[][] getSolvedGame() {
        // We need to allocate each value 1 through 9, nine times.

        // If an allocation is attempted too many times,
        // we'll increment our interrupt variable for each value.

        // If over 200 interrupts (failed attempts to allocate) have been made,
        // we increment attempts and reset the allocTracker list(set all to 0),
        // interrupts, and allocations.

        // While the number of times we've allocated a particular value is less than 9,
        // keep attempting to allocate that number(randomly populating the board with that value)

        // Each time we allocate a number we add that to our allocTracker Coordinate list.

        // Sometimes we get stuck and the algorithm runs forever.

        // If attempts is over 500,
        // then we are stuck and need to nuke the whole array and start over.
        Random random = new Random(System.currentTimeMillis());
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        for (int value = 1; value <= GRID_BOUNDARY; value++) {
            int allocations = 0; //times each value has been allocated
            int interrupt = 0;
            List<Coordinates> allocTracker = new ArrayList<>();
            int attempts = 0; //Fail safe
            while (allocations < GRID_BOUNDARY) {
                if (interrupt > 200) {
                    allocTracker.forEach(coord -> { newGrid[coord.getX()][coord.getY()] = 0; });
                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;
                    if (attempts > 500 ) {  //Nuke it all
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (newGrid[xCoordinate][yCoordinate] == 0) {
                    newGrid[xCoordinate][yCoordinate] = value;

                    if (GameLogic.sudokuIsInvalid(newGrid)) {
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    } else {
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }
                //else we just skip, don't want to overwrite what we just did
            } //end while (allocation < 9)
        } //end for (1 <= 9)
        return newGrid;
    }

    private static void clearArray(int[][] newGrid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }
}
