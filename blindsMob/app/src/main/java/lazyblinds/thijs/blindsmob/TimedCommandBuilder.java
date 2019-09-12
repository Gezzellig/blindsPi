package lazyblinds.thijs.blindsmob;

import android.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import blindsSenderBackEnd.Controller;

/**
 * Created by thijs on 8-10-16.
 */
public class TimedCommandBuilder {
    private int hour, min, year, month, day;
    private MainActivity mainActivity;
    private Controller controller;
    private Date alarmDate;

    public void startTimedCommandBuild(MainActivity mainActivity, Controller controller) {
        this.mainActivity = mainActivity;
        this.controller = controller;
        mainActivity.showDialog(new TimePickerFragment());
    }

    public void timeChosen(int hour, int min) {
        this.hour = hour;
        this.min = min;
        mainActivity.showDialog(new DatePickerFragment());
    }

    public void dateChosen(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        alarmDate = new GregorianCalendar(year, month, day, hour, min).getTime();
        DialogFragment ap = ActionPickerFragment.newInstance(new SimpleDateFormat("H:m, E d-MM").format(alarmDate));
        mainActivity.showDialog(ap);
    }

    public void actionChosenUp() {
        controller.tUp("Both", alarmDate);
    }

    public void actionChosenDown() {
        controller.tDown("Both", alarmDate);
    }

}
