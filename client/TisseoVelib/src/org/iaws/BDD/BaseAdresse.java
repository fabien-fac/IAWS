package org.iaws.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseAdresse extends SQLiteOpenHelper{
	
	private static final String TABLE_ADRESSE = "table_adresse";
	private static final String COL_ID = "id";
	private static final String COL_ADRESSE = "adresse";
	
	private static final String CREATE_BDD = "CREATE TABLE IF NOT EXISTS " + TABLE_ADRESSE + " ("
			+ COL_ID + " INTEGER PRIMARY KEY, " +  COL_ADRESSE + " TEXT NOT NULL);";

	public BaseAdresse(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BDD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + TABLE_ADRESSE + ";");
		onCreate(db);
	}

}
