import java.util.ArrayList;

public class Queen extends Piece{
    public Queen(Board.Color c) {
        super(c);
    }
    public Queen(){
        super();
    }
    public String toString() {
        if (this.getColor() == Board.getWhite())
            return "WQ";
        else
            return "BQ";
    }



    @Override
    public ArrayList<Position> allDestinations(Board board, Position p) {
        Rook rook = new Rook(board.getPieceAt(p).getColor());
        Bishop bishop = new Bishop(board.getPieceAt(p).getColor());
        ArrayList<Position> result = rook.allDestinations(board, p);
        result.addAll(bishop.allDestinations(board, p));
        return result;
        }
}
