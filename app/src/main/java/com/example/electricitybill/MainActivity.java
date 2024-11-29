package com.example.electricitybill;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    EditText etCharge;
    EditText etRebate;
    Button btnCalculate;
    Button btnClear;
    TextView tvBefore;
    TextView tvAfter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etCharge = findViewById(R.id.etCharge);
        etRebate = findViewById(R.id.etRebate);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        tvAfter = findViewById(R.id.tvAfter);
        tvBefore = findViewById(R.id.tvBefore);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBill();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

    }

    private void calculateBill() {
        String unitsInput = etCharge.getText().toString();
        String rebateInput = etRebate.getText().toString();

        if (unitsInput.isEmpty() || rebateInput.isEmpty()) {
            Toast.makeText(this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        int units = Integer.parseInt(unitsInput);
        double rebate = Double.parseDouble(rebateInput);

        if (rebate < 0 || rebate > 5) {
            Toast.makeText(this, "Rebate percentage must be between 0 and 5!", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalCharges = 0;

        if (units <= 200) {
            totalCharges = units * 0.218;
        } else if (units <= 300) {
            totalCharges = (200 * 0.218) + ((units - 200) * 0.334);
        } else if (units <= 600) {
            totalCharges = (200 * 0.218) + (100 * 0.334) + ((units - 300) * 0.516);
        } else {
            totalCharges = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((units - 600) * 0.546);
        }

        double rebateAmount = totalCharges * (rebate / 100);
        double finalCharges = totalCharges - rebateAmount;

        String result = String.format("Total Charges: RM %.2f\nRebate: RM %.2f\nFinal Cost: RM %.2f", totalCharges, rebateAmount, finalCharges);

        tvAfter.setText(result);
    }

    private void clearFields() {
        etCharge.setText("");
        etRebate.setText("");
        tvAfter.setText("Amount");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selected = item.getItemId();

        if (selected == R.id.menuAbout) {
            Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}