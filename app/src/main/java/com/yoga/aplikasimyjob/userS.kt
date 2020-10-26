package com.yoga.aplikasimyjob

class userS {
    /// MOdel class
    var nama : String? = null
    var role : String? = null
    var alamat : String? = null
    var urlportofolio : String? = null

    constructor(){

    }

    constructor(nama: String?, role: String?, alamat: String?, urlportofolio: String?) {
        this.nama = nama
        this.role = role
        this.alamat = alamat
        this.urlportofolio = urlportofolio
    }
}