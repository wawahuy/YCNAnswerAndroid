package ml.huytools.ycnanswer.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ml.huytools.ycnanswer.Models.Entities.TurnEntity;
import ml.huytools.ycnanswer.Models.Entities.UserEntity;
import ml.huytools.ycnanswer.R;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    List<UserEntity> list;
    LayoutInflater layoutInflater;


    public RankAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<UserEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_rank, parent, false);
        ViewHolder viewHodler = new ViewHolder(view);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.ViewHolder holder, int position) {
        holder.set(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRankAvt;
        TextView txvRankName, txvRankPoint;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRankAvt = itemView.findViewById(R.id.imgRankAvt);
            txvRankName = itemView.findViewById(R.id.txtRankName);
            txvRankPoint = itemView.findViewById(R.id.txtRankPoint);

        }
        public void set(UserEntity userEntity) {
            imgRankAvt.setImageResource(R.drawable.profile);
            txvRankName.setText(userEntity.tendangnhap);
            txvRankPoint.setText("Điểm: " + userEntity.diemcaonhat);
        }
    }
}
