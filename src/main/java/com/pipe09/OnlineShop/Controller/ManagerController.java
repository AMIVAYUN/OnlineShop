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
        return "fragments/private/Pop_Regitem";
    }
    @GetMapping(path = "/admin/manage")
    public String managerAccess(){
        return "fragments/private/managerv2";
    }
    @GetMapping(path="/admin/manage/register-item.do")
    public String resultitemUpload(){
        return "fragments/private/Reg_suc";
    }
    @GetMapping(path="/admin/manage/register-faq.do")
    public String resultfaqUpload(){
        return "fragments/private/Reg_suc";
    }
    @GetMapping(path="/admin/manage/view-faq")
    public String viewlistfaq(){return "fragments/private/MM_View_FAQ";}
    @GetMapping(path="/admin/manage/view-members")
    public String viewlistmem(){return "fragments/private/MM_View_Members";}
    @GetMapping(path="/admin/manage/view-delivery")
    public String viewlistdelievery(){ return "fragments/private/MM_View_Delivery";}
    @GetMapping(path="/admin/manage/view-orders")
    public String viewlistOrders(){return "fragments/private/MM_View_Orders";}
    @GetMapping(path="/admin/manage/view-item")
    public String viewlistItems(){return "fragments/private/MM_View_Item";}
    @GetMapping(path = "/admin/manage/image/putImage")
    public String putImageHTML(){ return "fragments/private/getImage";}
}
