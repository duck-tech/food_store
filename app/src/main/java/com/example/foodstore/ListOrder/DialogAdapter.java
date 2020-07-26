package com.example.foodstore.ListOrder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodstore.R;
import java.util.ArrayList;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<DataListDialog> listDialogs;

    public DialogAdapter(Context mContext , ArrayList<DataListDialog> listDialogs) {
        this.listDialogs = listDialogs;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_dialog , parent , false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final byte[] decodedString = Base64.decode(listDialogs.get(position).getImage(), Base64.DEFAULT);
        Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        holder.image.setImageBitmap(imgBitMap);
        holder.name.setText(listDialogs.get(position).getName());
        holder.price.setText(listDialogs.get(position).getPrice()+"");
        holder.amount.setText(listDialogs.get(position).getAmount()+"");
    }

    @Override
    public int getItemCount() {
        return listDialogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, price , amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
