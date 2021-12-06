package com.newslist.demo.Controller

import com.newslist.demo.Service.IndexService
import com.newslist.demo.pojo.NewsList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@ResponseBody
class DataController{
    @Autowired
    lateinit var indexService : IndexService
    @RequestMapping("top/getNewsData")
    @ResponseBody
    fun getNewsData() : List<NewsList>{
        return indexService.getListByNewsType(2);
    }
    @RequestMapping("top/getAdData")
    @ResponseBody
    fun getAdData() : List<NewsList>{
        return indexService.getListByNewsType(1);
    }
}