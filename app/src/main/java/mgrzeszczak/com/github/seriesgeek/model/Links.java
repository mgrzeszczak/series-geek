package mgrzeszczak.com.github.seriesgeek.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Maciej on 21.02.2017.
 */
public class Links {

    @SerializedName("nextepisode")
    private Link nextEpisode;
    @SerializedName("prevepisode")
    private Link prevEpisode;
    private Link self;

    public static class Link {

        private String href;

        public Link(String href) {
            this.href = href;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        @Override
        public String toString() {
            return "Link{" +
                    "href='" + href + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Links{" +
                "nextEpisode=" + nextEpisode +
                ", prevEpisode=" + prevEpisode +
                ", self=" + self +
                '}';
    }
}
