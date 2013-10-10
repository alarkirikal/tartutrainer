package com.tartutrainer.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter extends SQLiteOpenHelper {

	private static String DB_PATH = "";
	private static final String DB_NAME = "tartutrainer.db";
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	private static DBAdapter mDBConnection;

	private DBAdapter(Context context) {
		super(context, DB_NAME, null, 3);
		this.myContext = context;
        DB_PATH = context.getFilesDir().getParentFile().getPath() + "/databases/";
        Log.d("DB_PATH", DB_PATH);

	}

	public static synchronized DBAdapter getDBAdapterInstance(Context context) {
		if (mDBConnection == null) {
			mDBConnection = new DBAdapter(context);
		}
		return mDBConnection;
	}

	public void createDataBase() throws IOException {
		Log.d("db", "if exists");
		boolean dbExist = doesDatabaseExist((ContextWrapper) this.myContext,
				DB_NAME);
		Log.d("db", "boolean found");
		if (!dbExist) {
			Log.d("db", "DOESNT EXIST");;
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		} else {
			/** Database already exists */
			Log.d("DATABASE", "DB Existed");
			//copyDataBase();

		}
	}

	// Check database existence \\
	private static boolean doesDatabaseExist(ContextWrapper context,
			String dbName) {
		File dbFile = context.getDatabasePath(dbName);
		return dbFile.exists();
	}

	/*
	 * Takes the database from local filesystem and copies it to the one, that
	 * the application will actually use.
	 */
	private void copyDataBase() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);

		// Transfer \\
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void openDataBase() throws SQLException {
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}

	// Not sure if necessary, too lazy to check \\
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}