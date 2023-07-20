package belgrays.android_app.my_econ.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import belgrays.android_app.my_econ.database.dao.TasksDao;
import belgrays.android_app.my_econ.database.model.Tasks;

@Database(entities = Tasks.class, version = 1)
public abstract class TasksDatabase extends RoomDatabase {
    private static TasksDatabase instance;
    public abstract TasksDao tasksDao();
    public static synchronized TasksDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TasksDatabase.class, "tasks_database")
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
            service.execute(() -> instance.tasksDao());
        }
    };
}
