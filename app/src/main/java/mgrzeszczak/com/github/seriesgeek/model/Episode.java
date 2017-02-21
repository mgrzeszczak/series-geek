package mgrzeszczak.com.github.seriesgeek.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Maciej on 21.02.2017.
 */
public class Episode {

    private int id;
    private String name;
    private int season;
    private int number;
    @SerializedName("airstamp")
    private Date airDate;
    private int runtime;
    private Image image;
    private String summary;
    @SerializedName("_links")
    private Links links;

    public Episode(int id, String name, int season, int number, Date airDate, int runtime, Image image, String summary, Links links) {
        this.id = id;
        this.name = name;
        this.season = season;
        this.number = number;
        this.airDate = airDate;
        this.runtime = runtime;
        this.image = image;
        this.summary = summary;
        this.links = links;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getAirDate() {
        return airDate;
    }

    public void setAirDate(Date airDate) {
        this.airDate = airDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    @Override
    public String toString() {
        return "Episode{" +
                "links=" + links +
                ", summary='" + summary + '\'' +
                ", image=" + image +
                ", runtime=" + runtime +
                ", airDate=" + airDate +
                ", number=" + number +
                ", season=" + season +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
