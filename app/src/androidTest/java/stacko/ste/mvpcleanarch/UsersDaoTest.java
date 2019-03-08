package stacko.ste.mvpcleanarch;


import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import stacko.ste.mvpcleanarch.data.database.ProjectDb;
import stacko.ste.mvpcleanarch.data.database.UsersDao;
import stacko.ste.mvpcleanarch.domain.model.entities.ItemDbEntity;

@RunWith(AndroidJUnit4.class)
public class UsersDaoTest {
    private UsersDao mUsersDao;
    private ProjectDb mDb;

    private static final String ANY_STRING = "anystring";
    private static final Boolean ANY_BOOLEAN = true;
    private static final Long ANY_LONG = 1L;
    private static final int ANY_INT = 1;
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();


    @Before
    public void initDb() throws Exception {
        mDb = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                ProjectDb.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        mDb.close();
    }


    @Test
    public void insertReposAndGetAllRepos()  {
        // setup vars
        ItemDbEntity item = new ItemDbEntity(ANY_LONG,ANY_INT,ANY_LONG, ANY_INT,ANY_STRING, ANY_STRING,ANY_STRING,ANY_BOOLEAN, ANY_BOOLEAN);
        List<ItemDbEntity> items= Arrays.asList(item);
        mDb.usersDao().insert(items);
        mDb.usersDao().getAllRepos().test()
                .assertValue(emitteditems -> {
                    return  ((ItemDbEntity) emitteditems.get(0)).getDisplayName().equals(ANY_STRING);
                });
    }
}

