package com.newslist.demo

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.newslist.demo.dao")
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
