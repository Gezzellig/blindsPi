package lazyblinds.thijs.blindsmob;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.Serializable;

import blindsSenderBackEnd.Controller;

public class MainActivity extends AppCompatActivity implements Serializable{
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AndIpLoader aIL = AndIpLoaderHolder.getAndIpLoader();
        aIL.initialize(this);
        controller = new Controller(aIL);
        connectButtons(controller);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimedCommandBuilderHolder.gettcb().startTimedCommandBuild(MainActivity.this, controller);
            }
        });
    }

    public void showDialog(DialogFragment fragment) {
        DialogFragment newFragment = new TimePickerFragment();
        fragment.show(this.getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
    }

    public void showActionPicker() {
        DialogFragment newFragment = new ActionPickerFragment();
        newFragment.show(this.getFragmentManager(), "actionPicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Add controller.onClose()
    }

    private void connectButtons(final Controller controller) {
        Button lUp = (Button) findViewById(R.id.lUp) ;
        lUp.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {controller.lUp();}});

        Button lStop = (Button) findViewById(R.id.lStop) ;
        lStop.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {controller.lStop();}});

        Button lDown = (Button) findViewById(R.id.lDown) ;
        lDown.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {controller.lDown();}});


        Button bUp = (Button) findViewById(R.id.bUp) ;
        bUp.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {controller.bUp();}});

        Button bStop = (Button) findViewById(R.id.bStop) ;
        bStop.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {controller.bStop();}});

        Button bDown = (Button) findViewById(R.id.bDown) ;
        bDown.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {controller.bDown();}});


        Button rUp = (Button) findViewById(R.id.rUp) ;
        rUp.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {controller.rUp();}});

        Button rStop = (Button) findViewById(R.id.rStop) ;
        rStop.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {controller.rStop();}});

        Button rDown = (Button) findViewById(R.id.rDown) ;
        rDown.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {controller.rDown();}});
    }
}
