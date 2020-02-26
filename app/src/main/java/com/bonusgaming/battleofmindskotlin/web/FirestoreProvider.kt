package com.bonusgaming.battleofmindskotlin.web

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreProvider @Inject constructor() {
    private var database = FirebaseFirestore.getInstance()

    init {
        Log.e("poryadok", "init")

    }

    fun printHelloWorld() {
        Log.e("firestoresettings"," ${database.firestoreSettings.isPersistenceEnabled}")
        Log.e("firestoresettings"," ${database.firestoreSettings.isSslEnabled}")
        Log.e("firestoresettings"," ${database.firestoreSettings.areTimestampsInSnapshotsEnabled()}")
        
    }
}