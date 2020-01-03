package ml.huytools.ycnanswer.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ml.huytools.ycnanswer.Models.Entities.TurnEntity;
import ml.huytools.ycnanswer.R;

public class TurnAdapter extends RecyclerView.Adapter<TurnAdapter.ViewHodler> {

    List<TurnEntity> list;
    LayoutInflater layoutInflater;

    public TurnAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<TurnEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_turn, parent, false);
        ViewHodler viewHodler = new ViewHodler(view);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.set(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        TextView txvId, txvNum, txvPoint, txvTime;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txvId = itemView.findViewById(R.id.txtTurnId);
            txvNum = itemView.findViewById(R.id.txtTurnNum);
            txvPoint = itemView.findViewById(R.id.txtTurnPoint);
            txvTime = itemView.findViewById(R.id.txtTime);
        }

        public void set(TurnEntity turnEntity) {
            txvId.setText(String.valueOf(turnEntity.id));
            txvNum.setText(String.valueOf(turnEntity.socau));
            txvPoint.setText("Điểm: " + String.valueOf(turnEntity.diem));
            txvTime.setText(turnEntity.ngaygio);
        }
    }
}
