package com.bonusgaming.battleofmindskotlin.web

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

/*

root.child('images').listAll()
root.child('images/uid').listAll()

 */

object FirebaseImageImpl : FirebaseImageAdapter {
    override fun getBodiesUrls(completable: DownloadCompletable<Pair<String, Uri>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    const val PATH_FACES = "face"
    const val PATH_BODY_FLUFFY = "body/fluffy"
    const val PATH_BODY_GHOST = "body/ghost"
    const val PATH_BODY_SQUARE = "body/square"

    init {
        Log.e("firebase", "init called")
    }

    private val mStorageRef: StorageReference by lazy {
        Log.e("firebase", "lazy StorageReference")
        FirebaseStorage.getInstance("gs://battleofminds-2e099.appspot.com").reference
    }

    override fun getFacesUrls(completable: DownloadCompletable<Uri>) {
        Log.e("firebase", "getFacesUrls $mStorageRef")
        val child = mStorageRef.root.child(PATH_FACES)
        child.listAll().addOnSuccessListener { listResult ->

            val resultList = mutableListOf<Uri>()
            listResult.items.forEach { item ->
                item.downloadUrl.addOnSuccessListener {
                    resultList.add(it)
                }
            }
            completable.onComplete(resultList)
        }
            .addOnFailureListener {
                completable.onFailure(it)
            }
    }

    /* override fun getBodiesUrls(completable: DownloadCompletable<Pair<String, Uri>>) {
         val child = mStorageRef.root.child(PATH_FACES)
         child.listAll().addOnSuccessListener { listResult ->

             val resultList = mutableListOf<Pair<String, Uri>>()

             //Ghosts
             listResult.items.forEach { item ->
                 item.downloadUrl.addOnSuccessListener {
                     resultList.add(Pair(it, ""))
                 }
             }
             completable.onComplete(resultList)
         }
             .addOnFailureListener {
                 completable.onFailure(it)
             }
     }*/
}