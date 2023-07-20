package belgrays.android_app.my_econ.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import belgrays.android_app.my_econ.database.model.Goals;

@Dao
public interface GoalsDao {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insert(Goals goals);

    @Query("DELETE FROM goals WHERE id LIKE :id")
    void deleteGoalById(int id);

    @Query("SELECT * FROM goals")
    LiveData<List<Goals>> getAllGoals();

    @Query("UPDATE goals SET name = :name, text = :text, progress = :progress, amount = :amount WHERE id LIKE :id")
    void updateGoalById(int id, String name, String text, double progress, double amount);

    @Query("UPDATE goals SET main = :main WHERE id LIKE :id")
    void setMain(int id, boolean main);

    @Query("SELECT * FROM goals WHERE main LIKE 1")
    LiveData<List<Goals>> getMainGoal();

    @Query("UPDATE goals SET achieved = :achieved WHERE id LIKE :id")
    void setAchieved(int id, boolean achieved);

    @Query("UPDATE goals SET progress = :progress WHERE id LIKE :id")
    void setProgress(int id, double progress);

}
