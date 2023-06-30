import java.util.*;

/**
 * Creates the board
 */
public class Board {

    public enum Color {WHITE, BLACK}
    String[][] board;
    private Color color;
    private int numberOfMoves;
    public Board() {
        board = new String[][]{
                {"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"},
                {"BP", "BP", "BP", "BP","BP", "BP", "BP", "BP"},
                {"  ", "  ", "  ", "  ","  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ","  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ","  ", "  ", "  ", "  "},
                {"  ", "  ", "  ", "  ","  ", "  ", "  ", "  "},
                {"WP", "WP", "WP", "WP","WP", "WP", "WP", "WP"},
                {"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"}
        };
        color = Color.WHITE;
    }
    public Board(String[][] board, Color c){
        board = board.clone();
        color = c;
    }

    public static Color getWhite(){
        return Color.WHITE;
    }
    public static Color getBlack(){
            return Color.BLACK;
        }

    public Color getColor() {
        return color;
    }
//prints the board
    public void getBoard(){
        for (int i = 0; i < 8; i++) {
            System.out.println(" +---+---+---+---+---+---+---+---+");
            System.out.print(8-i + "| ");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + "| ");
            }
            System.out.println();
        }
        System.out.println(" +---+---+---+---+---+---+---+---+");
        System.out.println("   A   B   C   D   E   F   G   H");

    }

    /**
     * gives the piece at the given position
     */
    public Piece getPieceAt(Position p){
        if(isEmpty(p))
            return null;
        if(board[p.getRow()][p.getColumn()].charAt(0) == 'W')
           return piecesSwitch(Color.WHITE, p);
        else{
            return piecesSwitch(Color.BLACK, p);
        }
    }

    /**
     * gives information about the end of the game
     */
    public boolean isGameOver() throws Exception {
        if(win() || loose() || draw())
            return true;
        return false;
    }

    /**
     * checks if one of the opponent's king have positions to ga when it is under attack and
     * if there are figures of the same color that can "protect" the king
     */
    public boolean loose() throws Exception {
        if(isKingUnderAttack(getTurn())){
            ArrayList<Position> opponentDest = getAllDestinationsByColor(getOpponentColor(), false);
            ArrayList<Position> myDest = getAllDestinationsByColor(getTurn(), true);
            Position position = kingsPosition(getTurn());
            ArrayList<Position> kingsDest = getPieceAt(position).allDestinations(this, position);
            for (int i = 0; i < myDest.size(); i++) {
                if(!(checkPosOfAttack(myDest.get(i), getTurn())))
                    return false;
            }
            if(opponentDest.containsAll(kingsDest))
                return true;

        }
        return false;
    }

    private boolean checkPosOfAttack(Position p, Color color) throws Exception {
        String temp = board[p.getRow()][p.getColumn()];
        if(color == getWhite())
            board[p.getRow()][p.getColumn()] = "WP"; //not empty
        else
            board[p.getRow()][p.getColumn()] = "BP";
        if(isKingUnderAttack(color)){
            board[p.getRow()][p.getColumn()] = temp;
            return true;
        }
        board[p.getRow()][p.getColumn()] = temp;
        return false;
    }

    /**
     *  checks if one of the opponents lost their king
     *  (putting this just in case since idk when this will happen in real game :D)
     */
    public boolean win(){
        Color color = getOpponentColor();
        if(color == Color.WHITE)
            return (notMatch("WK") && notMatch("Wk"));
        else
           return (notMatch("BK") && notMatch("Bk"));
    }

//returns true if there is no match and false if there is a match
    private boolean notMatch(String piece){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j].equals(piece))
                    return false;
            }
        }
        return true;
    }

    /**
     * if there are only kings and if current color have anywhere to go
     */
    public boolean draw() throws Exception {
        int count = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if(board[i][j].equals("WK") || board[i][j].equals("BK") ||  board[i][j].equals("  "))
                    count++;
            }

        if(getAllDestinationsByColor(getTurn(), false) == null)
            return true;

       return count == 64;
    }

    public Color getOpponentColor(){
        if(this.getTurn() == Color.WHITE)
            return Color.BLACK;
        return Color.WHITE;
    }


    private Position kingsPosition(Color kingColor) throws Exception {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (getPieceAt(new Position(j,i)) != null
                        && getPieceAt(new Position(j,i)).getColor() == kingColor
                        && getPieceAt(new Position(j,i)) instanceof King)
                    return new Position(j, i);
            }
        return null;
    }

    /**
     * give a piece that is represented by a string in given position
     */
    private Piece piecesSwitch(Color color, Position p){
        switch (board[p.getRow()][p.getColumn()].charAt(1)) {
            case 'P':
                return new Pawn(color);
            case 'B':
                return new Bishop(color);
            case 'K', 'k':
                return new King(color);
            case 'Q':
                return new Queen(color);
            case 'R', 'r':
                return new Rook(color);
            case 'N':
                return new Knight(color);

        }
        return null;
    }

    public boolean isEmpty(Position p) {
        return (board[p.getRow()][p.getColumn()].equals("  "));
    }

    public Color getTurn() {
        return Color.values()[this.numberOfMoves % 2];
    }

