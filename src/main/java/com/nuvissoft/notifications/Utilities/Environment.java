package com.nuvissoft.notifications.Utilities;

import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;

public class Environment {
    // public static final Dotenv env = Dotenv.load();
    public static final Map<String, String> env = System.getenv();
}
