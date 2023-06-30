import java.util.ArrayList;

public class Pawn extends Piece{
    public Pawn(Board.Color c) {
        super(c);
    }
     public Pawn() {
        super();
    }
    public String toString() {
        if (this.getColor() == Board.Color.WHITE)
            return "WP";
        else
            return "BP";
    }

    @Override
    public ArrayList<Position> allDestinations(Board board, Position p) throws Exception {
        ArrayList<Position> result = new ArrayList<Position>();
        Board.Color myColor = board.getPieceAt(p).getColor();
        Position front = null, jump = null, left = null, right = null;

        if (myColor == Board.Color.WHITE) {
            front = new Position(p.getColumn(), p.getRow()-1);
            //starting pos
            if (p.getRow() == 6)
                jump = new Position(p.getColumn(), 6 - 2);
            left = new Position(p.getColumn() - 1, p.getRow() - 1);
            right = new Position(p.getColumn() + 1, p.getRow() - 1);
        } else {
            //downward movements.
            front = new Position(p.getColumn(), p.getRow() + 1);
            // starting pos
            if (p.getRow() == 1)
                jump = new Position(p.getColumn(), 1+2);
            left = new Position(p.getColumn() - 1, p.getRow() + 1);
            right = new Position(p.getColumn() + 1, p.getRow() + 1);
        }

        if (!front.isNull() && board.isEmpty(front))
            result.add(front);

        if (!left.isNull() && board.getPieceAt(left) != null
                && board.getPieceAt(left).getColor() != this.getColor())
            result.add(left);

        if (!right.isNull() && board.getPieceAt(right) != null
                && board.getPieceAt(right).getColor() != this.getColor())
            result.add(right);

        if (!front.isNull() && board.isEmpty(front) && jump != null && board.isEmpty(jump))
            result.add(jump);

        return result;
    }

}
