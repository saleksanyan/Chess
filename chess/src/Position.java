import java.util.Locale;
import java.util.Objects;

public class Position{
    private int column;
    private int row;

    public Position(Position pos) {
        setPos(pos);
    }

    public Position(int col, int row){
        if(col < 0 || col > 7 || row < 0 || row > 7 ) {
            column = -1;
            this.row = -1;
        }else {
            column = col;
            this.row = row;
        }
    }

    public Position(String pos) throws Exception {
        setPos(positionFromString(pos));
    }

    public Position getPos() throws Exception {
        return new Position(column, row);
    }

    public void setPos(Position pos) {
        if(pos != null) {
            column = pos.column;
            row = pos.row;
        }
    }
    public int getColumn(){
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        if(column < 0 || column > 7 || row < 0 || row > 7 )
            try {
                throw new Exception("WRONG INPUT");
            } catch (Exception e) {
                e.printStackTrace();
            }
        char letter = (char) (column + 97);
        char num = (char) (56 - row);
        return "Position: " + (String)(letter+"").toUpperCase() + num;
    }

    //Generate positions from strings
    public static Position positionFromString(String pos) throws Exception{
        char letter = pos.toUpperCase().charAt(0);
        char num = pos.charAt(1);
        if(num<49 || num > 56 || letter > 72 || letter < 65)
            throw new Exception("WRONG INPUT");
        int column = letter - 65;
        int row =  Math.abs(num - 56);
        return new Position(column, row);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Position position = (Position) o;
        return column == position.column && row == position.row;
    }


    public boolean isNull() {
        return this.row == -1 || this.column == -1;
    }

}
