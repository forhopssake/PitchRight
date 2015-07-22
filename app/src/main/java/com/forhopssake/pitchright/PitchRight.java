package com.forhopssake.pitchright;

import com.forhopssake.pitchright.context.PitchContext;
import com.forhopssake.pitchright.context.PitchContextFactory;
import com.forhopssake.pitchright.context.TargetType;
import com.forhopssake.pitchright.context.VolumeUnits;
import com.forhopssake.pitchright.context.Wort;
import com.forhopssake.pitchright.res.PropertyLoader;
import com.forhopssake.pitchright.util.Calculator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
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
        EditText editText = (EditText) findViewById(R.id.pitchRate);
        editText.setOnKeyListener(this);

        EditText volume = (EditText) findViewById(R.id.batchVolumeEditText);
        volume.setOnKeyListener(this);

        Spinner volumeUnits = (Spinner) findViewById(R.id.batchVolumeSpinner);
        staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.volumneUnits_items,
                        android.R.layout.simple_spinner_item);
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        volumeUnits.setAdapter(staticAdapter);
        volumeUnits.setOnItemSelectedListener(this);

        // init help


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
        String og = ogFormat.format(wort.getOriginalGravity());

        ((EditText) findViewById(R.id.batchVolumeEditText)).setText(wort.getBatchVolume() + "");
        ((EditText) findViewById(R.id.originalGravityEditText)).setText(og);

        double yeastRequired = Calculator.calculateYeastRequired(wort.getTargetPitchRate(),
                wort.getBatchVolume(),
                wort.getOriginalGravity(),
                wort.getCellOverbuild(),
                wort.isGallons());
        ((EditText) findViewById(R.id.requiredCellsEditText)).setText(yeastRequired + "");
    }
    // event handlers

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       switch  (parent.getId()) {
           case R.id.targetPitchRateSpinner: targetPitchRateChanged(parent);
               break;
           case R.id.batchVolumeSpinner:
               volumneUnitsChanged(parent);
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

    private double getCustomPitchRate() {
        EditText v = (EditText) findViewById(R.id.pitchRate);
        Editable edit = v.getText();
        double val = Double.parseDouble(edit.toString());
        return val;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
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
}
