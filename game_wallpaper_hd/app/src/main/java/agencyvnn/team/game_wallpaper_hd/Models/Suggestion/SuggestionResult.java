package agencyvnn.team.game_wallpaper_hd.Models.Suggestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 4/11/18.
 */

public class SuggestionResult {
    @SerializedName("categories")
    @Expose
    private ArrayList<Category> categories;

    public ArrayList<Category> getCategories() { return this.categories; }

    public void setCategories(ArrayList<Category> categories) { this.categories = categories; }
    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags;

    public ArrayList<String> getTags() { return this.tags; }

    public void setTags(ArrayList<String> tags) { this.tags = tags; }
}
