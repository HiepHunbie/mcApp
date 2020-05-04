package agencyvnn.team.beautiful_girlshd.Api.Service;


import agencyvnn.team.beautiful_girlshd.Model.Category_Detail;
import agencyvnn.team.beautiful_girlshd.Model.Category;
import agencyvnn.team.beautiful_girlshd.Model.ImageDetail;
import agencyvnn.team.beautiful_girlshd.Model.MoreApp;
import agencyvnn.team.beautiful_girlshd.Model.SOAnswersResponse;
import agencyvnn.team.beautiful_girlshd.Model.Tags;

import java.util.List;

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
    Call<Category> getCategorys(
            @Query("page") int page,
            @Query("per_page") int per_page
    );

    @GET("/category")
    Call<Category_Detail> getCategorys_Detail(
            @Query("category_name") String category_name,
            @Query("page") int page,
            @Query("per_page") int per_page
    );

    @GET("/more_app")
    Call<List<MoreApp>> getMoreApp(
    );

    @GET("/suggestion")
    Call<Tags> getSuggestion(
    );

    @GET("/images")
    Call<Category_Detail> getSearchImages(
            @Query("page") int page,
            @Query("per_page") int per_page,
            @Query("query") String query
    );

    @GET("/images/top_search")
    Call<Category_Detail> getRandomRecommend(
            @Query("page") int page,
            @Query("per_page") int per_page
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
}
