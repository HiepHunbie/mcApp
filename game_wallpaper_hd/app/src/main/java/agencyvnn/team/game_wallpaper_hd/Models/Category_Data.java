package agencyvnn.team.game_wallpaper_hd.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiepnt on 05/03/2018.
 */

public class Category_Data {
    @SerializedName("category_name")
    @Expose
    private String category_name;
    @SerializedName("category_id")
    @Expose
    private String category_id;
    @SerializedName("thumb")
    @Expose
    private String thumb;

    @SerializedName("total_image")
    @Expose
    private int total_image;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public int getTotal_image() {
        return total_image;
    }

    public void setTotal_image(int total_image) {
        this.total_image = total_image;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
