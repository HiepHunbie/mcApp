package agencyvnn.team.beautiful_girlshd.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiepnt on 06/03/2018.
 */

public class Tags {
    @SerializedName("categories")
    @Expose
    private ArrayList<String> categories;

    public ArrayList<String> getCategories() { return this.categories; }

    public void setCategories(ArrayList<String> categories) { this.categories = categories; }

    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags;

    public ArrayList<String> getTags() { return this.tags; }

    public void setTags(ArrayList<String> tags) { this.tags = tags; }

}
