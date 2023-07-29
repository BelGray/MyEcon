package belgrays.android_app.my_econ.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import belgrays.android_app.my_econ.activity.view_model.TasksViewModel;
import belgrays.android_app.my_econ.database.model.Tasks;
import belgrays.android_app.my_econ.databinding.ActivityCreateFinTaskBinding;
import belgrays.android_app.my_econ.databinding.ActivityMainBinding;

public class CreateFinTaskActivity extends AppCompatActivity {
    private ActivityCreateFinTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityCreateFinTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TasksViewModel tasksVM = new ViewModelProvider(this).get(TasksViewModel.class);

        Intent intent = getIntent();

        int goalId = intent.getIntExtra("goalId", 0);
        boolean financial = intent.getBooleanExtra("financial", true);

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
                tasksVM.insert(new Tasks(goalId, taskText, taskAward, false, financial));
                Toast.makeText(this, "Задание создано! Маленький шаг к достижению цели)", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
            }

        });

    }
}
