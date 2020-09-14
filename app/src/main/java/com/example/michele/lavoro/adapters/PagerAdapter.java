package com.example.michele.lavoro.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.michele.lavoro.entity.Profilo;
import com.example.michele.lavoro.fragment.VisualizzaPrenotazioniFragment;
import com.example.michele.lavoro.fragment.PrenotazioneFragment;
import com.example.michele.lavoro.fragment.TavoloPrenotazioneFragment;
import com.example.michele.lavoro.fragment.TavoloVisualizzazioneFragment;

/**
 * Created by Michele on 04/07/2019.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TavoloPrenotazioneFragment tab1 = new TavoloPrenotazioneFragment();
                return tab1;
            case 1:
                PrenotazioneFragment tab2 = new PrenotazioneFragment();
                return tab2;
            case 2:
                VisualizzaPrenotazioniFragment tab3 = new VisualizzaPrenotazioniFragment();
                return tab3;
            case 3:
                TavoloVisualizzazioneFragment tab4 = new TavoloVisualizzazioneFragment();
                return tab4;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}

