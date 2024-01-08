package com.example.exercise04.photos

import android.net.Uri

class DataItem {
    var id: Int = 0
    var name: String = "Empty name"
    var uripath: String = " Empty uri"
    var path: String = " Empty path"
    var curi: Uri? = null //Content Uri //other possible constructors

    constructor(id: Int, name: String, uripath: String, path: String, curi: Uri) : this() {
        this.id = id
        this.name = name
        this.uripath = uripath
        this.path = path
        this.curi = curi
    }

    constructor()

}