package mgrzeszczak.com.github.seriesgeek.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mgrzeszczak.com.github.seriesgeek.model.api.Series;

/**
 * Created by Maciej on 21.02.2017.
 */
public class ProfileData {

    private String id;
    private Set<Integer> savedShows;

    public ProfileData(String id) {
        this.id = id;
        this.savedShows = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Integer> getSavedShows() {
        return savedShows;
    }

    public void setSavedShows(Set<Integer> savedShows) {
        this.savedShows = savedShows;
    }
}
