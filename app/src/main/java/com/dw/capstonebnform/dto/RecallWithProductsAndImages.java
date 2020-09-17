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
public class RecallWithProductsAndImages {

    @Embedded
    public Recall recall;

    @Relation(
            parentColumn = "recallId",
            entityColumn = "recallId",
            entity = Product.class
    )
    public List<Product> productList;

    @Relation(
            parentColumn = "recallId",
            entityColumn = "recallId",
            entity = Images.class
    )
    public List<Images> imagesList;
}
