public class StartGame {


    public void start() {
        PrintService printService = PrintService.getInstance();
        PlayerInput playerInput = PlayerInput.getInstance();
        FileWriteRead fileWriteRead = FileWriteRead.getInstance();


        FileService.getInstance().setFileName(playerInput.askForName());

        DifficultyState difficulty = null;
        do {

            MatchHistory matchHistory = fileWriteRead.readFromHistoryFile(FileService.getInstance().getFILE_MATCH_HISTORY());
            Match match = StartGameUtil.returnRunningOrNewMatch(matchHistory, difficulty, fileWriteRead);
            difficulty = match.getDifficulty();


            match.play(matchHistory);

            Score score = StartGameUtil.updateScore(match, fileWriteRead, printService, matchHistory);
            //Score score = matchHistory.getScores().getFirst();

            displayScore(score);
            fileWriteRead.writeToHistoryFile(FileService.getInstance().getFILE_MATCH_HISTORY(), matchHistory);

            matchHistory = fileWriteRead.readFromHistoryFile(FileService.getInstance().getFILE_MATCH_HISTORY());
            printService.printAnalysedWinPositions(AnalyseService.getInstance().findBestWinningLine(matchHistory));
        } while (playerInput.askPlayAgainWithHistory());

        printService.printGameEndMessage();
    }




    private void displayScore(Score score) {
        PrintService.getInstance().printScore(score);
        PrintService.getInstance().printDrawCounter(score);
        PrintService.getInstance().printRoundCounter(score);
    }

}
