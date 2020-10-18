package com.dw.capstonebnform.dao;

import com.dw.capstonebnform.upc.Item;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class ItemDAO {

    public void insertTask(Map<Integer, List<Item>> itemMap){
        itemMap.forEach((k,v) -> v.get(0).setUpcId(k));

        List<Item> itemList = itemMap.values().stream().map(map -> map.get(0)).collect(Collectors.toList());

        _insertTask(itemList);

    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertTask(List<Item> itemList);


    @Query("Select * from Item where upcId = :upcId")
    public abstract List<Item> getItem(Integer upcId);
}
