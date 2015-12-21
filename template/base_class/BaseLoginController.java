package ${packageName}.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import ${packageName}.forms.AdminForm;
import ${packageName}.service.LonginService;
import com.linzi.framework.utils.StringUtils;
import com.linzi.framework.web.BinderUtil;
import com.linzi.framework.web.CookieUtils;

/**
 * @author 顾安妮
 * @date 15/11/29.
 */
public class BaseLoginController {

    public static final String COOKIE_USER_NAME = "userName";
    public static final String COOKIE_PASSWORDE = "password";

    public static final String PASSWORD_STR = "请输入登录密码";

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected LonginService loginService;

    /**
     * 获得展现登录页所需要的内容
     *
     * @return
     */
    protected AdminForm getFormForGet() {

        String username = CookieUtils.getCookieValue(request, COOKIE_USER_NAME, true);
        AdminForm form = BinderUtil.bindForm(request, AdminForm.class, false);
        if (StringUtils.isNotEmpty(username)) {
            // 如果有保存密码
            form.setAccount(username);
            form.setPassword(PASSWORD_STR);
            form.setRemember("1");
        }
        return form;
    }

    /**
     * 获得登录提交的内容
     *
     * @return
     */
    protected AdminForm getFormForPost() {

        // 先从request中获取数据
    	AdminForm form = BinderUtil.bindForm(request, AdminForm.class, false);

        String savedPassword = CookieUtils.getCookieValue(request, COOKIE_PASSWORDE, true);

        if (PASSWORD_STR.equals(form.getPassword()) && StringUtils.isNotEmpty(savedPassword)) {
            // 如果密码是默认的字符串，则从cookie中获取
            form.setPassword(savedPassword);
        }
        return form;
    }
}
