package ca.bcit.ass3.brotonel_chen.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ca.bcit.ass3.brotonel_chen.database.DatabaseHelper;

/**
 * Dao class is to create basic create, update, and delete operations with SQLite
 *
 * Created by Jason on 04-Nov-2017.
 */

public abstract class Dao {
    private static final String TAG = Dao.class.getSimpleName();
    private static final String WHERE = " = ?";

    private static int count = 0;

    protected final String mTableName;
    protected DatabaseHelper dbHelper;

    protected Dao(Context context, String tableName) {
        this.mTableName = tableName;
        if (dbHelper == null) {
            dbHelper = DatabaseHelper.getInstance(context);
        }
        count++;
    }

    /**
     * Insert data to the table.
     *
     * @param values - data of the record.
     * @return success - true if insert successfully.
     */
    protected boolean insert(ContentValues values) {
        boolean success = false;
        try {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            long result = sqLiteDatabase.insert(mTableName, null, values);
            sqLiteDatabase.close();
            Log.d(TAG, "Insert " + result + " row");
            success = result > 0;
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return success;
    }

    /**
     * Update data from the table.
     *
     * @param columnName - name of the column.
     * @param args - arguments.
     * @param values - data of the record.
     * @return success - true if update successfully.
     */
    protected boolean update(String columnName, String[] args, ContentValues values) {
        boolean success = false;
        try {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            long result = sqLiteDatabase.update(mTableName, values, columnName + WHERE, args);
            sqLiteDatabase.close();
            Log.d(TAG, "Update " + result + " row");
            success = result > 0;
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return success;
    }

    /**
     * Delete data from the table.
     *
     * @param columnName - name of the column.
     * @param args - arguments.
     * @return success - true if delete successfully.
     */
    protected boolean delete(String columnName, String[] args) {
        boolean success = false;
        try {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            int result = sqLiteDatabase.delete(mTableName, columnName + WHERE, args);
            sqLiteDatabase.close();
            Log.d(TAG, "Delete " + result + " row");
            success = result > 0;
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        return success;
    }

    /**
     * Close SQLite connection.
     * Only call this function onDestroy.
     */
    public void close() {
        try {
            if (dbHelper != null) {
                dbHelper.close();
                dbHelper = null;
                Log.d(TAG, "Close SQLite connection");
                count--;
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static int getCount() {
        return count;
    }
}
