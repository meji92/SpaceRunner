package globex.spacerunner;

public class Score {
    private int score = 0;

    public int getScore() {
        return score;
    }

    public String getScoreString() {
        return Integer.toString(score);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }
}
