package com.example.bismillahyukbisayuk.Adapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.bismillahyukbisayuk.Fragment.DataAnakBaru;
import com.example.bismillahyukbisayuk.Fragment.HistoriAnak;

public class PagerTabAdapter extends FragmentPagerAdapter {

    private int numoftabs;

    public PagerTabAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numoftabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HistoriAnak();
            case 1:
                return new DataAnakBaru();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numoftabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}



