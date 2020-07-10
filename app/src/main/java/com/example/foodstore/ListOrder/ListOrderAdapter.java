package com.example.foodstore.ListOrder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.Food.FoodAdapter;
import com.example.foodstore.R;

import java.util.ArrayList;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> {

    private ArrayList<DataListOrder> list;
    private Context mContext;

    public ListOrderAdapter(ArrayList<DataListOrder> list , Context context) {
        this.list = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_order , parent , false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvStt.setText(position+1+"");
        holder.tvNameTable.setText(list.get(position).getNameTable());
        holder.tvDate.setText(list.get(position).getDate());
        holder.tvTotal.setText(list.get(position).getTotal()+"");
        if (String.valueOf(list.get(position).getStatus()).equals("1")) {
            holder.tvStatus.setText("Đã thanh toán");
            holder.tvStatus.setTextColor(Color.GREEN);
        }else {
            holder.tvStatus.setText("Chưa thanh toán");
            holder.tvStatus.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvStt;
        TextView tvNameTable;
        TextView tvDate;
        TextView tvTotal;
        TextView tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStt = itemView.findViewById(R.id.tv_stt);
            tvNameTable = itemView.findViewById(R.id.tv_name_table);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTotal = itemView.findViewById(R.id.tv_total);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
