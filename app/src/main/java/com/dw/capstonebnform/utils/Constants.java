package com.dw.capstonebnform.utils;

public class Constants {
    //base Url
    public static final String RECALL_URL = "https://www.saferproducts.gov";
    public static final String RECALL_FORMAT = "RestWebServices/Recall?format=json";
    public static final String RECALL_BY_DATE = "RestWebServices/Recall?format=json&RecallDateStart=2020-08-01&" +
            "RecallEndDate=2020-08-15";
    public static final String RECALL_BY_PRODUCTS = "RestWebServices/Recall?format=json&Products";
    public static final int EMPTY_RECALL_LIST = 0;
    public static final String APP_KEY = "e7baa5d8-ecf3-4c70-831c-7894079b8f43.";

    //Shared Preferences
    public static final String PREF = "Preferences";
    public static final String SAVE_LIST_FOR_WIDGET = "SAVE_LIST_FOR_WIDGET";
    public static final String RECALL_PRODUCT_NAME = "Recall Product";


    public static final String RECALL_NUMBER = "Recall Number Id";
    public static final String RECALL_BY_LAST_DATE_PUBLISHED = "Recall Last Date Published";
}
;