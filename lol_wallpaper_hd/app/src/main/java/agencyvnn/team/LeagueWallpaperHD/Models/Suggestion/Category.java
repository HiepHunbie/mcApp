package agencyvnn.team.LeagueWallpaperHD.Models.Suggestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hiephunbie on 4/11/18.
 */

public class Category {
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
}
