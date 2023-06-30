import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(Board.Color c) {
        super(c);
    }
    public String toString() {
        if (this.getColor() == Board.getWhite())
            return "WB";
        else
            return "BB";
    }
    @Override
    public ArrayList<Position> allDestinations(Board board, Position p) {
        ArrayList<Position> result = new ArrayList<Position>();

        int[] rows = {1, -1, 1, -1};
        int[] columns = {1, 1, -1, -1};

        for (int to = 0; to < 4; to++) {
            int i = p.getRow() + rows[to];
            int j = p.getColumn() + columns[to];
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
                i += rows[to];
                j += columns[to];
            }
        }
        return result;
    }


}
