package com.dw.capstonebnform.upc;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "upc")
@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Ignore}))
@Builder
public class UPCodeSearch implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("total")
    @Expose
    private Integer total;

    @SerializedName("offset")
    @Expose
    private Integer offset;

    @Ignore
    private List<Item> items;

    protected UPCodeSearch(Parcel in) {
        code = in.readString();

        if (in.readByte() == 0) {
           total = null;
        } else {
            total = in.readInt();
        }

        if (in.readByte() == 0) {
            offset = null;
        } else {
            offset = in.readInt();
        }

        items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Creator<UPCodeSearch> CREATOR = new Creator<UPCodeSearch>() {
        @Override
        public UPCodeSearch createFromParcel(Parcel in) {
            return new UPCodeSearch(in);
        }

        @Override
        public UPCodeSearch[] newArray(int size) {
            return new UPCodeSearch[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(code);

        if(total == null){
            parcel.writeByte( (byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(total);
        }

        if(offset == null){
            parcel.writeByte( (byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(offset);
        }

        parcel.writeTypedList(items);
    }
}
