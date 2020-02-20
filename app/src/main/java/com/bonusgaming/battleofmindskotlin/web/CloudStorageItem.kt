package com.bonusgaming.battleofmindskotlin.web

data class CloudStorageItem(
    val items: List<Item>,
    val kind: String
)

//POJO класс для Retrofit через api Cloud Google Storage
data class Item(
    val bucket: String,
    val contentDisposition: String,
    val contentType: String,
    val crc32c: String,
    val etag: String,
    val generation: String,
    val id: String,
    val kind: String,
    val md5Hash: String,
    val mediaLink: String,
    val metadata: Metadata,
    val metageneration: String,
    val name: String,
    val selfLink: String,
    val size: String,
    val storageClass: String,
    val timeCreated: String,
    val timeStorageClassUpdated: String,
    val updated: String
)

data class Metadata(
    val firebaseStorageDownloadTokens: String
)