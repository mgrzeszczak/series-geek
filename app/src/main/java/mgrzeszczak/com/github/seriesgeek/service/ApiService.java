package mgrzeszczak.com.github.seriesgeek.service;

import java.util.List;

import mgrzeszczak.com.github.seriesgeek.model.api.Episode;
import mgrzeszczak.com.github.seriesgeek.model.api.Season;
import mgrzeszczak.com.github.seriesgeek.model.api.Series;
import mgrzeszczak.com.github.seriesgeek.model.api.SeriesSearchEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Maciej on 21.02.2017.
 */
public interface ApiService {

    String ENDPOINT = "http://api.tvmaze.com/";

    @GET("shows/{id}")
    Observable<Series> getSeriesInfo(@Path("id") int id);

    @GET("seasons/{id}")
    Observable<Season> getSeasonInfo(@Path("id") int id);

    @GET("search/shows")
    Observable<List<SeriesSearchEntity>> searchSeries(@Query("q") String query);

    @GET("shows/{id}/episodes")
    Observable<List<Episode>> getEpisodes(@Path("id") int showId);

    //@GET("shows/{id}/episodebynumber")
    @GET("episodes/{id}")
    Observable<Episode> getEpisode(@Path("id") int id);

    @GET("shows/{id}/seasons")
    Observable<List<Season>> getSeasons(@Path("id") int showId);

}
