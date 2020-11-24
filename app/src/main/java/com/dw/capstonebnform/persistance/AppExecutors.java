package com.dw.capstonebnform.persistance;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.Keep;

@Keep
public class AppExecutors {
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor mDbExecutor;

    private AppExecutors(Executor mDbExecutor) {
        this.mDbExecutor = mDbExecutor;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor mDbExecutor() {
        return mDbExecutor;
    }
}
