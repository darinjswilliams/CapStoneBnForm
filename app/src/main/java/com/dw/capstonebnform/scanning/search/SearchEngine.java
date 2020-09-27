package com.dw.capstonebnform.scanning.search;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dw.capstonebnform.scanning.utils.DetectedObject;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchEngine {

    private static final String TAG = SearchEngine.class.getSimpleName();

    private final RequestQueue searchRequestQueue;
    private final ExecutorService requestCreationExecutor;


    public interface SearchResultListener {
        void onSearchCompleted(DetectedObject object, List<ScanProduct> scanProductList);
    }

    public SearchEngine(Context context) {
        searchRequestQueue = Volley.newRequestQueue(context);
        requestCreationExecutor = Executors.newSingleThreadExecutor();
    }

    public void search(DetectedObject object, SearchResultListener listener) {
        // Crops the object image out of the full image is expensive, so do it off the UI thread.
        Tasks.call(requestCreationExecutor, () -> createRequest(object))
                .addOnSuccessListener(productRequest -> searchRequestQueue.add(productRequest.setTag(TAG)))
                .addOnFailureListener(
                        e -> {
                            Log.e(TAG, "Failed to create product search request!", e);
                            // TODO Remove the below dummy code after your own product search backed hooked up.
                            List<ScanProduct> sProductList = new ArrayList<>();
                            for (int i = 0; i < 8; i++) {
                                sProductList.add(
                                        new ScanProduct(/* imageUrl= */ "", "Product title " + i, "Product subtitle " + i));
                            }
                            listener.onSearchCompleted(object, sProductList);
                        });
    }

    private static JsonObjectRequest createRequest(DetectedObject searchingObject) throws Exception {
        byte[] objectImageData = searchingObject.getImageData();
        if (objectImageData == null) {
            throw new Exception("Failed to get object image data!");
        }

        //TODO Hooks up with your own product search backend here.
        throw new Exception("Hooks up with your own product search backend.");
    }

    public void shutdown() {
        searchRequestQueue.cancelAll(TAG);
        requestCreationExecutor.shutdown();
    }
}

