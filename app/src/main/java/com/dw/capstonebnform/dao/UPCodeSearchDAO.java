package com.dw.capstonebnform.dao;

import com.dw.capstonebnform.dto.UpcWithOfferItemList;
import com.dw.capstonebnform.upc.UPCodeSearch;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class UPCodeSearchDAO {

    public void insertTask(List<UPCodeSearch> upCodeSearch){ _insertUPCodeSearch(upCodeSearch); }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertUPCodeSearch(List<UPCodeSearch> upCodeSearch);

    @Transaction
    @Query("SELECT * FROM upc")
    public abstract LiveData<List<UpcWithOfferItemList>> getUpcWithOfferItemList();
}
