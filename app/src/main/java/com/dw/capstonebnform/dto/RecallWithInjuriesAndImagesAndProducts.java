package com.dw.capstonebnform.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import androidx.annotation.Keep;
import androidx.room.Embedded;
import androidx.room.Relation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Keep
public class RecallWithInjuriesAndImagesAndProducts implements Parcelable {

    @Embedded
    public Recall recall;

    @Relation(
            parentColumn = "recallId",
            entityColumn =  "recallId",
            entity = Injuries.class
    )
    public List<Injuries> injuriesList;

    @Relation(
            parentColumn = "recallId",
            entityColumn =  "recallId",
            entity = Images.class
    )
    public List<Images> imagesList;

    @Relation(
            parentColumn = "recallId",
            entityColumn =  "recallId",
            entity = Product.class
    )
    public List<Product> productList;

    protected RecallWithInjuriesAndImagesAndProducts(Parcel in) {
        recall = in.readParcelable(Recall.class.getClassLoader());
        injuriesList = in.createTypedArrayList(Injuries.CREATOR);
        imagesList = in.createTypedArrayList(Images.CREATOR);
        productList = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Creator<RecallWithInjuriesAndImagesAndProducts> CREATOR = new Creator<RecallWithInjuriesAndImagesAndProducts>() {
        @Override
        public RecallWithInjuriesAndImagesAndProducts createFromParcel(Parcel in) {
            return new RecallWithInjuriesAndImagesAndProducts(in);
        }

        @Override
        public RecallWithInjuriesAndImagesAndProducts[] newArray(int size) {
            return new RecallWithInjuriesAndImagesAndProducts[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(recall, i);
        parcel.writeTypedList(injuriesList);
        parcel.writeTypedList(imagesList);
        parcel.writeTypedList(productList);
    }
}
