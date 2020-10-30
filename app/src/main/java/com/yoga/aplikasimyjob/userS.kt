package com.yoga.aplikasimyjob

class userS {
    /// MOdel class
    var nama : String? = null
    var judul : String? = null
    var role : String? = null
    var alamat : String? = null
    var urlportofolio : String? = null
    var notlp : String? = null
    var tarif:String ?= null
    var deskripsi:String ?= null
    var url:String ?= null

    constructor(){

    }

    constructor(nama: String?, judul: String?, role: String?, alamat: String?, urlportofolio: String?, notlp: String?,
                tarif: String?, deskripsi: String?, url : String?) {
        this.nama = nama
        this.role = role
        this.judul = judul
        this.alamat = alamat
        this.urlportofolio = urlportofolio
        this.notlp = notlp
        this.tarif = tarif
        this.deskripsi = deskripsi
        this.url = url
    }
}