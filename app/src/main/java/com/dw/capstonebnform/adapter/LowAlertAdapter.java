package com.dw.capstonebnform.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.databinding.LowAlertItemsBinding;
import com.dw.capstonebnform.dto.RecallWithInjuriesAndImagesAndProducts;
import com.dw.capstonebnform.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class LowAlertAdapter extends RecyclerView.Adapter<LowAlertAdapter.LowAlertHolder> {

//    final private LowAlertAdapterClickListner mOnItemClickListener;
    private List<RecallWithInjuriesAndImagesAndProducts>  mRecallWithInjuriesAndImagesAndProducts;
    private Context mContext;
    public static final String DATE_INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_OUTPUT_FORMAT = "MM/dd/yyyy";

    private final ItemClickListener<RecallWithInjuriesAndImagesAndProducts> mListener;

    private static final String TAG = LowAlertAdapter.class.getSimpleName();


    public LowAlertAdapter(ItemClickListener<RecallWithInjuriesAndImagesAndProducts> mListener){
//        this.mOnItemClickListener = onItemClickListener;

        this.mListener = mListener;
    }

//    public interface LowAlertAdapterClickListner {
//        void onClick(RecallWithInjuriesAndImagesAndProducts mRecallWithInjuriesAndImagesAndProducts);
//    }


    @NonNull
    @Override
    public LowAlertAdapter.LowAlertHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        //Set Context
        mContext = parent.getContext();

        LowAlertItemsBinding lowAlertItemsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.low_alert_items, parent,false);

        return new LowAlertHolder(lowAlertItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LowAlertAdapter.LowAlertHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: position 0.." + mRecallWithInjuriesAndImagesAndProducts.get(position).recall.getMTitle());

        //get positon of recall product
        holder.bind(mRecallWithInjuriesAndImagesAndProducts.get(position));
    }

    @Override
    public int getItemCount() {
//        Log.i(TAG, "getItemCount: returning" + mRecallWithInjuriesAndImagesAndProducts.size());
        return mRecallWithInjuriesAndImagesAndProducts != null ? mRecallWithInjuriesAndImagesAndProducts.size() : Constants.EMPTY_RECALL_LIST;
    }

    public void setmRecallWithInjuriesAndImagesAndProducts(List<RecallWithInjuriesAndImagesAndProducts> recallWithInjuriesAndImagesAndProducts){
        Log.i(TAG, "setRecallWithProductsAndImages: ");
        this.mRecallWithInjuriesAndImagesAndProducts = recallWithInjuriesAndImagesAndProducts;
        notifyDataSetChanged();
    }

    public class LowAlertHolder extends RecyclerView.ViewHolder{

        LowAlertItemsBinding mLowAlertItemsBinding;

        public LowAlertHolder(@NonNull LowAlertItemsBinding lowAlertItemsBinding) {
            super(lowAlertItemsBinding.getRoot());

            this.mLowAlertItemsBinding = lowAlertItemsBinding;


//            mLowAlertItemsBinding.imageViewLowAlertItemImage.setOnClickListener(this);
//              this.mLowAlertItemsBinding.getRoot().setOnClickListener(this::onClick);
        }

        public void bind(RecallWithInjuriesAndImagesAndProducts recallItems){
            Log.i(TAG, "bind: ");

                //format Date
//                String mRecallDate = DateUtils.formatDateFromDateString(DATE_INPUT_FORMAT, DATE_OUTPUT_FORMAT, recallItems.recall.getMRecallDate());
                mLowAlertItemsBinding.dateIdLowAlertItemTxt.setText(recallItems.recall.getMRecallDate());
                mLowAlertItemsBinding.descriptionLowAlertItemText.setText(recallItems.recall.getMDescription());
                mLowAlertItemsBinding.titleLowAlertItemTxt.setText(recallItems.recall.getMTitle());

                if(recallItems.imagesList.get(0).getUrl().length() != 0) {
                    Picasso.get()
                            .load(recallItems.imagesList.get(0).getUrl())
                            .into(mLowAlertItemsBinding.imageViewLowAlertItemImage);
                }


            mLowAlertItemsBinding.imageViewLowAlertItemImage.setOnClickListener(
                    v -> mListener.onClick(recallItems));


        }


//        @Override
//        public void onClick(View view) {
//            int index = getLayoutPosition();
//            RecallWithInjuriesAndImagesAndProducts mRecallProducts = mRecallWithInjuriesAndImagesAndProducts.get(index);
////            mOnItemClickListener.onClick(mRecallProducts);
//            Log.i(TAG, "LowAlertAdapter : onClick: " + mRecallProducts.recall.getRecallNumber());
//
//
//            NavController navController = Navigation.findNavController(view);
//
//            if(navController.getCurrentDestination().getId() == R.id.action_lowAlertFragment_to_recallDetailFragment){
//                Log.i(TAG, "onClick: Correct Required Destination: " + navController.getCurrentDestination().getId());
//            } else {
//                Log.i(TAG, "onClick: Incorrect Destination: where I am going " + navController.getCurrentDestination().getDisplayName());
//
//            }
//
//            LowAlertFragmentDirections.ActionLowAlertFragmentToRecallDetailFragment action =
//                    LowAlertFragmentDirections.actionLowAlertFragmentToRecallDetailFragment();
//
//            action.setRecallNumber(mRecallProducts.recall.getRecallNumber());
//
//            navController.navigate(action);
//
//        }
    }
}
