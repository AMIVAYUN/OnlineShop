package com.pipe09.OnlineShop.Controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info(String.format("%s accessed general home with role type: %s",authentication.getName(),authentication.getAuthorities()));
        return "fragments/public/home";
    }
    @GetMapping("/search/{keyword}")
    public String search(@PathVariable @Nullable String keyword){
        return "fragments/public/result_search";
    }

    @GetMapping("/comp-intro")
    public String compIntroduction(){ return "fragments/public/companyintro"; }

    @GetMapping("/laws/DBstatic")
    public String getDBstaticlaw(){ return "fragments/private/DBServiceLaw"; }
    @GetMapping("/laws/ElectricTran")
    public String getDBElectricTransactionlaw(){ return "fragments/private/ElectricTransactionLaw"; }
    @GetMapping("/laws/PersonalInfo")
    public String getPersonalInfo(){ return "fragments/private/PersonalInfoLaw"; }

    /*
    @GetMapping("/test")
    public String getTest(){ return "fragments/public/Board/ViewNotice2"; }

     */


}
