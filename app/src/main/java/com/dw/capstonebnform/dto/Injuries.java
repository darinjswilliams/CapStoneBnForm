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

@Entity(tableName = "injuries", foreignKeys = @ForeignKey(
        entity = Recall.class,
        parentColumns = "recallId",
        childColumns = "recallId",
        onDelete = CASCADE
),
        indices = @Index(value = "recallId", name = "idx_injuries_recall_id")
)
@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Ignore}))

public class Injuries implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("recallId")
    @Expose
    private Integer recallId;

    @SerializedName("Name")
    @Expose
    private String name;

    protected Injuries(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Injuries> CREATOR = new Creator<Injuries>() {
        @Override
        public Injuries createFromParcel(Parcel in) {
            return new Injuries(in);
        }

        @Override
        public Injuries[] newArray(int size) {
            return new Injuries[size];
        }
    };
}
