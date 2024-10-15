import java.util.*;

public class AnalyseService {


    //TODO: make Field.class have a Position?
    public static void findBestWinningLine(){

        //Map<List<Position>, Integer> map = createMap();
        MatchHistory matchHistory = MatchHistory.fromFile();
        Map<List<Position>, Integer> map = new HashMap<>();
        List<List<Position>> wins = new ArrayList<>();

        for (Match match : matchHistory.getMatches()) {
            if (match.getStatus() == MatchStatus.COMPUTER_WON || match.getStatus() == MatchStatus.PLAYER_WON){
                wins.add(Winner.findWinningRow(match.getBoard(), getWinnerSymbol(match.getStatus())));
            }
        }

        for (List<Position> win : wins) {

            if (map.containsKey(win)) {
                map.put(win, map.get(win) + 1);
            } else {
                map.put(win, 1);
            }
        }



        List<Position> winPositions = Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
        for (Position position : winPositions) {
            System.out.print(position.getIndex() + " ");
        }

    }

    private static char getWinnerSymbol(MatchStatus status) {
        switch (status) {
            case COMPUTER_WON -> {
                return Match.COMPUTER_SYMBOL;
            }
            case PLAYER_WON -> {
                return Match.PLAYER_SYMBOL;
            }
            default -> {
                return 'p'; //TODO:
            }

        }
    }

}