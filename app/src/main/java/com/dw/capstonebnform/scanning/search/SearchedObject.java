package com.dw.capstonebnform.scanning.search;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.scanning.utils.DetectedObject;
import com.dw.capstonebnform.scanning.utils.Utils;

import java.util.List;

import androidx.annotation.Nullable;

public class SearchedObject {

    private final DetectedObject object;
    private final List<ScanProduct> productList;
    private final int objectThumbnailCornerRadius;

    @Nullable
    private Bitmap objectThumbnail;

    public SearchedObject(Resources resources, DetectedObject object, List<ScanProduct> productList) {
        this.object = object;
        this.productList = productList;
        this.objectThumbnailCornerRadius =
                resources.getDimensionPixelOffset(R.dimen.bounding_box_corner_radius);
    }

    public int getObjectIndex() {
        return object.getObjectIndex();
    }

    public List<ScanProduct> getProductList() {
        return productList;
    }

    public Rect getBoundingBox() {
        return object.getBoundingBox();
    }

    public synchronized Bitmap getObjectThumbnail() {
        if (objectThumbnail == null) {
            objectThumbnail =
                    Utils.getCornerRoundedBitmap(object.getBitmap(), objectThumbnailCornerRadius);
        }
        return objectThumbnail;
    }
}
