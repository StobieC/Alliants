package com.alliants.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alliants.R;
import com.alliants.controller.Controller;
import com.alliants.datapassing.parceable.DetailInformation;
import com.alliants.model.interfaces.IContact;
import com.alliants.model.model.Contact;
import com.alliants.model.model.ContactAdapter;
import com.alliants.model.utilities.Utils;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends Activity implements IContact {

	private Controller controller = null;
	private ListView contactListView = null;
	private ArrayAdapter<Contact> adapter = null;
	private final List<Contact> contacts = new ArrayList<Contact>();

		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initialize();

		contactListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Contact tempContact = (Contact) arg0.getAdapter().getItem(arg2);
				// Creating an instance of Student class with user input data
				DetailInformation detailInfo = new DetailInformation(tempContact.getFirstName(),
										(tempContact.getLastName()),
										(tempContact.getAddress()),
										(tempContact.getPhoneNumber()),
										(tempContact.getEmail()),
										(tempContact.getcreatedAt()),
										(tempContact.getupdatedAt()));
				// Starting Detail Activity Screen
				Intent in = new Intent(getApplicationContext(),DetailActivity.class);
				// Passing data as a parecelable object to StudentViewActivity
				in.putExtra("detailInfo",detailInfo);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				startActivity(in);
			}
		});

		retrieveData();

	}

	private void retrieveData() {

		clear();

		if (Utils.isNetworkAvailable(MainActivity.this)) {
			controller.reset();
			controller.parseJson(this, MainActivity.this);

			Crouton.makeText(MainActivity.this,"Network Available...", Style.INFO).show();

		} else {
			Toast.makeText(MainActivity.this,
					"No Network Available! Fetching Data from Database...",
					Toast.LENGTH_LONG).show();
			Crouton.makeText(MainActivity.this,"Sorry there is No Network...", Style.ALERT).show();

			for (Contact contact : controller.getContacts()) {
			contacts.add(contact);
			adapter.notifyDataSetChanged();
			}
		}

	}

	private void initialize() {
		controller = Controller
				.getInstanceUsingDoubleLocking(MainActivity.this);
		contactListView = (ListView) MainActivity.this
				.findViewById(R.id.listView);

		adapter = new ContactAdapter(MainActivity.this, contacts);
		contactListView.setAdapter(adapter);
	}

	@Override
	public void appendContact(Contact contact) {

		controller.saveContact(contact);
		contact.setId(contact.getId().replace(
				contact.getFirstName() + contact.getLastName(), ""));
		contacts.add(contact);
		adapter.notifyDataSetChanged();
	}

	private void clear() {
		if (!contacts.isEmpty()) {
			contacts.clear();
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.reload:
			retrieveData();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public int installationListCount() {
		return contactListView.getCount();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		Crouton.cancelAllCroutons();
	}

}
