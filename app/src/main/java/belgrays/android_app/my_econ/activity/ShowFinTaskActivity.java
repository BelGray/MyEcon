package belgrays.android_app.my_econ.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import belgrays.android_app.my_econ.activity.view_model.GoalsViewModel;
import belgrays.android_app.my_econ.activity.view_model.TasksViewModel;
import belgrays.android_app.my_econ.database.model.Tasks;
import belgrays.android_app.my_econ.databinding.ActivityShowFinGoalInfoBinding;
import belgrays.android_app.my_econ.databinding.ActivityShowFinTaskBinding;

public class ShowFinTaskActivity extends AppCompatActivity {

    private ActivityShowFinTaskBinding binding;

    private boolean taskCompletedButtonClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowFinTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TasksViewModel tasksVM = new ViewModelProvider(this).get(TasksViewModel.class);
        GoalsViewModel goalsVM = new ViewModelProvider(this).get(GoalsViewModel.class);


        Intent intent = getIntent();
        int taskId = intent.getIntExtra("taskId", 0);

        tasksVM.getTaskById(taskId).observe(this, task -> {
            try {
                binding.taskDescriptionTextInputLayout.getEditText().setText(task.getText());
                binding.taskAwardTextInputLayout.getEditText().setText(String.valueOf(0 + task.getAward()));
            } catch (Exception e) {

            }
        });

        binding.goBackButton.setOnClickListener(view -> {
            super.onBackPressed();
        });

        binding.saveButton.setOnClickListener(view -> {

            String taskText = binding.taskDescriptionTextInputLayout.getEditText().getText().toString().trim();
            double taskAward = Double.parseDouble(0 + binding.taskAwardTextInputLayout.getEditText().getText().toString());

            boolean taskTextStatus;
            boolean taskAwardStatus;

            if (taskText.length() > 1000 || taskText.isEmpty()){
                taskTextStatus = false;
                binding.taskDescriptionTextInputLayout.setError("");
            } else {
                taskTextStatus = true;
                binding.taskDescriptionTextInputLayout.setError(null);
            }

            if (taskAward > 99999999){
                taskAwardStatus = false;
                binding.taskAwardTextInputLayout.setError("Ух, какие-то прям нереальные суммы для одного задания..)");
            } else {
                taskAwardStatus = true;
                binding.taskAwardTextInputLayout.setError(null);
            }

            if (taskTextStatus && taskAwardStatus){
                tasksVM.updateTaskById(taskId, taskText, taskAward);
                Toast.makeText(this, "Все изменения сохранены успешно", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
            }

        });

        binding.taskCompletedButton.setOnClickListener(view -> {
            tasksVM.getTaskById(taskId).observe(this, task -> {
                taskCompletedButtonClicked = true;
                try {
                    goalsVM.getGoalById(task.getGoalId()).observe(this, goal -> {
                        if (taskCompletedButtonClicked) {
                            if (goal.getProgress() < goal.getAmount()) {
                                super.onBackPressed();
                                finish();
                                if ((goal.getAmount() - goal.getProgress()) < task.getAward()) {
                                    goalsVM.setProgress(goal.getId(), goal.getAmount());
                                    tasksVM.deleteTaskById(task.getId());
                                    Toast.makeText(this, "Этот путь был, наверное, нелегким.. Но тем не менее цель достигнута! МОЛОДЕЦ!", Toast.LENGTH_SHORT).show();
                                } else {
                                    goalsVM.setProgress(goal.getId(), goal.getProgress() + task.getAward());
                                    tasksVM.deleteTaskById(task.getId());
                                    Toast.makeText(this, "Задание выполнено! Двигайся и достигай цели дальше!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "Цель уже достигнута! ;)", Toast.LENGTH_SHORT).show();
                            }
                        }
                        taskCompletedButtonClicked = false;
                    });
                } catch (Exception e) {

                }
            });
        });

    }

}
