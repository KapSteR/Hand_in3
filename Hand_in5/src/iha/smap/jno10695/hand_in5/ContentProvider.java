package iha.smap.jno10695.hand_in5;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

public class ContentProvider extends android.content.ContentProvider {

	public static final Uri AUTHORITY = Uri
			.parse("content://iha.smap.jno10695.hand_in5.contentprovider");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView contactView = (TextView) findViewById(R.id.contactview);

		Cursor cursor = getContacts();

		while (cursor.moveToNext()) {

			String displayName = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
			contactView.append("Name: ");
			contactView.append(displayName);
			contactView.append("\n");
		}
	}

	private Cursor getContacts() {
		// Run query
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME };
		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
				+ ("1") + "'";
		String[] selectionArgs = null;
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		return getContentResolver().query(uri, projection, selection,
				selectionArgs, sortOrder);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}