package com.dw.capstonebnform.dto;


import android.os.Parcel;
import android.os.Parcelable;

import com.dw.capstonebnform.upc.Item;
import com.dw.capstonebnform.upc.Offer;
import com.dw.capstonebnform.upc.UPCodeSearch;
import com.dw.capstonebnform.upc.UrlList;

import java.util.List;

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
public class UpcWithOfferItemList implements Parcelable {

    @Embedded
    public UPCodeSearch upCodeSearch;

    @Relation(
            parentColumn = "id",
            entityColumn =  "upcId",
            entity = Offer.class
    )
    public List<Offer> offerList;

    @Relation(
            parentColumn = "id",
            entityColumn =  "upcId",
            entity = Item.class
    )
    public List<Item> itemList;

    @Relation(
            parentColumn = "id",
            entityColumn =  "upcId",
            entity = UrlList.class
    )
    public List<UrlList> urlList;


    protected UpcWithOfferItemList(Parcel in) {
        upCodeSearch = in.readParcelable(UPCodeSearch.class.getClassLoader());
        offerList = in.createTypedArrayList(Offer.CREATOR);
        itemList = in.createTypedArrayList(Item.CREATOR);
        urlList = in.createTypedArrayList(UrlList.CREATOR);
    }

    public static final Creator<UpcWithOfferItemList> CREATOR = new Creator<UpcWithOfferItemList>() {
        @Override
        public UpcWithOfferItemList createFromParcel(Parcel in) {
            return new UpcWithOfferItemList(in);
        }

        @Override
        public UpcWithOfferItemList[] newArray(int size) {
            return new UpcWithOfferItemList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(upCodeSearch, i);
        parcel.writeTypedList(offerList);
        parcel.writeTypedList(itemList);
        parcel.writeTypedList(urlList);

    }
}
