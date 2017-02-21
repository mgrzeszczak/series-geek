package mgrzeszczak.com.github.seriesgeek.model;

import java.util.ArrayList;
import java.util.List;

import mgrzeszczak.com.github.seriesgeek.model.api.Series;

/**
 * Created by Maciej on 21.02.2017.
 */
public class ProfileData {

    private String id;
    private List<Integer> savedShows;

    public ProfileData(String id) {
        this.id = id;
        this.savedShows = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getSavedShows() {
        return savedShows;
    }

    public void setSavedShows(List<Integer> savedShows) {
        this.savedShows = savedShows;
    }
}
