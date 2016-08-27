package android.nku.edu.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Gaurav on 8/26/2016.
 */
public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "android.nku.edu.criminalintent.time";

    private static final String ARG_TIME = "time";
    private TimePicker mTimePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_TIME);
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) view.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setCurrentHour(hours);
        mTimePicker.setCurrentMinute(minutes);

        return new AlertDialog.Builder(getActivity()).setView(view)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hours = mTimePicker.getCurrentHour();
                        int minutes = mTimePicker.getCurrentMinute();
                        Date date = new GregorianCalendar(0, 0, 0, hours, minutes).getTime();
                        //sendResult(Activity.RESULT_OK, date);
                    }
                }).create();
    }

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates an intent, puts the date on it as an extra, and then calls
     * CrimeFragment.onActivityResult(...)
     * */
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        else {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TIME, date);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }
}
