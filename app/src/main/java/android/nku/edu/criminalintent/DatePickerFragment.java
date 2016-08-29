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
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Gaurav on 8/25/2016.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "android.nku.edu.criminalintent.date";
    private static final String ARG_DATE = "date";
    private DatePicker mDatePicker;
    private Calendar mCalendar;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        mDatePicker = (DatePicker) view.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH),
                null);

        return new AlertDialog.Builder(getActivity()).setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();

                        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
                        int minute = mCalendar.get(Calendar.MINUTE);

                        Date date = new GregorianCalendar(year, month, day, hour, minute).getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                }).create();
    }

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
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
            intent.putExtra(EXTRA_DATE, date);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }
}