package com.dw.capstonebnform.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.dto.Product;
import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.dw.capstonebnform.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class BnFormAppWidgetService extends RemoteViewsService {

    private static final String TAG = BnFormAppWidgetService.class.getSimpleName();

    public static final SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static final SimpleDateFormat EEEddMMMyyyy = new SimpleDateFormat("EEE dd-MMM-yyyy", Locale.US);

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BnFormAppRemoteFactory(getApplicationContext(), intent);
    }

    class BnFormAppRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private List<RecallWithInjuriesAndImagesAndProducts> mRecall
                = new ArrayList<RecallWithInjuriesAndImagesAndProducts>();
        private List<Product> mRecallArray = new ArrayList<>();
        private StringBuilder sb = new StringBuilder();
        private int recallCount;
        private int appWidgetId;
        private String recallNumber;
        private String recallName;
        private String mLastPublishDate;


        public BnFormAppRemoteFactory(Context mContext, Intent intent) {
            this.mContext = mContext;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            loadSharePreferences();
        }

        private void loadSharePreferences() {
            Log.i(TAG, "loadSharePreferences: ");
            SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);

            recallName = prefs.getString(Constants.RECALL_PRODUCT_NAME, null);
            recallNumber = prefs.getString(Constants.RECALL_NUMBER, null);
            mLastPublishDate = prefs.getString(Constants.RECALL_BY_LAST_DATE_PUBLISHED, null);

            String mLastDate = parseDate(mLastPublishDate, ymdFormat, EEEddMMMyyyy);

            Log.i(TAG, "loadSharePreferences: RecallTitle " + recallName + "Recall Number .." + recallNumber + "Last Publish Date.." + mLastPublishDate);

            Gson gson = new Gson();
            String json = prefs.getString(Constants.SAVE_LIST_FOR_WIDGET, null);
            Type type = new TypeToken<ArrayList<Product>>() {
            }.getType();
            mRecallArray = gson.fromJson(json, type);


            ListIterator<Product> recallListIterator = mRecallArray.listIterator();
            while (recallListIterator.hasNext()) {
                Product recallItem = recallListIterator.next();
                sb.append(getResources().getString(R.string.recall_number) + recallNumber + "\n\n");
                sb.append(getResources().getString(R.string.recall_units) + recallItem.getNumberOfUnits() + " \n\n");
                sb.append(getResources().getString(R.string.recall_last_date_published) + mLastDate+ " \n\n");
                sb.append(recallItem.getName() );
            }

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.bnform_app_widget_item);
            views.setTextViewText(R.id.widget_recall_name, sb.toString());

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        private int getRecallCount() {
            return recallCount;
        }

        private void setRecallCount(int recallCount) {
            this.recallCount = recallCount;
        }
    }

    public static String parseDate(String inputDateString, SimpleDateFormat inputDateFormat, SimpleDateFormat outputDateFormat) {
        Date date;
        String outputDateString = null;
        try {
            date = inputDateFormat.parse(inputDateString);
            outputDateString = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }
}
