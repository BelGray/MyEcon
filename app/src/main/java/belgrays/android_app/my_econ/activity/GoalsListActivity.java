package belgrays.android_app.my_econ.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import belgrays.android_app.my_econ.databinding.ActivityMainBinding;

public class GoalsListActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
