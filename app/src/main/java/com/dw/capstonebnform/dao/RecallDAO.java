package com.dw.capstonebnform.dao;

import com.dw.capstonebnform.dto.Recall;
import com.dw.capstonebnform.dto.RecallWithDistributorsAndManufactures;
import com.dw.capstonebnform.dto.RecallWithImagesHazardRemedies;
import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.dw.capstonebnform.dto.RecallWithProductsAndImages;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class RecallDAO {


    public void insertTask(List<Recall> recall) {
        _insertRecall(recall);
    }


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertRecall(List<Recall> recall);


    //Room will perfrom get all instance of the data class that pairs the parent entity
    //and Child Entity. Room will run all queries automatically
    @Transaction
    @Query("SELECT * FROM recall where recallId = :recallId ")
    public abstract LiveData<List<RecallWithDistributorsAndManufactures>> getRecallWithDistributorsAndManufactures(int recallId);

    @Transaction
    @Query("SELECT * FROM recall where recallId = :recallId ")
    public abstract LiveData<List<RecallWithImagesHazardRemedies>> getRecallWithImagesHazardRemedies(int recallId);


    @Transaction
    @Query("SELECT * FROM recall")
    public abstract LiveData<List<RecallWithInjuriesAndImagesAndProducts>> getRecallWithInjuriesAndImagesAndProducts();


    @Transaction
    @Query("SELECT * FROM recall where recallId = :recallId ")
    public abstract LiveData<List<RecallWithProductsAndImages>> getRecallWithProductsAndImagess(int recallId);

}
