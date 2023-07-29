package belgrays.android_app.my_econ.activity.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import java.util.List;

import belgrays.android_app.my_econ.activity.repository.GoalsRepository;
import belgrays.android_app.my_econ.database.model.Goals;

public class GoalsViewModel extends AndroidViewModel {
    private final GoalsRepository repo;

    public GoalsViewModel(@NonNull Application app) {
        super(app);
        repo = new GoalsRepository(app);
    }

    public void insert(Goals goals){
        repo.insert(goals);
    }

    public void deleteGoalById(int id){
        repo.deleteGoalById(id);
    }

    public LiveData<List<Goals>> getAllGoals(){
        return repo.getAllGoals();
    }

    public void updateGoalById(int id, String name, String text, double progress, double amount){
        repo.updateGoalById(id, name, text, progress, amount);
    }

    public void setMain(int id, boolean main){
        repo.setMain(id, main);
    }

    public void setAmount(int id, double amount){
        repo.setAmount(id, amount);
    }

    public LiveData<List<Goals>> getMainGoal(boolean main){
        return repo.getMainGoal(main);
    }

    public void setAchieved(int id, boolean achieved){
        repo.setAchieved(id, achieved);
    }

    public void setProgress(int id, double progress){
        repo.setProgress(id, progress);
    }

    public LiveData<Goals> getGoalById(int id){
        return repo.getGoalById(id);
    }

}
