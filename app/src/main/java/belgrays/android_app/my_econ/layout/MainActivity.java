package belgrays.android_app.my_econ.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import belgrays.android_app.my_econ.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}