package com.fburaky.instagramcloneparse;

import android.app.Application;

import com.parse.Parse;

public class ParseStartedActivity extends Application {


    // Sunucu ile uygulamamızı bağlamak için bu sınıfı oluşturuyoruz .


    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        Parse.initialize(new Parse.Configuration.Builder(this)

             .applicationId("QeJ3rna8jjWGJob98Mkt9bgiQpdKnbaGrKqiwGTe")
             .clientKey("qroP8QWG1r4c2Jy3lSdRJE8Vkh2siGja5Dz97Tj6")
             .server("https://parseapi.back4app.com/")
             .build()
        );
    }
}
