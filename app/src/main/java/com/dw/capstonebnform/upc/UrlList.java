package com.dw.capstonebnform.upc;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "UrlList", foreignKeys = @ForeignKey(
        entity = UPCodeSearch.class,
        parentColumns = "id",
        childColumns = "upcId",
        onDelete = CASCADE
),
        indices = @Index(value = "upcId", name = "idx_url_upc_search_id")
)
@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Ignore}))
@Builder
public class UrlList implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("upcId")
    @Expose
    private Integer upcId;

    @SerializedName("url")
    @ColumnInfo(defaultValue = "images")
    @Expose
    String url;

    protected UrlList(Parcel in) {
        url = in.readString();
        upcId = in.readInt();
    }

    public static final Creator<UrlList> CREATOR = new Creator<UrlList>() {
        @Override
        public UrlList createFromParcel(Parcel in) {
            return new UrlList(in);
        }

        @Override
        public UrlList[] newArray(int size) {
            return new UrlList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeInt(upcId);
    }
}
