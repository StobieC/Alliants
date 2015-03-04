package com.alliants.model.model;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alliants.R;

public class ContactAdapter extends ArrayAdapter<Contact> {

	private List<Contact> contacts = null;
	private Context context = null;

	public ContactAdapter(Context context, List<Contact> contacts) {
		super(context, R.layout.contact_row, contacts);
		this.contacts = contacts;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(
					R.layout.contact_row, viewGroup, false);

			holder = new ViewHolder();
			holder.firstName = (TextView) convertView
					.findViewById(R.id.firstName);
			holder.lastName = (TextView) convertView
					.findViewById(R.id.lastName);
			convertView.setTag(holder);
			convertView.setTag(R.id.firstName, holder.firstName);
			convertView.setTag(R.id.lastName, holder.lastName);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Contact currentContact = contacts.get(position);
		holder.firstName.setText(currentContact.getFirstName()+ " ");
		holder.lastName.setText(currentContact.getLastName());
		return convertView;
	}

	public static class ViewHolder {
		public TextView  firstName, lastName;

	}
}
