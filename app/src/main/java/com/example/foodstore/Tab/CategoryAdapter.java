package com.example.foodstore.Tab;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

public class CategoryAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private ArrayList<DataCategory> list;

    public CategoryAdapter(@NonNull FragmentManager fm, Context context, ArrayList<DataCategory> list) {
        super(fm);
        mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new CategoryFragment(list.get(position).getId() , mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public CharSequence getPageTitle(int position) {
        return list.get(position).getNameCate();
    }



}
