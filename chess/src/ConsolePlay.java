
import java.util.Scanner;


public class ConsolePlay {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hi there!");
        instructions(scanner);
        String c = scanner.next();
        if (!c.toLowerCase().equals("c")) {
            return;
        }
        scanner.nextLine();
        Board game = new Board();
        game.getBoard();
        while (!game.isGameOver()) {
            if (game.getTurn() == Board.getWhite())
                System.out.println("White’s move: ");
            else
                System.out.println("Black’s move: ");

            String[] input = scanner.nextLine().split(" ");
            if (input.length == 2) {
                Move m = new Move(new Position(input[0]), new Position(input[1]));
                if (!game.performMove(m)) {
                    System.out.println("Invalid move. Please try again.");
                }
                game.getBoard();
            }
            else
                System.out.println("Enter "+ game.getTurn().toString().toLowerCase() +"'s move, i.e. A2 A3");
        }

        if(game.win())
            System.out.println(game.getColor() + " LOST THE GAME");
        else if(game.draw())
            System.out.println("ITS A TIE");
        else
            System.out.println(game.getOpponentColor() + " WON THE GAME");

    }

    private static void instructions(Scanner scanner) {
        System.out.print("If you want to start the game enter S/s: ");
        String s = scanner.next();
        if (!s.toLowerCase().equals("s")) {
            System.out.println("See you next time!");
            return;
        }
        scanner.nextLine();
        System.out.println();
        System.out.println("INSTRUCTIONS");
        System.out.println();
        System.out.println("This game runs in a console, i.e., that means no GUI is available to the user. \n" +
                "All the input is taken from the keyboard, i.e. A2 A3 means the pawn that is on A2 should go to A3\n" +
                "The white pieces are represented by 'W' and the capital letters of the figures " +
                "'and the black pieces are represented by 'B' and the capital letters of the figures. \n" +
                "The exceptions are Knight, which is represented by an N, \n" +
                "King that after making the first move changes from 'K' to 'k' \n" +
                "and Rook that after making the first move changes from 'R' to 'r'\n" +
                "GOOD LUCK WITH THE GAME :)\n" +
                "Press C/c to continue: ");
    }

}
