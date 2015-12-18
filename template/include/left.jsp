<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div class="page-sidebar-wrapper">
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN 左侧导航 -->
        <ul class="page-sidebar-menu" data-keep-expanded="false"
            data-auto-scroll="true" data-slide-speed="200">

            <!-- 应用管理 -->
            <li id="li-application"><a href="javascript:;"> <i
                    class="icon-home"></i> <span class="title">应用管理</span> <span
                    class="selected"></span> <span class="arrow "></span>
            </a>
                <ul class="sub-menu">
                    <li class="active"><a href="application/list"> <i class="icon-bulb"></i>
                        应用上架申请
                    </a></li>
                    <!-- 管理用户菜单 -->
                    <c:if test="@@@userType=='administrator'@@">
                        <li><a href="application/list"> <i class="icon-check"></i> 应用审核
                        </a></li>
                        <li><a href="application/list"> <i class="icon-eye"></i> 应用展示管理
                        </a></li>
                        <li><a href="appType/list"> <i class="icon-bar-chart"></i> 应用类型管理
                        </a></li>
                        <li><a href="provider/list"> <i class="icon-user-following"></i> 应用提供商管理
                        </a></li>
                    </c:if>
                </ul>
            </li>
            <!-- 订单管理 -->
            <li id="li-order"><a href="javascript:;"> <i
                    class="icon-basket"></i> <span class="title">订单管理</span> <span
                    class="selected"></span> <span class="arrow "></span>
            </a>
                <ul class="sub-menu">
                    <li><a href="order/list"> <i class="icon-search"></i> 订单查询
                    </a></li>
                </ul>
            </li>
            <!-- 消息管理 -->
            <li id="li-message" class=""><a href="javascript:;"> <i class="icon-globe"></i> <span
                    class="title">消息管理</span> <span
                    class="selected"></span> <span class="arrow "></span>
            </a>
                <ul class="sub-menu">
                    <li><a href="message/list"> <i
                            class="icon-paper-plane"></i> 消息提交
                    </a></li>
                    <!-- 管理用户菜单 -->
                    <c:if test="@@@userType=='administrator'@@">
                        <li><a href="message/list"> <i
                                class="icon-check"></i> 消息审核
                        </a></li>
                        <li><a href="whiteList/list"> <i
                                class="icon-user-following"></i> 消息白名单
                        </a></li>
                    </c:if>
                </ul>
            </li>
            <!-- APP用户管理 -->
            <li id="li-appUser" class=""><a href="client/list"> <i class="icon-user-follow"></i> <span
                    class="title">APP用户管理</span> <span
                    class="selected"></span> <span class="arrow "></span>
            </a>
            </li>

            <!-- 管理用户菜单 -->
            <c:if test="@@@userType=='administrator'@@">

            <!-- 广告管理 -->
            <li id="li-ad" class=""><a href="javascript:;"> <i class="icon-diamond"></i> <span
                    class="title">广告管理</span> <span
                    class="selected"></span> <span class="arrow "></span>
            </a></li>
            <!-- 后台用户管理 -->
            <li id="li-user" class=""><a href="javascript:;"> <i class="icon-user"></i> <span
                    class="title">后台用户管理</span> <span
                    class="selected"></span> <span class="arrow "></span>
            </a>
                <ul class="sub-menu">
                    <li><a href="user/list"> <i
                            class="icon-user"></i> 用户管理
                    </a></li>
                    <li><a href="role/list"> <i
                            class="icon-user"></i> 角色管理
                    </a></li>
                    <li><a href="user/list"> <i
                            class="icon-calculator"></i> 密码修改
                    </a></li>
                </ul>
            </li>
            <!-- 系统管理 -->
            <li id="li-sys" class=""><a href="javascript:;"> <i class="icon-settings"></i> <span
                    class="title">系统管理</span> <span
                    class="selected"></span> <span class="arrow "></span>
            </a>
                <ul class="sub-menu">
                    <li><a href="javascript:;"> <i
                            class="icon-docs"></i> 菜单管理
                    </a></li>
                    <li><a href="javascript:;"> <i
                            class="icon-key"></i> 平台公钥
                    </a></li>
                    <li><a href="javascript:;"> <i
                            class="icon-calendar"></i> 操作日期
                    </a></li>
                </ul>
            </li>
            <!-- 运营数据 -->
            <li id="li-data" class=""><a href="javascript:;"> <i class="icon-bar-chart"></i> <span
                    class="title">运营数据(暂不考虑)</span> <span
                    class="selected"></span> <span class="arrow "></span>
            </a>
            </li>
            </c:if>
            <!-- 推广管理 -->
            <li id="li-extension" class=""><a href="javascript:;"> <i class="icon-rocket"></i> <span
                    class="title">推广管理(暂不考虑)</span><span
                    class="selected"></span> <span class="arrow "></span>
            </a>
                <ul class="sub-menu">
                    <li><a href="extension/list"> <i class="icon-docs"></i>
                        推广申请
                    </a></li>
                    <!-- 管理用户菜单 -->
                    <c:if test="@@@userType=='administrator'@@">
                        <li><a href="extension/list"> <i
                                class="icon-check"></i> 推广审核
                        </a></li>
                    </c:if>
                </ul>
            </li>
        </ul>
        <!-- END SIDEBAR MENU -->
    </div>
</div>
