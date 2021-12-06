package com.newslist.demo.dao

import com.newslist.demo.pojo.NewsList
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface DataDao{
    fun getAllData() : List<NewsList>
    fun deleteData(@Param("id") id: Int): Int
    fun getNewsById(@Param("id") id: Int): NewsList
    fun addData(@Param("newsList") newsList: NewsList): Int
    fun updateData(@Param("newsList") newsList: NewsList): Int
    fun getNewsByNewsType(@Param("newsType") i: Int): List<NewsList>
}