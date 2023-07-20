package belgrays.android_app.my_econ.database.model;

import androidx.annotation.ColorInt;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.security.Provider;

@Entity(tableName = "goals")
public class Goals implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "achieved")
    private boolean achieved = false;

    @ColumnInfo(name = "main")
    private boolean main = false;

    @ColumnInfo(name = "financial")
    private boolean financial;

    @ColumnInfo(name = "progress")
    private double progress;

    @ColumnInfo(name = "amount")
    private double amount;

    public Goals(String name, String text, boolean achieved, boolean main, boolean financial, double progress, double amount) {
        this.name = name;
        this.text = text;
        this.achieved = achieved;
        this.main = main;
        this.financial = financial;
        this.progress = progress;
        this.amount = amount;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public void setFinancial(boolean financial) {
        this.financial = financial;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public boolean isMain() {
        return main;
    }

    public boolean isFinancial() {
        return financial;
    }

    public double getProgress() {
        return progress;
    }

    public double getAmount() {
        return amount;
    }
}
