package bd.com.gananalab.simpleproject.dependency;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import bd.com.gananalab.simpleproject.App;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * Created by sobuj on 5/17/17.
 */

@Singleton
@Module
public class AppModule {

    private App app;

    AppModule(App app){
        this.app = app;
    }

    @Provides
    @Singleton
    App provideContext(){
        return app;
    }



    @Singleton
    @Provides
    Realm provideRealm(){
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        //return Realm.getDefaultInstance();
        return Realm.getInstance(config);
    }







}
