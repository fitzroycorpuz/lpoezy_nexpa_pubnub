package com.lpoezy.nexpa.models;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.View;
import com.lpoezy.nexpa.utils.AccountManager;
import com.lpoezy.nexpa.utils.CBLConnectionManager;
import com.lpoezy.nexpa.utils.L;
import com.lpoezy.nexpa.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by pksimpson on 13/07/16.
 */
public class M_SignupScreen {


    //users
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String TYPE = "type";

    //users count
    private static final String USERS_COUNT = "count";
    private static final String EMAILS_VIEW_VR = "3";
    private static final String UNAME_VIEW_VR = "1";
    private static final String UNAME_PASS_VIEW_VR = "2";
    private static final String EMAIL_VIEW_VR = "1";
    private static final String TEST_UNAME_PASSWORD_VIEW_VR = "1";


    private final Context context;

    public M_SignupScreen(Context ctx) {
        this.context = ctx;
    }

    private List<OnRegistrationCompleteLister> onRegistrationCompleteListers = new ArrayList<OnRegistrationCompleteLister>();


    public void onRemoveRegistrationCompleteLister(OnRegistrationCompleteLister listener) {
        int index = onRegistrationCompleteListers.indexOf(listener);

        if (index != -1) {
            onRegistrationCompleteListers.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Can't remove OnRegistrationCompleteLister Object in M_SignupScreen at onRemoveRegistrationCompleteLister method");
        }

    }

    public void onSetRegistrationCompleteLister(OnRegistrationCompleteLister listener) {
        onRegistrationCompleteListers.add(listener);
    }

    private void notifyOnRegistrationCompleteListener(Results result) {
        for (OnRegistrationCompleteLister listener : onRegistrationCompleteListers) {
            listener.OnRegistrationComplete(result.id);
        }
    }

    public void login(String uname, String password) {

        // Create a new document and add data
        CBLConnectionManager connManager = CBLConnectionManager.getInstance().Create(this.context);
        Manager manager = null;
        Database database = null;

        try {
            manager = connManager.getManagerInstance();
            database = connManager.getDatabaseInstance(CBLConnectionManager.DB_NAME);
        } catch (Exception e) {
            L.error(e.getMessage());
        }

        if(isThereMissingField("email", uname, password)){
            notifyOnRegistrationCompleteListener(Results.MISSING_FILED);
            return;
        }


        if(isUsernameAndPasswordValid(database, uname, password)){

            String email = getRegisteredEmailOf(database, uname);

            AccountManager am = new AccountManager(this.context);
            am.setLoggedin(true);
            am.setUsername(uname);
            am.setEmail(email);

            L.debug("uname: "+uname+", email: "+email);

            notifyOnRegistrationCompleteListener(Results.LOGIN_COMPLETE);

        }else{
            notifyOnRegistrationCompleteListener(Results.UNAME_PASSWORD_INCORRECT);
        }


    }

