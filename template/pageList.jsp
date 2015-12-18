<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">

    <title>${modName }</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<jsp:include page="includes/css.jsp"/>

  </head>

  <body class="page-header-fixed page-quick-sidebar-over-content page-style-square">
  	<jsp:include page="includes/header.jsp"/>

  	<div class="clearfix"></div>

    <div id="showJson"></div>

	<!-- 控制left.jsp活动元素 -->
	<input type="hidden" id="leftNav" value="li-${pageName}">

    <div class="page-container">
    	<jsp:include page="includes/left.jsp"/>

    	<div class="page-content-wrapper">
			<div class="page-content">
				<!-- BEGIN 内容信息头部-->
				<h3 class="page-title">
					${modName }
				</h3>
				<P class="text-muted">查看应用最近使用情况</P>
				<div class="page-bar">
					<ul class="page-breadcrumb">
						<li>
							<i class="fa fa-home"></i>
							当前所在位置:
							<a href="application/list">${modNameCN }</a>
							<i class="fa fa-angle-right"></i>
							<a href="${pageName}/list">${modName }</a>
						</li>
					</ul>
					<div class="page-toolbar">
					</div>
				</div>
				<!-- END 内容信息头部-->
				<div class="clearfix"></div>

				<!-- BEGIN 标签页-->
				<div class="row">
					<div class="col-md-12">
						<div class="portlet box blue-hoki">
							<div class="portlet-title">
								<ul class="nav nav-tabs">
									<#if pageType=="all" || pageType=="list" || pageType=="list_no">
									<li class="active" id="listTab"><a href="#listApp" data-toggle="tab">列表管理</a></li>
									</#if>
									<#if pageType=="all" || pageType=="edit">
									<li id="addTab"><a href="#newApp" data-toggle="tab" <#if pageType=="all">onclick="addReset();</#if>"><#if pageType=="edit">${modName }<#else>新建</#if></a></li>
									</#if>
								</ul>
							</div>
							<div class="portlet-body">
								<div class="tab-content">
									<#if pageType=="all" || pageType=="edit">
									<!-- BEGIN 新建应用-->
									<div class="tab-pane fade<#if pageType=="edit"> in active</#if>" id="newApp">
										<form action="${pageName}/save" method="post" id="form" enctype="multipart/form-data"
	                                          class="form-horizontal form-bordered account-detail-list"
	                                          novalidate="novalidate">
	                                        <input type="hidden" id="h_id" name="id"/>
	                                        <#if addList??>
	                                        <#list addList as add>
	                                        	<#if add.isHidden>
                                        	<input type="hidden" id="${add.name }" name="${add.name }" value="${add.defaultVal }"/>
	                                        	</#if>
	                                        </#list>
	                                        </#if>
	                                        <#if addList??>
	                                        <#list addList as add>
	                                        <#if !add.isHidden && add.comment??>
	                                        <div class="form-group">
	                                            <label class="col-md-3 control-label">${add.comment }</label>
	                                        	<#if add.saveType=="select">
	                                            <div class="col-md-4">
	                                                <select class="form-control valid" id="${add.name }" name="${add.name }"
	                                                        aria-required="true" aria-invalid="false"
	                                                        aria-describedby="appType-error appType-error appType-error appType-error appType-error appType-error">
	                                                    <#if add.saveCodes??>
	                                                    <#list add.saveCodes as code>
		                                                <option value="${code }">${add.saveNames[code_index] }</option>
		                                                </#list>
		                                                </#if>
	                                                </select>
	                                            </div>
	                                            <#elseif add.saveType=="selectvo">
	                                        	<div class="col-md-4">
	                                        		<select class="form-control valid" id="${add.name }" name="${add.name }.id"
	                                                        aria-required="true" aria-invalid="false"
	                                                        aria-describedby="appType-error appType-error appType-error appType-error appType-error appType-error">
		                                                <option value="">--请选择--</option>
	                                                </select>
	                                            </div>
	                                            <#elseif add.saveType=="checkboxvo">
                                                <div class="col-md-5" id="ck${add.name }">

	                                            </div>
	                                        	<#elseif add.saveType=="radio">
	                                        	<div class="col-md-4">
	                                        		<#if add.saveCodes??>
	                                        		<#list add.saveCodes as code>
	                                                <label class="radio-inline" for="${add.name }-${code }">
	                                                    <input type="radio" id="${add.name }-${code }" name="${add.name }" value="${code }" <#if code_index==0>checked="checked"</#if>> ${add.saveNames[code_index] }
	                                                </label>
	                                                </#list>
	                                                </#if>
	                                            </div>
	                                        	<#elseif add.saveType=="checkbox">
	                                            <div class="col-md-5">
	                                            	<#if add.saveCodes??>
		                                        	<#list add.saveCodes as code>
	                                                <label for="${add.name }-${code }" class="checkbox-inline">
	                                                    <input type="checkbox" id="${add.name }-${code }" name="${add.name }"
	                                                           value="${code }"> ${add.saveNames[code_index] }
	                                                </label>
	                                                </#list>
	                                                </#if>
	                                            </div>
	                                        	<#elseif add.saveType=="background">
	                                        	<div class="col-md-8">
	                                        	<span class="help-block">请选择一个颜色</span>
	                                        		<#if add.saveColors??>
	                                        		<#list add.saveColors as color>
                                                <label class="radio-inline" for="radio-color-${color_index+1 }">
                                                    <input type="radio" id="radio-color-${color_index+1 }" name="${add.name }"
                                                           value="${add.saveCodes[color_index] }" <#if color_index==0>checked="checked"</#if>>

                                                    <div class="app-icon-wrap ${add.saveNames[color_index] }1"> </div>
                                                </label>
	                                                </#list>
	                                                </#if>
	                                            </div>
	                                        	<#elseif add.saveType=="date" || add.saveType=="datetime">
                                                <div class="col-md-4">
													<input class="form-control datepicker" data-provide="datepicker" data-date-format="yyyy-mm-dd" 
														   id="${add.name }" name="${add.name }" readonly="readonly" type="text" placeholder="${add.comment }">
                                                </div>
                                                <#elseif add.saveType=="textarea">
                                                <div class="col-md-4">
                                                    <textarea id="${add.name }" name="${add.name }" rows="5"
                                                              class="form-control" placeholder=""></textarea>
                                                </div>
                                                <!-- <div class="col-md-3"><span style="color: red">*注：</span>
                                                    <a href="key/publickey.action"
                                                       style="color: red;text-decoration:underline;">说明这是啥</a>
                                                </div> -->
	                                        	<#elseif add.saveType=="textedit">
	                                        	<div class="col-md-9">
                                                    <textarea id="${add.name }" name="${add.name }"></textarea>
                                            	</div>
	                                        	<#elseif add.saveType=="file" || add.saveType=="img">
	                                        	<div class="col-md-3">
													<input type="file" id="${add.name }" class="dropify-fr" name="${add.name }_file"
	                                                data-height="250" data-default-file="" data-max-file-size="200K"/>
	                                                <!--<span class="help-block">图标大小为60x60(px),格式为png</span>-->
	                                            </div>
	                                            <#elseif add.typeName?index_of(".entity")!=-1>
	                                        	<div class="col-md-4">
	                                                <input type="text" id="${add.name }" name="${add.name }.id" class="form-control"
	                                                       placeholder="" maxlength="${add.length }"> <span
	                                                    class="help-block">${add.length }个字符以内</span>
	                                            </div>
	                                            <#else>
	                                            <div class="col-md-4">
	                                                <input type="text" id="${add.name }" name="${add.name }" class="form-control"
	                                                       placeholder="" maxlength="${add.length }"> <span
	                                                    class="help-block">${add.length }个字符以内</span>
	                                            </div>
	                                        	</#if>
	                                        </div>
	                                        </#if>
	                                        </#list>
	                                        </#if>
	                                        <div class="form-group form-actions">
	                                            <div class="col-md-4 col-md-offset-4">
	                                                <button type="submit" class="btn btn-primary">
	                                                    <i class="fa fa-angle-right"></i> 提交
	                                                </button>
	                                                <#if pageType=="all">
	                                                <button type="button" class="btn btn-default"
	                                                        onclick="backListTab()">
	                                                    <i class="fa fa-arrow-left"></i> 返回${modName }
	                                                </button>
	                                                </#if>
	                                            </div>
	                                        </div>
	                                    </form>
									</div>
									<!-- END 新建应用-->
									</#if>
									<#if pageType=="all" || pageType=="list" || pageType=="list_no">
									<!-- BEGIN 列表管理-->
									<div class="tab-pane fade in active" id="listApp">
										<form action="${pageName }/list" method="post" id="listSearchForm">
											<div class="row">
										<#if searchList??>
										<#list searchList as search>
											<#if search.showType == "select">
											<div class="col-xs-2">
	                                            <select id="${search.name }_search" name="${search.name }" class="form-control" size="1">
	                                                <option value="-1">所有(${search.comment })</option>
	                                                <#list search.codes as code>
	                                                <option value="${code }">${search.names[code_index] }</option>
	                                                </#list>
	                                            </select>
	                                        </div>
											<#elseif search.showType == "date" || search.showType == "datetime">
											<div class="col-xs-2">
												<input id="${search.name }_s" name="${search.name }_s" title="${search.comment }--开始"
													class="form-control datepicker" data-provide="datepicker" data-date-format="yyyy-mm-dd" type="text" placeholder="${search.comment }--开始">
												-
												<input id="${search.name }_e" name="${search.name }_e" title="${search.comment }--结束"
													class="form-control datepicker" data-provide="datepicker" data-date-format="yyyy-mm-dd" type="text" placeholder="${search.comment }--结束">
											</div>
											<#else>
											<div class="col-xs-2">
												<input id="${search.name }_search" class="form-control" type="text" value="@@@form.${search.name } @@"
													placeholder="${search.comment }" name="${search.name }" title="${search.comment }">
											</div>
											</#if>
											<#if search_index==searchList?size-1>
											<div class="col-xs-2">
												<input id="cpId" type="hidden" value="61" name="cpId">
												<span class="input-group-btn">
													<button id="querybtn" class="btn btn-primary" type="submit">
														<i class="fa fa-search"></i> 查询
													</button>
													
												</span>
												<span class="input-group-btn">
													<button id="resetbtn" class="btn btn-primary" type="reset">
														<i class="fa fa-search"></i> 重置
													</button>
													
												</span>
											</div>
											<#else>
												<#if (search_index+1)%6==0>
										</div>
										<br>
										<div class="row">
												</#if>
											</#if>
										</#list>
										</#if>
										</div>
										</form>

										<div class="portlet-body">
											<div class="table-scrollable">
												<table class="table table-striped table-bordered table-hover"
													id="sample_1">
													<thead>
														<tr>
															<#if showList??>
															<#list showList as cName>
															<th>${cName.comment }</th>
															</#list>
															</#if>
															<th>操作</th>
														</tr>
													</thead>
													<tbody>
														<c:if test="@@@${pageName }List!=null@@">
															<c:forEach items="@@@${pageName }List@@" var="var" >
																<tr>
																	<#if showList??>
																	<#list showList as clm>
																	<#if (clm.saveType=="select" || clm.saveType=="radio" || clm.saveType=="selectvo" || clm.saveType=="checkboxvo")>
																	<td>@@@var.${clm.name }_vo@@</td>
																	<#elseif clm.typeName?index_of(".entity")!=-1>
																	<td>@@@var.${clm.name }.${clm.voFieldName }@@</td>
																	<#else>
																	<td>@@@var.${clm.name }@@</td>
																	</#if>
																	</#list>
																	</#if>
																	<td>
																		<div class="btn-group">
																			<button class="btn btn-info btn-sm" data-target="#modal-adInfo-detail" data-toggle="modal" type="button" onclick="showView(@@@var.id@@)">查看 </button>
						                                                    <#if pageType=="all" || pageType=="list">
						                                                    <#if pageType=="all">
						                                                    <a href="javascript:void(0)" class="btn btn-sm btn-primary" onclick="getEditData(@@@var.id@@)">修改</a>
						                                                    </#if>
						                                                    <a href="#modal-app-delete" class="btn btn-sm btn-danger" data-toggle="modal" onclick="setDelId(@@@var.id@@)">删除</a>
						                                                    <#if addList??>
																		  	<#list addList as add>
																				<#if add.optionName!="">
						                                                    <a href="javascript:void(0)" class="btn btn-sm btn-primary" onclick="${add.name}(@@@var.id@@,@@@var.${add.name}@@)"><c:if test="@@@var.${add.name}==1 @@">取消</c:if>${add.optionName}</a>
					                                                    		</#if>
					                                                    	</#list>
					                                                    	</#if>
					                                                    	</#if>
					                                                    </div>
																	</td>
																</tr>
															</c:forEach>
														</c:if>
														<c:if test="@@@${pageName }List==null@@">
															<tr>
																<td colspan="${showList?size + 1 }">暂无数据</td>
															</tr>
														</c:if>
													</tbody>
												</table>
											</div>
											<jsp:include page="includes/pager.jsp"/>
										</div>
									</div>
									<!-- END 列表管理-->
									</#if>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- END 标签页-->
			</div>
		</div>
		<!-- END CONTENT -->
		<!-- BEGIN QUICK SIDEBAR -->
		<a href="javascript:;" class="page-quick-sidebar-toggler"><i class="icon-close"></i></a>
		<!-- END QUICK SIDEBAR -->
    </div>


	<!-- Begin 详情-->
	<div id="modal-adInfo-detail" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">详情</h4>
				</div>
				<div class="modal-body titleholder">
					<form action="" method="post" enctype="multipart/form-data"
						class="form-horizontal form-bordered" onsubmit="return false;">
						<#if addList??>
                        <#list addList as add>
                        <#if add.comment??>
                        <div class="form-group">
                            <label class="col-md-3 control-label">${add.comment }</label>
                        	<#if add.saveType=="img" || add.saveType=="file">
                            <div class="col-md-9">
								<img src="" class="ad-pic-wrap" id="${add.name }_view">
							</div>
                        	<#else>
                            <div class="col-md-9">
								<p class="form-control-static" id="${add.name }_view"></p>
							</div>
                        	</#if>
                        </div>
                        </#if>
                        </#list>
                        </#if>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">关闭
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-- END 详情modal-->
	<#if pageType=="all" || pageType=="list">
	<!-- BEGIN 删除modal -->
	<div id="modal-app-delete" class="modal hny-delete-modal fade"
		tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		aria-hidden="true" style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">删除</h4>
				</div>
				<div class="modal-body">
					确定要删除吗？
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						data-dismiss="modal" id="delete"
						value="" page="${pageName }">确定</button>
					<button type="button" class="btn btn-default"
						data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- END 删除modal-->
	</#if>

	<jsp:include page="includes/footer.jsp"/>
  </body>

  <jsp:include page="includes/js.jsp"/>

  <script type="text/javascript">
  	jQuery(document).ready(function(){
  		
  		<#if pageType=="edit">
  		$.post("${pageName }/all", {}, function(d) {
  			if(d.length>0){
  				getEditData(d[0].id);
  			}
  		},"json");
  		</#if>
  		
  		<#if pageType=="all" || pageType=="list" || pageType=="list_no">
  		/**
  		 * 列表查询重置
  		 */
  		$("#resetbtn").click(function(){
  			window.location.href = "${pageName }/list?reset=1";
  		});
  		</#if>
  		
		//控制左侧导航栏激活状态
		var leftNavVal = $("#leftNav").val();
		$("#" + leftNavVal).attr("class", "active");

		<#if pageType=="all" || pageType=="edit">
  		<#if addList??>
  		//初始化单文件上传控件
  		<#list addList as add>
  		<#if add.saveType=="img" || add.saveType=="file">
  		$("#${add.name}").dropify({
			messages : {
				'default' : '点击或拖拽文件到这里',
				'replace' : '点击或拖拽文件到这里来替换文件',
				'remove' : '移除文件',
				'error' : '对不起，你上传的文件太大了'
			}
		});
 		</#if>
	    </#list>
	    </#if>
	    </#if>

		//初始化日期控件
		$(".datepicker").datepicker().on('changeDate', function () {
			$(".datepicker").datepicker('hide');
		});
		
		<#if pageType=="all" || pageType=="list" || pageType=="list_no">
		<#if searchList??>
		<#list searchList as search>
		<#if search.showType == "date">
		var ${search.name}_s = "@@@form.${search.name}_s @@";
		var ${search.name}_e = "@@@form.${search.name}_e @@";
		if(${search.name}_s){
			$("#${search.name}_s").val(FormatDate(${search.name}_s));
		}
		if(${search.name}_e){
			$("#${search.name}_e").val(FormatDate(${search.name}_e));
		}
		</#if>
		</#list>
	    </#if>
	    </#if>
		
	    <#if pageType=="all" || pageType=="edit">
		<#if addList??>
  		<#list addList as add>
  		<#if add.saveType=="textedit">
		//初始化编辑器
        UM.getEditor('${add.name}', {
            toolbar: [
                'undo redo | bold italic underline  horizontal | superscript subscript | forecolor backcolor | removeformat |',
                'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize',
                '| justifyleft justifycenter justifyright justifyjustify |',
                'link unlink | image'
            ],
            lang: "zh-cn",
            charset: "utf-8",
            initialFrameWidth: 600, //初始化编辑器宽度,默认600
            initialFrameHeight: 400  //初始化编辑器高度,默认400
        });
        </#if>
	    </#list>
	    </#if>
	    </#if>

	    <#if pageType=="all" || pageType=="edit">
		<#if addList??>
	    <#list addList as add>
	    	<#if add.saveType=="selectvo">
    	/** 根据vo填充下拉选项 */
	    $.post("${add.voName}", {}, function(d){
    		$.each(d,function(i, item){
    			$("#${add.name}").append("<option value='"+item.id+"'>"+item.${add.voFieldName}+"</option>");
   		  	});
	    },"json");
	    	</#if>
	    </#list>
	    </#if>

		<#if addList??>
	    <#list addList as add>
	    	<#if add.saveType=="checkboxvo">
    	/** 根据vo填充checkbox选项 */
	    $.post("${add.voName}", {}, function(d){
    		var html = "";
    		$.each(d,function(i, item){
    			html += '<label for="${add.name }-'+item.id+' " class="checkbox-inline">'+
			                    '<input type="checkbox" id="${add.name }-'+item.id+'" name="${add.name }"'+
			                        'value="'+item.id+'"> '+item.${add.voFieldName}+
			               '</label>';
   		  	});
    		$("#ck${add.name}").html(html);
	    },"json");
	    	</#if>
	    </#list>
	    </#if>
	    
	    /** 表单验证 **/
		$('#form').validate({
			errorElement : 'span', //default input error message container
			errorClass : 'help-block', // default input error message class
			focusInvalid : false, // do not focus the last invalid input
			rules : {
				<#if addList??>
			    <#list addList as add>
			    	<#if !add.isHidden>
				${add.name} : {
					<#list add.valids as v>
					${v} : <#if v=="equalTo">"#${add.param}"<#else>true</#if><#if v_index!=add.valids?size-1>,</#if>
					</#list>
				},
		    		</#if>
			    </#list>
			    </#if>
			},
			errorPlacement : function(error, element) { // render error placement for each input type
				element.parent().parent().attr("class", "form-group has-error");
				if (element.attr("name") == "education") { // for chosen elements, need to insert the error after the chosen container
					error.insertAfter("#form_2_education_chzn");
				} else if (element.attr("name") == "membership") { // for uniform radio buttons, insert the after the given container
					error.addClass("no-left-padding").insertAfter(
							"#form_2_membership_error");
				} else if (element.attr("name") == "service") { // for uniform checkboxes, insert the after the given container
					error.addClass("no-left-padding").insertAfter(
							"#form_2_service_error");
				} else {
					error.insertAfter(element); // for other inputs, just perform default behavoir
				}
			},
			success : function(label) {
				label.parent().parent().attr("class", "form-group");
				if (label.attr("for") == "service"
						|| label.attr("for") == "membership") { // for checkboxes and radip buttons, no need to show OK icon
					label.closest('.control-group').removeClass('error').addClass(
							'success');
					label.remove(); // remove error label here
				} else { // display success icon for other inputs
					label.addClass('valid').addClass('help-inline ok') // mark the current input as valid and display OK icon
					.closest('.control-group').removeClass('error').addClass(
							'success'); // set success class to the control group
				}
			}
		});
		</#if>
  	});

  	<#if pageType=="all" || pageType=="list">
  	<#if addList??>
  	<#list addList as add>
		<#if add.optionName!="">
  	/** ${add.optionName} **/
  	function ${add.name}(id, value){
  		$.post("${pageName }/changeStatus", {"id":id, "option":"${add.name}", "${add.name}":value}, function(d) {
  			if (d.code=="200") {
  				var status = value==1?"取消":"";
  				alert(status+"${add.optionName}成功！");
  				$("#querybtn").click();
  			}else{
  				alert("${add.optionName}失败！");
  			}
  		},"json");
  	}
    	</#if>
    </#list>
    </#if>
    </#if>

    <#if pageType=="all" || pageType=="list" || pageType=="list_no">
  	/** 查看--暂时用的是添加的项来迭代，有需要的时候再换 */
  	function showView(id){
  		var url = "${pageName }/view";
  		$.post(url, {"id":id}, function(d) {
  				$("#form")[0].reset();
  				<#if addList??>
                <#list addList as add>
                	<#if add.saveType=="img">
				if(d.${add.name } || d.${add.name }>-1){
  					$("#${add.name }_view").attr("src", d.${add.name });
  				}
					<#elseif (add.saveType=="select" || add.saveType=="radio" || add.saveType=="selectvo"
							|| add.saveType=="background" || add.saveType=="checkbox" || add.saveType=="checkboxvo")>
				if(d.${add.name } || d.${add.name }>-1){
  					$("#${add.name }_view").html(d.${add.name }_vo);
  				}
					<#elseif (add.saveType=="img" || add.saveType=="file")>
				if(d.${add.name } || d.${add.name }>-1){
  					$("#${add.name }_view").src(d.${add.name });
  				}
					<#elseif add.typeName?index_of(".entity")!=-1>
				if(d.${add.name } || d.${add.name }>-1){
  					$("#${add.name }_view").html(d.${add.name }.${add.voFieldName});
  				}
                	<#else>
				if(d.${add.name } || d.${add.name }>-1){
  					$("#${add.name }_view").html(d.${add.name });
  				}
                	</#if>
                </#list>
                </#if>
  		}, "json");
  	}
  	</#if>
  	
  	<#if pageType=="all" || pageType=="edit">
	  	/** 编辑--内容填充 */
	  	function getEditData(id){
	  		var url = "${pageName }/view";
	  		$.post(url, {"id":id}, function(d) {
	  				$("#form")[0].reset();
	  				$("#h_id").val(d.id);
	  				<#if addList??>
	                <#list addList as add>
	                	<#if add.saveType=="textarea" || add.saveType=="textedit">
					if(d.${add.name } || d.${add.name }>-1){
	  					$("#${add.name }").html(d.${add.name });
	  				}
						<#elseif add.saveType=="radio" || add.saveType=="background" || add.saveType=="checkbox" || add.saveType=="checkboxvo">
					if(d.${add.name } || d.${add.name }>-1){
						if(d.${add.name }.length>1&&d.${add.name }.indexOf(",")!=-1){
							$.each(d.${add.name }.split(","),function(i, item){
								$("#form #${add.name }-"+item+"[value="+item+"]").attr("checked",true);
			    		  	});
						}else{
							$("#form input[name='${add.name }'][value="+d.${add.name }+"]").attr("checked",true);
						}
					}
						<#elseif add.saveType=="img" || add.saveType=="file">
					if(d.${add.name } || d.${add.name }>-1){
						$("#${add.name }").attr("data-default-file",d.${add.name });
						$("#${add.name }").parent().find(".dropify-preview").attr("style","display:block;");
						var fName = d.${add.name }.substring(d.${add.name }.lastIndexOf("/")+1);
						$("#${add.name }").parent().find(".dropify-filename-inner").html(fName);
						$("#${add.name }").parent().find(".dropify-render").html("<img src='"+d.${add.name }+"'>");
					}
						<#elseif add.typeName?index_of(".entity")!=-1>
					if(d.${add.name } || d.${add.name }>-1){
	  					$("#${add.name }").val(d.${add.name }.id);
	  				}
	                	<#else>
					if(d.${add.name } || d.${add.name }>-1){
	  					$("#${add.name }").val(d.${add.name });
	  				}
	                	</#if>
	                </#list>
	                </#if>
					$("#addTab").attr("class", "active");
					$("#listTab").attr("class", "");
					$("#listApp").attr("class", "tab-pane fade");
					$("#newApp").attr("class", "tab-pane fade in active");
	  		}, "json");
	  	}
	  	</#if>

  </script>
</html>
