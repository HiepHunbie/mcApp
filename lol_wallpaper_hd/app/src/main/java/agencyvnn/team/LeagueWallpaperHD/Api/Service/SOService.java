package agencyvnn.team.LeagueWallpaperHD.Api.Service;


import java.util.List;

import agencyvnn.team.LeagueWallpaperHD.Models.Category_Detail;
import agencyvnn.team.LeagueWallpaperHD.Models.CatelogyDetail.CatelogyDetailResult;
import agencyvnn.team.LeagueWallpaperHD.Models.GetCategory.CategoryResult;
import agencyvnn.team.LeagueWallpaperHD.Models.ImageDetail;
import agencyvnn.team.LeagueWallpaperHD.Models.MoreApp;
import agencyvnn.team.LeagueWallpaperHD.Models.SOAnswersResponse;
import agencyvnn.team.LeagueWallpaperHD.Models.Suggestion.SuggestionResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hiepnt on 05/01/2018.
 */

public interface SOService {
    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers();

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers(@Query("tagged") String tags);

    @GET("/categories")
    Call<CategoryResult> getCategorys(
            @Query("page") int page,
            @Query("per_page") int per_page,
            @Query("numberImageView") int numberImageView,
            @Query("parent_id") int parent_id
    );
    @GET("/category")
    Call<CatelogyDetailResult> getCategorys_Detail(
            @Query("category_id") String category_id,
            @Query("page") int page,
            @Query("per_page") int per_page
    );

    @GET("/more_app")
    Call<List<MoreApp>> getMoreApp(
    );

    @GET("/suggestion")
    Call<SuggestionResult> getSuggestion(
    );

    @GET("/images")
    Call<CatelogyDetailResult> getSearchImages(
            @Query("page") int page,
            @Query("per_page") int per_page,
            @Query("query") String query,
            @Query("category_id") String category_id
    );

    @GET("/images")
    Call<CatelogyDetailResult> getSearchImagesNotId(
            @Query("page") int page,
            @Query("per_page") int per_page,
            @Query("query") String query
    );

    @GET("/images/newest")
    Call<Category_Detail> getNewestRecommend(
            @Query("page") int page,
            @Query("per_page") int per_page
    );

    @GET("/image")
    Call<ImageDetail> getImageDetail(
            @Query("public_id") String public_id
    );

    @GET("/images/top_search")
    Call<Category_Detail> getTop(
            @Query("page") int page,
            @Query("per_page") int per_page
    );
}
