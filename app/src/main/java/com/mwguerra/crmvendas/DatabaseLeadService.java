package com.mwguerra.crmvendas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseLeadService {
  protected DatabaseDriver dbInstance;
  protected String databaseTable = "leads";

  public DatabaseLeadService(DatabaseDriver dbInstance) {
    this.dbInstance = dbInstance;
  }

  ////////////////////////////////////////
  // Index
  public List<Lead> index() {
    SQLiteDatabase db = dbInstance.getReadableDatabase();
    Cursor c = db.rawQuery("SELECT * FROM " + databaseTable, null);
    List<Lead> leads = new ArrayList<>();
    c.moveToFirst();

    if (c.getCount() > 0) {
      do {
        Long id = c.getLong(c.getColumnIndex("id"));
        String name = c.getString(c.getColumnIndex("name"));
        String email = c.getString(c.getColumnIndex("email"));
        String status = c.getString(c.getColumnIndex("status"));
        Integer score = c.getInt(c.getColumnIndex("score"));
        Boolean isVip = c.getInt(c.getColumnIndex("is_vip")) > 0;

        Lead lead = new Lead(id, name, email, status, score, isVip);

        leads.add(lead);
      } while (c.moveToNext());
    }

    return leads;
  }

  ////////////////////////////////////////
  // Create
  public long create(String name, String email, String status, Integer score, Boolean isVip) {
    SQLiteDatabase db = dbInstance.getWritableDatabase();
    ContentValues cv = new ContentValues();

    cv.put("name", name);
    cv.put("email", email);
    cv.put("status", status);
    cv.put("score", score);
    cv.put("is_vip", isVip ? 1 : 0);

    return db.insert(databaseTable, null, cv);
  }

  ////////////////////////////////////////
  // Show
  public Lead show(Long id) {
    SQLiteDatabase db = dbInstance.getReadableDatabase();
    Cursor c = db.rawQuery(
      "SELECT * FROM " + databaseTable + " WHERE id=?",
      new String[]{String.valueOf(id)}
    );
    c.moveToFirst();

    if (c.getCount() == 1) {
      String name = c.getString(c.getColumnIndex("name"));
      String email = c.getString(c.getColumnIndex("email"));
      String status = c.getString(c.getColumnIndex("status"));
      Integer score = c.getInt(c.getColumnIndex("score"));
      Boolean isVip = c.getInt(c.getColumnIndex("is_vip")) > 0;

      return new Lead(id, name, email, status, score, isVip);
    }
    return null;
  }

  ////////////////////////////////////////
  // Update
  public long update(Long id, String name, String email, String status, Integer score, Boolean isVip) {
    SQLiteDatabase db = dbInstance.getWritableDatabase();
    ContentValues cv = new ContentValues();

    cv.put("name", name);
    cv.put("email", email);
    cv.put("status", status);
    cv.put("score", score);
    cv.put("is_vip", isVip ? 1 : 0);

    return db.update(databaseTable, cv, "id=?", new String[]{String.valueOf(id)});
  }

  ////////////////////////////////////////
  // Delete
  public long delete(Long id) {
    SQLiteDatabase db = dbInstance.getWritableDatabase();

    return db.delete(databaseTable, "id=?", new String[]{String.valueOf(id)});
  }
}
