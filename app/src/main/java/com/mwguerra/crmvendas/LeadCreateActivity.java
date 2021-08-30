package com.mwguerra.crmvendas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputEditText;

public class LeadCreateActivity extends AppCompatActivity {

  protected TextInputEditText editNewName;
  protected TextInputEditText editNewEmail;
  protected TextInputEditText editNewStatus;
  protected TextInputEditText editNewScore;
  protected CheckBox checkBoxNewVip;
  protected Button buttonCreateLead;
  protected Intent i;
  protected DatabaseDriver dbDriver;
  protected DatabaseLeadService dbLeadService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lead_create);

    editNewName = findViewById(R.id.editNewName);
    editNewEmail = findViewById(R.id.editNewEmail);
    editNewStatus = findViewById(R.id.editNewStatus);
    editNewScore = findViewById(R.id.editNewScore);
    checkBoxNewVip = findViewById(R.id.checkBoxNewVip);
    buttonCreateLead = findViewById(R.id.buttonCreateLead);

    ////////////////////////////////////////
    // Database setup
    dbDriver = new DatabaseDriver(this);
    dbLeadService = new DatabaseLeadService(dbDriver);

    i = getIntent();

    ////////////////////////////////////////
    // Toolbar
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("New Lead");

    buttonCreateLead.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String newName = editNewName.getText().toString();
        String newEmail = editNewEmail.getText().toString();
        String newStatus = editNewStatus.getText().toString();
        String newScore = editNewScore.getText().toString();

        if ((newName == null  || newName.equals("")) || (newEmail == null || newEmail.equals(""))) {
          return;
        }

        if (newStatus == null || newStatus.equals("")) {
          newStatus = "Prospecção";
        }

        if (newScore == null || newScore.equals("")) {
          newScore = "0";
        }

        dbLeadService.create(
          newName,
          newEmail,
          newStatus,
          Integer.parseInt(newScore),
          checkBoxNewVip.isChecked()
        );

        editNewName.setText("");
        editNewEmail.setText("");
        editNewStatus.setText("");
        editNewScore.setText("");
        checkBoxNewVip.setChecked(false);

        Intent i = new Intent();
        setResult(Activity.RESULT_OK, i);
        finish();
      }
    });

  }
}