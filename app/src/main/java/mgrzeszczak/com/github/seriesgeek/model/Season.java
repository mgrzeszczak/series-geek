package mgrzeszczak.com.github.seriesgeek.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Maciej on 21.02.2017.
 */
public class Season {

    private String name;
    private int id;
    private int number;
    private String summary;
    @SerializedName("_links")
    private Links links;
    @SerializedName("episodeOrder")
    private int episodeCount;
    private String premiereDate;
    private String endDate;
    private Image image;

    @Override
    public String toString() {
        return "Season{" +
                "image=" + image +
                ", endDate='" + endDate + '\'' +
                ", premiereDate='" + premiereDate + '\'' +
                ", episodeCount=" + episodeCount +
                ", links=" + links +
                ", summary='" + summary + '\'' +
                ", number=" + number +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public String getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(String premiereDate) {
        this.premiereDate = premiereDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Season(String name, int id, int number, String summary, Links links, int episodeCount, String premiereDate, String endDate, Image image) {
        this.name = name;

        this.id = id;
        this.number = number;
        this.summary = summary;
        this.links = links;
        this.episodeCount = episodeCount;
        this.premiereDate = premiereDate;
        this.endDate = endDate;
        this.image = image;
    }
}
