package zalora.twitsplit.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import zalora.twitsplit.data.room.dao.MessageDao;
import zalora.twitsplit.data.room.entity.MessageModel;
import zalora.twitsplit.utils.AppConstants;

@Database(entities = {MessageModel.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();

    private static MyDatabase INSTANCE;


    public static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, AppConstants.DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}

