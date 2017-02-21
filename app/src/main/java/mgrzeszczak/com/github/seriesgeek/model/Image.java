package mgrzeszczak.com.github.seriesgeek.model;

/**
 * Created by Maciej on 21.02.2017.
 */
public class Image {

    private String medium;
    private String original;

    public Image(String medium, String original) {
        this.medium = medium;
        this.original = original;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    @Override
    public String toString() {
        return "Image{" +
                "medium='" + medium + '\'' +
                ", original='" + original + '\'' +
                '}';
    }
}
