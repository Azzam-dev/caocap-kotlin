package com.ficruty.caocap.Services

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.*

var DB_BASE: DatabaseReference = Firebase.database.reference
    var STORAGE_BASE: StorageReference = Firebase.storage.reference


class DataService {


    val inctance  = DataService()


    // DB references
    private var _REF_DB_BASE = DB_BASE
    private var _REF_APPDATA = DB_BASE.child("appData")
    private var _REF_MINIMUM_VERSION = DB_BASE.child("appData").child("minimumVersion-ios")
    private var _REF_REPAIRING = DB_BASE.child("appData").child("repairing")
    private var _REF_RELEASED = DB_BASE.child("appData").child("released")

    private var _REF_USERS = DB_BASE.child("users")
    private var _REF_CAOCAPS = DB_BASE.child("caocap")

    private var _REF_CHATS = DB_BASE.child("chats")
    //private var _REF_ROOMS = DB_BASE.child("rooms")


    // Storage references
    private var _REF_STORAGE_BASE = STORAGE_BASE

    private var _REF_CHAT_STORAGE = STORAGE_BASE.child("chats")

    private var _REF_PROFILE_IMAGES = STORAGE_BASE.child("profile-images")
    private var _REF_CAOCAP_IMAGES = STORAGE_BASE.child("caocap-images")
    private var _REF_GROUP_IMAGES = STORAGE_BASE.child("group-images")



    fun updateUserData() {

    }

    fun createNewContact() {

    }

    fun createChat() {

    }

    fun createCaocap() {

    }

    fun removeCaocap() {


    }

    fun launchCaocap() {

    }

    // this function adds and removes caocaps from the user's orbit

    fun addAndReomveFromOrbit() {

    }


    //This sends a new message for the caocap room, You must provide the caocap key and the message data
    fun sendRoomMessage() {

    }

    //This sends a new message for the caocap room, You must provide the caocap key and the message data
    fun sendChatMessage() {

    }

    fun getCurrentUserChats() {

    }

    fun getCurrentUserChatsQuery() {


    }


}