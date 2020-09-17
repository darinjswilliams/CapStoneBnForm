package com.dw.capstonebnform.dto;

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


@Entity(tableName = "Product", foreignKeys = @ForeignKey(
        entity = Recall.class,
        parentColumns = "recallId",
        childColumns = "recallId",
        onDelete = CASCADE
),
        indices =  @Index(value = "recallId", name = "idx_product_recall_id")
)
@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Ignore}))
@Builder

public class Product implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("RecallId")
    @Expose
    private Integer recallId;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Description")
    @Expose
    private String description;

    @SerializedName("Model")
    @Expose
    private String model;

    @SerializedName("Type")
    @Expose
    private String type;

    @SerializedName("CategoryID")
    @Expose
    private String categoryId;

    @SerializedName("NumberOfUnits")
    @Expose
    private String numberOfUnits;

    protected Product(Parcel in) {
        name = in.readString();
        description = in.readString();
        model = in.readString();
        type = in.readString();
        categoryId = in.readString();
        numberOfUnits = in.readString();
        recallId = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(model);
        parcel.writeString(type);
        parcel.writeString(categoryId);
        parcel.writeString(numberOfUnits);
        parcel.writeInt(recallId);
    }
}
