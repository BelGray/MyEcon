package belgrays.android_app.my_econ.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import belgrays.android_app.my_econ.activity.adapter.FinTasksAdapter;
import belgrays.android_app.my_econ.activity.adapter.TasksAdapter;
import belgrays.android_app.my_econ.activity.view_model.GoalsViewModel;
import belgrays.android_app.my_econ.activity.view_model.TasksViewModel;
import belgrays.android_app.my_econ.database.model.Tasks;
import belgrays.android_app.my_econ.databinding.ActivityMainBinding;
import belgrays.android_app.my_econ.databinding.ActivityTasksListBinding;
import belgrays.android_app.my_econ.tools.Tool;

public class TasksListActivity extends AppCompatActivity {
    private ActivityTasksListBinding binding;

    private boolean taskCompletedButtonClicked = false;
    private boolean addTaskButtonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityTasksListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        int goalId = intent.getIntExtra("goalId", 0);
        boolean financial = intent.getBooleanExtra("financial", false);

        TasksViewModel tasksVM = new ViewModelProvider(this).get(TasksViewModel.class);
        GoalsViewModel goalsVM = new ViewModelProvider(this).get(GoalsViewModel.class);


        FinTasksAdapter finAdapter = new FinTasksAdapter();
        TasksAdapter adapter = new TasksAdapter();

        if (financial){

            finAdapter.setOnCardClickListener(task -> {
                cardClickListener(task, financial);
            });

            finAdapter.setOnTaskCompletedButtonClickListener(task -> {
                taskCompletedButtonClickListener(goalsVM, tasksVM, task);
            });

        } else {

            adapter.setOnCardClickListener(task -> {
                cardClickListener(task, financial);
            });

            adapter.setOnTaskCompletedButtonClickListener(task -> {
                taskCompletedButtonClickListener(goalsVM, tasksVM, task);
            });
        }

        tasksVM.getTasksByGoalId(financial, goalId).observe(this, tasks -> {

            if (financial){
                if (tasks.isEmpty()){
                    binding.noTasksImageView.setVisibility(View.VISIBLE);
                    binding.finTasksListRecyclerView.setVisibility(View.GONE);
                    return;
                }
                binding.finTasksListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.finTasksListRecyclerView.setHasFixedSize(true);
                binding.finTasksListRecyclerView.setAdapter(finAdapter);
                finAdapter.setTasksList(tasks);
                binding.finTasksListRecyclerView.setVisibility(View.VISIBLE);
                binding.noTasksImageView.setVisibility(View.GONE);
            } else {
                if (tasks.isEmpty()){
                    binding.noTasksImageView.setVisibility(View.VISIBLE);
                    binding.tasksListRecyclerView.setVisibility(View.GONE);
                    return;
                }
                binding.tasksListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.tasksListRecyclerView.setHasFixedSize(true);
                binding.tasksListRecyclerView.setAdapter(adapter);
                adapter.setTasksList(tasks);
                binding.tasksListRecyclerView.setVisibility(View.VISIBLE);
                binding.noTasksImageView.setVisibility(View.GONE);
            }

        });

        binding.noTasksImageView.setOnClickListener(view -> {
            createTaskButtonClicked(goalsVM, goalId, financial);
        });

        binding.goBackButton.setOnClickListener(view -> {
            super.onBackPressed();
        });

        binding.createTaskButton.setOnClickListener(view -> {
            createTaskButtonClicked(goalsVM, goalId, financial);
        });

    }

    protected void taskCompletedButtonClickListener(GoalsViewModel goalsVM, TasksViewModel tasksVM, Tasks task){
        taskCompletedButtonClicked = true;
        goalsVM.getGoalById(task.getGoalId()).observe(this, goal -> {
            if (taskCompletedButtonClicked) {
                if (goal.getProgress() < goal.getAmount()) {
                    if ((goal.getAmount() - goal.getProgress()) < task.getAward()) {
                        goalsVM.setProgress(goal.getId(), goal.getAmount());
                        tasksVM.deleteTaskById(task.getId());
                        Toast.makeText(this, "Этот путь был, наверное, нелегким.. Но тем не менее цель достигнута! МОЛОДЕЦ!", Toast.LENGTH_SHORT).show();
                    } else {
                        goalsVM.setProgress(goal.getId(), task.getAward());
                        tasksVM.deleteTaskById(task.getId());
                        Toast.makeText(this, "Задание выполнено! Двигайся и достигай цели дальше!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Цель уже достигнута! ;)", Toast.LENGTH_SHORT).show();
                }
            }
            taskCompletedButtonClicked = false;
        });

    }

    private void cardClickListener(Tasks task, boolean financial){
        Intent intent;
        if (financial){
            intent = new Intent(TasksListActivity.this, ShowFinTaskActivity.class);
        } else {
            intent = new Intent(TasksListActivity.this, ShowTaskActivity.class);
        }
        intent.putExtra("taskId", task.getId());
        startActivity(intent);
    }

    private void createTaskButtonClicked(GoalsViewModel goalsVM, int goalId, boolean financial){
        addTaskButtonClicked = true;

        goalsVM.getGoalById(goalId).observe(this, goal -> {

            if (addTaskButtonClicked) {

                Intent goIntent;
                boolean goalAchieved = Tool.calculateProgressPercents(goal.getProgress(), goal.getAmount()) >= 100;

                if (goalAchieved) {
                    Toast.makeText(this, "Одумайся! Цель достигнута, лучше возьми перерыв!", Toast.LENGTH_SHORT).show();
                } else {

                    if (financial) {
                        goIntent = new Intent(this, CreateFinTaskActivity.class);
                    } else {
                        goIntent = new Intent(this, CreateTaskActivity.class);
                    }

                    goIntent.putExtra("goalId", goalId);
                    goIntent.putExtra("financial", financial);
                    startActivity(goIntent);

                }

            }

            addTaskButtonClicked = false;

        });
    }

}
