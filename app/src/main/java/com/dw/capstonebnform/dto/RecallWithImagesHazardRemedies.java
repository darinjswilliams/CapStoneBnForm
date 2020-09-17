package com.dw.capstonebnform.dto;


import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecallWithImagesHazardRemedies {

    @Embedded
    public Recall recall;

    @Relation(
            parentColumn = "recallId",
            entityColumn = "recallId",
            entity = Images.class
    )
    public List<Images> imageList;

    @Relation(
            parentColumn = "recallId",
            entityColumn = "recallId",
            entity = Hazards.class
    )
    public List<Hazards> hazardsList;

    @Relation(
            parentColumn = "recallId",
            entityColumn = "recallId",
            entity = Remedies.class
    )
    public List<Remedies> remediesList;

    @Relation(
            parentColumn = "recallId",
            entityColumn = "recallId",
            entity = RemedyOptions.class
    )
    public List<RemedyOptions> remedyOptionsList;
}
