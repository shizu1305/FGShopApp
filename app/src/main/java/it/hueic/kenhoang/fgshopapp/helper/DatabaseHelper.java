package it.hueic.kenhoang.fgshopapp.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.hueic.kenhoang.fgshopapp.object.Order;

public class DatabaseHelper extends SQLiteAssetHelper{
    private static final String DB_NAME = "fgshop";
    private static final int DB_VER = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public boolean checkCart(int id_user, int id_product) {
        boolean flag = false;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;
        String sqlQuery = String.format("SELECT * FROM Cart WHERE id_user = %d AND id_product = %d", id_user, id_product);
        cursor = db.rawQuery(sqlQuery, null);

        if (cursor.getCount() > 0) flag = true;
        else flag = false;

        cursor.close();
        return flag;
    }

    public List<Order> allCart(int id_user) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"id_user", "id_product", "quantity"};
        String sqlTable    = "Cart";

        qb.setTables(sqlTable);

        Cursor cursor = qb.query(db, sqlSelect, "id_user = ?", new String[] {String.valueOf(id_user)}, null, null, null);
        final List<Order> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setId_user(cursor.getColumnIndex(sqlSelect[0]));
                order.setId_product(cursor.getColumnIndex(sqlSelect[1]));
                order.setQuanity(cursor.getColumnIndex(sqlSelect[2]));
                result.add(order);
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void saveCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();

        String query = String.format(Locale.getDefault(), "INSERT OR REPLACE INTO Cart (id_user, id_product, quantity) VALUES (%d, %d, %d);",
                order.getId_product(),
                order.getId_product(),
                order.getQuanity());
        try {
            db.execSQL(query);
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
    }

    public void updateCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format(Locale.getDefault(), "UPDATE Cart SET quantity = %d WHERE id_user = %d AND id_product = %d", order.getQuanity(), order.getId_user(), order.getId_product());
        db.execSQL(query);
    }

    public void removeAllCart(int id_user) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format(Locale.getDefault(), "DELETE FROM Cart WHERE id_user = %d", id_user);
        db.execSQL(query);
    }

    public void removeCart(int id_user, int id_product) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format(Locale.getDefault(), "DELETE FROM Cart WHERE id_user = %d AND id_product = %d", id_user, id_product);
        db.execSQL(query);
    }

    public void increaseCart(int id_user, int id_product) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format(Locale.getDefault(), "UPDATE Cart SET quantity = quantity + 1 WHERE id_user = %d AND id_product = %d", id_user, id_product);
        db.execSQL(query);
    }

    public int countCart(int id_user) {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format(Locale.getDefault(), "SELECT COUNT(*) FROM Cart WHERE id_user = %d", id_user);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                count = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return count;
    }
}
