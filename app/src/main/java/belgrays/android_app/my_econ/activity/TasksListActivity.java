package belgrays.android_app.my_econ.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import belgrays.android_app.my_econ.databinding.ActivityMainBinding;

public class TasksListActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = belgrays.android_app.my_econ.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
