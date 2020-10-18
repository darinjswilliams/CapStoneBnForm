package com.dw.capstonebnform.upc;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

@Entity(tableName = "item", foreignKeys = @ForeignKey(
        entity = UPCodeSearch.class,
        parentColumns = "id",
        childColumns = "upcId",
        onDelete = CASCADE
),
        indices = @Index(value = "upcId", name = "idx_item_upc_search_id")
)

@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Ignore}))
@Builder
public class Item implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("upcId")
    @Expose
    private Integer upcId;

    @SerializedName("ean")
    @Expose
    private String ean;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("upc")
    @Expose
    private String upc;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("size")
    @Expose
    private String size;

    @SerializedName("dimension")
    @Expose
    private String dimension;

    @SerializedName("weight")
    @Expose
    private String weight;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("lowestRecordedPrice")
    @Expose
    private Double lowestRecordedPrice;

    @SerializedName("highestRecordedPrice")
    @Expose
    private Long highestRecordedPrice;

    @Ignore
    private List<UrlList> images;

    @Ignore
    private List<Offer> offers;

    @SerializedName("asin")
    @Expose
    private String asin;

    @SerializedName("elid")
    @Expose
    private String elid;

    protected Item(Parcel in) {
        ean = in.readString();
        title = in.readString();
        description = in.readString();
        upc = in.readString();
        brand = in.readString();
        model = in.readString();
        color = in.readString();
        size = in.readString();
        dimension = in.readString();
        weight = in.readString();
        category = in.readString();
        currency = in.readString();

        if (in.readByte() == 0) {
            lowestRecordedPrice = null;
        } else {
            lowestRecordedPrice = in.readDouble();
        }

        if (in.readByte() == 0) {
            highestRecordedPrice = null;
        } else {
            highestRecordedPrice = in.readLong();
        }

        images = in.createTypedArrayList(UrlList.CREATOR);
        asin = in.readString();
        elid = in.readString();
        upcId = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ean);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(upc);
        parcel.writeString(brand);
        parcel.writeString(model);
        parcel.writeString(color);
        parcel.writeString(size);
        parcel.writeString(dimension);
        parcel.writeString(weight);
        parcel.writeString(category);
        parcel.writeString(currency);

        if(lowestRecordedPrice == null){
            parcel.writeByte( (byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(lowestRecordedPrice);
        }

        if(highestRecordedPrice == null){
            parcel.writeByte( (byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(highestRecordedPrice);
        }

        parcel.writeTypedList(images);
        parcel.writeString(asin);
        parcel.writeString(elid);
        parcel.writeInt(upcId);
    }
}
