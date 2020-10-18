package com.dw.capstonebnform.dao;

import com.dw.capstonebnform.upc.UrlList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class UrlListDAO {

    public void insertTask(Map<Integer, List<UrlList>> urlMap){
        urlMap.forEach((k,v) -> v.get(0).setUpcId(k));

        List<UrlList> urlList = urlMap.values().stream().map(map -> map.get(0)).collect(Collectors.toList());

        _insertTask(urlList);

    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertTask(List<UrlList> urlLists);


    @Query("Select * from UrlList where upcId = :upcId")
    public abstract List<UrlList> getUrlList(Integer upcId);

}
