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

@Entity(tableName = "retailers", foreignKeys = @ForeignKey(
        entity = Recall.class,
        parentColumns = "recallId",
        childColumns = "recallId",
        onDelete = CASCADE
),
        indices = @Index(value = "recallId", name = "idx_retailers_recall_id")
)
@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Ignore}))
public class Retailers implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("recallId")
    @Expose
    private Integer recallId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("companyId")
    @Expose
    private String companyId;

    protected Retailers(Parcel in) {
        name = in.readString();
        companyId = in.readString();
    }

    public static final Creator<Retailers> CREATOR = new Creator<Retailers>() {
        @Override
        public Retailers createFromParcel(Parcel in) {
            return new Retailers(in);
        }

        @Override
        public Retailers[] newArray(int size) {
            return new Retailers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(companyId);
    }
}
