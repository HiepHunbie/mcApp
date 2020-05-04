package agencyvnn.team.game_wallpaper_hd.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiepnt on 05/03/2018.
 */

public class Category_Detail_Data {
    @SerializedName("public_id")
    @Expose
    private String public_id;
    @SerializedName("category_name")
    @Expose
    private String category_name;
    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("optimizeUrl")
    @Expose
    private String optimizeUrl;

    public String getOptimizeUrl() {
        return optimizeUrl;
    }

    public void setOptimizeUrl(String optimizeUrl) {
        this.optimizeUrl = optimizeUrl;
    }

    public String getPublic_id() {
        return public_id;
    }

    public void setPublic_id(String public_id) {
        this.public_id = public_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
