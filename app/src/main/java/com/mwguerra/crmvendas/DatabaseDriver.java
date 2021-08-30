package com.mwguerra.crmvendas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDriver extends SQLiteOpenHelper {

  private static String databaseName = "crmvendas.db";
  private static int databaseVersion = 1;

  private String[] sqlQueriesOnCreate = {
    "CREATE TABLE leads (id INTEGER NOT NULL UNIQUE, name TEXT, email TEXT, status TEXT, score INTEGER, is_vip INTEGER, PRIMARY KEY(id AUTOINCREMENT));",
    "INSERT INTO leads ('name', 'email', 'status', 'score', 'is_vip') VALUES ('João da Silva', 'joao@silva.br', 'Frio', 430, 0)",
    "INSERT INTO leads ('name', 'email', 'status', 'score', 'is_vip') VALUES ('Carlos Fernandes', 'carlos@fernandes.br', 'Boleto Emitido', 210, 0)",
    "INSERT INTO leads ('name', 'email', 'status', 'score', 'is_vip') VALUES ('Pedro Souza', 'pedro@souza.br', 'Comprou', 100, 1)",
  };

  private String[] sqlQueriesOnDrop = {
    "DROP TABLE IF EXISTS leads;"
  };

  public DatabaseDriver(@Nullable Context context) {
    super(context, databaseName, null, databaseVersion);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    for (String sqlQuery : sqlQueriesOnCreate) {
      db.execSQL(sqlQuery);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    databaseVersion++;
    for (String sqlQuery : sqlQueriesOnDrop) {
      db.execSQL(sqlQuery);
    }
    onCreate(db);
  }
}