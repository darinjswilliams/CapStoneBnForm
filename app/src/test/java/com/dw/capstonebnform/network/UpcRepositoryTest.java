package com.dw.capstonebnform.network;

import android.app.Application;
import android.util.Log;

import com.dw.capstonebnform.datasource.FakeDataSource;
import com.dw.capstonebnform.dto.UpcWithOfferItemList;
import com.dw.capstonebnform.persistance.AppDatabase;
import com.dw.capstonebnform.persistance.AppExecutors;
import com.dw.capstonebnform.upc.Item;
import com.dw.capstonebnform.upc.Offer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.LiveData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpcRepositoryTest{
    //helper fields
//mock help class

    UpcRepository SUT;

    AppDatabase appDatabaseMock;

    @Mock
    AppExecutors appExecutorsMock;

    @Mock
    Application applicationMock;

    @Mock
    FakeDataSource fakeDataSourceMock;

    @Mock
    private UPCApi mUpcApiMock;
    
    private static String TAG = UpcRepositoryTest.class.getSimpleName();

    @Before
    public void setUp() {

//        Context context = ApplicationProvider.getApplicationContext();
        applicationMock = mock(Application.class);
        appDatabaseMock = mock(AppDatabase.class);
        assertNotNull(applicationMock);
//        appDatabaseMock = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
//                .allowMainThreadQueries().build();
        appExecutorsMock = mock(AppExecutors.class);
        fakeDataSourceMock = mock(FakeDataSource.class);

        mUpcApiMock = mock(UPCApi.class);
        Log.d(TAG, "setUp: ");
        SUT = new UpcRepository(applicationMock);
        assertNotNull(SUT);
    }


    @Test
    //subjectUnderTest_actionOrInput_resultState
    public void getUpcRepository_returnUpcOfferItemList_whenGivenAvalidUpcCode() {
        //Arrange - Expected and When Setup


        UpcWithOfferItemList expectedUpcWithOfferItemList = UpcWithOfferItemList
                .builder()
                .itemList(Collections.singletonList(Item.builder()
                        .asin("asin")
                        .brand("some brand")
                        .category("category")
                        .offers(Arrays.asList(Offer.builder()
                                .title("title")
                                .build()))
                        .build()))
                .build();
        when(fakeDataSourceMock.getUpcWithOfferItemList()).thenReturn(expectedUpcWithOfferItemList).getMock();
        //Act - Calling the implementation Code
        LiveData<List<UpcWithOfferItemList>> actualResponse =  SUT.getUpcWithOfferItems("0007655");
//        //Assert  - Verify and Assert
        assertEquals(expectedUpcWithOfferItemList,  actualResponse);
    }

//region for test methods

//region for private methods  
}