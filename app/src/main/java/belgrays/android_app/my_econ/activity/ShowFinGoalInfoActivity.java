package belgrays.android_app.my_econ.activity;

import android.os.Bundle;
import belgrays.android_app.my_econ.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;

public class ShowFinGoalInfoActivity extends AppCompatActivity {
     private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = belgrays.android_app.my_econ.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
