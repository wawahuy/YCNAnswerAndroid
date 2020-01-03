package ml.huytools.ycnanswer.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import ml.huytools.ycnanswer.Models.Entities.CreditEntity;
import ml.huytools.ycnanswer.R;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {

    List<CreditEntity> list;
    LayoutInflater layoutInflater;


    public  CreditAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<CreditEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_credit, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.set(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txvName, txvNum, txvMoney;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvName = itemView.findViewById(R.id.txtCreditName);
            txvNum = itemView.findViewById(R.id.txtCreditNum);
            txvMoney = itemView.findViewById(R.id.txtCreditMoney);

        }

        public void set(CreditEntity creditEntity){
            txvName.setText(creditEntity.tengoi);
            txvNum.setText("$" + creditEntity.credit);
            txvMoney.setText(creditEntity.sotien + "đ");
        }
    }
}
