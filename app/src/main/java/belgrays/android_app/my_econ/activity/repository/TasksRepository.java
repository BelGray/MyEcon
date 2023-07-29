package belgrays.android_app.my_econ.activity.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import belgrays.android_app.my_econ.database.TasksDatabase;
import belgrays.android_app.my_econ.database.dao.TasksDao;
import belgrays.android_app.my_econ.database.model.Tasks;

public class TasksRepository {

    private final TasksDao tasksDao;
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    public TasksRepository(Application app){
        TasksDatabase tasksDatabase = TasksDatabase.getInstance(app);
        tasksDao = tasksDatabase.tasksDao();
    }

    public void insert(Tasks tasks){
        service.execute(() -> tasksDao.insert(tasks));
    }

    public void setCompleted(int id, boolean completed){
        service.execute(() -> tasksDao.setCompleted(id, completed));
    }

    public LiveData<List<Tasks>> getAllTasks(boolean financial){
        return tasksDao.getAllTasks(financial);
    }

    public LiveData<List<Tasks>> getTasksByGoalId(boolean financial, int goalId){
        return tasksDao.getTasksByGoalId(financial, goalId);
    }

    public LiveData<Tasks> getTaskById(int id){
        return tasksDao.getTaskById(id);
    }

    public void deleteTasksByGoalId(int goalId){
        service.execute(() -> tasksDao.deleteTasksByGoalId(goalId));
    }

    public void updateTaskById(int id, String text, double award){
        service.execute(() -> tasksDao.updateTaskById(id, text, award));
    }

    public void deleteTaskById(int id){
        service.execute(() -> tasksDao.deleteTaskById(id));
    }
    
}
