package com.lpoezy.nexpa.utils;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

/**
 * Created by pksimpson on 12/07/16.
 */
public class CBLConnectionManager {

    private Context context;
    private Manager manager;
    private Database database;

    private static CBLConnectionManager ourInstance = new CBLConnectionManager();

    public static final java.lang.String DB_NAME = "nexpa_db_dev";

    public static CBLConnectionManager getInstance() {
        return ourInstance;
    }

    public CBLConnectionManager Create(Context ctx)
    {
        if (context == null) {
            this.context = ctx.getApplicationContext();
        }

        return ourInstance;
    }

    public Database getDatabaseInstance(String db_name) throws CouchbaseLiteException {
        if ((this.database == null) & (this.manager != null)) {
            this.database = manager.getDatabase(db_name);
        }
        return database;
    }
    public Manager getManagerInstance() throws IOException {
        if (manager == null) {
            manager = new Manager(new AndroidContext(this.context), Manager.DEFAULT_OPTIONS);
        }
        return manager;
    }

    private CBLConnectionManager() {}




}
