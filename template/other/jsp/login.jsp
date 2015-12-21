<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<title>云南林业惠农云服务网</title>
	<!-- m 1.2.2版本的css -->
	<link href="../static/css/bootstrap.css" rel="stylesheet" />
	<link href="../static/css/bootstrap-responsive.min.css" rel="stylesheet" />
	<link href="../static/css/metro.css" rel="stylesheet" />
	<link href="../static/css/style.css" rel="stylesheet" />
	
	<!-- 通用的css -->
	<link href="../static/uniform/css/uniform.default.css" rel="stylesheet" />
	<link href="../static/font-awesome.3.0/css/font-awesome.min.css" rel="stylesheet">
	<!-- 页面css -->
	<link href="../static/css/login-ins.css" rel="stylesheet" type="text/css">
	<!-- 自定义的css -->
	<style type="text/css">
	
	/** 设置debug显示样式 快速方案，直接加。否则会影响登录样式，有时间在改 */
	.debug_none {
		position: absolute;
		display: block;
		bottom: 0px;
		right: 1px !important;
		right: 18px;
		width: 150px;
		line-height: 40px;
		position: fixed;
		border: 1px solid #fff;
		text-align: center;
		border-radius: 10px;
		display: none;
	}
	
	.debug_p {
		font-size: 22px;
		color: red;
		font-family: "经典圆体繁", "microsoft yahei", "Arial Rounded MT Bold",
			"Helvetica Rounded", Arial, sans-serif;
		text-shadow: 0 0 5px #fff, 0 0 10px #fff, 0 0 15px #fff, 0 0 40px
			#ff00de, 0 0 70px #ff00de;
	}
	
	.debug_css {
		display: inline;
	} 
	</style>
</head>
<body class="login">
	<!-- BEGIN LOGO -->
	<div class="logo_ywjg"></div>
	<!-- END LOGO -->

	<div class="row-fluid">
	  <div class="span4">
	    <!-- BEGIN LOGIN -->
	    <div class="content">
	      <!-- BEGIN LOGIN FORM -->
	      <form class="login-form" action="login.do" method="post" id="login_form">
	        <input type="hidden" name="redirectUrl" value="">
	        <h3 class="form-title">标题</h3>
	        <div class="alert alert-error hide" id="error_div">
		      <span id="error">请输入用户名和密码</span>
		    </div>
	        <div class="form-group">
	          <label class="control-label">用户名:</label>
	          <div class="input-icon left">
	            <i class="icon-user"></i>
	            <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入用户名" name="account" value="${admin.account}"/>
	          </div>
	        </div>
	        <div class="form-group">
	          <label class="control-label">密码</label>
	          <div class="input-icon left">
	            <i class="icon-lock"></i>
	            <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="请输入密码" name="password" value="${admin.password}"/>
	          </div>
	        </div>
	        <div>
	           <label class="checkbox">
	            <input type="checkbox" name="remember" value="1" <c:if test="${admin.remember==1 }">checked</c:if>/>
	            记住密码 </label>
	        </div>
	        <div class="form-actions">
	          <button type="button" id="login_btn" class="btn blue pull-left"> 登录 <i class="m-icon-swapright m-icon-white"></i>
	          </button>
	        </div>
	       <!--  <div class="alert alert-error hide">
	          <span>请输入用户名和密码</span>
	        </div> -->
	      </form>
	      <!-- END LOGIN FORM -->
	      
	    </div>
	    <!-- END LOGIN -->
	  </div>
	  <div class="span8 ">
	    <div class="help">
		       <h3>帮助</h3>
		      <p>大概介绍下你的项目</p>
		      <hr/>
	    </div>
	  </div>
	</div>
	
	<form action="reg.do" method="post" id="regForBz" style="display: none">
		<input name="userId" value=""/>
		<input name="bz" value=""/>
	</form>
	<!-- END LOGIN -->

	<!-- BEGIN COPYRIGHT -->
	<div class="copyright">2014 &copy; 云南林资科技有限公司.</div>
	<!-- END COPYRIGHT -->
	  
</body>
	<script type="text/javascript" src="../static/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="../static/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../static/js/jquery.backstretch.min.js"></script>
	<script type="text/javascript" src="../static/js/linzi-login-soc.js"></script>
	<script type="text/javascript" src="../static/js/jquery.validate.js"></script>
	<script type="text/javascript" src="../static/js/jquery.validate.i18n.zh_cn.js"></script>
<script type="text/javascript">
	$(function() {
		Login.init();
	});
	
	var form2 = $('#reset_pwd_form');
	var error2 = $('.alert-error', form2);
	var success2 = $('.alert-success', form2);

	function sub() {
		var param = form2.serialize();
		var url = form2.attr("action");
		$.post(url, param, function(d) {
			alert(d.message);
			if (d.success) {
				form2[0].reset();
			}
		}, "json");
	}

	form2.submit(function() {
		return false;
	});

	$(function() {
		$("#register-submit-btn").unbind("click").click(function() {
			form2.submit();
		});
	});

	form2.validate({
		errorElement : 'span', //default input error message container
		errorClass : 'help-inline', // default input error message class
		focusInvalid : false, // do not focus the last invalid input
		ignore : "",
		rules : {
			account : {
				minlength : 1,
				required : true,
			},
			pwd : {
				required : true,
				minlength : 6
			},
			rpassword : {
				required : true,
				equalTo : "#register_password"
			}
		},

		messages : { // custom messages for radio buttons and checkboxes
			membership : {
				required : "Please select a Membership type"
			},
			service : {
				required : "Please select  at least 2 types of Service",
				minlength : jQuery
						.format("Please select  at least {0} types of Service")
			}
		},

		errorPlacement : function(error, element) { // render error placement for each input type
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

		invalidHandler : function(event, validator) { //display error alert on form submit   
			success2.hide();
			error2.show();
			//App.scrollTo(error2, -200);
		},

		highlight : function(element) { // hightlight error inputs
			$(element).closest('.help-inline').removeClass('ok'); // display OK icon
			$(element).closest('.control-group').removeClass('success')
					.addClass('error'); // set error class to the control group
		},

		unhighlight : function(element) { // revert the change dony by hightlight
			$(element).closest('.control-group').removeClass('error'); // set error class to the control group
		},

		success : function(label) {
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
		},

		submitHandler : function(form) {
			success2.show();
			error2.hide();
			sub();
		}

	});
</script>

</html>