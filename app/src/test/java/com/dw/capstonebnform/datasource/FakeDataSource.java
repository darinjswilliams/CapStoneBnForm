package com.dw.capstonebnform.datasource;

import com.dw.capstonebnform.dao.DistributorsDAO;
import com.dw.capstonebnform.dao.HazardsDAO;
import com.dw.capstonebnform.dao.ImagesDAO;
import com.dw.capstonebnform.dao.ImportersDAO;
import com.dw.capstonebnform.dao.InjuriesDAO;
import com.dw.capstonebnform.dao.ItemDAO;
import com.dw.capstonebnform.dao.ManufacturerCountriesDAO;
import com.dw.capstonebnform.dao.ManufacturesDAO;
import com.dw.capstonebnform.dao.OfferDAO;
import com.dw.capstonebnform.dao.ProductsDAO;
import com.dw.capstonebnform.dao.RecallDAO;
import com.dw.capstonebnform.dao.RemediesDAO;
import com.dw.capstonebnform.dao.RetailersDAO;
import com.dw.capstonebnform.dao.SearchRecallDAO;
import com.dw.capstonebnform.dao.UPCodeSearchDAO;
import com.dw.capstonebnform.dao.UrlListDAO;
import com.dw.capstonebnform.dto.UpcWithOfferItemList;
import com.dw.capstonebnform.persistance.AppDatabase;
import com.dw.capstonebnform.upc.Item;
import com.dw.capstonebnform.upc.Offer;

import java.util.Arrays;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

public class FakeDataSource extends AppDatabase {

    @Override
    public DistributorsDAO distributorsDAO() {
        return null;
    }

    @Override
    public HazardsDAO hazardsDAO() {
        return null;
    }

    @Override
    public ImagesDAO imagesDAO() {
        return null;
    }

    @Override
    public ImportersDAO importersDAO() {
        return null;
    }

    @Override
    public InjuriesDAO injuriesDAO() {
        return null;
    }

    @Override
    public ManufacturerCountriesDAO manufacturerCountriesDAO() {
        return null;
    }

    @Override
    public ManufacturesDAO manufacturesDAO() {
        return null;
    }

    @Override
    public ProductsDAO productsDAO() {
        return null;
    }

    @Override
    public RecallDAO recallDAO() {
        return null;
    }

    @Override
    public RemediesDAO remediesDAO() {
        return null;
    }

    @Override
    public RetailersDAO retailersDAO() {
        return null;
    }

    @Override
    public ItemDAO itemDAO() {
        return null;
    }

    @Override
    public OfferDAO offerDAO() {
        return null;
    }

    @Override
    public UrlListDAO urlListDAO() {
        return null;
    }

    @Override
    public UPCodeSearchDAO upCodeSearchDAO() {
        return null;
    }

    @Override
    public SearchRecallDAO searchRecallProductsDAO() {
        return null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    public UpcWithOfferItemList getUpcWithOfferItemList(){

        return  UpcWithOfferItemList
                .builder()
                .itemList(Collections.singletonList(Item.builder()
                        .asin("asin")
                        .brand("some brand")
                        .category("category")
                        .offers(Arrays.asList(Offer.builder()
                                .title("title")
                                .build()))
                        .build()))
                .build();
    }
}
