package model;

public class Rating {
    private double totalScore;
    private long totalRatings;

    public Rating() {
        this.totalScore = 0;
        this.totalRatings = 0;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

    public double getRating() {
        return totalScore / totalRatings;
    }

    public void addRating(double score) {
        if (score < 0.0d) score = 0;
        if (score > 5.0d) score = 5.0d;

        totalScore += score;
        totalRatings++;
    }
}
