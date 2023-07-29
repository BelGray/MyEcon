package belgrays.android_app.my_econ.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import belgrays.android_app.my_econ.databinding.ActivityWhiteFoxBinding;


public class WhiteFoxActivity extends AppCompatActivity {

    private ActivityWhiteFoxBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWhiteFoxBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.whiteFoxImageView.setOnClickListener(view -> {
            super.onBackPressed();
        });

    }
}
