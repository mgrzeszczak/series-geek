package mgrzeszczak.com.github.seriesgeek.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Maciej on 21.02.2017.
 */
public class Series {

    private int id;
    private List<String> genres;
    private Image image;
    private String name;
    private String summary;
    private int runtime;
    private String status;
    @SerializedName("_links")
    private Links links;

    public Series(int id, List<String> genres, Image image, String name, String summary, int runtime, String status, Links links) {
        this.id = id;
        this.genres = genres;
        this.image = image;
        this.name = name;
        this.summary = summary;
        this.runtime = runtime;
        this.status = status;
        this.links = links;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Series{" +
                "id=" + id +
                ", genres=" + genres +
                ", image=" + image +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", runtime=" + runtime +
                ", status='" + status + '\'' +
                ", links=" + links +
                '}';
    }
}
