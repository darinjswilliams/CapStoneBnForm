package com.dw.capstonebnform.dao;

import com.dw.capstonebnform.dto.Injuries;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class InjuriesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTask(Map<Integer, List<Injuries>> injuriesMap) {

        //Since a K contains can contain Multiple Images, set the id for each image
        injuriesMap.forEach((k, v) -> {
            for (int i = 0; i < v.size(); i++) {
                v.get(i).setRecallId(k.intValue());
            }
        });

        //Flatten the list of Images out as one List<List<Images>> -> List<Images> send to insert
        List<Injuries> ingList = injuriesMap.entrySet().stream().filter(k -> k.getKey() != null)
                .flatMap(v -> v.getValue().stream()).collect(Collectors.toList());

        _insertTask(ingList);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertTask(List<Injuries> injuriesList);

    @Query("Select * from injuries where recallId = :recallId")
    public abstract List<Injuries> getInjuries(int recallId);

}
