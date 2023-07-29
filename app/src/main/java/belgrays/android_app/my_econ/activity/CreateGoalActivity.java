package belgrays.android_app.my_econ.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import belgrays.android_app.my_econ.activity.view_model.GoalsViewModel;
import belgrays.android_app.my_econ.database.model.Goals;
import belgrays.android_app.my_econ.databinding.ActivityCreateGoalBinding;
import belgrays.android_app.my_econ.databinding.ActivityMainBinding;
import belgrays.android_app.my_econ.tools.Tool;

public class CreateGoalActivity extends AppCompatActivity {
    private ActivityCreateGoalBinding binding;

    private GoalsViewModel goalsVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateGoalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        goalsVM = new ViewModelProvider(this).get(GoalsViewModel.class);

        binding.finGoalImageButton.setOnClickListener(view -> {

            binding.anotherGoalLinearLayout.setVisibility(View.GONE);
            binding.finGoalLinearLayout.setVisibility(View.VISIBLE);

        });

        binding.anotherGoalImageButton.setOnClickListener(view -> {

            binding.finGoalLinearLayout.setVisibility(View.GONE);
            binding.anotherGoalLinearLayout.setVisibility(View.VISIBLE);

        });

        binding.goBackButton.setOnClickListener(view -> {

            super.onBackPressed();

        });

        binding.createAnotherGoalButton.setOnClickListener(view -> {

            String goalName = binding.goalNameTextInputLayout.getEditText().getText().toString().trim();
            String goalText = binding.goalDescriptionTextInputLayout.getEditText().getText().toString().trim();
            double goalProgress = Double.parseDouble(0 + binding.goalCurrentProgressTextInputLayout.getEditText().getText().toString().trim());

            boolean goalNameStatus = false;
            boolean goalTextStatus = false;
            boolean goalProgressStatus = false;

            if (goalName.length() > 100){
                goalNameStatus = false;
                binding.goalNameTextInputLayout.setError("Слишком большое название!");
            }
            else if (goalName.isEmpty()) {
                goalNameStatus = false;
                binding.goalNameTextInputLayout.setError("Нельзя достигнуть цели, не определив ее чётко");
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

            if (goalProgress >= 100) {
                goalProgressStatus = false;
                binding.goalCurrentProgressTextInputLayout.setError("Но ведь получается, что цель уже достигнута?)");
            } else {
                goalProgressStatus = true;
                binding.goalCurrentProgressTextInputLayout.setError(null);
            }

            if (goalNameStatus && goalProgressStatus && goalTextStatus){
                onCreateGoalButtonClick(goalName, goalText, false, goalProgress, 100);
            }

        });

        binding.createFinGoalButton.setOnClickListener(view -> {

            String goalName = binding.goalNameTextInputLayout.getEditText().getText().toString().trim();
            String goalText = binding.goalDescriptionTextInputLayout.getEditText().getText().toString().trim();
            double goalAmount = Double.parseDouble(0 + binding.goalAmountTextInputLayout.getEditText().getText().toString().trim());
            double goalCurrentAmount = Double.parseDouble(0 +binding.goalCurrentAmountTextInputLayout.getEditText().getText().toString().trim());

            boolean goalNameStatus = false;
            boolean goalTextStatus = false;
            boolean goalAmountStatus = false;
            boolean goalCurrentAmountStatus = false;

            if (goalName.length() > 100){
                goalNameStatus = false;
                binding.goalNameTextInputLayout.setError("Слишком большое название!");
            }
            else if (goalName.isEmpty()) {
                goalNameStatus = false;
                binding.goalNameTextInputLayout.setError("Нельзя достигнуть цели, не определив ее чётко");
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

            if (goalAmount <= goalCurrentAmount && goalCurrentAmount != 0){
                goalAmountStatus = false;
                binding.goalAmountTextInputLayout.setError("Класс, цель уже достигнута? )");
            }
            else if (goalAmount == 0) {
                goalAmountStatus = false;
                binding.goalAmountTextInputLayout.setError("Введи сумму, к которой стремишься");
            } else {
                goalAmountStatus = true;
                binding.goalAmountTextInputLayout.setError(null);
            }

            if (goalCurrentAmount == 0) {
                goalCurrentAmountStatus = false;
                binding.goalCurrentAmountTextInputLayout.setError("Введи сумму, которая уже накоплена");
            } else {
                goalCurrentAmountStatus = true;
                binding.goalCurrentAmountTextInputLayout.setError(null);
            }

            if (goalAmountStatus && goalNameStatus && goalCurrentAmountStatus && goalTextStatus){
                onCreateGoalButtonClick(goalName, goalText, true, goalCurrentAmount, goalAmount);
            }

        });

    }

    private void onCreateGoalButtonClick(String name, String text, boolean financial, double progress, double amount){
        goalsVM.insert(new Goals(name, text, false, false, financial, progress, amount));
        startActivity(new Intent(this, GoalsListActivity.class));
        Toast.makeText(this, "Новая цель создана. Успехов в её достижении!", Toast.LENGTH_SHORT).show();
        finish();
    }

}
