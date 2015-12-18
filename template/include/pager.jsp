<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="row">
	<div class="col-md-5 col-sm-12">
		&nbsp;
		<div id="sample_editable_1_info" class="dataTables_info" role="status"
			aria-live="polite">共 @@@page.itemTotal@@行, 当前第@@@page.pageNo@@ / @@@page.pageTotal@@ 页</div>
	</div>
	<div class="col-md-7 col-sm-12">
		<div id="sample_editable_1_paginate"
			class="pull-right">
			<ul class="pagination">
				<li id="sample_editable_1_previous" class="paginate_button previous<c:if test="@@@!page.prev.bol@@"> disabled</c:if>" aria-controls="sample_editable_1" tabindex="0">
					<a <c:if test="@@@!page.prev.bol@@">href="javascript:void(0)"</c:if><c:if test="@@@page.prev.bol@@">href="<%=basePath %>@@@page.actionUrl@@?pageNo=@@@page.prev.pageNo @@"</c:if>>
						<i class="fa fa-angle-left"></i>&nbsp;
					</a>
				</li>
				
				<c:forEach items="@@@page.pageList@@" var="pageNum">
					<c:if test="@@@pageNum.bol@@">
						<li class="paginate_button active" aria-controls="sample_editable_1" tabindex="0"><a href="javascript:void(0)">@@@pageNum.pageNo@@</a></li>
					</c:if>
					<c:if test="@@@!pageNum.bol@@">
						<li class="paginate_button " aria-controls="sample_editable_1" tabindex="0"><a href="<%=basePath %>@@@page.actionUrl@@?pageNo=@@@pageNum.pageNo@@">@@@pageNum.pageNo@@</a></li>
					</c:if>
				</c:forEach>
				
				<li id="sample_editable_1_next" class="paginate_button next<c:if test="@@@!page.next.bol@@"> disabled</c:if>" aria-controls="sample_editable_1" tabindex="0">
					<a <c:if test="@@@!page.next.bol@@">href="javascript:void(0)"</c:if><c:if test="@@@page.next.bol@@">href="<%=basePath %>@@@page.actionUrl@@?pageNo=@@@page.next.pageNo @@"</c:if>>
							&nbsp;<i class="fa fa-angle-right"></i>
					</a>
				</li>
			</ul>
		</div>
	</div>
</div>

