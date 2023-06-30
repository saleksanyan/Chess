import java.util.ArrayList;

public abstract class Piece {
     private Board.Color color;


     public Piece(Board.Color c) {
          color = c;
     }
     public Piece() {
          this(Board.getWhite());
     }

     public Board.Color getColor() {
          return color;
     }


     /**
      * all reachable destinations of the given piece
      */
     public abstract ArrayList<Position> allDestinations(Board board, Position p) throws Exception;
}
