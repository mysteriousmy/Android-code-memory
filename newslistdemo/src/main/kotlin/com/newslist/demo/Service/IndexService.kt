package com.newslist.demo.Service

import com.newslist.demo.pojo.NewsList

interface IndexService {
    fun getAllList() : List<NewsList>
    fun deleteData(id: Int): Int
    fun getListById(id: Int): NewsList
    fun addData(newsList: NewsList): Int
    fun updateData(newsList: NewsList): Int
    fun getListByNewsType(i: Int): List<NewsList>
}