package edu.doggy228.antoxapos;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class AttrStorage {
    private static final String PREF_SERVER_NAME = "server_name";
    private static final String DEFAULT_SERVER_NAME = "localhost";
    private static String sServerName = null;
    private static final String PREF_LOYALTY_SYSTEM = "loyalty_system";
    private static final String DEFAULT_LOYALTY_SYSTEM = "";
    private static String sLoyaltySystem = null;
    private static final String PREF_PAY_BILL = "pay_bill";
    private static final String DEFAULT_PAY_BILL = "";
    private static String sPayBill = null;
    private static final Object sLock = new Object();

    public static void serverNameSet(Context c, String s) {
        synchronized(sLock) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_SERVER_NAME, s).commit();
            sServerName = s;
        }
    }

    public static String serverNameGet(Context c) {
        synchronized (sLock) {
            if (sServerName == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String serverName = prefs.getString(PREF_SERVER_NAME, DEFAULT_SERVER_NAME);
                sServerName = serverName;
            }
            return sServerName;
        }
    }
    public static void loyaltySystemSet(Context c, String s) {
        synchronized(sLock) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_LOYALTY_SYSTEM, s).commit();
            sLoyaltySystem = s;
        }
    }

    public static String loyaltySystemGet(Context c) {
        synchronized (sLock) {
            if (sLoyaltySystem == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String loyaltySystem = prefs.getString(PREF_LOYALTY_SYSTEM, DEFAULT_LOYALTY_SYSTEM);
                sLoyaltySystem = loyaltySystem;
            }
            return sLoyaltySystem;
        }
    }
    public static void payBillSet(Context c, String s) {
        synchronized(sLock) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_PAY_BILL, s).commit();
            sPayBill = s;
        }
    }

    public static String PayBillGet(Context c) {
        synchronized (sLock) {
            if (sPayBill == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String payBill = prefs.getString(PREF_PAY_BILL, DEFAULT_PAY_BILL);
                sPayBill = payBill;
            }
            return sPayBill;
        }
    }
}

