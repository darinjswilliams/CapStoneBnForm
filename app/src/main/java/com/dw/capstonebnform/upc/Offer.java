package com.dw.capstonebnform.upc;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

@Entity(tableName = "offer", foreignKeys = @ForeignKey(
        entity = UPCodeSearch.class,
        parentColumns = "id",
        childColumns = "upcId",
        onDelete = CASCADE
),
        indices = @Index(value = "upcId", name = "idx_offer_upc_search_id")
)
@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Ignore}))
@Builder
public class Offer implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("upcId")
    @Expose
    private Integer upcId;

    @SerializedName("merchant")
    @Expose
    private String merchant;

    @SerializedName("domain")
    @Expose
    private String domain;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("list_price")
    @Expose
    private String listPrice;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("shipping")
    @Expose
    private String shipping;

    @SerializedName("condition")
    @Expose
    private String condition;

    @SerializedName("availability")
    @Expose
    private String availability;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("updatedT")
    @Expose
    private Long updatedT;

    protected Offer(Parcel in) {
        merchant = in.readString();
        domain = in.readString();
        title = in.readString();
        currency = in.readString();
        listPrice = in.readString();

        if(in.readDouble() == 0){
            price = null;
        } else {
            price = in.readDouble();
        }

        shipping = in.readString();
        condition = in.readString();
        availability = in.readString();
        link = in.readString();

        if(in.readLong() == 0){
            updatedT = null;
        } else {
            updatedT = in.readLong();
        }

        upcId = in.readInt();
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(merchant);
        parcel.writeString(domain);
        parcel.writeString(title);
        parcel.writeString(currency);
        parcel.writeString(listPrice);

        if(price == null){
            parcel.writeDouble((byte) 0);
        } else {
            parcel.writeDouble((byte) 1);
            parcel.writeDouble(price);
        }

        parcel.writeString(shipping);
        parcel.writeString(condition);
        parcel.writeString(availability);
        parcel.writeString(link);
        parcel.writeLong(updatedT);

        if(updatedT == null){
            parcel.writeDouble((byte) 0);
        } else {
            parcel.writeDouble((byte) 1);
            parcel.writeDouble(updatedT);
        }

        parcel.writeInt(upcId);
    }
}
