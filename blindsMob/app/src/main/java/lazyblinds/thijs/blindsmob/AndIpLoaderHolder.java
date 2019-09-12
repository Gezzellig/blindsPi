package lazyblinds.thijs.blindsmob;

/**
 * Created by thijs on 1-9-16.
 */
public class AndIpLoaderHolder {
    private static AndIpLoader aIL = new AndIpLoader();

    public static AndIpLoader getAndIpLoader() {
        return aIL;
    }
}
