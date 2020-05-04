package agencyvnn.team.game_wallpaper_hd.Models.GetCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 4/11/18.
 */

public class Datum {

    @SerializedName("thumb")
    @Expose
    private String thumb;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    @SerializedName("totalImages")
    @Expose
    private int totalImages;

    public int getTotalImages() {
        return totalImages;
    }
    public void setTotalImages(int totalImages) {
        this.totalImages = totalImages;
    }
    @SerializedName("category_id")
    @Expose
    private String category_id;

    public String getCategoryId() { return this.category_id; }

    public void setCategoryId(String category_id) { this.category_id = category_id; }
    @SerializedName("category_name")
    @Expose
    private String category_name;

    public String getCategoryName() { return this.category_name; }

    public void setCategoryName(String category_name) { this.category_name = category_name; }
    @SerializedName("imageInCategory")
    @Expose
    private ArrayList<ImageInCategory> imageInCategory;

    public ArrayList<ImageInCategory> getImageInCategory() { return this.imageInCategory; }

    public void setImageInCategory(ArrayList<ImageInCategory> imageInCategory) { this.imageInCategory = imageInCategory; }
}
