<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div class="page-sidebar-wrapper">
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN 左侧导航 -->
        <ul class="page-sidebar-menu" data-keep-expanded="false"
            data-auto-scroll="true" data-slide-speed="200">

            <#if modelList??>
            <#list modelList as mod>
            <!-- APP用户管理 -->
            <li id="li-${mod.modelName }" class=""><a href="${mod.modelName }/list.do?reset=0"> <i class="icon-user-follow"></i> <span
                    class="title">${mod.modelNameCN }</span> <span
                    class="selected"></span> <span class="arrow "></span>
            </a>
            </li>
            </#list>
            </#if>
            
        </ul>
        <!-- END SIDEBAR MENU -->
    </div>
</div>
