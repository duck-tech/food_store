package com.example.foodstore.ListOrder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.AboutUs.AboutAcitivity;
import com.example.foodstore.Login.LoginActivity;
import com.example.foodstore.MainActivity;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
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
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , Dialog.class);
                intent.putExtra("id" , list.get(position).getId());
                intent.putExtra("name" , list.get(position).getNameTable());
                intent.putExtra("status" , list.get(position).getStatus());
                intent.putExtra("date" , list.get(position).getDate());
                intent.putExtra("total" , list.get(position).getTotal());
                mContext.startActivity(intent);
            }
        });
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
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStt = itemView.findViewById(R.id.tv_stt);
            tvNameTable = itemView.findViewById(R.id.tv_name_table);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTotal = itemView.findViewById(R.id.tv_total);
            tvStatus = itemView.findViewById(R.id.tv_status);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }

    }
}
