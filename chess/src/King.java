import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece{
    public King(Board.Color c) {
        super(c);
    }
    public King() {
        super();
    }

    public King(Board.Color color, boolean moved) {
        super(color);
    }

    public String toString() {
        if (this.getColor() == Board.getWhite())
            return "WK";
        else
            return "BK";
    }



    @Override
    public ArrayList<Position> allDestinations(Board board, Position p) {
        int[][] pattern = {
                {p.getColumn() - 1, p.getRow() - 1},
                {p.getColumn() -1,  p.getRow()},
                {p.getColumn() - 1, p.getRow() + 1},

                {p.getColumn(), p.getRow()- 1},
                {p.getColumn(), p.getRow() + 1},

                {p.getColumn() + 1, p.getRow() - 1},
                {p.getColumn() +1,  p.getRow()},
                {p.getColumn() + 1, p.getRow() + 1}
        };

        ArrayList<Position> result = new ArrayList<Position>();

        for (int i = 0; i < pattern.length; i++) {
            Position potential = new Position(pattern[i][0],
                    pattern[i][1]);
            if (!potential.isNull() && (board.isEmpty(potential)
                    || board.getPieceAt(potential).getColor()
                    != board.getPieceAt(p).getColor()))
                result.add(potential);
        }

        return result;
    }

}
