// JavaScript Document
// 社会化机构登录页面的js
// 作者：梁韦江

var Login = function () {

	var handleLogin = function() {
		$('.login-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                userName: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                remember: {
	                    required: false
	                }
	            },

	            messages: {
	                userName: {
	                    required: "请输入用户名/邮箱"
	                },
	                password: {
	                    required: "请输入密码"
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	                $('.alert-error', $('.login-form')).show();
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	                form.submit();
	            }
	        });
		
			$("#login_btn").unbind("click").click(function() {
				loginFormCheckAndSubmit();
			});

	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	            	loginFormCheckAndSubmit();
	            }
	        });
	}
	
	var loginFormCheckAndSubmit = function(){
		if ($('.login-form').validate().form()) {
            //$('.login-form').submit();
        	
        	var param = $("#login_form").serialize();
			var url = $("#login_form").attr("action");
			$.post(url, param, function(d) {
				if (d.success) {
					window.location.href = d.data;
				} else {
					if(d.code == -4){
						//未通过邮箱验证时
						var param = d.data.split('&&');
						$("input[name='userId']").val(param[0]);
						$("input[name='bz']").val(param[1]);
						$("#regForBz").submit();
					}else{
						$("#error_div").attr("class", "alert alert-error");
						$("#error").html(d.message)
					}
				}
			}, "json");
        	
        }
        return false;
	}

	var handleForgetPassword = function () {
		$('.forget-form').validate({
	            errorElement: 'span', //default input error message container
	            errorClass: 'help-block', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	                email: {
	                    required: true,
	                    email: true
	                }
	            },

	            messages: {
	                email: {
	                    required: "请输入您的邮箱"
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   

	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.form-group').addClass('has-error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.form-group').removeClass('has-error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	                form.submit();
	            }
	        });

	        $('.forget-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.forget-form').validate().form()) {
	                    $('.forget-form').submit();
	                }
	                return false;
	            }
	        });

	        jQuery('#forget-password').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.forget-form').show();
	        });

	        jQuery('#back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.forget-form').hide();
	        });

	}

    return {
        //main function to initiate the module
        init: function () {
        	
            handleLogin();
            handleForgetPassword();
	       
	       	$.backstretch([
		        "../static/img/login/1.jpg",
		        "../static/img/login/2.jpg",
		        "../static/img/login/3.jpg",
		        "../static/img/login/4.jpg"
		        ], {
		          fade: 1000,
		          duration: 8000
		    });
        }

    };

}();