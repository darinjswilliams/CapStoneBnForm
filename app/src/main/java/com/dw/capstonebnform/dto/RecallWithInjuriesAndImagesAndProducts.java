package com.dw.capstonebnform.dto;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecallWithInjuriesAndImagesAndProducts {

    @Embedded
    public Recall recall;

    @Relation(
            parentColumn = "recallId",
            entityColumn =  "recallId",
            entity = Injuries.class
    )
    public List<Injuries> injuriesList;

    @Relation(
            parentColumn = "recallId",
            entityColumn =  "recallId",
            entity = Images.class
    )
    public List<Images> imagesList;

    @Relation(
            parentColumn = "recallId",
            entityColumn =  "recallId",
            entity = Product.class
    )
    public List<Product> productList;
}
