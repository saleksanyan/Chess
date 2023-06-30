import java.util.ArrayList;
import java.util.Arrays;

public class Knight extends Piece{

    public Knight(Board.Color c) {
        super(c);
    }

    public Knight(){
        super();
    }

    @Override
    public String toString() {
            if (this.getColor() == Board.getWhite())
                return "WN";
            else
                return "BN";


    }


    @Override
    public ArrayList<Position> allDestinations(Board board, Position p) throws Exception {
        int[][] pattern = {
                {p.getColumn() + 2, p.getRow() + 1},
                {p.getColumn() + 2, p.getRow() - 1},
                {p.getColumn() - 2, p.getRow() + 1},
                {p.getColumn() - 2, p.getRow() - 1},

                {p.getColumn() + 1, p.getRow() + 2},
                {p.getColumn() + 1, p.getRow() - 2},
                {p.getColumn() - 1, p.getRow() + 2},
                {p.getColumn() - 1, p.getRow() - 2}
        };

        ArrayList<Position> result = new ArrayList<Position>();

        for (int i = 0; i < pattern.length; i++) {
            Position potential;
            if(pattern[i][0] < 0 || pattern[i][0] > 7 || pattern[i][1] < 0 || pattern[i][1] > 7 ){
                potential = null;
            }else
                potential = new Position(pattern[i][0], pattern[i][1]);
            if (potential != null && (board.isEmpty(potential)
                    || board.getPieceAt(potential) != null && board.getPieceAt(p).getColor()
                    != board.getPieceAt(p).getColor()))
                result.add(potential);
        }

        return result;
    }


}
