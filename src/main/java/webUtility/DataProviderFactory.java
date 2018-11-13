package webUtility;

public class DataProviderFactory {
    static ReadConfig config;

    public static ReadConfig getConfig() {

        if (config == null) {
            config = new ReadConfig();
        }
        return config;
    }

}
