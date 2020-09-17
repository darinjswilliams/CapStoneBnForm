package com.dw.capstonebnform.dao;

import com.dw.capstonebnform.dto.Hazards;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface HazardsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(List<Hazards> hazardsList);

    @Query("Select * from hazards where recallId = :recallId")
    List<Hazards> getHazards(int recallId);

}
