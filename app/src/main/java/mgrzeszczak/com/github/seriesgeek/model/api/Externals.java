package mgrzeszczak.com.github.seriesgeek.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Maciej on 21.02.2017.
 */
public class Externals {

    @SerializedName("tvrage")
    private String tvRageId;
    @SerializedName("thetvdb")
    private String tvdbId;
    @SerializedName("imdb")
    private String imdbId;

    public Externals(String tvRageId, String tvdbId, String imdbId) {
        this.tvRageId = tvRageId;
        this.tvdbId = tvdbId;
        this.imdbId = imdbId;
    }

    public String getTvRageId() {
        return tvRageId;
    }

    public void setTvRageId(String tvRageId) {
        this.tvRageId = tvRageId;
    }

    public String getTvdbId() {
        return tvdbId;
    }

    public void setTvdbId(String tvdbId) {
        this.tvdbId = tvdbId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}
