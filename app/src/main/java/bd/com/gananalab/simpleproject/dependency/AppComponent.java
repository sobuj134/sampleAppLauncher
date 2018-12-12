package bd.com.gananalab.simpleproject.dependency;

import javax.inject.Singleton;

import bd.com.gananalab.simpleproject.App;
import bd.com.gananalab.simpleproject.MainActivity;
import bd.com.gananalab.simpleproject.SecondActivity;
import dagger.Component;

/**
 * Created by sobuj on 5/17/17.
 */

@Singleton
@Component(modules = AppModule.class)

public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(SecondActivity mainActivity);

    final class Initializer {
        private Initializer(){}

        public static AppComponent init(App app){
            return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
        }
    }

}
