package mgrzeszczak.com.github.seriesgeek.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Maciej on 21.02.2017.
 */
public class SeriesSearchEntity {

    @SerializedName("show")
    private Series series;
    private float score;

    public SeriesSearchEntity(Series series, float score) {
        this.series = series;
        this.score = score;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
