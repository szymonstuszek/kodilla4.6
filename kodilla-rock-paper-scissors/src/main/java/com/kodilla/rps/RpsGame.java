package com.kodilla.rps;

public class RpsGame extends Game {
    public static final String gameName = "Rock Paper Scissors";



    public RpsGame(String name, int maxRounds, int[][] rules) {
        super(name, maxRounds, rules);
    }

    @Override
    public RoundResult getResult(int player1, int player2) {
        int result = Rules.rpsRules[player1][player2];

        String resultString = Integer.toString(result);
        RoundResult roundResult = null;

        for (int i = 0; i < RoundResult.values().length; i++) {
            if(resultString.equals(RoundResult.values()[i].getRoundResultId())) {
                roundResult = RoundResult.values()[i];
                break;
            }
        }

        return roundResult;
    }
}