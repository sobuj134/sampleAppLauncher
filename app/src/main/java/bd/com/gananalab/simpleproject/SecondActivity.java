package bd.com.gananalab.simpleproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bd.com.gananalab.simpleproject.models.LaunchAppsModel;
import bd.com.gananalab.simpleproject.models.MainAppsModel;
import bd.com.gananalab.simpleproject.models.SelectedApps;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class SecondActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    @Inject
    Realm r;

    @BindView(R.id.rvApps)
    RecyclerView rvApps;
    FastItemAdapter<LaunchAppsModel> fastAdapter;
    List<LaunchAppsModel> mainAppsModelList = new ArrayList<>();
    List<PackageInfo> packageInfos = new ArrayList<>();
    @Inject
    App app;

    public static void start(Context context){
        Intent intent = new Intent(context,SecondActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        App.getComponent().inject(this);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fastAdapter = new FastItemAdapter<>();
        List<SelectedApps> selectedApps = r.where(SelectedApps.class).findAll();
        if(selectedApps != null && selectedApps.size() > 0){
            for(SelectedApps selectedApp: selectedApps) {
                try {
                    PackageInfo packageInfo = app.getPackageManager().getPackageInfo(selectedApp.getPkName(), PackageManager.GET_PERMISSIONS);
                    packageInfos.add(packageInfo);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if(packageInfos != null && packageInfos.size() > 0){
                setFastAdapter();
            }
        }
    }

    public void setFastAdapter(){
        for(PackageInfo packageInfo:packageInfos){
            LaunchAppsModel mainAppsModel = new LaunchAppsModel();
            mainAppsModel.setPkInfo(packageInfo);
            mainAppsModel.setSelected(false);
            mainAppsModelList.add(mainAppsModel);
        }
        fastAdapter.add(mainAppsModelList);
        fastAdapter.setHasStableIds(false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvApps.setLayoutManager(layoutManager);
        rvApps.setAdapter(fastAdapter);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<LaunchAppsModel>() {
            @Override
            public boolean onClick(View v, IAdapter<LaunchAppsModel> adapter, LaunchAppsModel item, int position) {
                Intent i = getPackageManager().getLaunchIntentForPackage(item.getPkInfo().packageName);
                startActivity(i);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();

        }
        return true;
    }

}
