package belgrays.android_app.my_econ.tools;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import belgrays.android_app.my_econ.activity.repository.GoalsRepository;
import belgrays.android_app.my_econ.activity.view_model.GoalsViewModel;
import belgrays.android_app.my_econ.database.model.Goals;

public class Tool {

    public static double calculateProgressPercents(double currentProgress, double goal) {
        return (double) currentProgress / goal * 100;
    }

    public static String makeProgressPercentsText(double progressPercents, boolean spaceBetweenValues){
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        String formatResult = decimalFormat.format(progressPercents);
        return spaceBetweenValues ? formatResult + " %" : formatResult + "%";
    }

    public static String makeAwardText(double award, boolean spaceBetweenValues, AwardType awardType, int digitsCountAfterDot){
        String awardLetter = "?";
        String digitsPattern = "";
        switch (awardType){
            case RUBLES:
                awardLetter = "₽";
                break;
            case PERCENTS:
                awardLetter = "%";
                break;
        }

        for (int count = 0; count < digitsCountAfterDot; count++) digitsPattern += "#";

        DecimalFormat decimalFormat = new DecimalFormat("##." + digitsPattern);
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        String formatResult = decimalFormat.format(award);

        return spaceBetweenValues ? formatResult + " " + awardLetter : formatResult + awardLetter;

    }

}
