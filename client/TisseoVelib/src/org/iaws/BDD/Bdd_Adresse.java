package org.iaws.BDD;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Bdd_Adresse {
	
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "tisseoVelib.db";

	private static final String TABLE_ADRESSE = "table_adresse";
	private static final String COL_ID = "id";
	private static final String COL_ADRESSE = "adresse";
	
	
	private SQLiteDatabase bdd;

	private BaseAdresse base_msg;
	
	private static final String CREATE_BDD = "CREATE TABLE IF NOT EXISTS " + TABLE_ADRESSE + " ("
			+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +  COL_ADRESSE + "TEXT NOT NULL);";
	
	public Bdd_Adresse(Context context){
		base_msg = new BaseAdresse(context, NOM_BDD, null, VERSION_BDD);
		
		bdd = base_msg.getWritableDatabase();
		bdd.execSQL(CREATE_BDD);
		bdd.close();
		
	}
	
	/* Ouverture de la BDD en écriture */
	public void open(){
		bdd = base_msg.getWritableDatabase();
	}
	
	/* Fermeture de la BDD */
	public void close() {
		bdd.close();
	}

	/* On récupère la BDD */
	public SQLiteDatabase getBDD() {
		return bdd;
	}
	
	
	/* Insertion d'une adresse */
	public long insert_adresse(String adresse) {
		this.open();
		long res;
		try {
			ContentValues values = new ContentValues();		
			values.put(COL_ADRESSE, adresse);
			res = bdd.insert(TABLE_ADRESSE, null, values);
		} catch (Exception e) {
			res = 0;
		}
		this.close();
		return res;
	}
	
	public String get_adresse(){
		this.open();
		
		String res = null;
		String req = "SELECT adresse FROM table_adresse WHERE id = (SELECT MAX(id) FROM table_adresse)";
		Cursor c = bdd.rawQuery(req ,null);
		
		if (c.moveToFirst()) {
			res = c.getString(0);
        }
		this.close();
		
		return res;
	}
	
}
