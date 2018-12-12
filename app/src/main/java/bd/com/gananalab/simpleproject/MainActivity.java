package bd.com.gananalab.simpleproject;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bd.com.gananalab.simpleproject.models.MainAppsModel;
import bd.com.gananalab.simpleproject.models.SelectedApps;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    @Inject
    Realm r;

    @BindView(R.id.rvApps)
    RecyclerView rvApps;
    @BindView(R.id.btnNext)
    Button btnNext;
    FastItemAdapter<MainAppsModel> fastAdapter;
    List<ResolveInfo> packageInfos;
    List<MainAppsModel> mainAppsModelList = new ArrayList<>();
    List<ResolveInfo> nextPkInfo = new ArrayList<>();
    @Inject
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent().inject(this);
        ButterKnife.bind(this);
        fastAdapter = new FastItemAdapter<>();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        packageInfos = getPackageManager().queryIntentActivities( mainIntent, 0);
        setFastAdapter();
    }

    public void setFastAdapter(){
        for(ResolveInfo packageInfo:packageInfos){
            MainAppsModel mainAppsModel = new MainAppsModel();
            mainAppsModel.setPkInfo(packageInfo);
            mainAppsModel.setSelected(false);
            mainAppsModelList.add(mainAppsModel);
        }
        fastAdapter.add(mainAppsModelList);
        fastAdapter.setHasStableIds(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvApps.setLayoutManager(layoutManager);
        rvApps.setAdapter(fastAdapter);

        fastAdapter.withItemEvent(new ClickEventHook<MainAppsModel>() {

            @Nullable
            @Override
            public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
                //return the views on which you want to bind this event
                if (viewHolder instanceof MainAppsModel.ViewHolder) {
                    return ((MainAppsModel.ViewHolder) viewHolder).swSelect;
                }
                return null;
            }

            @Override
            public void onClick(View v, int position, FastAdapter<MainAppsModel> fastAdapter1, final MainAppsModel item) {
                //react on the click event
                if (item.isSelected()) {
                    nextPkInfo.remove(item.getPkInfo());
                    item.setSelected(false);
                } else {
                    nextPkInfo.add(item.getPkInfo());
                    item.setSelected(true);
                }
                fastAdapter1.notifyDataSetChanged();
                Log.d(TAG, "onClick: Size(): "+ nextPkInfo.size());
            }
        });
    }
    @OnClick(R.id.btnNext)
    public void clickNext(){
        if(nextPkInfo != null && nextPkInfo.size() > 0) {
            r.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if(realm.where(SelectedApps.class).findAll() != null)
                        realm.where(SelectedApps.class).findAll().deleteAllFromRealm();

                    for (ResolveInfo pkInfo : nextPkInfo){
                        SelectedApps selectedApps = new SelectedApps();
                        selectedApps.setId(System.currentTimeMillis());
                        selectedApps.setPkName(pkInfo.activityInfo.packageName);
                        r.insertOrUpdate(selectedApps);
                    }
                    goToNextActivity();
                }
            });

        }
    }

    public void goToNextActivity(){
        SecondActivity.start(this);
    }
}
