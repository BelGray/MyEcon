package belgrays.android_app.my_econ.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import belgrays.android_app.my_econ.R;
import belgrays.android_app.my_econ.activity.view_model.GoalsViewModel;
import belgrays.android_app.my_econ.activity.view_model.TasksViewModel;
import belgrays.android_app.my_econ.database.dao.GoalsDao;
import belgrays.android_app.my_econ.database.model.Goals;
import belgrays.android_app.my_econ.databinding.ActivityMainBinding;
import belgrays.android_app.my_econ.databinding.ActivityShowGoalInfoBinding;
import belgrays.android_app.my_econ.tools.Tool;

public class ShowGoalInfoActivity extends AppCompatActivity {
    private ActivityShowGoalInfoBinding binding;

    private Goals currentGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowGoalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int goalId = intent.getIntExtra("goalId", 0);

        Resources res = this.getResources();

        GoalsViewModel goalsVM = new ViewModelProvider(this).get(GoalsViewModel.class);
        TasksViewModel tasksVM = new ViewModelProvider(this).get(TasksViewModel.class);

        binding.goalLabelTextView.setOnClickListener(view -> {
            startActivity(new Intent(this, WhiteFoxActivity.class));
        });

        goalsVM.getGoalById(goalId).observe(this, goal -> {

            if (goal != null) {

                currentGoal = goal;

                double goalProgress = Tool.calculateProgressPercents(goal.getProgress(), goal.getAmount());

                binding.goalProgressLine.setProgress((int) goalProgress);
                binding.goalProgressPerc.setText(goalProgress < 100 ? Tool.makeProgressPercentsText(goalProgress, false) : "100%");
                binding.progressEditText.setText(String.valueOf(goalProgress));
                binding.goalNameTextInputLayout.getEditText().setText(goal.getName());
                binding.goalDescriptionTextInputLayout.getEditText().setText(goal.getText());

                if (goalProgress >= 100 || goal.isAchieved()) {
                    binding.goalProgressPerc.setTextColor(res.getColor(R.color.success));
                    binding.goalProgressLine.setIndicatorColor(res.getColor(R.color.success));
                    binding.goalLabelTextView.setText("Цель достигнута!");
                } else {
                    binding.goalProgressPerc.setTextColor(res.getColor(R.color.blue_for_ui));
                    binding.goalProgressLine.setIndicatorColor(res.getColor(R.color.blue_for_ui));
                    binding.goalLabelTextView.setText("Цель");
                }

                if (goal.isMain()) {
                    binding.mainGoalCheckBox.setChecked(true);
                }

            }

        });

        binding.minusOneImageButton.setOnClickListener(view -> {

            if (currentGoal.getProgress() > 0){
                goalsVM.setProgress(goalId, currentGoal.getProgress() - 1);
            }

        });

        binding.plusOneImageButton.setOnClickListener(view -> {

            if (currentGoal.getProgress() < 100) {
                goalsVM.setProgress(goalId, currentGoal.getProgress() + 1);
            }

        });

        binding.mainGoalCheckBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                goalsVM.getMainGoal(true).observe(this, goals -> {
                    for (Goals goal : goals) {
                        if (goal.getId() != goalId) {
                            goalsVM.setMain(goal.getId(), false);
                        }
                    }
                });
                goalsVM.setMain(goalId, true);

            } else {
                goalsVM.setMain(goalId, false);
            }
        });

        binding.progressEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                        double body = Double.parseDouble(0 + binding.progressEditText.getText().toString());

                        if (body > 100) {
                            binding.progressEditText.setText("100");
                        }

                        if (body < 0) {
                            binding.progressEditText.setText("0");
                }
            }
        });

        binding.progressEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused){
                    goalsVM.setProgress(goalId, Double.parseDouble(binding.progressEditText.getText().toString()));
                }
            }
        });

        binding.deleteButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setMessage("Ты действительно хочешь удалить цель?")
                    .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            goalsVM.deleteGoalById(goalId);
                            tasksVM.deleteTasksByGoalId(goalId);
                            ShowGoalInfoActivity.super.onBackPressed();
                            Toast.makeText(ShowGoalInfoActivity.this, "Цель удалена!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

            builder.create().show();
        });

        binding.goBackAndSaveButton.setOnClickListener(view -> {
            String goalName = binding.goalNameTextInputLayout.getEditText().getText().toString().trim();
            String goalText = binding.goalDescriptionTextInputLayout.getEditText().getText().toString().trim();

            boolean goalNameStatus = false;
            boolean goalTextStatus = false;

            if (goalName.length() > 100){
                goalNameStatus = false;
                binding.goalNameTextInputLayout.setError("Слишком большое название!");
            }
            else if (goalName.isEmpty()) {
                goalNameStatus = false;
                binding.goalNameTextInputLayout.setError("Выбери название для цели");
            }
            else {
                goalNameStatus = true;
                binding.goalNameTextInputLayout.setError(null);
            }

            if (goalText.length() > 5000){
                goalTextStatus = false;
                binding.goalDescriptionTextInputLayout.setError("Слишком большое описание!");

            }
            else if (goalText.isEmpty()) {
                goalTextStatus = false;
                binding.goalDescriptionTextInputLayout.setError("Опиши свою цель!");
            }
            else {
                goalTextStatus = true;
                binding.goalDescriptionTextInputLayout.setError(null);
            }

            if (goalNameStatus && goalTextStatus){
                goalsVM.updateGoalById(goalId, goalName, goalText, currentGoal.getProgress(), currentGoal.getAmount());
                Toast.makeText(ShowGoalInfoActivity.this, "Все изменения успешно сохранены!", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
            }

        });

        binding.tasksButton.setOnClickListener(view -> {
            Intent goTasksList = new Intent(ShowGoalInfoActivity.this, TasksListActivity.class);
            boolean financial;
            if (currentGoal.isFinancial()) {
                financial = true;
            } else {
                financial = false;
            }

            goTasksList.putExtra("goalId", goalId);
            goTasksList.putExtra("financial", financial);
            startActivity(goTasksList);

        });

    }
}
