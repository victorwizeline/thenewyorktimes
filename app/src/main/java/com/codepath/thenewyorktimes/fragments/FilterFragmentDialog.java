package com.codepath.thenewyorktimes.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Toast;

import com.codepath.thenewyorktimes.R;
import com.codepath.thenewyorktimes.databinding.FragmentDialogFilterBinding;
import com.codepath.thenewyorktimes.interfaces.FilterFragmentDialogListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.codepath.thenewyorktimes.utils.Constants.DATE_FORMAT;

public class FilterFragmentDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private FragmentDialogFilterBinding bind;
    private FilterFragmentDialogListener listener;
    private Date date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_filter, container, false);
        return bind.getRoot();
    }

    @Override
    @SuppressLint("SimpleDateFormat")
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        date = calendar.getTime();
        bind.tvDate.setText(new SimpleDateFormat(DATE_FORMAT).format(date));
        bind.tvDate.setOnClickListener(v -> new DatePickerDialog(getContext(), this, year, month, day).show());
        bind.spinner.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.sort_array, R.layout.spinner_item));
        bind.save.setOnClickListener(v -> {
            String newsDesk = "";
            CheckBox[] checkBoxes = {bind.arts, bind.fashion, bind.sports};
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) {
                    newsDesk += String.format("\"%s\" ", checkBox.getText());
                }
            }
            if (newsDesk.length() < 1) {
                Toast.makeText(getContext(), "Please select at least one category", Toast.LENGTH_LONG).show();
                return;
            }

            calendar.setTime(date);
            @SuppressLint("DefaultLocale")
            String beginDate = String.format("%04d%02d%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            String sort = ((String) bind.spinner.getSelectedItem()).toLowerCase();
            String fq = String.format("news_desk:(%s)", newsDesk);
            listener.onFiltered(beginDate, sort, fq);
            dismiss();
        });
    }

    @Override
    @SuppressLint({"DefaultLocale", "SimpleDateFormat"})
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        date = calendar.getTime();
        bind.tvDate.setText(new SimpleDateFormat(DATE_FORMAT).format(date));
    }

    public void setListener(FilterFragmentDialogListener listener) {
        this.listener = listener;
    }
}