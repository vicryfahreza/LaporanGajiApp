package com.vicry.laporgajiapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class GajiDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var gajiDatabase: GajiRoomDatabase
    private lateinit var gajiDao: GajiDao

    @Before
    fun setup() {
        gajiDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GajiRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        gajiDao = gajiDatabase.gajiDao()
    }

    @After
    fun teardown() {
        gajiDatabase.close()
    }

    @Test
    fun `insert Gaji`() = runTest {
            val gajiItem = Gaji(0,"766980","vicry","jln fahreza","Gol.1 Manager","2002/08/19","2024/04/04","1750000","1500000","750000")
            gajiDao.insert(gajiItem)

            val allGajiItems = gajiDao.getAllGaji().getOrAwaitValue()

            assertThat(allGajiItems).contains(gajiItem)
    }

    @Test
    fun `delete Gaji`() = runTest {
        val gajiItem = Gaji(0,"766980","vicry","jln fahreza","Gol.1 Manager","2002/08/19","2024/04/04","1750000","1500000","750000")
        gajiDao.insert(gajiItem)
        gajiDao.delete(gajiItem)

        val allGajiItems = gajiDao.getAllGaji().getOrAwaitValue()

        assertThat(allGajiItems).doesNotContain(gajiItem)
    }



}