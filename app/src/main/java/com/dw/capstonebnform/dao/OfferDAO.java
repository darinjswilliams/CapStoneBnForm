package com.dw.capstonebnform.dao;

import com.dw.capstonebnform.upc.Offer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class OfferDAO {

    public void insertTask(Map<Integer, List<Offer>> offerMap){
        offerMap.forEach((k,v) -> v.get(0).setUpcId(k));

        List<Offer> offerList = offerMap.values().stream().map(map -> map.get(0)).collect(Collectors.toList());

        _insertTask(offerList);

    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertTask(List<Offer> OfferList);


    @Query("Select * from OFFER where upcId = :upcId")
    public abstract List<Offer> getOffer(Integer upcId);
}
