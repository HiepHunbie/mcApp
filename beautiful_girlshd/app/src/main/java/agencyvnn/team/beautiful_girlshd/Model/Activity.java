package agencyvnn.team.beautiful_girlshd.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiepnt on 05/01/2018.
 */

public class Activity {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("comment")
    @Expose
    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
