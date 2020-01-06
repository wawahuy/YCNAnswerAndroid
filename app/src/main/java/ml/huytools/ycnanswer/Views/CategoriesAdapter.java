package ml.huytools.ycnanswer.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ml.huytools.ycnanswer.Core.Game.Graphics.Drawing.Text;
import ml.huytools.ycnanswer.Core.MVP.EntityManager;
import ml.huytools.ycnanswer.Models.Entities.CategoriesEntity;
import ml.huytools.ycnanswer.Presenters.Interface.CategoriesPresenter;
import ml.huytools.ycnanswer.R;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.Holder> {
    CategoriesPresenter categoriesPresenter;
    LayoutInflater layoutInflater;
    EntityManager<CategoriesEntity> categoriesEntities;

    public CategoriesAdapter(Context context, CategoriesPresenter categoriesPresenter) {
        layoutInflater = LayoutInflater.from(context);
        this.categoriesPresenter = categoriesPresenter;
    }

    public void setCategoriesEntities(EntityManager<CategoriesEntity> categoriesEntities) {
        this.categoriesEntities = categoriesEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_categories, parent, false);
        return new Holder(view, categoriesPresenter);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.set(categoriesEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return categoriesEntities == null ? 0 : categoriesEntities.size();
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CategoriesPresenter categoriesPresenter;
        CategoriesEntity categoriesEntity;
        TextView txtName;
        TextView txtNum;

        public Holder(@NonNull View itemView, CategoriesPresenter categoriesPresenter) {
            super(itemView);
            this.categoriesPresenter = categoriesPresenter;
            txtName = itemView.findViewById(R.id.txtCategorieName);
            txtNum = itemView.findViewById(R.id.txtCategorieNum);
            itemView.setOnClickListener(this);
        }

        public void set(CategoriesEntity categoriesEntity){
            this.categoriesEntity = categoriesEntity;
            txtName.setText(categoriesEntity.ten_linh_vuc);
            txtNum.setText("T.Câu hỏi: " + categoriesEntity.soLuongCauHoi);
        }

        @Override
        public void onClick(View view) {
            categoriesPresenter.selectCategorises(categoriesEntity);
        }
    }
}
