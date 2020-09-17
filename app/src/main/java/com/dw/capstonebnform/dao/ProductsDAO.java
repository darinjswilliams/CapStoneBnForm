package com.dw.capstonebnform.dao;

import com.dw.capstonebnform.dto.Product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class ProductsDAO {

    public void insertTask(Map<Integer, List<Product>> productMap){

        //set recall id number and set it on the product
        productMap.forEach((k, v) -> v.get(0).setRecallId(k));

        List<Product> pList = productMap.values().stream().map(map -> map.get(0)).collect(Collectors.toList());

        _insertTask(pList);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertTask(List<Product> productList);


    @Query("Select * from Product where RecallId = :recallId")
    public abstract List<Product> getProduct(Integer recallId);

}
