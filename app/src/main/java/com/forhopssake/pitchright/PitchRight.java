package com.forhopssake.pitchright;

import com.forhopssake.pitchright.context.PitchContext;
import com.forhopssake.pitchright.context.PitchContextFactory;
import com.forhopssake.pitchright.context.Starter;
import com.forhopssake.pitchright.context.StarterStep;
import com.forhopssake.pitchright.context.TargetType;
import com.forhopssake.pitchright.context.VolumeUnits;
import com.forhopssake.pitchright.context.Wort;
import com.forhopssake.pitchright.context.Yeast;
import com.forhopssake.pitchright.res.PropertyLoader;
import com.forhopssake.pitchright.util.Calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


public class PitchRight extends Activity implements AdapterView.OnItemSelectedListener, View.OnKeyListener {

    private PitchContext pitchContext;
    private PitchContextFactory factory;
    private DecimalFormat ogFormat = new DecimalFormat("#.000");
    private PopupWindow help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch_right);
        init();
        updateResults();


    }

    private void init() {
        initContext(getApplicationContext());
        initControls();
        initHelp();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }


    private void initContext(Context andContext) {
        PropertyLoader propertyLoader = new PropertyLoader(andContext);
        Properties properties = propertyLoader.getProperties("pitch.properties");
        factory = new PitchContextFactory(properties);
        pitchContext = factory.createContext();
    }

    private void initControls() {
        final Spinner pitchRate = (Spinner) findViewById(R.id.targetPitchRateSpinner);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.targetPitchRate_items,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        pitchRate.setAdapter(staticAdapter);
        pitchRate.setOnItemSelectedListener(this);
        //
        final Wort wort = pitchContext.getWort();
        EditText editText = (EditText) findViewById(R.id.pitchRate);
        editText.setText(wort.getTargetPitchRate() + "");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                double d = Calculator.getDouble(s.toString());
                wort.setTargetPitchRate(d);
                updateResults();
            }
        });


        EditText volume = (EditText) findViewById(R.id.batchVolumeEditText);
        volume.setText(wort.getBatchVolume() + "");
        volume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                double d = Calculator.getDouble(s.toString());
                wort.setBatchVolume(d);
                updateResults();
            }
        });

        EditText og = (EditText) findViewById(R.id.originalGravityEditText);
        og.setText(wort.getOriginalGravity() + "");
        og.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                double d = Calculator.getDouble(s.toString());
                wort.setOriginalGravity(d);
                updateResults();
            }
        });


        Spinner volumeUnits = (Spinner) findViewById(R.id.batchVolumeSpinner);
        staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.volumneUnits_items,
                        android.R.layout.simple_spinner_item);
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        volumeUnits.setAdapter(staticAdapter);
        volumeUnits.setOnItemSelectedListener(this);

        // init Yeast
        final Yeast yeast = pitchContext.getYeast();
        EditText initialCells = (EditText) findViewById(R.id.initialCellCountEditText);
        initialCells.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int i = Integer.parseInt(s.toString());
                yeast.setIntialCellCount(i);
                updateResults();
            }
        });

        EditText productionDate = (EditText) findViewById(R.id.productionDateEditText);
        Date date = pitchContext.getYeast().getProductionDate();
        productionDate.setText(getDateString(date));

        setViability();
        EditText viability = (EditText) findViewById(R.id.viabilityEditText);
        viability.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int via = Integer.parseInt(s.toString());
                yeast.setViability(via);
                updateResults();
            }
        });

        initStarter();
        // init help


    }

    protected void initStarter() {
        Starter starter = pitchContext.getStarter();
        final Spinner flaskSize = (Spinner) findViewById(R.id.flaskSizeSpinner);
        flaskSize.setOnItemSelectedListener(this);
        flaskSize.setSelection(3);

        StarterStep s1 = new StarterStep();
        starter.getStarterSteps().add(s1);
        final Spinner aerationMethod = (Spinner) findViewById(R.id.aerationMethodSpinner);
        flaskSize.setOnItemSelectedListener(this);
        flaskSize.setSelection(0);

    }

    private void initHelp() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout
        View layout = inflater.inflate(R.layout.help_layout,
                (ViewGroup) findViewById(R.id.helpElement));
        // create a 300px width and 470px height PopupWindow
        help = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        layout.findViewById(R.id.help_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissHelp();
            }
        });
    }

    // performs pitch rate calculation using context
    private void updateResults() {
        Wort wort = pitchContext.getWort();

        double yeastRequired = Calculator.calculateYeastRequired(wort.getTargetPitchRate(),
                wort.getBatchVolume(),
                wort.getOriginalGravity(),
                wort.getCellOverbuild(),
                wort.isGallons());
        ((EditText) findViewById(R.id.requiredCellsEditText)).setText(yeastRequired + "");


        Yeast yeast = pitchContext.getYeast();
        int viability = yeast.getViability();
        int initialCells = yeast.getIntialCellCount();
        double viableCellCount = (viability * initialCells) / 100;
        yeast.setViableCellCount((int) Math.round(viableCellCount));
        ((EditText) findViewById(R.id.viableCellCountEditText)).setText(yeast.getViableCellCount() + "");

        Starter starter = pitchContext.getStarter();
        double starterGravity = starter.getStartingGravity();


        // starter steps


    }
    // event handlers

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       switch  (parent.getId()) {
           case R.id.targetPitchRateSpinner: targetPitchRateChanged(parent);
               break;
           case R.id.batchVolumeSpinner:
               volumneUnitsChanged(parent);
               break;
           case R.id.flaskSizeSpinner:
               flaskSizeChanged(parent);
               break;
       }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void targetPitchRateChanged(View view) {
        String valStr = (String)((Spinner)view).getSelectedItem();
        TargetType type = TargetType.valueOf(valStr.toUpperCase());
        if (TargetType.CUSTOM.equals(type)) {
            // show custom box and set value
            EditText v = (EditText)findViewById(R.id.pitchRate);
            v.setEnabled(true);
            v.setSelection(v.getText().length());
            double val = getCustomPitchRate();
            pitchContext.getWort().setTargetPitchRate(val);
        } else {
            EditText v = (EditText)findViewById(R.id.pitchRate);
            double val = type.getValue();
            v.setEnabled(false);
            v.setText(String.valueOf(val));
            pitchContext.getWort().setTargetPitchRate(val);
        }
       updateResults();
    }

    private void volumneUnitsChanged(View view) {
        String valStr = (String) ((Spinner) view).getSelectedItem();
        VolumeUnits type = VolumeUnits.valueOf(valStr.toUpperCase());
        pitchContext.getWort().setUnits(type);
        updateResults();
    }

    private void flaskSizeChanged(View view) {
        Spinner flaskSpinner = (Spinner) view;
        String selectedVal = getResources().getStringArray(R.array.starter_flaskSize_values)[flaskSpinner.getSelectedItemPosition()];
        double size = Double.parseDouble(selectedVal);
        pitchContext.getStarter().setFlaskSize(size);
        updateResults();
    }

    private double getCustomPitchRate() {
        EditText v = (EditText) findViewById(R.id.pitchRate);
        Editable edit = v.getText();
        double val = Double.parseDouble(edit.toString());
        return val;
    }



    public void showHelp(View view) {
        String text = (String) view.getTag();
        //    String[] textar = text.split("\\$");

        View v = help.getContentView();
        help.showAtLocation(findViewById(R.id.frameLayout), Gravity.CENTER, 0, 0);

        TextView tv = (TextView) v.findViewById(R.id.helpText);
        tv.setText(Html.fromHtml(text));
//        tv = (TextView)v.findViewById(R.id.helpTitle);
//        tv.setText(Html.fromHtml(textar[0]));
//        Toast t = Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG);
//        t.show();

    }

    public void dismissHelp() {
        help.dismiss();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    public void showDatePicker(View view) {
        final Calendar c = Calendar.getInstance();
        c.setTime(pitchContext.getYeast().getProductionDate());
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        setDate(year, monthOfYear, dayOfMonth);
                        setViability();

                    }
                }, mYear, mMonth, mDay);
        dpd.show();

    }

    protected void setDate(int year, int month, int day) {
        final EditText txtDate = (EditText) findViewById(R.id.productionDateEditText);
        String dateStr = day + "/"
                + (month + 1) + "/" + year;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = df.parse(dateStr);
            pitchContext.getYeast().setProductionDate(date);
            txtDate.setText(dateStr);

        } catch (ParseException e) {
            Log.e("Error setting date", e.toString());
        }
    }

    protected void setViability() {
        Yeast yeast = pitchContext.getYeast();
        int via = Calculator.calculateYeastViability(yeast.getProductionDate());
        yeast.setViability(via);
        ((EditText) findViewById(R.id.viabilityEditText)).setText(via + "");
    }

    private String getDateString(Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int monthOfYear = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth + "/"
                + (monthOfYear + 1) + "/" + year;
    }

}
