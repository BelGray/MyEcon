package belgrays.android_app.my_econ.activity.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import belgrays.android_app.my_econ.activity.repository.TasksRepository;
import belgrays.android_app.my_econ.database.model.Tasks;

public class TasksViewModel extends AndroidViewModel {

    private final TasksRepository repo;

    public TasksViewModel(@NonNull Application app) {
        super(app);
        repo = new TasksRepository(app);
    }

    public void updateTaskById(int id, String text, double award){
        repo.updateTaskById(id, text, award);
    }

    public LiveData<Tasks> getTaskById(int id){
        return repo.getTaskById(id);
    }

    public void insert(Tasks tasks){
        repo.insert(tasks);
    }

    public void setCompleted(int id, boolean completed){
        repo.setCompleted(id, completed);
    }

    public LiveData<List<Tasks>> getTasks(boolean financial){
        return repo.getAllTasks(financial);
    }

    public LiveData<List<Tasks>> getTasksByGoalId(boolean financial, int goalId){
        return repo.getTasksByGoalId(financial, goalId);
    }

    public void deleteTasksByGoalId(int goalId){
        repo.deleteTasksByGoalId(goalId);
    }

    public void deleteTaskById(int id){
        repo.deleteTaskById(id);
    }

}
