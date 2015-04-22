package com.alliants.datapassing.parceable;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailInformation implements Parcelable{

	public  String mfirst_name;
	public  String msurname;
	public  String mAddress;
	public  String mPhone;
	public  String mEmail;
	public  String mCreatedAt;
	public  String mUpdatedAt;

	@Override
	public int describeContents() {
		return 0;
	}

	/*
	 * Storing the Student data to Parcel object
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mfirst_name);
		dest.writeString(msurname);
		dest.writeString(mAddress);
		dest.writeString(mPhone);
		dest.writeString(mEmail);
		dest.writeString(mCreatedAt);
		dest.writeString(mUpdatedAt);

	}

	/*
	 * A constructor that initializes the Student object
	 */
	public DetailInformation(String cfirst_name, String csurname, String cAddress, String cPhone, String cEmail, String cCreatedAt, String cUpdatedAt){
		this.mfirst_name = cfirst_name;
		this.msurname = csurname;
		this.mAddress = cAddress;
		this.mPhone = cPhone;
		this.mEmail = cEmail;
		this.mCreatedAt = cCreatedAt;
		this.mUpdatedAt = cUpdatedAt;

	}

	/*
	 * Retrieving Student data from Parcel object
	 * This constructor is invoked by the method createFromParcel(Parcel source) of
	 * the object CREATOR
	 */
	private DetailInformation(Parcel in){
		this.mfirst_name = in.readString();
		this.msurname = in.readString();
		this.mAddress = in.readString();
		this.mPhone = in.readString();
		this.mEmail = in.readString();
		this.mCreatedAt = in.readString();
		this.mUpdatedAt = in.readString();
	}

	public static final Parcelable.Creator<DetailInformation> CREATOR = new Parcelable.Creator<DetailInformation>() {

		@Override
		public DetailInformation createFromParcel(Parcel source) {
			return new DetailInformation(source);
		}

		@Override
		public DetailInformation[] newArray(int size) {
			return new DetailInformation[size];
		}
	};
}