package com.yoga.aplikasimyjob

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User (
    var nama:String ?="",
    var email:String ?="",
    var username:String ?="",
    var password:String ?="",
    var url:String ?="",
    var alamat:String ?="",
    var deskripsi:String ?="",
    var judul:String ?="",
    var role:String ?="",
    var tarif:String ?="",
    var urlportofolio:String ?="",
    var notlp:String ?=""
): Parcelable