import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ComputerMoveServiceTest {

    private static Board generateBoard(int[] player, int[] computer) {
        Board board = new Board();

        for (int j : player) {
            Position position = new Position(j);
            board.getRows().get(position.getRow()).getFields().get(position.getColumn()).setSymbol(Match.PLAYER_SYMBOL);
        }

        for (int j : computer) {
            Position position = new Position(j);
            board.getRows().get(position.getRow()).getFields().get(position.getColumn()).setSymbol(Match.COMPUTER_SYMBOL);
        }

        return board;
    }

    private static Stream<Arguments> generateComputerMoves() {
        return Stream.of(
                // tests winning move row
                Arguments.of(generateBoard(new int[]{}, new int[]{1,2}), 3),

                // tests winning move column
                Arguments.of(generateBoard(new int[]{}, new int[]{1,4}), 7),

                //prevents winning move row
                Arguments.of(generateBoard(new int[]{1,2}, new int[]{}), 3),

                //prevents winning move column
                Arguments.of(generateBoard(new int[]{1,4}, new int[]{}), 7),

                // tests first move on empty board
                Arguments.of(generateBoard(new int[]{}, new int[]{}), 1),

                // first computer move if middle is not takes and is not first move on the board
                Arguments.of(generateBoard(new int[]{1}, new int[]{}), 5),

                // takes winning move while there is opponent to block
                Arguments.of(generateBoard(new int[]{1,4}, new int[]{3,6}), 9),

                // takes a fork
                Arguments.of(generateBoard(new int[]{5,6}, new int[]{4,9}), 7),
                Arguments.of(generateBoard(new int[]{5,8}, new int[]{2,9}), 3),

                // stopping opponents fork by making him defend
                Arguments.of(generateBoard(new int[]{4,9}, new int[]{5,6}), 2),

                // defend double fork
                Arguments.of(generateBoard(new int[]{5,9}, new int[]{1}), 3),
                Arguments.of(generateBoard(new int[]{1,9}, new int[]{5}), 2)



        );
    }

    @ParameterizedTest
    @MethodSource("generateComputerMoves")
    void computerMove(Board board, int expected) {
        Position position = ComputerMoveService.impossibleComputerMove(board);

        assertEquals(position.getIndex(), expected);
    }




    @Test
    void stoppingOpponentsForkByPlacingSymbolThere(){
        Board board = new Board();
        board.getRows().get(1).getFields().get(0).setSymbol(Match.PLAYER_SYMBOL);
        board.getRows().get(2).getFields().get(2).setSymbol(Match.PLAYER_SYMBOL);

        board.getRows().get(0).getFields().get(1).setSymbol('t');

        board.getRows().get(1).getFields().get(1).setSymbol(Match.COMPUTER_SYMBOL);
        board.getRows().get(1).getFields().get(2).setSymbol(Match.COMPUTER_SYMBOL);

        Position position = ComputerMoveService.impossibleComputerMove(board);

        assertEquals(position.getIndex(), 7);
    }












}