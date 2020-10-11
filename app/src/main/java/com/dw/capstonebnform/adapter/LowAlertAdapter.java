package com.dw.capstonebnform.adapter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.databinding.LowAlertItemsBinding;
import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.dw.capstonebnform.utils.Constants;
import com.dw.capstonebnform.widget.BnFormAppWidgetProvider;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class LowAlertAdapter extends RecyclerView.Adapter<LowAlertAdapter.LowAlertHolder> {

    //    final private LowAlertAdapterClickListner mOnItemClickListener;
    private List<RecallWithInjuriesAndImagesAndProducts> mRecallWithInjuriesAndImagesAndProducts;
    private Context mContext;
    public static final String DATE_INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_OUTPUT_FORMAT = "MM/dd/yyyy";
    private BnFormAppWidgetProvider bnFormAppWidgetProvider;
    private int recordCount = 3;

    private final ItemClickListener<RecallWithInjuriesAndImagesAndProducts> mListener;

    private static final String TAG = LowAlertAdapter.class.getSimpleName();


    public LowAlertAdapter(ItemClickListener<RecallWithInjuriesAndImagesAndProducts> mListener) {

        this.mListener = mListener;
    }


    @NonNull
    @Override
    public LowAlertAdapter.LowAlertHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        //Set Context
        mContext = parent.getContext();

        LowAlertItemsBinding lowAlertItemsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.low_alert_items, parent, false);

        return new LowAlertHolder(lowAlertItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LowAlertAdapter.LowAlertHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: position 0.." + mRecallWithInjuriesAndImagesAndProducts.get(position).recall.getMTitle());

        //get positon of recall product
        try {
            holder.bind(mRecallWithInjuriesAndImagesAndProducts.get(position));
            if(recordCount <= 3){
                holder.saveSharePrefrences(mRecallWithInjuriesAndImagesAndProducts.get(position));
                recordCount--;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mRecallWithInjuriesAndImagesAndProducts != null ? mRecallWithInjuriesAndImagesAndProducts.size() : Constants.EMPTY_RECALL_LIST;
    }

    public void setmRecallWithInjuriesAndImagesAndProducts(List<RecallWithInjuriesAndImagesAndProducts> recallWithInjuriesAndImagesAndProducts) {
        Log.i(TAG, "setRecallWithProductsAndImages: ");
        this.mRecallWithInjuriesAndImagesAndProducts = recallWithInjuriesAndImagesAndProducts;
        notifyDataSetChanged();
    }

    public class LowAlertHolder extends RecyclerView.ViewHolder {

        LowAlertItemsBinding mLowAlertItemsBinding;

        public LowAlertHolder(@NonNull LowAlertItemsBinding lowAlertItemsBinding) {
            super(lowAlertItemsBinding.getRoot());

            this.mLowAlertItemsBinding = lowAlertItemsBinding;

        }

        public void bind(RecallWithInjuriesAndImagesAndProducts recallItems) throws ParseException {
            Log.i(TAG, "bind: ");

            //format Date
//                String mRecallDate = DateUtils.formatDateFromDateString(DATE_INPUT_FORMAT, DATE_OUTPUT_FORMAT, recallItems.recall.getMRecallDate());
            mLowAlertItemsBinding.dateIdLowAlertItemTxt.setText(recallItems.recall.getMRecallDate());
            mLowAlertItemsBinding.descriptionLowAlertItemText.setText(recallItems.recall.getMDescription());
            mLowAlertItemsBinding.titleLowAlertItemTxt.setText(recallItems.recall.getMTitle());

            if (recallItems.imagesList.get(0).getUrl().length() != 0) {
                Picasso.get()
                        .load(recallItems.imagesList.get(0).getUrl())
                        .into(mLowAlertItemsBinding.imageViewLowAlertItemImage);
            }


            mLowAlertItemsBinding.imageViewLowAlertItemImage.setOnClickListener(
                    v -> mListener.onClick(recallItems));

        }

        private void saveSharePrefrences(RecallWithInjuriesAndImagesAndProducts recallItems) {

            SharedPreferences prefs = mContext.getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            Log.i(TAG, "saveSharePrefrences: " + recallItems.getRecall());

            //Save Recall using Gson
            Gson gson = new Gson();
            String json = gson.toJson(recallItems.productList);
            editor.putString(Constants.SAVE_LIST_FOR_WIDGET, json);
            editor.putString(Constants.RECALL_PRODUCT_NAME, recallItems.getRecall().getMTitle());
            editor.putString(Constants.RECALL_BY_LAST_DATE_PUBLISHED, recallItems.getRecall().getMLastPublishDate());
            editor.putString(Constants.RECALL_NUMBER, recallItems.getRecall().getRecallNumber());
            editor.clear();
            editor.apply();


            bnFormAppWidgetProvider = new BnFormAppWidgetProvider();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, BnFormAppWidgetProvider.class));

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.bnform_widget_item_task_name_label);
            bnFormAppWidgetProvider.updateWidgetBnForm(mContext, appWidgetManager, appWidgetIds);

        }

    }
}
