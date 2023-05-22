package com.linkdom.readbook.model



class Book(){
    var id: String = ""
    var name: String = ""
    var author: String = ""
    var cover: String = ""
    var description: String = ""
    var chapters: List<Chapter> = listOf()
}
