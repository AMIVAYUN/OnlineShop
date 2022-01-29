package com.pipe09.OnlineShop.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ManagerController {

    @GetMapping("/admin/manage/register-faq")
    public String regfaqAccess(){
        return "fragments/private/MM_Register_FAQ";
    }
    @GetMapping("/admin/manage/register-item")
    public String regitemAccess(){
        return "fragments/private/MM_Register_Item";
    }
    @GetMapping(path = "/admin/manage")
    public String managerAccess(){
        return "fragments/private/manager";
    }
    @GetMapping(path="/admin/manage/register-item.do")
    public String resultitemUpload(){
        return "fragments/private/Reg_suc";
    }
    @GetMapping(path="/admin/manage/register-faq.do")
    public String resultfaqUpload(){
        return "fragments/private/Reg_suc";
    }
}
