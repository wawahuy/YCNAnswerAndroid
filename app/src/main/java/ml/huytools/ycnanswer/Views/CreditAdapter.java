package ml.huytools.ycnanswer.Views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import ml.huytools.ycnanswer.Core.API.ApiOutput;
import ml.huytools.ycnanswer.Core.API.ApiProvider;
import ml.huytools.ycnanswer.Models.Entities.CreditEntity;
import ml.huytools.ycnanswer.Models.UserModel;
import ml.huytools.ycnanswer.R;
import ml.huytools.ycnanswer.Views.GameComponents.Loading;
import ml.huytools.ycnanswer.Views.ViewComponents.LoadingView;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {

    List<CreditEntity> list;
    LayoutInflater layoutInflater;
    WeakReference<Activity> activityWeakReference;


    public  CreditAdapter(Activity activity){
        layoutInflater = LayoutInflater.from(activity.getApplicationContext());
        activityWeakReference = new WeakReference<>(activity);
    }

    public void setList(List<CreditEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_credit, parent, false);
        ViewHolder holder = new ViewHolder(view, activityWeakReference);
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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txvName, txvNum, txvMoney;
        CreditEntity creditEntity;
        LoadingView loadingView;
        WeakReference<Activity> activityWeakReference;

        public ViewHolder(@NonNull View itemView, WeakReference<Activity> activityWeakReference) {
            super(itemView);
            txvName = itemView.findViewById(R.id.txtCreditName);
            txvNum = itemView.findViewById(R.id.txtCreditNum);
            txvMoney = itemView.findViewById(R.id.txtCreditMoney);
            this.activityWeakReference = activityWeakReference;
            itemView.setOnClickListener(this);
        }

        public void set(CreditEntity creditEntity){
            txvName.setText(creditEntity.tengoi);
            txvNum.setText("$" + creditEntity.credit);
            txvMoney.setText(creditEntity.sotien + "đ");
            this.creditEntity = creditEntity;
        }

        @Override
        public void onClick(View view) {
            loadingView = LoadingView.create(activityWeakReference.get());
            ApiProvider.Async.Callback callback = new ApiProvider.Async.Callback() {
                @Override
                public void OnAPIResult(ApiOutput output, int requestCode) {
                    if(output.Status){
                        Toast.makeText(activityWeakReference.get().getApplicationContext(), "Bạn được +"+creditEntity.credit, Toast.LENGTH_LONG).show();
                        UserModel.getUserGlobal().credit += creditEntity.credit;
                    } else {
                        Toast.makeText(activityWeakReference.get().getApplicationContext(), "Mua credit thất bại", Toast.LENGTH_LONG).show();
                    }
                    loadingView.removeOnView();
                }
            };
            UserModel.addCredit(creditEntity.credit, callback);
        }
    }
}
