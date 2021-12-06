package com.newslist.demo.Service

import com.newslist.demo.dao.DataDao
import com.newslist.demo.pojo.NewsList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class IndexServiceImpl : IndexService{
    @Autowired
    lateinit var dataDao : DataDao
    override fun getAllList(): List<NewsList> {
        return dataDao.getAllData()
    }
    override fun deleteData(id: Int): Int {
        return dataDao.deleteData(id)
    }
    override fun getListById(id: Int): NewsList {
       return dataDao.getNewsById(id)
    }
    override fun addData(newsList : NewsList): Int {
        return dataDao.addData(newsList)
    }
    override fun updateData(newsList: NewsList): Int {
        return dataDao.updateData(newsList)
    }
    override fun getListByNewsType(i: Int): List<NewsList> {
        return dataDao.getNewsByNewsType(i)
    }
}