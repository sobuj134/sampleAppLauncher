package bd.com.gananalab.simpleproject;

import android.app.Application;
import android.content.pm.PackageInfo;


import bd.com.gananalab.simpleproject.dependency.AppComponent;
import bd.com.gananalab.simpleproject.dependency.DaggerAppComponent;
import io.realm.Realm;

/**
 * Created by sobuj on 5/17/17.
 */

public class App extends Application {

    private static AppComponent appComponent;

    private static App mInstance;


    public static synchronized App getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        appComponent = DaggerAppComponent.Initializer.init(this);
        //initialize Realm
        Realm.init(this);
    }

    public static AppComponent getComponent(){
        return appComponent;
    }

    public App getActivity(){
        return this;
    }


}