    public void register(final String email, final String username, final String password) {

        // Create a new document and add data
        CBLConnectionManager connManager = CBLConnectionManager.getInstance().Create(this.context);
        Manager manager = null;
        Database database = null;

        try {
            manager = connManager.getManagerInstance();
            database = connManager.getDatabaseInstance(CBLConnectionManager.DB_NAME);
        } catch (Exception e) {
            L.error(e.getMessage());
        }

        //check user input
        boolean isEmailValid = Utils.isValidEmail(email);
        boolean isPasswordValid = Utils.isPasswordValid(password);
        boolean isEmailAlreadyExists = isEmailAlreadyExists(database, email);
        boolean isUsernameAlreadyExists = isUsernameAlreadyExists(database, username);
        boolean isThereMissingField = isThereMissingField(email, username, password);

        if(isThereMissingField){
            notifyOnRegistrationCompleteListener(Results.MISSING_FILED);
            return;
        }

        if (!isEmailValid) {

            notifyOnRegistrationCompleteListener(Results.EMAIL_NOT_VALID);
            return;
        }

        if (!isPasswordValid) {

            notifyOnRegistrationCompleteListener(Results.PASSWORD_NOT_VALID);
            return;
        }

        if (isEmailAlreadyExists) {
            notifyOnRegistrationCompleteListener(Results.EMAIL_ALREADY_EXISTS);
            return;
        }

        if (isUsernameAlreadyExists) {
            notifyOnRegistrationCompleteListener(Results.UNAME_ALREADY_EXISTS);
            return;
        }

        //retrieved users count
        Document usersCountDocument = null;

        //create document
        usersCountDocument = database.getDocument("user::count");
        final int START_COUNT = 0;
        int usersCount = START_COUNT;


        Integer count = (Integer) usersCountDocument.getProperty(USERS_COUNT);
        //add data to document
        Map<String, Object> usersCountMap = new HashMap<String, Object>();

        usersCountMap.put(USERS_COUNT, usersCount);

        if (count == null) {

            try {
                usersCountDocument.putProperties(usersCountMap);
            } catch (CouchbaseLiteException e) {
                L.error(e.getMessage());
            }

        } else {

            usersCount = count.intValue();

        }


        String usersDocumentId = email + "::" + usersCount + "::" + "user";
        Document usersDocument = null;

        //create document
        usersDocument = database.getDocument(usersDocumentId);
        L.debug("usersDocumentId: " + usersDocument.getId());

        String retrievedEmail = String.valueOf(usersDocument.getProperty(EMAIL));

        //user did not exists
        if (usersDocument.getProperty(EMAIL) == null) {

            //add data to document
            Map<String, Object> map = new HashMap<String, Object>();

            map.put(EMAIL, email);
            map.put(USERNAME, username);
            map.put(PASSWORD, password);
            map.put(TYPE, "user");

            if (usersDocument != null) {
                try {
                    // Save the properties to the document
                    usersDocument.putProperties(map);
                } catch (CouchbaseLiteException e) {
                    L.error("Error putting " + e);

                }
            }

            //update user count
            ++usersCount;
            Map<String, Object> updatedProperties = new HashMap<String, Object>();
            updatedProperties.putAll(usersCountDocument.getProperties());
            updatedProperties.put(USERS_COUNT, usersCount);
            // Save to the Couchbase local Couchbase Lite DB
            try {
                usersCountDocument.putProperties(updatedProperties);
            } catch (CouchbaseLiteException e) {
                L.error("Error putting " + e);
            }

        }


        AccountManager am = new AccountManager(this.context);
        am.setLoggedin(true);
        am.setUsername(username);
        am.setEmail(email);

        notifyOnRegistrationCompleteListener(Results.REGISTRATION_COMPLETE);
    }



    public static final int REGISTRATION_COMPLETE = 2000;
    public static final int LOGIN_COMPLETE = 2001;
    public static final int UNAME_PASSWORD_INCORRECT = 2002;
    public static final int EMAIL_NOT_VALID = 2003;
    public static final int EMAIL_ALREADY_EXISTS = 2004;
    public static final int UNAME_ALREADY_EXISTS = 2005;
    public static final int PASSWORD_NOT_VALID = 2006;
    public static final int MISSING_FIELD = 2007;


    public enum Results {
        REGISTRATION_COMPLETE(M_SignupScreen.REGISTRATION_COMPLETE), LOGIN_COMPLETE(M_SignupScreen.LOGIN_COMPLETE),
        UNAME_PASSWORD_INCORRECT(M_SignupScreen.UNAME_PASSWORD_INCORRECT), EMAIL_NOT_VALID(M_SignupScreen.EMAIL_NOT_VALID),
        EMAIL_ALREADY_EXISTS(M_SignupScreen.EMAIL_ALREADY_EXISTS), UNAME_ALREADY_EXISTS(M_SignupScreen.UNAME_ALREADY_EXISTS),
        PASSWORD_NOT_VALID(M_SignupScreen.PASSWORD_NOT_VALID), MISSING_FILED(MISSING_FIELD);

        public int id;

        Results(int i) {
            id = i;
        }
    }

