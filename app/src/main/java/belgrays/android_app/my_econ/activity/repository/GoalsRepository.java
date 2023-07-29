package belgrays.android_app.my_econ.activity.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import belgrays.android_app.my_econ.database.GoalsDatabase;
import belgrays.android_app.my_econ.database.dao.GoalsDao;
import belgrays.android_app.my_econ.database.model.Goals;

public class GoalsRepository {
    private final GoalsDao goalsDao;
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    public GoalsRepository(Application application){
        GoalsDatabase goalsDatabase = GoalsDatabase.getInstance(application);
        goalsDao = goalsDatabase.goalsDao();
    }

    public void insert(Goals goals){
        service.execute(() -> goalsDao.insert(goals));
    }

    public void deleteGoalById(int id){
        service.execute(() -> goalsDao.deleteGoalById(id));
    }

    public LiveData<List<Goals>> getAllGoals(){
        return goalsDao.getAllGoals();
    }

    public void updateGoalById(int id, String name, String text, double progress, double amount){
        service.execute(() -> goalsDao.updateGoalById(id, name, text, progress, amount));
    }

    public void setMain(int id, boolean main){
        service.execute(() -> goalsDao.setMain(id, main));
    }

    public LiveData<List<Goals>> getMainGoal(boolean main){
        return goalsDao.getMainGoal(main);
    }

    public void setAchieved(int id, boolean achieved){
        service.execute(() -> goalsDao.setAchieved(id, achieved));
    }

    public void setProgress(int id, double progress){
        service.execute(() -> goalsDao.setProgress(id, progress));
    }

    public void setAmount(int id, double amount){
        service.execute(() -> goalsDao.setAmount(id, amount));
    }

    public LiveData<Goals> getGoalById(int id){
        return goalsDao.getGoalById(id);
    }

}
