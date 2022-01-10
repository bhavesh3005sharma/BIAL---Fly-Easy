package com.fitnesshub.bial_flyeasy.models

import java.io.Serializable


data class FoodStoreModel(
    var type:String?=null,
    var name:String?=null,
    var address:String?=null,
    var description:String?=null,
    var rating:Int?=0,
    var images:List<String>?=null
):Serializable
