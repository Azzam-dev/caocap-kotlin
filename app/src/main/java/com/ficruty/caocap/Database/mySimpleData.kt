package com.ficruty.caocap.Database

 class mySimpleData {

//val cs = listOf<caocap>(
// caocap("Google","hoodie1"),
// caocap("Facebook","hoodie2"),
// caocap("Android","hoodie3"),
// caocap("مناحي","hoodie4"),
// caocap("Google","hoodie1"),
// caocap("Facebook","hoodie2"),
// caocap("Android","hoodie3"),
// caocap("مناحي","hoodie4"),
// caocap("Google","hoodie1"),
// caocap("Facebook","hoodie2"),
// caocap("Android","hoodie3"),
// caocap("مناحي","hoodie4"),
// caocap("Google","hoodie1"),
// caocap("Facebook","hoodie2"),
// caocap("Android","hoodie3"),
// caocap("مناحي","hoodie4"),
// caocap("Google","hoodie1"),
// caocap("Facebook","hoodie2"),
// caocap("Android","hoodie3"),
// caocap("مناحي","hoodie4"),
// caocap("Google","hoodie1"),
// caocap("Facebook","hoodie2"),
// caocap("Android","hoodie3"),
// caocap("مناحي","hoodie4"),
// caocap("Google","hoodie1"),
// caocap("Facebook","hoodie2"),
// caocap("Android","hoodie3"),
// caocap("مناحي","hoodie4"),
// caocap("Google","hoodie1"),
// caocap("Facebook","hoodie2"),
// caocap("Android","hoodie3"),
// caocap("مناحي","hoodie4"),
// caocap("Google","hoodie1"),
// caocap("Facebook","hoodie2"),
// caocap("Android","hoodie3"),
// caocap("مناحي","hoodie4")
//)

  object webCao {
   val caocapList = listOf<caocap>(
    caocap("google","https://www.google.com"),
    caocap("University of Jeddah","https://www.uj.edu.sa"),
    caocap("carecards","https://carecards.io/cards"),
    caocap("apple","https://www.apple.com"),
    caocap("blogger", "https://www.blogger.com"),
    caocap("facebook", "https://www.facebook.com"),
    caocap("vocabulary","https://www.vocabulary.com")


   ).shuffled()

  }

}