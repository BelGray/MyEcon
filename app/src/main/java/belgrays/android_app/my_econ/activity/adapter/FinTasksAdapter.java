package belgrays.android_app.my_econ.activity.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import belgrays.android_app.my_econ.R;
import belgrays.android_app.my_econ.database.model.Tasks;
import belgrays.android_app.my_econ.tools.AwardType;
import belgrays.android_app.my_econ.tools.Tool;

public class FinTasksAdapter extends RecyclerView.Adapter<FinTasksAdapter.ViewHolder> {

    List<Tasks> tasksList;
    OnItemClickListener onItemClickListener;

    OnCardClickListener onCardClickListener;

    @NonNull
    @Override
    public FinTasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fin_task_item, parent, false);
        return new FinTasksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinTasksAdapter.ViewHolder holder, int position) {

        Tasks task = tasksList.get(position);
        holder.taskText.setText(task.getText());
        holder.taskAward.setText(Tool.makeAwardText(task.getAward(), false, AwardType.RUBLES, 2));

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public void setTasksList(List<Tasks> tasksList) {
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskText, taskAward, taskCompletedText;
        Button completeButton;

        Resources appRes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskTextTextView);
            taskAward = itemView.findViewById(R.id.taskAmountTextView);
            taskCompletedText = itemView.findViewById(R.id.taskCompletedTextView);
            completeButton = itemView.findViewById(R.id.taskCompletedButton);
            appRes = itemView.getResources();

            completeButton.setOnClickListener(view -> {
                int position = getAbsoluteAdapterPosition();
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onClick(tasksList.get(position));
                }
            });

            itemView.setOnClickListener(view -> {
                int position = getAbsoluteAdapterPosition();
                if (onCardClickListener != null && position != RecyclerView.NO_POSITION) {
                    onCardClickListener.onClick(tasksList.get(position));
                }
            });

        }

    }

    public interface OnItemClickListener {
        void onClick(Tasks tasks);
    }

    public interface OnCardClickListener {
        void onClick(Tasks tasks);
    }

    public void setOnTaskCompletedButtonClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener){
        this.onCardClickListener = onCardClickListener;
    }

}
