package mgrzeszczak.com.github.seriesgeek.service;

import android.os.Environment;

import com.facebook.Profile;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mgrzeszczak.com.github.seriesgeek.model.ProfileData;

/**
 * Created by Maciej on 21.02.2017.
 */
public class ProfileService {

    private Gson gson;
    private Map<String,ProfileData> profiles;
    private static final String FILENAME = Environment.getExternalStorageDirectory().getAbsolutePath()+"/series_geek_file";

    public ProfileService(Gson gson) {
        this.gson = gson;
        this.profiles = new HashMap<>();
    }

    public ProfileData get(String profileId){
        if (profiles.containsKey(profileId)) return profiles.get(profileId);
        else return null;
    }

    public void save(ProfileData profile){
        if (profiles.containsKey(profile.getId())) profiles.remove(profile.getId());
        profiles.put(profile.getId(),profile);
        saveToFile();
    }

    public void loadFromFile() {
        Data data = null;
        try {
            data = gson.fromJson(new FileReader(FILENAME), Data.class);
            if (data == null) return;
            profiles = data.getProfiles();
        } catch (FileNotFoundException e) {
            profiles = new HashMap<>();
        }
    }

    private void saveToFile(){
        try {
            gson.toJson(new Data(profiles), new FileWriter(FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Data {
        private Map<String,ProfileData> profiles;

        public Data(Map<String, ProfileData> profiles) {
            this.profiles = profiles;
        }

        public Map<String, ProfileData> getProfiles() {
            return profiles;
        }

        public void setProfiles(Map<String, ProfileData> profiles) {
            this.profiles = profiles;
        }
    }

}
