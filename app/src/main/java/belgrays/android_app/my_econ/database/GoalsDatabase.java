package belgrays.android_app.my_econ.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import belgrays.android_app.my_econ.database.dao.GoalsDao;
import belgrays.android_app.my_econ.database.model.Goals;

@Database(entities = Goals.class, version = 1)
public abstract class GoalsDatabase extends RoomDatabase {
    private static GoalsDatabase instance;
    public abstract GoalsDao goalsDao();
    public static synchronized GoalsDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            GoalsDatabase.class, "goals_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(() -> instance.goalsDao());
        }
    };
}
