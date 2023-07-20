package belgrays.android_app.my_econ.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import belgrays.android_app.my_econ.database.model.Tasks;

@Dao
public interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insert(Tasks tasks);

    @Query("UPDATE tasks SET completed = :completed WHERE id LIKE :id")
    void setCompleted(int id, boolean completed);

    @Query("SELECT * FROM tasks WHERE financial LIKE :financial")
    LiveData<List<Tasks>> getAllTasks(boolean financial);

    @Query("DELETE FROM tasks WHERE id LIKE :id")
    void deleteTaskById(int id);
}
