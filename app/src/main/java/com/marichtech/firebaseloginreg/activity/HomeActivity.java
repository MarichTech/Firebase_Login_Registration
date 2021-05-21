package com.marichtech.firebaseloginreg.activity;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marichtech.firebaseloginreg.R;
import com.marichtech.firebaseloginreg.fragments.DashboardFragment;
import com.marichtech.firebaseloginreg.ui.DrawerAdapter;
import com.marichtech.firebaseloginreg.ui.DrawerItem;
import com.marichtech.firebaseloginreg.ui.SimpleItem;
import com.marichtech.firebaseloginreg.ui.SpaceItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_PROFILE = 1;
    private static final int POS_ABOUT = 2;
    private static final int POS_LOGOUT = 4;

    DrawerAdapter adapter;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;
    public static Toolbar toolbar;

    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;
    private FirebaseFirestore firestore;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mAuth = FirebaseAuth.getInstance();
        currentuser=mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();


        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        if(currentuser==null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }else{
            userId = currentuser.getUid();
        }

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.activity_main_drawer)
                .inject();

        InitializeSlideRoot();

        // InitializeFragments();


    }

    public void InitializeToolBar(){

    }

    public void InitializeSlideRoot(){


        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor (POS_PROFILE),
                createItemFor(POS_ABOUT),
                new SpaceItem(40),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
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
                .withSelectedIconTint(color(R.color.colorPrimaryDark))
                .withSelectedTextTint(color(R.color.colorPrimaryDark));
    }
    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }


    @Override
    public void onItemSelected(int position) {

        Fragment selectedScreen;

        switch (position) {

            case POS_DASHBOARD:
                slidingRootNav.closeMenu(true);
                selectedScreen = new DashboardFragment();
                showFragment(selectedScreen);
                return;


            case POS_PROFILE:
                slidingRootNav.closeMenu(true);
                startActivity(new Intent(this, ProfileActivity.class));

                return;

            case POS_ABOUT:
                slidingRootNav.closeMenu(true);
                startActivity(new Intent(this, AboutActivity.class));
                return;

            case POS_LOGOUT:
                slidingRootNav.closeMenu(true);
                if (currentuser != null && isOnline()) {

                    new MaterialDialog.Builder(this)
                            .title("Logout")
                            .content("Are you sure do you want to logout from this account?")
                            .positiveText("Yes")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    logout();
                                    dialog.dismiss();
                                }
                            }).negativeText("No")
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            }).show();

                } else {

                    new MaterialDialog.Builder(this)
                            .title("Logout")
                            .content("A technical occurred while logging you out, Check your network connection and try again.")
                            .positiveText("Done")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            }).show();

                }
                return;
        }

        slidingRootNav.closeMenu(true);
    }

    public void logout() {

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setIndeterminate(true);
        mDialog.setMessage("Logging you out...");
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        Map<String, Object> tokenRemove = new HashMap<>();
        tokenRemove.put("token_id", "");

        firestore.collection("Users").document(userId).update(tokenRemove).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                mDialog.dismiss();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Logout Error", e.getMessage());
            }
        });

    }


    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public void viewProfile(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentuser==null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
