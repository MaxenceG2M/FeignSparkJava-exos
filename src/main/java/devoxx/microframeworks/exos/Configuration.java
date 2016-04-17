package devoxx.microframeworks.exos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Optional;
import java.util.ResourceBundle;

public enum Configuration {
    INSTANCE;
    private final ResourceBundle bundle;

    Configuration() {
        bundle = ResourceBundle.getBundle("configuration");
    }

    public Gson getGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public int getPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String sPort = Optional.ofNullable(processBuilder.environment().get("PORT"))
                .orElse(bundle.getString("port"));
        return Integer.parseInt(sPort);
    }

    public String getUrl(Class<?> aClass) {
        return bundle.getString(aClass.getSimpleName() + ".url");
    }

}