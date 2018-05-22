package com.mxi.demotablelayout.Db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class SQLDatabase {

    // Database Name
    private static String dbname = "Invoice.db";

    Context mcon;
    SQLiteDatabase db;
    static String db_path = Environment.getExternalStorageDirectory()
            .toString() + "/" + dbname;

    public SQLDatabase(Context context) {

        mcon = context;

        db = mcon.openOrCreateDatabase(db_path, Context.MODE_PRIVATE, null);

        /*Add to Cart*/
        db.execSQL("CREATE TABLE IF NOT EXISTS InvoiceTable(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tokeno VARCHAR, particulars VARCHAR,mat VARCHAR,process VARCHAR,hardness VARCHAR,hardness1 VARCHAR" +
                ",number VARCHAR,wt VARCHAR); ");


    }

    public void InsertWishlistData(String tokeno, String particulars, String mat, String process, String hardness, String hardness1, String number, String wt) {
        String query = "INSERT INTO InvoiceTable(tokeno, particulars, mat, process, hardness, hardness1, number, wt)VALUES ('"

                + tokeno + "','"
                + particulars + "','"
                + mat + "','"
                + process + "','"
                + hardness + "','"
                + hardness1 + "','"
                + number + "','"
                + wt + "')";

        try {
            db.execSQL(query);
        } catch (SQLException e) {
            Log.e("ErrorInsertWishlistData", e.getMessage());
        }

    }

    public Cursor GetAllData() {
        Cursor cur = null;
        try {
            String query = "SELECT  * FROM  InvoiceTable";
            cur = db.rawQuery(query, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("Error: GetAllData ", e.getMessage());
        }
        return cur;
    }


    public void deleteTable(String invoice_id) {
        try {

            db.execSQL("delete from InvoiceTable" + " where id='" + invoice_id + "'");
            Log.e("delete", "delete from Players" + " where id='" + invoice_id + "'" + "");
        } catch (SQLException e) {
            Log.e("Error: Deleteplayer_id", e.getMessage());
        }

    }

    public String getinvoiceIdFromtokenname(String token_name,String invoice_id) {
        Cursor cur = null;
        String id = "";
        try {
            String query = "SELECT  * FROM  InvoiceTable WHERE particulars = "
                    + "'" + token_name + "' AND id ='"+invoice_id+"'";
            cur = db.rawQuery(query, null);
            if (cur != null && cur.getCount() != 0) {
                cur.moveToFirst();
                id = cur.getString(0);
            }
        } catch (Exception e) {
            Log.e("E:Invoiceid", e.getMessage());
        }
        return id;
    }





    public String getIdFromName(String name) {
        Cursor cur = null;
        String id = "";
        try {
            String query = "SELECT  * FROM  InvoiceTable WHERE tokeno = "
                    + "'" + name + "'";
            cur = db.rawQuery(query, null);
            if (cur != null && cur.getCount() != 0) {
                cur.moveToFirst();
                id = cur.getString(0);
            }
        } catch (Exception e) {
            Log.e("E:IdFromName", e.getMessage());
        }
        return id;
    }

}