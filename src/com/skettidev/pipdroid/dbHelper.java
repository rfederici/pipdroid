package com.skettidev.pipdroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class dbHelper extends SQLiteOpenHelper {

	static final String dbName = "Items";

	static final String weaponsTable = "Weapons";
	static final String apparelTable = "Apparel";
	static final String ownedWeaponsTable = "OwnedWeapons";
	static final String ownedApparelTable = "OwnedApparel";
	
	static final String colID = BaseColumns._ID;
	static final String colName = "WeaponName";
	static final String colDam = "WeaponDamage";
	static final String colDT = "DamageThreshold";
	static final String colCost = "WeaponCost";
	static final String colIsWearing = "IsWearing";
	static final String colType = "Type";
	static final String colWG = "Weight";
	
	static final int HEAD = 1;
	static final int EYES = 2;
	static final int FULL_HEAD = 3;
	static final int BODY = 4;

	static final String viewEmps = "ViewEmps";

	public dbHelper(Context context) {
		super(context, dbName, null, 2);
		Log.d("DB", "Created the database!");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		  // TODO Auto-generated method stub
		Log.d("DB", db.getPath() + "Making the tables!");
		  
		// Weapon databases
		  db.execSQL("CREATE TABLE " + weaponsTable + " (" +
				  colID + " INT, " +
				  colName + " TEXT, " +
				  colDam + " INT, " +
				  colCost + " INT, " +
				  colWG + " INT);");
		  
		  db.execSQL("CREATE TABLE " + ownedWeaponsTable + " (" +
				  colID + " INT, " +
				  colName + " TEXT, " +
				  colDam + " INT, " +
				  colCost + " INT, " +
				  colIsWearing + " INT, " +
				  colWG + " INT);");
		  
		  // Apparel databases
		  db.execSQL("CREATE TABLE " + apparelTable + " (" +
				  colID + " INT, " +
				  colName + " TEXT, " +
				  colDT + " INT, " +
				  colType + " INT, " +
				  colCost + " INT, " +
				  colWG + " INT);");
		  
		  db.execSQL("CREATE TABLE " + ownedApparelTable + " (" +
				  colID + " INT, " +
				  colName + " TEXT, " +
				  colDT + " INT, " +
				  colType + " INT, " +
				  colCost + " INT, " +
				  colIsWearing + " INT, " +
				  colWG + " INT);");
		  
		  Log.d("DB", "Tables made! Filling...");
		  
		  // Fill the tables
		  fillWeaponsTable(db);
		  fillApparelTable(db);
		  Log.d("DB", "Filled!");
		  
	}

	private void fillWeaponsTable(SQLiteDatabase db){
		addToWeapTable(db, 0, "9mm Pistol", 10, 40, 3);
		addToWeapTable(db, 1, "Brass Knuckles", 7, 20, 1);
		addToWeapTable(db, 2, ".44 Magnum", 50, 200, 2);
		addToWeapTable(db, 3, "Baseball Bat", 8, 20, 3);
		addToWeapTable(db, 4, "Golf Club", 7, 20, 3);
		addToWeapTable(db, 5, "10mm Pistol", 20, 50, 3);
		addToWeapTable(db, 6, "Sniper Rifle", 70, 420, 8);
		addToWeapTable(db, 7, "Incinerator", 160, 2390, 15);
		addToWeapTable(db, 8, "Minigun", 165, 2900, 15);
		addToWeapTable(db, 9, "Fat Man", 180, 4650, 18);
		
		addToWeapTable(db, 7, "Incinerator", 160, 2390, 1, 15);
		addToWeapTable(db, 8, "Minigun", 165, 2900, 0, 15);
		addToWeapTable(db, 9, "Fat Man", 180, 4650, 0, 18);
	}
	private void fillApparelTable(SQLiteDatabase db){
		addToAppTable(db, 100, "Baseball Cap", 1, HEAD, 20, 1);
		addToAppTable(db, 110, "T-51b Power Helmet", 10, HEAD, 670, 5);
		addToAppTable(db, 111, "Enclave Power Helmet", 9, HEAD, 560, 5);
		addToAppTable(db, 200, "Biker Goggles", 1, EYES, 10, 1);
		addToAppTable(db, 201, "Eyeglasses", 1, EYES, 10, 1);
		addToAppTable(db, 202, "Shades", 1, EYES, 10, 1);
		addToAppTable(db, 300, "Hockey Mask", 3, FULL_HEAD, 40, 2);
		addToAppTable(db, 301, "Motorcycle Helmet", 5, FULL_HEAD, 50, 5);
		addToAppTable(db, 400, "Vault 101 Jumpsuit", 10, BODY, 420, 3);
		addToAppTable(db, 401, "Vault Lab Uniform", 5, BODY, 100, 3);
		addToAppTable(db, 410, "T-51b Power Armor", 50, BODY, 2100, 20);
		addToAppTable(db, 411, "Enclave Power Armor", 40, BODY, 2100, 20);
		
		addToAppTable(db, 100, "Baseball Cap", 1, HEAD, 20, 1, 1);
		addToAppTable(db, 110, "T-51b Power Helmet", 10, HEAD, 670, 0, 5);
		addToAppTable(db, 201, "Eyeglasses", 1, EYES, 10, 1, 1);
		addToAppTable(db, 202, "Shades", 1, EYES, 10, 0, 1);
		addToAppTable(db, 300, "Hockey Mask", 3, FULL_HEAD, 40, 0, 2);
		addToAppTable(db, 301, "Motorcycle Helmet", 5, FULL_HEAD, 50, 0, 5);
		addToAppTable(db, 400, "Vault 101 Jumpsuit", 10, BODY, 420, 1, 3);
		addToAppTable(db, 401, "Vault Lab Uniform", 5, BODY, 100, 0, 3);
	}
	
	public void addToWeapTable(SQLiteDatabase db, int ID, String name, int damDT, int cost, int wg) {
		db.execSQL(String.format("INSERT INTO Weapons VALUES (%d, '%s', %d, %d, %d);", ID, name, damDT, cost, wg));
	}
	
	public void addToWeapTable(SQLiteDatabase db, int ID, String name, int damDT, int cost, int isWearing, int wg) {
		db.execSQL(String.format("INSERT INTO OwnedWeapons VALUES (%d, '%s', %d, %d, %d, %d);", ID, name, damDT, cost, isWearing, wg));
	}
	
	public void addToAppTable(SQLiteDatabase db, int ID, String name, int damDT, int type, int cost, int wg) {
		db.execSQL(String.format("INSERT INTO Apparel VALUES (%d, '%s', %d, %d, %d, %d);", ID, name, damDT, type, cost, wg));
	}
	
	public void addToAppTable(SQLiteDatabase db, int ID, String name, int damDT, int type, int cost, int isWearing, int wg) {
		db.execSQL(String.format("INSERT INTO OwnedApparel VALUES (%d, '%s', %d, %d, %d, %d, %d);", ID, name, damDT, type, cost, isWearing, wg));
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
