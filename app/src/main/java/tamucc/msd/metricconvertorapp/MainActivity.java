package tamucc.msd.metricconvertorapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button temperatureBtn, currencyBtn, weightBtn, lengthBtn, convertBtn;
    EditText inputDataEditText;
    TextView textViewOne, textViewTwo, textViewThree, textViewFour, metricTitle;
    double inputValue;
    SharedPreferences sharedPreferences;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperatureBtn = findViewById(R.id.btnTemparature);
        currencyBtn = findViewById(R.id.btnCurrency);
        weightBtn = findViewById(R.id.btnWeight);
        lengthBtn = findViewById(R.id.btnLength);
        convertBtn = findViewById(R.id.btnConvert);
        inputDataEditText = findViewById(R.id.inputDataEditText);
        textViewOne = findViewById(R.id.textViewOne);
        textViewTwo = findViewById(R.id.textViewTwo);
        textViewFour = findViewById(R.id.textViewFour);
        textViewThree = findViewById(R.id.textViewThree);
        metricTitle = findViewById(R.id.metricTitle);
        temperatureBtn.setBackgroundColor(getResources().getColor(R.color.white));
        currencyBtn.setBackgroundColor(getResources().getColor(R.color.white));
        weightBtn.setBackgroundColor(getResources().getColor(R.color.white));
        lengthBtn.setBackgroundColor(getResources().getColor(R.color.white));
        setToDefault();
        clearTexts();
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        if(!sharedPreferences.getAll().isEmpty()) {
            String previouslySelectedBtn = sharedPreferences.getString("selectedBtn","temperature");
            double previouslyInputValue = Double.parseDouble(sharedPreferences.getString("inputValue", "0"));
            if(previouslySelectedBtn.equals("currency")){
                currencyBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                setCurrencyMetrics(previouslyInputValue);
            }
            else if(previouslySelectedBtn.equals("length")){
                setLengthMetrics(previouslyInputValue);
                lengthBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
            else if(previouslySelectedBtn.equals("weight")){
                setWeightMetrics(previouslyInputValue);
                weightBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
            else{
                setTemperatureMetrics(previouslyInputValue);
                temperatureBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
            inputDataEditText.setText(previouslyInputValue+"");
        } else {
            currencyBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        temperatureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToDefault();
                temperatureBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                clearTexts();
            }
        });
        currencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToDefault();
                currencyBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                clearTexts();
            }
        });
        weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToDefault();
                weightBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                clearTexts();
            }
        });
        lengthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToDefault();
                lengthBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                clearTexts();
            }
        });
       convertBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(inputDataEditText.getText().toString().isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Please enter valid input value...", Toast.LENGTH_SHORT).show();
                   clearTexts();
               } else {
                   try {
                       inputValue = Double.parseDouble(inputDataEditText.getText().toString());
                   } catch (NumberFormatException e) {
                       Toast.makeText(getApplicationContext(), "Please enter valid input value...", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   if (currencyBtn.getCurrentTextColor() == getResources().getColor(android.R.color.holo_red_dark)) {
                        setCurrencyMetrics(inputValue);
                        updateSharedPreferences("currency", inputValue);
                   } else if (lengthBtn.getCurrentTextColor() == getResources().getColor(android.R.color.holo_red_dark)) {
                        setLengthMetrics(inputValue);
                       updateSharedPreferences("length", inputValue);
                   } else if (weightBtn.getCurrentTextColor() == getResources().getColor(android.R.color.holo_red_dark)) {
                        setWeightMetrics(inputValue);
                        updateSharedPreferences("weight", inputValue);
                   } else {
                        setTemperatureMetrics(inputValue);
                        updateSharedPreferences("temperature", inputValue);
                   }
               }
           }
       });
    }

    private void updateSharedPreferences(String selectedBtn, double inputValue) {
        SharedPreferences.Editor editor = this.getPreferences(Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.putString("selectedBtn", selectedBtn);
        editor.putString("inputValue", String.valueOf(inputValue));
        editor.commit();
    }

    private void setTemperatureMetrics(double fahrenheit) {
        metricTitle.setText("Temperature Metrics");
        textViewOne.setText(String.format("%.2f", (float) ((fahrenheit-32) * (0.55))) + " degree celsius");
        textViewTwo.setText(String.format("%.2f", (float) ((fahrenheit-32) * (0.55)+(273.15))) + " kelvin");
        textViewOne.setVisibility(View.VISIBLE);
        textViewTwo.setVisibility(View.VISIBLE);
        metricTitle.setVisibility(View.VISIBLE);
    }

    private void setWeightMetrics(double pounds) {
        metricTitle.setText("Weight Metrics");
        textViewOne.setText(String.format("%.2f", pounds * Double.parseDouble(getResources().getString(R.string.kilogram_conversion))) + " kilograms");
        textViewTwo.setText(String.format("%.2f", pounds * Double.parseDouble(getResources().getString(R.string.gram_conversion)))+ " grams");
        textViewThree.setText(String.format("%.2f", pounds * Double.parseDouble(getResources().getString(R.string.ounces_conversion)))+ " ounces");
        textViewOne.setVisibility(View.VISIBLE);
        textViewTwo.setVisibility(View.VISIBLE);
        textViewThree.setVisibility(View.VISIBLE);
        metricTitle.setVisibility(View.VISIBLE);
    }

    private void setLengthMetrics(double miles) {
        metricTitle.setText("Length Metrics");
        textViewOne.setText(String.format("%.2f", miles * Double.parseDouble(getResources().getString(R.string.kilometers_conversion))) + " kilometers");
        textViewTwo.setText(String.format("%.2f", miles * Double.parseDouble(getResources().getString(R.string.meters_conversion)))+ " meters");
        textViewThree.setText(String.format("%.2f", miles * Double.parseDouble(getResources().getString(R.string.feet_conversion)))+ " feet");
        textViewFour.setText(String.format("%.2f", miles * Double.parseDouble(getResources().getString(R.string.inches_conversion)))+ " inches");
        textViewOne.setVisibility(View.VISIBLE);
        textViewTwo.setVisibility(View.VISIBLE);
        textViewThree.setVisibility(View.VISIBLE);
        textViewFour.setVisibility(View.VISIBLE);
        metricTitle.setVisibility(View.VISIBLE);
    }
    private void setCurrencyMetrics(double dollar) {
        metricTitle.setText("Currency Metrics");
        textViewOne.setText(String.format("%.2f", dollar * Double.parseDouble(getResources().getString(R.string.euro_conversion))) + " euro");
        textViewTwo.setText(String.format("%.2f", dollar * Double.parseDouble(getResources().getString(R.string.inr_conversion)))+ " indian rupee");
        textViewThree.setText(String.format("%.2f", dollar * Double.parseDouble(getResources().getString(R.string.australian_dollar_conversion)))+ " australian dollar");
        textViewFour.setText(String.format("%.2f", dollar * Double.parseDouble(getResources().getString(R.string.canadian_dollar_conversion)))+ " canadian dollar");
        textViewOne.setVisibility(View.VISIBLE);
        textViewTwo.setVisibility(View.VISIBLE);
        textViewThree.setVisibility(View.VISIBLE);
        textViewFour.setVisibility(View.VISIBLE);
        metricTitle.setVisibility(View.VISIBLE);
    }


    private void setToDefault() {
        temperatureBtn.setTextColor(getResources().getColor(R.color.black));
        currencyBtn.setTextColor(getResources().getColor(R.color.black));
        weightBtn.setTextColor(getResources().getColor(R.color.black));
        lengthBtn.setTextColor(getResources().getColor(R.color.black));

    }

    private void clearTexts() {
        metricTitle.setVisibility(View.INVISIBLE);
        textViewOne.setVisibility(View.INVISIBLE);
        textViewTwo.setVisibility(View.INVISIBLE);
        textViewThree.setVisibility(View.INVISIBLE);
        textViewFour.setVisibility(View.INVISIBLE);
    }
}