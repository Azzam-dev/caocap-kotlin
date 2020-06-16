package com.ficruty.caocap.Models

class UserData (var username: String, var name:String, var bio:String, var website:String, var email:String, var phoneNumber:String, var imageURL:String, var color:Int ) {
    constructor() : this("", "","","","","","",0)
}