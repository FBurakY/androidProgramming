package com.fburaky.parselearning;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Set Log Level
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("8DFo2ls00wfOjV3usf7EYskv4vceOZ7iHCNTZgPS")
                        .clientKey("S9U3AqcDvWePjIZff3QuruUQxImyQidKkaBJO3Jh")
                        .server("https://parseapi.back4app.com/")
                        .build()
                );
        // Parse bir sunucu olduğundan applicationId , clientKey ,server olması lazım ki bu şekilde projemizi internette ki server'a bağlaya bilelim .
    }
}
