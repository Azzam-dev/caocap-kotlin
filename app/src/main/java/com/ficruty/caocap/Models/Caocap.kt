package com.ficruty.caocap.Models

import android.os.Parcelable
import com.google.firebase.database.DataSnapshot
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize

class Caocap(var name:String,var link:String,var type:String,var color:Int,var imageURL:String,var published:Boolean):Parcelable {

    constructor():this("","","",0,"",true);

}

@Parcelize
class CaocapLink(var name:String,var link:String,val type:String="link",var color:Int,var published:Boolean, var owners:HashMap<String,String>,var imageURL:String):Parcelable{
    constructor():this("","","link",0,true,HashMap(),"");
}

@Parcelize

class CaocapCode(var name:String,var code : HashMap<String,String> ,var type:String,var color:Int,var published:Boolean,var owners:HashMap<String,String>,var imageURL:String):Parcelable {

    constructor():this("", HashMap(),"",0,true,HashMap(),"");

}

@Parcelize
class CaocapTest(var name:String,var link:String,var type:String,var color:Int,var imageURL:String,var published:Boolean,var html : String,var css:String,var js : String):Parcelable {

    constructor():this("","","",0,"",true , "", " ", "");

}