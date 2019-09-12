package lazyblinds.thijs.blindsmob;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.Serializable;

import blindsSenderBackEnd.IpLoader;
import blindsSenderBackEnd.Sender;

/**
 * Created by thijs on 21-8-16.
 */
public class AndIpLoader extends IpLoader implements Serializable{
    private String FILEPATH;
    private static String FILENAME = "ipAddress", TAG = "AndIpLoader";
    private Activity mainActivity;
    private Sender sender;

    public void initialize(Activity mainActivity) {
        this.mainActivity = mainActivity;
        FILEPATH = mainActivity.getFilesDir().toString()+FILENAME;
    }

    @Override
    public File getFile() {
        Log.d(TAG, "getting File");
        return new File(FILEPATH);
    }

    @Override
    public File newFile() {
        Log.d(TAG, "creating new File");
        return getFile();
    }

    @Override
    public void connect(Sender sender) {
        this.sender = sender;

        String ipAddress = getIpAddressFromFile();
        if (ipAddress != "") {
            if (sender.connectToReceiver(ipAddress)) {
                Log.d(TAG, "Got ip from file");
                return;
            }
        }

        Log.d(TAG, "Open AskIpActivity");

        mainActivity.startActivity(new Intent(mainActivity.getApplicationContext(), GetAndIpActivity.class));
        return;
    }

    public boolean tryConnect(String ipAddress) {
        if (sender != null) {
            Log.d(TAG, "Connecting with: "+ ipAddress);
            if (sender.connectToReceiver(ipAddress)) {
                this.saveIpAddressInFile(ipAddress);
                return true;
            }
        } else {
            Log.d(TAG, "Sender is NULL");
        }
        return false;
    }
}

