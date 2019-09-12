package lazyblinds.thijs.blindsmob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetAndIpActivity extends AppCompatActivity{
    private static String TAG = "GetAndIpActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_and_ip);
        Log.d("GetIP", "Asking the ip");

        final AndIpLoader aIL = AndIpLoaderHolder.getAndIpLoader();

        final EditText ipAddressTxtF   = (EditText)findViewById(R.id.ipAddressTxtF);
        Button connectBtn = (Button) findViewById(R.id.connect) ;
        connectBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "Connect die shit!!");
                Log.d(TAG, "kutlogger: "+ipAddressTxtF.getText().toString());
                if (aIL.tryConnect(ipAddressTxtF.getText().toString())) {
                    Log.d(TAG, "Connected!!");
                    GetAndIpActivity.this.finish();
                } else {
                    Log.d(TAG, "Could not connect");
                }
            }
        });
    }
}
