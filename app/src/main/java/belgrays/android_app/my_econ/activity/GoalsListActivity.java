package belgrays.android_app.my_econ.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import belgrays.android_app.my_econ.activity.adapter.GoalsAdapter;
import belgrays.android_app.my_econ.activity.view_model.GoalsViewModel;
import belgrays.android_app.my_econ.database.model.Goals;
import belgrays.android_app.my_econ.databinding.ActivityGoalsListBinding;
import belgrays.android_app.my_econ.databinding.ActivityMainBinding;

public class GoalsListActivity extends AppCompatActivity {
    private ActivityGoalsListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGoalsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GoalsAdapter adapter = new GoalsAdapter();
        binding.goalsListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.goalsListRecyclerView.setHasFixedSize(true);
        binding.goalsListRecyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        GoalsViewModel goalsVM = new ViewModelProvider(this).get(GoalsViewModel.class);

        goalsVM.getAllGoals().observe(this, goals -> {

            if (goals.isEmpty()){
                binding.goalsListRecyclerView.setVisibility(View.GONE);
                binding.noGoalsImageView.setVisibility(View.VISIBLE);
                return;
            }

            adapter.setGoalsList(goals);

            binding.noGoalsImageView.setVisibility(View.GONE);
            binding.goalsListRecyclerView.setVisibility(View.VISIBLE);

        });

        adapter.setOnItemClickListener(goals -> {

            Intent goShowGoalInfo;

            if (goals.isFinancial()){
                goShowGoalInfo = new Intent(this, ShowFinGoalInfoActivity.class);
            } else {
                goShowGoalInfo = new Intent(this, ShowGoalInfoActivity.class);
            }

            goShowGoalInfo.putExtra("goalId", goals.getId());
            startActivity(goShowGoalInfo);

        });

        binding.goBackButton.setOnClickListener(view -> {
            super.onBackPressed();
        });

        binding.createGoalButton.setOnClickListener(view -> {
            goCreateGoalActivity();
        });

        binding.noGoalsImageView.setOnClickListener(view -> {
            goCreateGoalActivity();
        });

    }

    private void goCreateGoalActivity(){
        Intent createGoal = new Intent(this, CreateGoalActivity.class);
        startActivity(createGoal);
    }
}
