package agencyvnn.team.LeagueWallpaperHD.Models.GetCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hiephunbie on 4/11/18.
 */

public class ImageInCategory {
    @SerializedName("public_id")
    @Expose
    private String public_id;

    public String getPublicId() { return this.public_id; }

    public void setPublicId(String public_id) { this.public_id = public_id; }
    @SerializedName("version")
    @Expose
    private int version;

    public int getVersion() { return this.version; }

    public void setVersion(int version) { this.version = version; }
    @SerializedName("signature")
    @Expose
    private String signature;

    public String getSignature() { return this.signature; }

    public void setSignature(String signature) { this.signature = signature; }
    @SerializedName("width")
    @Expose
    private int width;

    public int getWidth() { return this.width; }

    public void setWidth(int width) { this.width = width; }
    @SerializedName("height")
    @Expose
    private int height;

    public int getHeight() { return this.height; }

    public void setHeight(int height) { this.height = height; }
    @SerializedName("format")
    @Expose
    private String format;

    public String getFormat() { return this.format; }

    public void setFormat(String format) { this.format = format; }
    @SerializedName("resource_type")
    @Expose
    private String resource_type;

    public String getResourceType() { return this.resource_type; }

    public void setResourceType(String resource_type) { this.resource_type = resource_type; }
    @SerializedName("created_at")
    @Expose
    private String created_at;

    public String getCreatedAt() { return this.created_at; }

    public void setCreatedAt(String created_at) { this.created_at = created_at; }
    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags;

    public ArrayList<String> getTags() { return this.tags; }

    public void setTags(ArrayList<String> tags) { this.tags = tags; }
    @SerializedName("bytes")
    @Expose
    private int bytes;

    public int getBytes() { return this.bytes; }

    public void setBytes(int bytes) { this.bytes = bytes; }
    @SerializedName("type")
    @Expose
    private String type;

    public String getType() { return this.type; }

    public void setType(String type) { this.type = type; }
    @SerializedName("etag")
    @Expose
    private String etag;

    public String getEtag() { return this.etag; }

    public void setEtag(String etag) { this.etag = etag; }
    @SerializedName("placeholder")
    @Expose
    private boolean placeholder;

    public boolean getPlaceholder() { return this.placeholder; }

    public void setPlaceholder(boolean placeholder) { this.placeholder = placeholder; }
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() { return this.url; }

    public void setUrl(String url) { this.url = url; }
    @SerializedName("secure_url")
    @Expose
    private String secure_url;

    public String getSecureUrl() { return this.secure_url; }

    public void setSecureUrl(String secure_url) { this.secure_url = secure_url; }
    @SerializedName("original_filename")
    @Expose
    private String original_filename;

    public String getOriginalFilename() { return this.original_filename; }

    public void setOriginalFilename(String original_filename) { this.original_filename = original_filename; }
    @SerializedName("optimizeUrl")
    @Expose
    private String optimizeUrl;

    public String getOptimizeUrl() { return this.optimizeUrl; }

    public void setOptimizeUrl(String optimizeUrl) { this.optimizeUrl = optimizeUrl; }
    @SerializedName("category_name")
    @Expose
    private String category_name;

    public String getCategoryName() { return this.category_name; }

    public void setCategoryName(String category_name) { this.category_name = category_name; }
    @SerializedName("isFeatureImage")
    @Expose
    private boolean isFeatureImage;

    public boolean getIsFeatureImage() { return this.isFeatureImage; }

    public void setIsFeatureImage(boolean isFeatureImage) { this.isFeatureImage = isFeatureImage; }
    @SerializedName("viewNumber")
    @Expose
    private int viewNumber;

    public int getViewNumber() { return this.viewNumber; }

    public void setViewNumber(int viewNumber) { this.viewNumber = viewNumber; }
    @SerializedName("category_id")
    @Expose
    private String category_id;

    public String getCategoryId() { return this.category_id; }

    public void setCategoryId(String category_id) { this.category_id = category_id; }
}

