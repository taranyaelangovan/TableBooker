package com.example.tablebooker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BookingPage extends AppCompatActivity implements View.OnClickListener {

    private SeekBar seekBar;
    private TextView textView;
    private Button button;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);
        initializeVariables();

        addListenerOnSpinnerItemSelection();

        textView.setText(Integer.toString(seekBar.getProgress()));

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                Toast.makeText(getApplicationContext(), "Selecting seat count", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText(Integer.toString(progress));
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT);
            }
        });

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_button:
                EditText personName = (EditText) findViewById(R.id.person_name);
                String name = personName.getText().toString();
                String time = String.valueOf(spinner.getSelectedItem());
                String seatCount = textView.getText().toString();
                alertDialog(name, time, seatCount);
                break;
        }
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner = (Spinner) findViewById(R.id.input_time);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    private void alertDialog(String name, String time, String seatCount) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage(name + ", this is to confirm that you have made a reservation at " + time + " for a table for " + seatCount + ".");
        dialog.setTitle("Booking Details");
        dialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Booking confirmed",Toast.LENGTH_LONG).show();
                    }
                });
        dialog.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Booking cancelled",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seatcount_bar);
        textView = (TextView) findViewById(R.id.seatcount);
        button = (Button) findViewById(R.id.book_button);
    }
}
