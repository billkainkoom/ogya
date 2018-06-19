package com.billkainkoom.ogya.shared

import android.content.Context
import android.os.AsyncTask

class QuickAsync<T>(val context: Context, val initiateAsync: () -> T?, val asyncComplete: (response: T?) -> Unit) : AsyncTask<String, Void, T>() {


    override fun doInBackground(vararg urls: String): T? {
        return try {
            initiateAsync()
        } catch (e: Exception) {
            null
        }

    }

    override fun onCancelled() {
        super.onCancelled()
        asyncComplete(null)
    }


    override fun onPostExecute(result: T) {
        try {
            asyncComplete(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}