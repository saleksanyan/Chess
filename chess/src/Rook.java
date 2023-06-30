import java.util.ArrayList;

public class Rook extends Piece{
    public Rook(Board.Color color) {
        super(color);
    }
    public Rook(Board.Color color, boolean moved){
        super(color);
    }
    public Rook(){
        super();
    }
    public String toString() {
        if (this.getColor() == Board.Color.WHITE)
            return "WR";
        else
            return "BR";
    }

    @Override
    public ArrayList<Position> allDestinations(Board board, Position p) {

        int[] rankOffsets = {1, -1, 0, 0};
        int[] fileOffsets = {0, 0, 1, -1};
        ArrayList<Position> result = new ArrayList<Position>();

        for (int d = 0; d < 4; d++) {
            int i = p.getRow() + rankOffsets[d];
            int j = p.getColumn() + fileOffsets[d];
            while (i >= 0 && i < 8
                    && j >= 0 && j < 8) {
                Position current = new Position(j, i);

                if (board.isEmpty(current))
                    result.add(current);
                else {
                    if (board.getPieceAt(current).getColor()
                            != board.getPieceAt(p).getColor())
                        result.add(current);
                    break;
                }

                i += rankOffsets[d];
                j += fileOffsets[d];
            }
        }

        return result;
    }

}
