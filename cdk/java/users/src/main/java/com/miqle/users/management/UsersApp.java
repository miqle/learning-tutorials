package com.miqle.users.management;

import software.amazon.awscdk.core.App;


public class UsersApp {
    public static void main(final String[] args) {
        App app = new App();

        new UsersStack(app, "UsersStack");

        app.synth();
    }
}
