package agencyvnn.team.LeagueWallpaperHD.Models.GetCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 4/11/18.
 */

public class CategoryResult {
    @SerializedName("page")
    @Expose
    private int page;

    public int getPage() { return this.page; }

    public void setPage(int page) { this.page = page; }
    @SerializedName("perPage")
    @Expose
    private int perPage;

    public int getPerPage() { return this.perPage; }

    public void setPerPage(int perPage) { this.perPage = perPage; }
    @SerializedName("total")
    @Expose
    private int total;

    public int getTotal() { return this.total; }

    public void setTotal(int total) { this.total = total; }
    @SerializedName("total_pages")
    @Expose
    private int total_pages;

    public int getTotalPages() { return this.total_pages; }

    public void setTotalPages(int total_pages) { this.total_pages = total_pages; }
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data;

    public ArrayList<Datum> getData() { return this.data; }

    public void setData(ArrayList<Datum> data) { this.data = data; }
}
