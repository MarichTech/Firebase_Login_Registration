package com.marichtech.firebaseloginreg;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.marichtech.firebaseloginreg.ui.DrawerAdapter;
import com.marichtech.firebaseloginreg.ui.DrawerItem;
import com.marichtech.firebaseloginreg.ui.SimpleItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_PROFILE = 1;
    private static final int POS_HISTORY = 2;
    private static final int POS_PRICE = 3;
    private static final int POS_ABOUT = 4;
    private static final int POS_AGENT = 5;
    private static final int POS_LOGOUT = 6;

    DrawerAdapter adapter;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        InitializeToolBar();

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.activity_main_drawer)
                .inject();

        InitializeSlideRoot();


    }

    public void InitializeToolBar(){
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
    }

    public void InitializeSlideRoot(){


        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor (POS_PROFILE),
                createItemFor (POS_HISTORY),
                createItemFor(POS_PRICE),
                createItemFor(POS_AGENT),
                createItemFor(POS_ABOUT),
                //new SpaceItem(10),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);
    }

    @NonNull
    private String[] loadScreenTitles() {

        String[] selectedTile;

            selectedTile =  getResources().getStringArray(R.array.ld_activityScreenTitles);

        return selectedTile;

    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.minimal_black))
                .withTextTint(color(R.color.minimal_black))
                .withSelectedIconTint(color(R.color.colorAccentt))
                .withSelectedTextTint(color(R.color.colorAccentt));
    }
    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }


    @Override
    public void onItemSelected(int position) {

        switch (position) {

            case POS_PROFILE:

                    slidingRootNav.closeMenu(true);


                    return;


            case POS_HISTORY:



                    this.invalidateOptionsMenu();

                    slidingRootNav.closeMenu(true);


                    return;


            case POS_PRICE:

                this.invalidateOptionsMenu();

                slidingRootNav.closeMenu(true);


                return;


            case POS_AGENT:

                return;

            case POS_ABOUT:



                return;


            case POS_LOGOUT:


                    return;

        }

        slidingRootNav.closeMenu(true);
    }


    public void viewProfile(View view) {
    }
}
