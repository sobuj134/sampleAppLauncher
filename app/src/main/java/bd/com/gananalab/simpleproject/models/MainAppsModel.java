package bd.com.gananalab.simpleproject.models;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.IItem;

import java.util.Collections;
import java.util.List;

import bd.com.gananalab.simpleproject.App;
import bd.com.gananalab.simpleproject.R;
import bd.com.gananalab.simpleproject.utils.StringUtils;
import butterknife.ButterKnife;
import io.realm.annotations.Ignore;

/**
 * Created by monir.sobuj on 6/8/17.
 */

public class MainAppsModel implements IItem<MainAppsModel, MainAppsModel.ViewHolder> {

    ResolveInfo pkInfo;

    private boolean clicked = false;
    private Object tag;// defines if this item is isSelectable
    private boolean isSelectable = true;
    private boolean isEnabled = true;
    private boolean isSelected = false; // defines if the item is selected

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    @Override
    public Object getTag() {
        return tag;
    }

    @Override
    public MainAppsModel withTag(Object tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public MainAppsModel withEnabled(boolean enabled) {
        this.isEnabled = enabled;
        return this;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public MainAppsModel withSetSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public ResolveInfo getPkInfo() {
        return pkInfo;
    }

    public void setPkInfo(ResolveInfo pkInfo) {
        this.pkInfo = pkInfo;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean isSelectable() {
        return isSelectable;
    }

    @Override
    public MainAppsModel  withSelectable(boolean selectable) {
        this.isSelectable = selectable;
        return this;
    }

    @Override
    public int getType() {
        return R.id.rvApps;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_main_app_list;
    }

    @Override
    public View generateView(Context ctx) {
        ViewHolder viewHolder                           = getViewHolder(LayoutInflater.from(ctx).inflate(getLayoutRes(), null, false));
        bindView(viewHolder, Collections.EMPTY_LIST);
        return viewHolder.itemView;
    }

    @Override
    public View generateView(Context ctx, ViewGroup parent) {
        ViewHolder viewHolder                           = getViewHolder(LayoutInflater.from(ctx).inflate(getLayoutRes(), parent, false));
        bindView(viewHolder, Collections.EMPTY_LIST);
        return viewHolder.itemView;
    }

    private ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder getViewHolder(ViewGroup parent) {
        return getViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), parent, false));
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
//        SystemUtils.log("on bind view");
        PackageManager packageManager = App.getInstance().getPackageManager();
        Drawable appIcon = pkInfo.loadIcon(packageManager);
        String appName = pkInfo.loadLabel(packageManager).toString();
        holder.ivAppIcon.setImageDrawable(appIcon);
        holder.txtAppName.setText(appName);
        holder.swSelect.setChecked(isSelected);
    }

    @Override
    public void unbindView(ViewHolder holder) {
        holder.txtAppName.setText(null);
    }

    @Override
    public boolean equals(int id) {
        return false;
    }


    @Override
    public MainAppsModel withIdentifier(long identifier) {
        return null;
    }

    @Override
    public long getIdentifier() {
        return 0;
    }

    static public class ViewHolder extends RecyclerView.ViewHolder{

       TextView txtAppName;
      public SwitchCompat swSelect;
       ImageView ivAppIcon;


        public ViewHolder(View itemView) {
            super(itemView);
            txtAppName = itemView.findViewById(R.id.txtAppName);
            swSelect = itemView.findViewById(R.id.swSelect);
            ivAppIcon = itemView.findViewById(R.id.ivAppIcon);

        }
    }
}
