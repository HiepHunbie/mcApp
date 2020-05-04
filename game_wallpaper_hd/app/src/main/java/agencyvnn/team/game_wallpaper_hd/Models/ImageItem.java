package agencyvnn.team.game_wallpaper_hd.Models;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

/**
 * Created by hiepnt on 01/02/2018.
 */

public class ImageItem implements AsymmetricItem {
    private int columnSpan;
    private int rowSpan;
    private int position;

    public ImageItem() {
        this(1, 1, 0);
    }

    public ImageItem(int columnSpan, int rowSpan, int position) {
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
        this.position = position;
    }

    public ImageItem(Parcel in) {
        readFromParcel(in);
    }

    @Override public int getColumnSpan() {
        return columnSpan;
    }

    @Override public int getRowSpan() {
        return rowSpan;
    }

    public int getPosition() {
        return position;
    }

    @Override public String toString() {
        return String.format("%s: %sx%s", position, rowSpan, columnSpan);
    }

    @Override public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in) {
        columnSpan = in.readInt();
        rowSpan = in.readInt();
        position = in.readInt();
    }

    @Override public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(columnSpan);
        dest.writeInt(rowSpan);
        dest.writeInt(position);
    }

    /* Parcelable interface implementation */
    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override public ImageItem createFromParcel(@NonNull Parcel in) {
            return new ImageItem(in);
        }

        @Override @NonNull public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
