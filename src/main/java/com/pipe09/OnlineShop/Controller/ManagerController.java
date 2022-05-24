package com.pipe09.OnlineShop.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ManagerController {

    @Value("${latest.version}")
    private String version;
    @Value("${latest.reportUrl}")
    private String reportUrl;

    //version2
    @GetMapping("/admin/manage/register-faq")
    public String regfaqAccess(){
        return "fragments/private/MM_Register_FAQ";
    }
    @GetMapping("/admin/manage/register-item")
    public String regitemAccess(){
        return "fragments/private/Pop_Regitem";
    }
    @GetMapping(path = "/admin/manage")
    public String managerAccess(Model model){
        model.addAttribute("version",version);
        model.addAttribute("reportUrl",reportUrl);
        return "fragments/private/managerv2";
    }
    @GetMapping(path = "/admin/manage/image/putImage")
    public String putImageHTML(){ return "fragments/private/getImage";}
    @GetMapping(path="/api/v2/register-item.do")
    public String resultitemUpload(){
        return "fragments/private/Reg_suc";
    }
    @GetMapping(path="/admin/manage/register-faq.do")
    public String resultfaqUpload(){
        return "fragments/private/Reg_suc";
    }
    //version_1;
    /*
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



     */

}
