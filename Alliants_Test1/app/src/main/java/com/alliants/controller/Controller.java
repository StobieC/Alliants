package com.alliants.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;

import com.alliants.model.database.Database;
import com.alliants.model.interfaces.IContact;
import com.alliants.model.model.Contact;
import com.alliants.model.utilities.Utils;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class Controller {

	private static Controller instance;
	private ProgressDialog progressDialog = null;
	private Database model = null;
	private Context context = null;
	private RequestQueue queue = null;
	private IContact contactInterface = null;
	private Contact contact = null;

	private Controller(Context context) {
		model = new Database(context);
		this.context = context;
	}

	/**
	 * This method takes no parameter and double checked locking principle is
	 * used. In this approach, the synchronized block is used inside if
	 * condition with an additional check to ensure that only one instance of
	 * singleton class is created.
	 *
	 * @return Controller
	 */

	public static Controller getInstanceUsingDoubleLocking(Context context) {
		if (instance == null) {
			synchronized (Controller.class) {
				if (instance == null) {
					instance = new Controller(context);
				}
			}
		}
		return instance;
	}

	public void saveContact(Contact contact) {
		model.addContact(contact);
	}

	public void deleteContact(Contact contact) {
		model.removeContact(contact);
	}

	public ArrayList<Contact> getContacts() {

		ArrayList<Contact> contacts = model.getContacts();

		return contacts;
	}

	public void reset() {
		model.emptyTables();
	}

	public void parseJson(IContact iContact, Context dialogContext) {

		displayDialog(dialogContext);
		this.contactInterface = iContact;

		queue = Volley.newRequestQueue(context);
		JsonArrayRequest Jreq = new JsonArrayRequest(Utils.SITE_UL,
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray jsonArry) {
						for (int i = 0; i < jsonArry.length(); i++) {

							JSONObject obj = null;
							contact = new Contact();
							try {
								obj = jsonArry.getJSONObject(i);

							} catch (JSONException e1) {
							}
							try {

								contact.setFirstName(obj.getString("first_name"));
								contact.setLastName(obj.getString("surname"));
								contact.setAddress(obj.getString("address"));
								contact.setPhoneNumber(obj.getString("phone_number"));
								contact.setEmail(obj.getString("email"));
				 				contact.setId(obj.getString("id"));
								contact.setcreatedAt(obj.getString("createdAt"));
								contact.setupdatedAt(obj.getString("updatedAt"));
								contactInterface.appendContact(contact);
							} catch (JSONException e) {
							}
						}
						hideDialog();
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						hideDialog();
					}

				});

		queue.add(Jreq);
	}

	private void displayDialog(Context context) {

		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("Please Wait ...");
		progressDialog.setMessage("Downloading Contacts ...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);

		progressDialog.show();
	}

	private void hideDialog() {
		progressDialog.dismiss();
	}
}