    private boolean isUsernameAndPasswordValid(Database database, String uname, String password) {
        boolean isUnameAndPasswordValid = false;
        View unameAndPasswordView = database.getView("usernames_passwords");

        unameAndPasswordView.setMap(new Mapper() {

            @Override
            public void map(Map<String, Object> document, Emitter emitter) {

                String type = String.valueOf(document.get("type"));

                if ("user".equals(type)) {
                    emitter.emit(document.get("username"), document.get("password"));
                }

            }
        }, UNAME_PASS_VIEW_VR);


        Query query = unameAndPasswordView.createQuery();
        // we don't need the reduce here
        query.setMapOnly(true);
        QueryEnumerator qResult = null;
        try {
            qResult = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        for (Iterator<QueryRow> it = qResult; it.hasNext(); ) {
            QueryRow row = it.next();
            String unameVal = (String) row.getKey();
            String passwordVal = (String) row.getValue();
            L.debug("passwordVal: " + passwordVal+", "+password);
            if (unameVal.equals(uname) && passwordVal.equals(password)) {
                isUnameAndPasswordValid = true;
                break;
            }
        }

        return isUnameAndPasswordValid;
    }

    private boolean isThereMissingField(String email, String username, String password) {

        return ((email!=null && !email.isEmpty()) && (username!=null && !username.isEmpty()) && (password!=null && !password.isEmpty()))?false:true;
    }

    private String getRegisteredEmailOf(final Database database, final String uname) {

        String email = "";

        View emailView = database.getView("email");

        emailView.setMap(new Mapper() {

            @Override
            public void map(Map<String, Object> document, Emitter emitter) {

                String type = String.valueOf(document.get("type"));

                if ("user".equals(type)) {
                    emitter.emit(document.get("username"), document.get("email"));
                }

            }
        }, EMAIL_VIEW_VR);


        Query query = emailView.createQuery();
        // we don't need the reduce here
        query.setMapOnly(true);
        QueryEnumerator qResult = null;
        try {
            qResult = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        for (Iterator<QueryRow> it = qResult; it.hasNext(); ) {
            QueryRow row = it.next();
            String key = (String) row.getKey();

            if (key.equals(uname)) {
                email = String.valueOf(row.getValue());
                break;
            }
        }

        return email;
    }

    private boolean isEmailAlreadyExists(Database database, String email) {

        boolean isAlreadyExists = false;
        View emailsView = database.getView("emails");

        emailsView.setMap(new Mapper() {

            @Override
            public void map(Map<String, Object> document, Emitter emitter) {

                String type = String.valueOf(document.get("type"));

                if ("user".equals(type)) {
                    emitter.emit(document.get("email"), null);
                }

            }
        }, EMAILS_VIEW_VR);


        Query query = emailsView.createQuery();
        // we don't need the reduce here
        query.setMapOnly(true);
        QueryEnumerator qResult = null;
        try {
            qResult = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        for (Iterator<QueryRow> it = qResult; it.hasNext(); ) {
            QueryRow row = it.next();
            String emailVal = (String) row.getKey();
            //L.debug("email: " + emailVal);
            if (emailVal.equals(email)) {
                isAlreadyExists = true;
                L.error("email already exists");
                break;
            }
        }

        return isAlreadyExists;
    }

    private boolean isUsernameAlreadyExists(Database database, String uname) {

        boolean isAlreadyExists = false;
        View unameView = database.getView("usernames");

        unameView.setMap(new Mapper() {

            @Override
            public void map(Map<String, Object> document, Emitter emitter) {

                String type = String.valueOf(document.get("type"));

                if ("user".equals(type)) {
                    emitter.emit(document.get("username"), null);
                }

            }
        }, UNAME_VIEW_VR);


        Query query = unameView.createQuery();
        // we don't need the reduce here
        query.setMapOnly(true);
        QueryEnumerator qResult = null;
        try {
            qResult = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        for (Iterator<QueryRow> it = qResult; it.hasNext(); ) {
            QueryRow row = it.next();
            String unameVal = (String) row.getKey();
            //L.debug("email: " + emailVal);
            if (unameVal.equals(uname)) {
                isAlreadyExists = true;
                L.error("username already exists");
                break;
            }
        }

        return isAlreadyExists;
    }


    public interface OnRegistrationCompleteLister {
        public void OnRegistrationComplete(int result);
    }

}