// return true if it is and false if it is not
    public boolean isKingUnderAttack(Color kingColor) throws Exception {
        Position kingPosition = null;
        Color opponentColor;
        ArrayList<Position> p;

        kingPosition = kingsPosition(kingColor);
        if (kingColor == Color.WHITE)
            opponentColor = Color.BLACK;
        else
            opponentColor = Color.WHITE;

        p = getAllDestinationsByColor(opponentColor, false);

        for (int i = 0; i < p.size(); i++)
            if (p.get(i).equals(kingPosition))
                return true;

        return false;
    }

    // return given color's figure's all destinations
    public ArrayList<Position> getAllDestinationsByColor(Color color, boolean isKingUnderAttack) throws Exception {
        ArrayList<Position> result = new ArrayList<Position>();

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (getPieceAt(new Position(j,i)) != null && getPieceAt(new Position(j,i)).getColor() == color) {
                    ArrayList<Position> current = null;
                    if(isKingUnderAttack) {
                        if (!(getPieceAt(new Position(j, i)) instanceof King)) {
                            current = getPieceAt(new Position(j, i)).allDestinations(this,
                                    new Position(j, i));
                            duplicates:
                            for (int k = 0; k < current.size(); k++) {
                                for (int l = 0; l < result.size(); l++)
                                    if (current.get(k).equals(result.get(l)))
                                        continue duplicates;
                                result.addAll(current);
                            }
                        }
                    }
                    else {
                        current = getPieceAt(new Position(j, i)).allDestinations(this, new Position(j, i));

                        duplicates:
                        for (int k = 0; k < current.size(); k++) {
                            for (int l = 0; l < result.size(); l++)
                                if (current.get(k).equals(result.get(l)))
                                    continue duplicates;
                            result.addAll(current);

                        }
                    }
                }

        return result;
    }

    /**
     * returns true if the move has been performed and false if there were problems with performing the move
     */
    public boolean performMove(Move move) throws Exception {

        Position to = move.getTo();
        Position from = move.getFrom();
        if (getPieceAt(from) == null)
            return false;
        if(getPieceAt(from).getColor() != getTurn())
            return false;
        if(((board[from.getRow()][from.getColumn()].charAt(1) == 'K' && board[to.getRow()][to.getColumn()].charAt(1) == 'R')
                || (board[from.getRow()][from.getColumn()].charAt(1) == 'R'
                && board[to.getRow()][to.getColumn()].charAt(1) == 'K')) && getPieceAt(to).getColor() == getTurn())
            return swapping(move);
        ArrayList<Position> positions = getPieceAt(from).allDestinations(this, from);
        for (int i = 0; i < positions.size(); i++) {
            if ((to.getColumn() == positions.get(i).getColumn()
                    && to.getRow() == positions.get(i).getRow())) {
                if(board[from.getRow()][from.getColumn()].charAt(1) == 'K')
                    this.board[to.getRow()][to.getColumn()] = this.board[from.getRow()][from.getColumn()].replace('K', 'k');
                else if(board[from.getRow()][from.getColumn()].charAt(1) == 'R')
                    this.board[to.getRow()][to.getColumn()] = this.board[from.getRow()][from.getColumn()].replace('R','r');
                else
                    this.board[to.getRow()][to.getColumn()] = this.board[from.getRow()][from.getColumn()];
                this.board[from.getRow()][from.getColumn()] = "  ";
                if (isKingUnderAttack(this.getTurn())) {
                    return false;
                }
                numberOfMoves++;
                return true;
            }
        }
        return false;
    }


// swapping king and rook
    private boolean swapping(Move move) throws Exception {
        Position to = move.getTo();
        Position from = move.getFrom();
        if(!notFiguresBetween(from.getColumn(), to.getColumn()))
            return false;
        if(board[from.getRow()][from.getColumn()].charAt(1) == 'K' && getPieceAt(to).getColor() == getTurn()
                && board[to.getRow()][to.getColumn()].charAt(1) == 'R'){
            this.board[to.getRow()][to.getColumn()] = this.board[to.getRow()][to.getColumn()].replace('R', 'k');
            this.board[from.getRow()][from.getColumn()] = this.board[from.getRow()][from.getColumn()].replace('K', 'r');
        }
        else if(board[from.getRow()][from.getColumn()].charAt(1) == 'R' && getPieceAt(to).getColor() == getTurn()
                && board[to.getRow()][to.getColumn()].charAt(1) == 'K'){
            this.board[to.getRow()][to.getColumn()] = this.board[to.getRow()][to.getColumn()].replace('K', 'r');
            this.board[from.getRow()][from.getColumn()] = this.board[from.getRow()][from.getColumn()].replace('R', 'k');
        }
        if (isKingUnderAttack(this.getTurn())) {
            return false;
        }
        numberOfMoves++;
        return true;
    }
//checks if there are figures between rook and king
    private boolean notFiguresBetween(int y1, int y2){
        if(y1 > y2) {
            if (getTurn() == Color.WHITE)
                return notFiguresBetweenCheck(y2, y1, 7);
            else
                return notFiguresBetweenCheck(y2, y1, 0);

        }
        else {
            if (getTurn() == Color.WHITE)
                return notFiguresBetweenCheck(y1, y2, 7);
            else
                return notFiguresBetweenCheck(y1, y2, 0);
        }
    }

    private boolean notFiguresBetweenCheck( int min, int max, int row){
        for (int i = min+1; i < max; i++) {
            if(!board[row][i].equals("  "))
                return false;
        }
        return true;
    }


}
