package agencyvnn.team.LeagueWallpaperHD.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hiepnt on 07/03/2018.
 */

public class ImageDetail {
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
    private Date created_at;

    public Date getCreatedAt() { return this.created_at; }

    public void setCreatedAt(Date created_at) { this.created_at = created_at; }

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

    @SerializedName("optimizeUrl")
    @Expose
    private String optimizeUrl;

    @SerializedName("viewNumber")
    @Expose
    private int viewNumber;

    public String getPublic_id() {
        return public_id;
    }

    public void setPublic_id(String public_id) {
        this.public_id = public_id;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public boolean isPlaceholder() {
        return placeholder;
    }

    public String getSecure_url() {
        return secure_url;
    }

    public void setSecure_url(String secure_url) {
        this.secure_url = secure_url;
    }

    public String getOriginal_filename() {
        return original_filename;
    }

    public void setOriginal_filename(String original_filename) {
        this.original_filename = original_filename;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean isFeatureImage() {
        return isFeatureImage;
    }

    public void setFeatureImage(boolean featureImage) {
        isFeatureImage = featureImage;
    }

    public String getOptimizeUrl() {
        return optimizeUrl;
    }

    public void setOptimizeUrl(String optimizeUrl) {
        this.optimizeUrl = optimizeUrl;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }
}
