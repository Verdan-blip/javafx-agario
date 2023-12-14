package ru.kpfu.itis.bagaviev.agario.engine.util;

public class AgarFightConclusion {
    private AgarItem winner;
    private AgarItem loser;

    public boolean isDraw() {
        return winner == null && loser == null;
    }

    public AgarItem getWinner() {
        return winner;
    }

    public AgarItem getLoser() {
        return loser;
    }

    public void setWinner(AgarItem winner) {
        this.winner = winner;
    }

    public void setLoser(AgarItem loser) {
        this.loser = loser;
    }

}
