package com.newslist.demo.Controller

import com.newslist.demo.Service.IndexService
import com.newslist.demo.pojo.NewsList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class IndexController{
    @Autowired
    lateinit var indexService : IndexService
    @GetMapping(*arrayOf("/","index"))
    fun getAllData() : ModelAndView{
        val newsList : List<NewsList> = indexService.getAllList()
        val modelAndView = ModelAndView("index")
        modelAndView.addObject("newslist", newsList)
        return modelAndView
    }
    @RequestMapping("deletenew/{id}",method = arrayOf(RequestMethod.GET), produces = arrayOf("text/html; charset=UTF-8"))
    @ResponseBody
    fun deleteData(@PathVariable id : Int) : String {
        val result : Int = indexService.deleteData(id)
        if (result == 0){
            return "<script>alert('删除失败！请重试');window.location.href=document.referrer;</script>"
        }else{
            return "<script>alert('删除成功！');window.location.href=document.referrer;</script>"
        }
    }
    @GetMapping("editnew/{id}")
    fun editPage(@PathVariable id: Int) : ModelAndView{
        val modelAndView = ModelAndView("addoredit")
        val newsList : NewsList = indexService.getListById(id)
        modelAndView.addObject("msg", 1)
        modelAndView.addObject("newsinfo", newsList)
        return modelAndView
    }
    @GetMapping("addnew")
    fun addPage() : ModelAndView{
        val modelAndView = ModelAndView("addoredit")
        modelAndView.addObject("msg", 0)
        return modelAndView
    }
    @RequestMapping("addnews", method = arrayOf(RequestMethod.POST))
    fun addData(newsList: NewsList, ra : RedirectAttributes) : String{
        val result : Int = indexService.addData(newsList)
        if(result == 0){
            ra.addFlashAttribute("msg", "添加数据失败！")
            return "redirect:/index"
        }else{
            ra.addFlashAttribute("msg", "添加数据成功！")
            return "redirect:/index"
        }
    }
    @RequestMapping("editnews", method = arrayOf(RequestMethod.POST))
    fun editData(newsList: NewsList, ra : RedirectAttributes) : String{
        val result : Int = indexService.updateData(newsList)
        if(result == 0){
            ra.addFlashAttribute("msg", "更新数据失败！")
            return "redirect:/index"
        }else{
            ra.addFlashAttribute("msg", "更新数据成功！")
            return "redirect:/index"
        }
    }
}