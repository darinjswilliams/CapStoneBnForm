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
public class RecallWithDistributorsAndManufactures {

    @Embedded
    public Recall recall;


    @Relation(
            parentColumn = "recallId",
            entityColumn = "recallId",
            entity = Distributors.class
    )
    public List<Distributors> distributorsList;


    @Relation(
            parentColumn = "recallId",
            entityColumn = "recallId",
            entity = Manufacturers.class
    )

    public List<Manufacturers> manufacturersList;


    @Relation(
            parentColumn = "recallId",
            entityColumn = "recallId",
            entity = ManufacturerCountries.class
    )
    public List<ManufacturerCountries> manufacturerCountriesList;
}
