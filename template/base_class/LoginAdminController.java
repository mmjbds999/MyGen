package com.hy.test.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.test.service.json.BaseJsonResponse;

/**
 * <pre>
 * 登录Action
 * </pre>
 * 
 * @author 黄云
 * 2015年12月19日
 */
@Controller
@RequestMapping("/loginAdmin")
public class LoginAdminController extends BaseLoginController {

    /**
     * 初始登录页面
     */
    @RequestMapping("/index.do")
    public String index(Map<String, Object> model) {
        model.put("admin", this.getFormForGet());
        return "login";
    }

    /**
     * 登录验证
     */
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public
    @ResponseBody
    String login(HttpServletResponse response) {
        BaseJsonResponse res = loginService.loginForAdmin(this.getFormForPost(), request, response);
        return JSON.toJSONString(res);
    }

    /**
     * 注销登录
     */
    @RequestMapping(value = "loginOut")
    public String loginOut() {
        String url;
        url = "redirect:/loginAdmin/index.do";
        //清空session
        request.getSession().invalidate();
        return url;
    }

}
