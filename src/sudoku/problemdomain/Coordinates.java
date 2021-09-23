package sudoku.problemdomain;

import java.util.Objects;

public class Coordinates {
    private final int x;  //final: cannot be changed.
    private final int y;  //private: not accessible outside of package

    //Constructor; initialize x,y
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }


    /**
     * We want to be able to store these objects in a hash map
     * basically as keys to keep track of different UI elements
     *
     * TRUE If they have same address.
     * FALSE If arg is null or not same type.
     * TRUE if both x,y are the same, ELSE FALSE
     */
    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true; }
        if (o == null || getClass() != o.getClass()) {
            return false; }
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }


    /**
     * hashCode is a unique identifier that is generated from
     * some data you give it
     */
    @Override
    public int hashCode(){
       //generate a unique identifier from specific x,y values of a Coordinate
       return Objects.hash(x,y);
    }



}
