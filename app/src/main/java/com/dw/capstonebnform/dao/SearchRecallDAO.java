package com.dw.capstonebnform.dao;

import com.dw.capstonebnform.dto.SearchRecallProducts;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class SearchRecallDAO {

    public void insertTask(List<SearchRecallProducts> searchRecallProducts) {
        _insertRecall(searchRecallProducts);
    }


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertRecall(List<SearchRecallProducts> searchRecallProducts);

    @Transaction
    @Query("SELECT * FROM searchrecallproducts where mDescription = :desc ")
    public abstract LiveData<List<SearchRecallProducts>> getSearchProductsByDescription(String desc);

}
