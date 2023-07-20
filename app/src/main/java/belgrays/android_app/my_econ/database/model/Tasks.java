package belgrays.android_app.my_econ.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tasks")
public class Tasks implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    @ColumnInfo(name = "goalId")
    private int goalId;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "award")
    private double award;

    @ColumnInfo(name = "completed")
    private boolean completed = false;

    @ColumnInfo(name = "financial")
    private boolean financial;

    public Tasks(int goalId, String text, double award, boolean completed, boolean financial) {
        this.goalId = goalId;
        this.text = text;
        this.award = award;
        this.completed = completed;
        this.financial = financial;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAward(double award) {
        this.award = award;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setFinancial(boolean financial) {
        this.financial = financial;
    }

    public int getId() {
        return id;
    }

    public int getGoalId() {
        return goalId;
    }

    public String getText() {
        return text;
    }

    public double getAward() {
        return award;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isFinancial() {
        return financial;
    }
}
