package com.mwguerra.crmvendas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LeadEditActivity extends AppCompatActivity {
  TextInputEditText editUpdateName;
  TextInputEditText editUpdateEmail;
  TextInputEditText editUpdateStatus;
  TextInputEditText editUpdateScore;
  CheckBox checkBoxUpdateVip;
  Button buttonUpdateLead;
  Button buttonDeleteLead;
  Intent i;
  DatabaseDriver dbDriver;
  DatabaseLeadService dbLeadService;
  Long lead_id;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lead_edit);

    this.editUpdateName = findViewById(R.id.editUpdateName);
    this.editUpdateEmail = findViewById(R.id.editUpdateEmail);
    this.editUpdateStatus = findViewById(R.id.editUpdateStatus);
    this.editUpdateScore = findViewById(R.id.editUpdateScore);
    this.checkBoxUpdateVip = findViewById(R.id.checkBoxUpdateVip);
    this.buttonUpdateLead = findViewById(R.id.buttonUpdateLead);
    this.buttonDeleteLead = findViewById(R.id.buttonDeleteLead);

    ////////////////////////////////////////
    // Database setup
    this.dbDriver = new DatabaseDriver(this);
    this.dbLeadService = new DatabaseLeadService(this.dbDriver);

    ////////////////////////////////////////
    // Load Lead to Edit
    i = getIntent();
    this.lead_id = i.getExtras().getLong("lead_id");
    this.loadLead(lead_id);

    ////////////////////////////////////////
    // Toolbar
    getSupportActionBar().setTitle("Update Lead");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    buttonUpdateLead.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String newName = editUpdateName.getText().toString();
        String newEmail = editUpdateEmail.getText().toString();
        String newStatus = editUpdateStatus.getText().toString();
        String newScore = editUpdateScore.getText().toString();

        if ((newName == null  || newName.equals("")) || (newEmail == null || newEmail.equals(""))) {
          return;
        }

        if (newStatus == null || newStatus.equals("")) {
          newStatus = "Prospecção";
        }

        if (newScore == null || newScore.equals("")) {
          newScore = "0";
        }

        dbLeadService.update(
          lead_id,
          newName,
          newEmail,
          newStatus,
          Integer.parseInt(newScore),
          checkBoxUpdateVip.isChecked()
        );

        editUpdateName.setText("");
        editUpdateEmail.setText("");
        editUpdateStatus.setText("");
        editUpdateScore.setText("");
        checkBoxUpdateVip.setChecked(false);

        Intent i = new Intent();
        i.putExtra("action", "updated");
        i.putExtra("leadName", newName);
        setResult(Activity.RESULT_OK, i);
        finish();
      }
    });

    buttonDeleteLead.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dbLeadService.delete(lead_id);

        editUpdateName.setText("");
        editUpdateEmail.setText("");
        editUpdateStatus.setText("");
        editUpdateScore.setText("");
        checkBoxUpdateVip.setChecked(false);

        String newName = editUpdateName.getText().toString();

        Intent i = new Intent();
        i.putExtra("action", "deleted");
        i.putExtra("leadName", newName);
        setResult(Activity.RESULT_OK, i);
        finish();
      }
    });
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  protected Lead loadLead(Long id) {
    Lead lead = this.dbLeadService.show(id);

    if (lead == null) {
      Toast.makeText(this, "Erro ao carregar o lead", Toast.LENGTH_SHORT).show();

      setResult(1, i);
      finish();
    }

    assert lead != null;
    editUpdateName.setText(lead.getName());
    editUpdateEmail.setText(lead.getEmail());
    editUpdateStatus.setText(lead.getStatus());
    editUpdateScore.setText(String.format("%d", lead.getScore()));
    checkBoxUpdateVip.setChecked(lead.getVip());

    return lead;
  }
}