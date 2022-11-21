package com.example.fecafootdemo.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fecafootdemo.R;
import com.example.fecafootdemo.data.dao.entities.Game;
import com.example.fecafootdemo.data.dao.entities.Ticket;
import com.example.fecafootdemo.data.dao.entities.User;
import com.example.fecafootdemo.databinding.ActivityMainBinding;
import com.example.fecafootdemo.ui.base.BaseActivity;
import com.example.fecafootdemo.ui.dashboard.control.ControlActivity;
import com.example.fecafootdemo.utils.views.CustomProgressbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends BaseActivity implements MainView {

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private MainPresenter<MainView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        presenter = new MainPresenter<>(this);
        onSetup();
    }

    @Override
    public void onSetup() {
        super.onSetup();
        binding.appBarMain.toolbar.setNavigationOnClickListener(v -> binding.drawerLayout.openDrawer(GravityCompat.START));
        binding.appBarMain.openDrawer.setOnClickListener(v -> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_control_ticket:
                        presenter.startControl();
                        break;
                    case R.id.nav_export:
                        new Handler().postDelayed(() -> {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(R.string.export)
                                    .setMessage(getString(R.string.are_sure_of_this_action))
                                    .setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
                                        dialogInterface.dismiss();
                                        new Handler().postDelayed(() -> {
                                            presenter.exportData();
                                        }, 300);
                                    }).create()
                                    .show();
                        }, 300);


                        break;
                    default:
                        break;
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        binding.appBarMain.container.fabButton.setOnClickListener(v -> {
            presenter.startControl();
        });
        presenter.setNavBarInfo();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            presenter.logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStartControlActivity() {
        startActivity(new Intent(this, ControlActivity.class));
    }

    @Override
    public void onResumeView() {

        //binding.appBarMain.toolbarSubtitle.setText(match);

        /**int remaining =  (prestigeCap + premiumCap + vipCap) - (vipControl + premiumControl + prestigeControl);
        int checked =  (vipControl + premiumControl + prestigeControl);
        int maxCapacity = prestigeCap + premiumCap + vipCap;

        binding.appBarMain.container.remainingCountTv.setText(String.valueOf(remaining));
        binding.appBarMain.container.checkCountTv.setText(String.valueOf(checked));
        binding.appBarMain.container.progressStatsGeneral.setMax(maxCapacity);
        binding.appBarMain.container.progressStatsGeneral.setProgress(checked);

        binding.appBarMain.container.firstLabelCount.setText(String.valueOf(prestigeControl));
        binding.appBarMain.container.firstLabelTotal.setText(String.valueOf(prestigeCap));
        binding.appBarMain.container.secondLabelCount.setText(String.valueOf(vipControl));
        binding.appBarMain.container.secondLabelTotal.setText(String.valueOf(vipCap));
        binding.appBarMain.container.thirdLabelCount.setText(String.valueOf(premiumControl));
        binding.appBarMain.container.thirdLabelTotal.setText(String.valueOf(premiumCap));

        binding.appBarMain.container.prestigeProgress.setMax(prestigeCap);
        binding.appBarMain.container.prestigeProgress.setProgress(prestigeControl);

        binding.appBarMain.container.vipProgress.setMax(vipCap);
        binding.appBarMain.container.vipProgress.setProgress(vipControl);

        binding.appBarMain.container.premiumProgress.setMax(premiumCap);
        binding.appBarMain.container.premiumProgress.setProgress(premiumControl);**/


        //codes for content
       /** binding.appBarMain.container.prestigeControlTv.setText(getString(R.string._spectators_controlled, prestigeControl));
        binding.appBarMain.container.premiumControlTv.setText(getString(R.string._spectators_controlled, premiumControl));
        binding.appBarMain.container.vipControlTv.setText(getString(R.string._spectators_controlled, vipControl));


        binding.appBarMain.container.prestigeNotControlledTv.setText(getString(R.string._spectators_not_controlled, unControlledPres));//to review
        binding.appBarMain.container.premiumNotControlledTv.setText(getString(R.string._spectators_not_controlled, unControlledPrem));//to review
        binding.appBarMain.container.vipNotControlledTv.setText(getString(R.string._spectators_not_controlled, unControlledVip));//to review


        binding.appBarMain.container.prestigeCapacityTv.setText(getString(R.string.no_of_spectators_n_prestige, prestigeCap));
        binding.appBarMain.container.premiumCapacityTv.setText(getString(R.string.no_of_spectators_, premiumCap));
        binding.appBarMain.container.vipCapacityTv.setText(getString(R.string.no_of_spectators_vip, vipCap));

        CustomProgressbar generalWheel = binding.getRoot().findViewById(R.id.general_wheel_percentage);
        generalWheel.setText(String.valueOf(vipPercentage + premiumPercentage + prestigePercentage));
        generalWheel.setMax(premiumCap + vipCap + premiumCap);
        generalWheel.setProgress(vipPercentage + premiumPercentage + prestigePercentage);

        CustomProgressbar prestigeWheel = binding.getRoot().findViewById(R.id.prestige_percentage_wheel_1);
        CustomProgressbar prestigeWheel2 = binding.getRoot().findViewById(R.id.prestige_wheel_2);
        prestigeWheel.setText(String.valueOf(prestigePercentage));
        prestigeWheel.setMax(prestigeCap);
        prestigeWheel.setProgress(prestigePercentage);
        prestigeWheel2.setText(String.valueOf(prestigePercentage));
        prestigeWheel2.setMax(prestigeCap);
        prestigeWheel2.setProgress(prestigePercentage);

        CustomProgressbar vipWheel = binding.getRoot().findViewById(R.id.vip_wheel_1);
        CustomProgressbar vipWheel2 = binding.getRoot().findViewById(R.id.vip_wheel_2);
        vipWheel.setText(String.valueOf(vipPercentage));
        vipWheel.setProgress(vipPercentage);
        vipWheel.setMax(vipCap);
        vipWheel2.setText(String.valueOf(vipPercentage));
        vipWheel2.setProgress(vipPercentage);
        vipWheel2.setMax(vipCap);

        CustomProgressbar premiumWheel = binding.getRoot().findViewById(R.id.premium_wheel_1);
        CustomProgressbar premiumWheel2 = binding.getRoot().findViewById(R.id.premium_wheel_2);
        premiumWheel.setText(String.valueOf(premiumPercentage));
        premiumWheel.setProgress(premiumPercentage);
        premiumWheel.setMax(premiumCap);
        premiumWheel2.setText(String.valueOf(premiumPercentage));
        premiumWheel2.setProgress(premiumPercentage);
        premiumWheel2.setMax(premiumCap);**/

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onNavBarSetup(User user, ArrayList<Ticket> tickets) {
        View view = binding.navView.getHeaderView(0);
        ((TextView) view.findViewById(R.id.current_user_name_tv)).setText(user.getPenomd());
        ((TextView) view.findViewById(R.id.current_user_email_tv)).setText(user.getPemail());
        ((TextView)findViewById(R.id.check_count_tv)).setText("" + tickets.size());
        TicketAdapter adapter = new TicketAdapter(tickets);
        RecyclerView recyclerView = findViewById(R.id.ticketList);
        recyclerView.setAdapter(adapter);

        if (tickets.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.empty_container).setVisibility(View.INVISIBLE);
        }else{
            recyclerView.setVisibility(View.GONE);
            findViewById(R.id.empty_container).setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resumeView();
    }
}