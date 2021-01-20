package com.ficruty.caocap.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Caocap(var name:String,var link:String,var type:String,var color:Int,var imageURL:String,var published:Boolean, var html:String, var css:String, var js:String):Parcelable {
    constructor():this("","","",0,"",true,"","","");
}

@Parcelize
class CaocapLink(var name:String,var link:String,val type:String="link",var color:Int,var imageURL:String,var published:Boolean):Parcelable{
    constructor():this("","","link",0,"",true);
}