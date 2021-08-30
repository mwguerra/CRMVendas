package com.mwguerra.crmvendas;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  DatabaseDriver dbDriver;
  DatabaseLeadService dbLeadService;
  ListView listViewLeads;
  List<Lead> leads;
  Button buttonNewLead;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    dbDriver = new DatabaseDriver(this);
    dbLeadService = new DatabaseLeadService(dbDriver);
    leads = new ArrayList<>();
    listViewLeads = findViewById(R.id.listViewLeads);
    buttonNewLead = findViewById(R.id.buttonNewLead);

    ////////////////////////////////////////
    // Retornos de activities com ações

    ActivityResultLauncher<Intent> launchLeadCreateActivity = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
      new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
          if (result.getResultCode() == Activity.RESULT_OK) {
            loadLeadListFromDatabase();
            Intent data = result.getData();
            Toast.makeText(getApplicationContext(),"Lead criado!",Toast.LENGTH_SHORT).show();
          }
        }
      });

    ActivityResultLauncher<Intent> launchLeadEditActivity = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
      new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
          if (result.getResultCode() == Activity.RESULT_OK) {
            loadLeadListFromDatabase();
            Intent i = result.getData();
            String action = i.getExtras().getString("action");
            String leadName = i.getExtras().getString("leadName");

            if (action.equals("updated")) {
              Toast.makeText(getApplicationContext(),leadName + " atualizado!", Toast.LENGTH_SHORT).show();
            }

            if (action.equals("deleted")) {
              Toast toast = Toast.makeText(getApplicationContext(),leadName + " excluído!", Toast.LENGTH_SHORT);
              toast.show();
            }
          }
        }
      });

    ////////////////////////////////////////
    // Cliques em botões e em list view items

    buttonNewLead.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, LeadCreateActivity.class);
        launchLeadCreateActivity.launch(i);
      }
    });

    listViewLeads.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(MainActivity.this, LeadEditActivity.class);
        i.putExtra("lead_id", leads.get(position).getId());
        launchLeadEditActivity.launch(i);
      }
    });

    loadLeadListFromDatabase();
  }

  private void loadLeadListFromDatabase() {
    leads = dbLeadService.index();

    ArrayAdapter<Lead> adapter = new ArrayAdapter<>(
        this,
        android.R.layout.simple_list_item_1,
        leads
    );

    listViewLeads.setAdapter(adapter);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    return super.onOptionsItemSelected(item);
  }
}