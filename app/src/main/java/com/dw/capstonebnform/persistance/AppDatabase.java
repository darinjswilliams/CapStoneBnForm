package com.dw.capstonebnform.persistance;

import android.app.Application;
import android.util.Log;

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
import com.dw.capstonebnform.dto.Distributors;
import com.dw.capstonebnform.dto.Hazards;
import com.dw.capstonebnform.dto.Images;
import com.dw.capstonebnform.dto.Importers;
import com.dw.capstonebnform.dto.Inconjuctions;
import com.dw.capstonebnform.dto.Injuries;
import com.dw.capstonebnform.dto.ManufacturerCountries;
import com.dw.capstonebnform.dto.Manufacturers;
import com.dw.capstonebnform.dto.Product;
import com.dw.capstonebnform.dto.ProductUPC;
import com.dw.capstonebnform.dto.Recall;
import com.dw.capstonebnform.dto.Remedies;
import com.dw.capstonebnform.dto.RemedyOptions;
import com.dw.capstonebnform.dto.Retailers;
import com.dw.capstonebnform.dto.SearchRecallProducts;
import com.dw.capstonebnform.upc.Item;
import com.dw.capstonebnform.upc.Offer;
import com.dw.capstonebnform.upc.UPCodeSearch;
import com.dw.capstonebnform.upc.UrlList;

import androidx.annotation.Keep;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

//ADD THE TYPE CONVERTERS SO ROOM KNOWS HOW TO DEAL WITH List CONVERSION
@Database(entities = {Distributors.class, Hazards.class, Images.class, Importers.class, Inconjuctions.class, Injuries.class, ManufacturerCountries.class,
Manufacturers.class, Product.class, ProductUPC.class, Recall.class, Remedies.class, RemedyOptions.class, Retailers.class, SearchRecallProducts.class,
Item.class, Offer.class, UrlList.class, UPCodeSearch.class}, version = 4,  exportSchema = false)
@TypeConverters(DateTypeConverter.class)
@Keep
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "Bnform";
    private  static AppDatabase sInstance;

    public static AppDatabase getsInstance(Application application) {
        if(sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG,   "Creating new database instance");
                sInstance = Room.databaseBuilder(application,
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        //TODO -  Queries should be done in a separate thread to avoid locking the UI
                        //We will allow this only temporally to see that our db is working
                        //remove allowMainThreadQueries after verification.
                        .fallbackToDestructiveMigration()
//                        .allowMainThreadQueries()
                        .build();
            }
        }

        Log.d(LOG_TAG, "GETTING DATABASE INSTANCE");
        return sInstance;
    }

    public abstract DistributorsDAO distributorsDAO();
    public abstract HazardsDAO hazardsDAO();
    public abstract ImagesDAO imagesDAO();
    public abstract ImportersDAO importersDAO();
    public abstract InjuriesDAO injuriesDAO();
    public abstract ManufacturerCountriesDAO manufacturerCountriesDAO();
    public abstract ManufacturesDAO manufacturesDAO();
    public abstract ProductsDAO productsDAO();
    public abstract RecallDAO recallDAO();
    public abstract RemediesDAO remediesDAO();
    public abstract RetailersDAO retailersDAO();
    public abstract ItemDAO itemDAO();
    public abstract OfferDAO offerDAO();
    public abstract UrlListDAO urlListDAO();
    public abstract UPCodeSearchDAO upCodeSearchDAO();
    public abstract SearchRecallDAO searchRecallProductsDAO();
}
