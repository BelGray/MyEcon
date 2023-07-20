package belgrays.android_app.my_econ.activity.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

import belgrays.android_app.my_econ.R;
import belgrays.android_app.my_econ.database.model.Goals;
import belgrays.android_app.my_econ.tools.Tool;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {

    private List<Goals> goalsList;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsAdapter.ViewHolder holder, int position) {
        Goals goal = goalsList.get(position);
        double totalProgressPercents = Tool.calculateProgressPercents(goal.getProgress(), goal.getAmount());
        holder.goalText.setText(goal.getText());
        holder.goalProgress.setText(Tool.makeProgressPercentsText(totalProgressPercents, false));
        holder.goalProgressLine.setProgress((int) totalProgressPercents);
        if (totalProgressPercents >= 100 || goal.isAchieved()){
            holder.goalProgressLine.setIndicatorColor(holder.appResources.getColor(R.color.success));
            holder.goalProgress.setTextColor(holder.appResources.getColor(R.color.success));
        }
        if (goal.isFinancial()){
            holder.cookieImageView.setVisibility(View.GONE);
            holder.rubleImageView.setVisibility(View.VISIBLE);
            return;
        }

        holder.rubleImageView.setVisibility(View.GONE);
        holder.cookieImageView.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return goalsList.size();
    }

    public void setGoalsList(List<Goals> goalsList) {
        this.goalsList = goalsList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView goalText, goalProgress;
        LinearProgressIndicator goalProgressLine;
        ImageView cookieImageView, rubleImageView;

        Resources appResources;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goalText = itemView.findViewById(R.id.goalText);
            goalProgress = itemView.findViewById(R.id.goalProgress);
            goalProgressLine = itemView.findViewById(R.id.goalProgressLine);
            cookieImageView = itemView.findViewById(R.id.cookieImageView);
            rubleImageView = itemView.findViewById(R.id.rubleImageView);
            appResources = itemView.getResources();

            itemView.setOnClickListener(view -> {
                int position = getAbsoluteAdapterPosition();
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onClick(goalsList.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(Goals goals);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
