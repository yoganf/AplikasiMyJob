package com.yoga.aplikasimyjob

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class Portofolio (
    var alamat:String ?="",
    var deskripsi:String ?="",
    var judul:String ?="",
    var role:String ?="",
    var tarif:String ?="",
    var urlportofolio:String ?="",
): Parcelable