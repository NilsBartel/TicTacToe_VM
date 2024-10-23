public final class StartGameUtil {
    private StartGameUtil() {
    }


    public static Match returnRunningOrNewMatch(MatchHistory matchHistory, DifficultyState difficulty, FileWriteRead fileWriteRead) {

        Match match;

        if (!matchHistory.getMatches().isEmpty() && (matchHistory.compareLastStatus(MatchStatus.RUNNING) || matchHistory.compareLastStatus(MatchStatus.NOT_STARTED) || matchHistory.compareLastStatus(MatchStatus.MATCH_ALREADY_FINISHED))) {
            System.out.println("loaded a match");
            match = matchHistory.getMatches().getLast();
        } else {
            System.out.println("Creating a new match");
            match = new Match();
            match.setDifficulty(difficulty);
            if (difficulty == null) {
                match.setDifficulty(PlayerInput.getInstance().askForDifficulty());
            }
            matchHistory.addMatch(match);
            fileWriteRead.writeToHistoryFile(FileService.getInstance().getFILE_MATCH_HISTORY(), matchHistory);
        }

        return match;
    }

    public static Score updateScore(Match match, FileWriteRead fileWriteRead, PrintService printService, MatchHistory matchHistory) {
        MatchStatus status = match.getStatus();
        //Score score = fileWriteRead.readFile(FileService.getInstance().getFILE_SCORE());
        Score score = matchHistory.getScore();

        switch (status) {
            case PLAYER_WON -> {
                score.setPlayerScorePlusOne();
                printService.printWhoWon(match.isIsPlayerTurn());
            }
            case COMPUTER_WON -> {
                score.setComputerScorePlusOne();
                printService.printWhoWon(match.isIsPlayerTurn());
            }
            case DRAW -> {
                score.setDrawCountPlusOne();
                printService.printDraw();
            }
            case MATCH_ALREADY_FINISHED -> {
            }
            case NOT_STARTED, RUNNING -> {}
            default -> printService.printInvalidStatus();
        }
        matchHistory.updateScore(score);
        //fileWriteRead.writeFile(FileService.getInstance().getFILE_SCORE(), score);

        return score;
    }

    public static Score updateScoreForRandom(Match match, FileWriteRead fileWriteRead, PrintService printService) {
        MatchStatus status = match.getStatus();
        Score score = fileWriteRead.readFile(MakeComputerPlayRandom.SCORE_FILE_TEST);

        switch (status) {
            case PLAYER_WON -> {
                score.setPlayerScorePlusOne();
                //printService.printWhoWon(match.isIsPlayerTurn());
            }
            case COMPUTER_WON -> {
                score.setComputerScorePlusOne();
                //printService.printWhoWon(match.isIsPlayerTurn());
            }
            case DRAW -> {
                score.setDrawCountPlusOne();
                //printService.printDraw();
            }
            case MATCH_ALREADY_FINISHED -> {
            }
            case NOT_STARTED, RUNNING -> {}
            default -> printService.printInvalidStatus();
        }
        fileWriteRead.writeFile(MakeComputerPlayRandom.SCORE_FILE_TEST, score);

        return score;
    }










}
