package com.dw.capstonebnform.dao;

import com.dw.capstonebnform.dto.Images;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class ImagesDAO {



    public void insertTask(Map<Integer, List<Images>> imageMap){

        //Since a K contains can contain Multiple Images, set the id for each image
      imageMap.forEach((k, v) -> {
            for (int i = 0; i < v.size(); i++) {
                v.get(i).setRecallId(k.intValue());
            }
        });

      //Flatten the list of Images out as one List<List<Images>> -> List<Images> send to insert
     List<Images> imgList = imageMap.entrySet().stream().filter(k -> k.getKey() != null)
             .flatMap(v -> v.getValue().stream()).collect(Collectors.toList());


        _insertTask(imgList);
    }



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insertTask(List<Images> imagesList);

    @Query("Select * from Images where recallId = :recallId")
    public abstract List<Images> getImages(Integer recallId);

}
