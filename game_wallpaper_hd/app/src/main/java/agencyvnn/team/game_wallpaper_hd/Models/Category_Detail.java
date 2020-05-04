package agencyvnn.team.game_wallpaper_hd.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hiepnt on 05/03/2018.
 */

public class Category_Detail {
    @SerializedName("data")
    @Expose
    private List<Category_Detail_Data> datas = null;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("per_page")
    @Expose
    private int per_page;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("total_pages")
    @Expose
    private int total_pages;

    public List<Category_Detail_Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Category_Detail_Data> datas) {
        this.datas = datas;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}
