package com.example.android.exemploappidioma_fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //Seta o fragment selecionado na aba de temas
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumerosFragment();
        } else if (position == 1) {
            return new FamiliaFragment();
        } else if (position == 2) {
            return new CoresFragment();
        } else {
            return new FrasesFragment();
        }
    }

    //Retorna os títulos relativos a cada "página tema"
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Números";
        } else if (position == 1) {
            return "Família";
        } else if (position == 2) {
            return "Cores";
        } else {
            return "Frases";
        }
    }

    @Override
        public int getCount () {
            return 4;
        }

}