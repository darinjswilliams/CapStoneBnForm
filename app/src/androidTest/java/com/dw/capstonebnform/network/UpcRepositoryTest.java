package com.dw.capstonebnform.network;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import androidx.test.core.app.ApplicationProvider;
import retrofit2.Retrofit;

public class UpcRepositoryTest {

    Retrofit upcClient;
    UpcRepository ourInstance;

    Context mContext;

    @Before
    public void setup(){
        upcClient = UpcClient.getClient();
        mContext = ApplicationProvider.getApplicationContext();
        Assert.assertNotNull(mContext);
    }

    @Test
    public void getInstance() {

       ourInstance = new UpcRepository(mContext);

        Assert.assertNotNull(ourInstance);
    }

    @Test
    public void lookForUPCProductCode() {
    }
}