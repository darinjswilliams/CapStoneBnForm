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


@Entity(tableName = "hazards", foreignKeys = @ForeignKey(
        entity= Recall.class,
        parentColumns = "recallId",
        childColumns = "recallId",
        onDelete = CASCADE
),
        indices = @Index(value = "recallId", name = "idx_hazards_recall_id")
)
@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Ignore}))
public class Hazards implements Parcelable {

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

    @SerializedName("hazardType")
    @Expose
    private String hazardType;

    @SerializedName("hazardTypeId")
    @Expose
    private String hazardTypeId;

    protected Hazards(Parcel in) {
        name = in.readString();
        hazardType = in.readString();
        hazardTypeId = in.readString();
        recallId = in.readInt();
    }

    public static final Creator<Hazards> CREATOR = new Creator<Hazards>() {
        @Override
        public Hazards createFromParcel(Parcel in) {
            return new Hazards(in);
        }

        @Override
        public Hazards[] newArray(int size) {
            return new Hazards[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(hazardType);
        parcel.writeString(hazardTypeId);
        parcel.writeInt(recallId);
    }
}
