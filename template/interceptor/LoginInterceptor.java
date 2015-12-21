package ${packageName}.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ${packageName}.entity.Admin;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 黄云
 * 2015年12月18日
 */
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		//获取用户session
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        //不验证登录的controller
        String url = request.getRequestURI();
        if(null==admin&&!url.contains("loginAdmin/index.do")&&!url.contains("loginAdmin/login.do")){
            try {
                response.sendRedirect("../loginAdmin/index.do");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
	}

}
