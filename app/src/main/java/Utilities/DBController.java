package Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import Models.TagItem;

public class DBController  extends SQLiteOpenHelper {
	private static final String LOGCAT = null;

	public DBController(Context applicationcontext) {
		super(applicationcontext, "tagssqlite.db", null, 1);
		Log.d(LOGCAT, "Created");
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;
		query = "CREATE TABLE tags ( tagID TEXT PRIMARY KEY, tagName TEXT)";
		database.execSQL(query);
		Log.d(LOGCAT,"tags Created");
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS tags";
		database.execSQL(query);
		onCreate(database);
	}

	public void insertTag(TagItem tagItem) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues contentValue = new ContentValues();
		contentValue.put("tagID", tagItem.getTagID());
		contentValue.put("tagName", tagItem.getTagName());
		database.insert("tags", null, contentValue);
		database.close();
	}

	public ArrayList<TagItem> getAllTags() {
		ArrayList<TagItem> tagsList = new ArrayList<TagItem>();
		String selectQuery = "SELECT  * FROM tags";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				tagsList.add(new TagItem(cursor.getString(0), cursor.getString(1)));
			} while (cursor.moveToNext());
		}
		return tagsList;
	}

	public void deleteAllTags() {
		Log.d(LOGCAT,"delete");
		SQLiteDatabase database = this.getWritableDatabase();	 
		String deleteQuery = "delete from tags";
		Log.d("query",deleteQuery);		
		database.execSQL(deleteQuery);
	}

}