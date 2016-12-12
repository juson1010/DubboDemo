package com.cqx.DubboDemo.DemoForMaven.Consumers.Controller;

import com.cqx.DubboDemo.DemoForMaven.Commons.IServices.UserService;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by cqx on 2016/11/28.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private UserService userService;
//
//    @Autowired
//    Datastore datastore;
//
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
//    public String getHello(com.cqx.DubboDemo.DemoForMaven.Commons.Model model) {
//        model.addAttribute("hello", "Hello Please type in your name:");
//        return "test/hello";
//    }
//
//    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
//    public String welcome(com.cqx.DubboDemo.DemoForMaven.Commons.Model model, @RequestParam String name) {
//
//        model.addAttribute("welcome", "Welcome " + name);
//        return "test/welcome";
//    }
//
    @RequestMapping(value = "/dbInitUser", method = RequestMethod.GET)
    @ResponseBody
    public String dbInitUser() {
        for(int i = 1 ;i< 20;i++){

            User user = new User();
            String email = "qwe"+i+"tty@qq.com";
            user.setName("Admin"+i);

            user.setEmail(email);
            if (i %2 ==0)
                user.setGender("男");
            else
                user.setGender("女");

            user.setBirthday(new Date());
            user.setLastUpdateDate(new Date());
            user.setRegisterDate(new Date());
            if (i<10){
                user.setMobile("1865"+i+"481862");
            }else{
                user.setMobile("186504818"+i);
            }

            userService.save(user);
            userService.savePassword(user, "123456");

        }
        return "User Created!";
    }


    @RequestMapping(value = "/dbInit", method = RequestMethod.GET)
    @ResponseBody
    public String dbInit() {

        return userService.initDb();
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
