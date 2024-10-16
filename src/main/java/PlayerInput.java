import java.util.Scanner;

public final class PlayerInput {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String INVALID_INPUT = "This is not a valid input! Please try again.";
    private static PlayerInput instance;

    private PlayerInput() {
    }
    public static PlayerInput getInstance() {
        if (instance == null) {
            instance = new PlayerInput();
        }
        return instance;
    }



    private String myScanner() {
        return SCANNER.nextLine();
    }

    public Position askForMove(Board board) {
        String move;
        int number;
        while(true){
            System.out.println("Please pick a field (1-9)");
            move = myScanner();

            if(isInteger(move) && board.isValid(Integer.parseInt(move))){
                number = Integer.parseInt(move);
                break;
            } else {
                System.out.println(INVALID_INPUT);
            }
        }

        return new Position(number);
    }

    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    public boolean askPlayAgainWithHistory() {
        String response;

        System.out.println();
        System.out.println("Do you want to play again? (Type y/n)");
        System.out.println("Or type (h) for history.");
        response = myScanner();

        while(! ("y".equals(response) || "n".equals(response) || "h".equals(response))) {
            System.out.println(INVALID_INPUT);
            response = myScanner();
        }

        if("h".equals(response)) {
            MatchHistory matchHistory = MatchHistory.fromFile(Main.FILE_MATCH_HISTORY);
            matchHistory.printMatchHistory();
            return askPlayAgain();
        }

        return "y".equals(response);
    }

    public boolean askPlayAgain() {
        String response;

        System.out.println();
        System.out.println("Do you want to play again? (Type y/n)");
        response = myScanner();

        while(! ("y".equals(response) || "n".equals(response))) {
            System.out.println(INVALID_INPUT);
            response = myScanner();
        }
        return "y".equals(response);
    }

    public DifficultyState askForDifficulty() {
        String response;
        System.out.println();
        System.out.println("What difficulty would you like to play?   Easy, Medium, impossible?   (Type e/m/i)");
        response = myScanner();

        while(! ("e".equals(response) || "m".equals(response) || "i".equals(response))) {
            System.out.println(INVALID_INPUT);
            response = myScanner();
        }

        return switch (response) {
            case "e" -> DifficultyState.EASY;
            case "m" -> DifficultyState.MEDIUM;
            case "i" -> DifficultyState.IMPOSSIBLE;
            default -> throw new IllegalStateException("Unexpected value: " + response);
        };
    }

    @SuppressWarnings("PMD.UnusedLocalVariable")
    public boolean isInteger(String input) {
        if (input == null) {
            return false;
        }
        try {
            int num = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
