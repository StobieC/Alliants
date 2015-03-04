package com.alliants.model.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alliants.model.model.Contact;

public class Database extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "alliants.db";
	private static final String TABLE_NAME = "contacts";

	private static final int DATABASE_VERSION = 3;
	private Contact contact = null;
	private SQLiteDatabase database = null;

	private static final String CREATE_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS `"
			+ TABLE_NAME
			+ "` ("
			+ "`id` varchar(250) DEFAULT NULL PRIMARY KEY,"
			+ "`first_name` varchar(250) DEFAULT NULL,"
			+ "`surname` varchar(250) DEFAULT NULL,"
			+ "`address` varchar(250) DEFAULT NULL,"
			+ "`phone_number` varchar(250) DEFAULT NULL,"
			+ "`email` varchar(250) DEFAULT NULL,"
			+ "`created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP);";

	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.database = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(CREATE_CONTACT_TABLE);

		} catch (SQLException msg) {
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

	public ArrayList<Contact> getContacts() throws SQLException {

		ArrayList<Contact> contactList = new ArrayList<Contact>();

		//String id, first_name, surname, address,phone_number,email;

		Cursor cur = database.query(true, TABLE_NAME, new String[] { "id",
				"first_name", "surname", "address",  "phone_number", "email" }, null, null, null, null,
				null, null);

		while (cur.moveToNext()) {

			contact = new Contact();
			contact.setId( cur.getString(cur.getColumnIndex("id")));
			contact.setFirstName(cur.getString(cur.getColumnIndex("first_name")));
			contact.setLastName(cur.getString(cur.getColumnIndex("surname")));
			contact.setAddress(cur.getString(cur.getColumnIndex("address")));
			contact.setPhoneNumber(cur.getString(cur.getColumnIndex("phone_number")));
			contact.setEmail(cur.getString(cur.getColumnIndex("email")));

			contactList.add(contact);
		}

		return contactList;
	}

	public boolean addContact(Contact contact) {
		boolean bool = false;

		try {
			database.execSQL("INSERT INTO " + TABLE_NAME + "(id, first_name,"
					+ "surname, address, phone_number, email) values ('" + contact.getId() + "','"
					+ contact.getFirstName() + "','" + contact.getLastName() + "','" + contact.getAddress()+ "','" + contact.getPhoneNumber()
					+ "','" + contact.getEmail() + "')");
			bool = true;
		} catch (SQLException e) {

		}
		return bool;
	}

	public void removeContact(Contact contact) {

		database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE id = '"
				+ contact.getId() + "'");
	}

	public void emptyTables() {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}

}
