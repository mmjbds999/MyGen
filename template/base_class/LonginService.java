package ${packageName}.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${packageName}.controller.BaseLoginController;
import ${packageName}.dao.AdminDao;
import ${packageName}.entity.Admin;
import ${packageName}.forms.AdminForm;
import ${packageName}.service.json.JsonLoginResponse;
import com.linzi.framework.utils.EncryptUtil;
import com.linzi.framework.web.CookieUtils;

/**
 * <pre>
 * 登录service
 * </pre>
 * 
 * @author 黄云
 * 2015年12月19日
 */
@Service
public class LonginService {

    public static final int COOKIE_MAX_SECOND_AGE = 60 * 24 * 365;// cookie存1年
    private static Logger logger = Logger.getLogger(LonginService.class);

    @Autowired
    private AdminDao adminDao;

    /**
     * 系统后台登录(管理员)
     * @return
     */
    public JsonLoginResponse loginForAdmin(AdminForm form, HttpServletRequest request, HttpServletResponse response) {
        JsonLoginResponse res = new JsonLoginResponse();

        Admin admin = adminDao.findByUserNameAndPassword(form.getAccount(), EncryptUtil.md5(form.getPassword()));

        if (admin != null) {
            // 如果登录成功，就将用户保存到session
            request.getSession().setAttribute("admin", admin);
            //session保存登录用户类型
            request.getSession().setAttribute("adminType","administrator");

            //处理cookie
            saveCookie(form,request,response);

            logger.debug("管理员登录:"+form.getAccount());// 将要跳转的url传会给客户端
            res.setData(String.format("../%s", "admin/list.do"));
        } else {
            res.setCodeError("用户名或密码不正确，请重新输入!");
            logger.warn("登录失败,找不到管理员:" + form.getAccount());// 将要跳转的url传会给客户端s
        }
        return res;
    }

    //根据用户选择处理cookie
    private void saveCookie(AdminForm form, HttpServletRequest request, HttpServletResponse response){
        //获取cookiepath
        String uri = request.getRequestURI();
        int startOfAction = uri.lastIndexOf("/");
        String cookiePath = uri.substring(0, startOfAction);
        //若记住密码 则把用户登录信息保存到cookie
        if(form.getRemember().equals("1")){
            // cookie记录用户名和密码
            try {
                CookieUtils.addEncryptCookie(response, cookiePath, BaseLoginController.COOKIE_USER_NAME, form.getAccount(), null,
                        COOKIE_MAX_SECOND_AGE);
                CookieUtils.addEncryptCookie(response, cookiePath, BaseLoginController.COOKIE_PASSWORDE, form.getPassword(), null,
                        COOKIE_MAX_SECOND_AGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{//若不记住密码 则清除cookie
            CookieUtils.removeCookie(response, cookiePath, BaseLoginController.COOKIE_USER_NAME, null);
            CookieUtils.removeCookie(response, cookiePath, BaseLoginController.COOKIE_PASSWORDE, null);
        }
    }
}
