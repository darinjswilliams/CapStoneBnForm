package com.dw.capstonebnform.scanning.camera;

import android.app.Application;
import android.content.Context;

import com.dw.capstonebnform.scanning.search.ScanProduct;
import com.dw.capstonebnform.scanning.search.SearchEngine;
import com.dw.capstonebnform.scanning.search.SearchedObject;
import com.dw.capstonebnform.scanning.utils.DetectedObject;
import com.dw.capstonebnform.scanning.utils.PreferenceUtils;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import static com.google.common.base.Preconditions.checkNotNull;

public class WorkflowModel extends AndroidViewModel implements SearchEngine.SearchResultListener {


    public final MutableLiveData<WorkflowState> workflowState = new MutableLiveData<>();
    public final MutableLiveData<DetectedObject> objectToSearch = new MutableLiveData<>();
    public final MutableLiveData<SearchedObject> searchedObject = new MutableLiveData<>();

    public final MutableLiveData<FirebaseVisionBarcode> detectedBarcode = new MutableLiveData<>();

    private final Set<Integer> objectIdsToSearch = new HashSet<>();

    private boolean isCameraLive = false;

    @Nullable
    private DetectedObject confirmedObject;



    public enum WorkflowState {
        NOT_STARTED,
        DETECTING,
        DETECTED,
        CONFIRMING,
        CONFIRMED,
        SEARCHING,
        SEARCHED
    }

    public WorkflowModel(@NonNull Application application) {
        super(application);
    }

    @MainThread
    public void setWorkflowState(WorkflowState workflowState) {
        if (!workflowState.equals(WorkflowState.CONFIRMED)
                && !workflowState.equals(WorkflowState.SEARCHING)
                && !workflowState.equals(WorkflowState.SEARCHED)) {
            confirmedObject = null;
        }
        this.workflowState.setValue(workflowState);
    }



    @Override
    public void onSearchCompleted(DetectedObject object, List<ScanProduct> scanProductList) {
        if (!object.equals(confirmedObject)) {
            // Drops the search result from the object that has lost focus.
            return;
        }
        objectIdsToSearch.remove(object.getObjectId());
        setWorkflowState(WorkflowState.SEARCHED);
        searchedObject.setValue(
                new SearchedObject(getContext().getResources(), confirmedObject, scanProductList));
    }

    @MainThread
    public void confirmingObject(DetectedObject object, float progress) {
        boolean isConfirmed = (Float.compare(progress, 1f) == 0);
        if (isConfirmed) {
            confirmedObject = object;
            if (PreferenceUtils.isAutoSearchEnabled(getContext())) {
                setWorkflowState(WorkflowState.SEARCHING);
                triggerSearch(object);
            } else {
                setWorkflowState(WorkflowState.CONFIRMED);
            }
        } else {
            setWorkflowState(WorkflowState.CONFIRMING);
        }
    }

    @MainThread
    public void onSearchButtonClicked() {
        if (confirmedObject == null) {
            return;
        }

        setWorkflowState(WorkflowState.SEARCHING);
        triggerSearch(confirmedObject);
    }

    private void triggerSearch(DetectedObject object) {
        Integer objectId = checkNotNull(object.getObjectId());
        if (objectIdsToSearch.contains(objectId)) {
            // Already in searching.
            return;
        }

        objectIdsToSearch.add(objectId);
        objectToSearch.setValue(object);
    }

    public void markCameraLive() {
        isCameraLive = true;
        objectIdsToSearch.clear();
    }

    public void markCameraFrozen() {
        isCameraLive = false;
    }

    public boolean isCameraLive() {
        return isCameraLive;
    }

    private Context getContext() {
        return getApplication().getApplicationContext();
    }
}
