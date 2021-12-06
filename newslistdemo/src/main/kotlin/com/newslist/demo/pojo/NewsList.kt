package com.newslist.demo.pojo

import lombok.Data

@Data
class NewsList{
    var id : Int ? = null
    var type : Int ? = null
    var newsType : Int ? = null
    var newsName : String ? = null
    var img1 : String ? = null
    var img2 : String ? = null
    var img3 : String ? = null
    var newsUrl : String ? = null
}