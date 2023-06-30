public class Move {
    private Position from;
    private Position to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Move getMove() {
        return new Move(from, to);
    }
//origin
    public Position getTo() {
        return to;
    }
//destination
    public Position getFrom() {
        return from;
    }

    @Override
    public String toString() {
        return from +" "+to;
    }

}
