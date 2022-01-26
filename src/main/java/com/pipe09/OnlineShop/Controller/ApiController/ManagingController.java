package com.pipe09.OnlineShop.Controller.ApiController;


import com.pipe09.OnlineShop.Domain.Item.Item;
import com.pipe09.OnlineShop.Dto.FileDto;
import com.pipe09.OnlineShop.Dto.R_itemDto;
import com.pipe09.OnlineShop.Service.BoardService;
import com.pipe09.OnlineShop.Service.ItemService;
import com.pipe09.OnlineShop.Service.MemberService;
import com.pipe09.OnlineShop.Service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ManagingController {
    private final ItemService itemService;
    private final OrderService orderService;
    private final MemberService memberService;
    private final BoardService boardService;


    @GetMapping("/admin/manage")
    public ModelAndView tmpAccess(Model model){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("fragments/private/manager");
        return modelAndView;
    }

    @GetMapping("/admin/manage/register-item")
    public ModelAndView tmpregAccess(Model model){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("fragments/private/ManageMent_register_item");
        return modelAndView;
    }
    @PostMapping("/admin/manage/register_item.do")
    @ResponseBody
    public String register(@Valid R_itemDto dto, BindingResult result){
        String path=System.getProperty("user.dir");

        if(result.hasErrors()){
            return "fail"+result.toString();
        }

        if(dto.img!=null){
            File file = new File("./src/main/resources/static/img",dto.img.getOriginalFilename());
            System.out.println(file.getPath());
            try{
                file.createNewFile();
                FileOutputStream fos=new FileOutputStream(file);
                fos.write(dto.img.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return "finished";

    }
}
