package com.bonusgaming.battleofmindskotlin.web

import android.net.Uri

interface FirebaseImageAdapter {
    fun getFacesUrls(completable: DownloadCompletable<Uri>)
    fun getBodiesUrls(completable: DownloadCompletable<Pair<String, Uri>>)
}