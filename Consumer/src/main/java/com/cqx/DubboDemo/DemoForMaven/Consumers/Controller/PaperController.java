package com.cqx.DubboDemo.DemoForMaven.Consumers.Controller;

import com.cqx.DubboDemo.DemoForMaven.Consumers.Security.CurrentUser;
import com.cqx.DubboDemo.DemoForMaven.Consumers.Security.CustomUserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by cqx on 2016/11/28.
 */
@Controller
public class PaperController {

    @RequestMapping(value = "/papers", method = RequestMethod.GET)
    public String getHome(Model model, @CurrentUser CustomUserDetails userDetails) {

        System.out.println("username is "+userDetails.getUsername());
//        model.addAttribute("username",userDetails.getUsername());

        return "home/papers";
    }
}
