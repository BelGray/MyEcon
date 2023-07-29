package belgrays.android_app.my_econ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import belgrays.android_app.my_econ.R;
import belgrays.android_app.my_econ.activity.view_model.GoalsViewModel;
import belgrays.android_app.my_econ.database.model.Goals;
import belgrays.android_app.my_econ.databinding.ActivityMainBinding;
import belgrays.android_app.my_econ.tools.Tool;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Goals mainGoal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GoalsViewModel goalsVM = new ViewModelProvider(this).get(GoalsViewModel.class);
        Resources res = this.getResources();

        goalsVM.getMainGoal(true).observe(this, goals -> {

            if (goals.isEmpty()){
                binding.mainGoalCardView.setVisibility(View.GONE);
                binding.noMainGoalCardView.setVisibility(View.VISIBLE);
            } else {
                mainGoal = goals.get(0);
                double mainGoalProgress = mainGoal.getProgress();
                double mainGoalAmount = mainGoal.getAmount();
                double mainGoalProgressPercents = Tool.calculateProgressPercents(mainGoalProgress, mainGoalAmount);

                binding.goalText.setText(mainGoal.getName());
                binding.mainGoalProgressLine.setProgress((int) mainGoalProgressPercents);
                binding.mainGoalProgressPercents.setText((int) mainGoalProgressPercents + "%");
                if (mainGoal.isAchieved() || mainGoalProgressPercents >= 100){
                    binding.mainGoalProgressLine.setIndicatorColor(res.getColor(R.color.success));
                    binding.mainGoalProgressPercents.setTextColor(res.getColor(R.color.success));
                }
                if (mainGoal.isFinancial()){

                    binding.cookieImageView.setVisibility(View.GONE);
                    binding.rubleImageView.setVisibility(View.VISIBLE);

                    binding.mainGoalProgressRubles.setVisibility(View.VISIBLE);
                    binding.mainGoalProgressRubles.setText((int) mainGoalProgress + "₽");
                } else {
                    binding.rubleImageView.setVisibility(View.GONE);
                    binding.cookieImageView.setVisibility(View.VISIBLE);

                    binding.mainGoalProgressRubles.setVisibility(View.GONE);
                }

                binding.noMainGoalCardView.setVisibility(View.GONE);
                binding.mainGoalCardView.setVisibility(View.VISIBLE);

            }
        });

        binding.mainGoalCardView.setOnClickListener(v -> {

            Intent intent;

            if (mainGoal.isFinancial()){
                intent = new Intent(this, ShowFinGoalInfoActivity.class);
            } else {
                intent = new Intent(this, ShowGoalInfoActivity.class);
            }

            intent.putExtra("goalId", mainGoal.getId());
            startActivity(intent);

        });

        binding.myGoalsButton.setOnClickListener(v -> {

            startActivity(new Intent(this, GoalsListActivity.class));

        });

    }
}