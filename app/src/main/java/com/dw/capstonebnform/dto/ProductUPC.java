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
import lombok.Data;
import lombok.NoArgsConstructor;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "productupc", foreignKeys = @ForeignKey(
        entity = Recall.class,
        parentColumns = "recallId",
        childColumns = "recallId",
        onDelete = CASCADE
),
        indices = @Index(value = "recallId", name = "idx_productupc_recall_id")
)
@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Ignore}))

public class ProductUPC implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("recallId")
    @Expose
    private Integer recallId;

    @SerializedName("upc")
    @Expose
    private String upc;

    protected ProductUPC(Parcel in) {
        upc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(upc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductUPC> CREATOR = new Creator<ProductUPC>() {
        @Override
        public ProductUPC createFromParcel(Parcel in) {
            return new ProductUPC(in);
        }

        @Override
        public ProductUPC[] newArray(int size) {
            return new ProductUPC[size];
        }
    };
}
