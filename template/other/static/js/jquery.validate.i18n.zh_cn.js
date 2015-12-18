$.validator.addMethod("mobile", function(value, element) {   
    var mobile = /^[1][0-9][0-9]{9}$/;
    return this.optional(element) || (mobile.test(value.trim()));
}, "手机号格式错误！");

$.validator.addMethod("orgCode", function(value, element) {   
    var orgCode = /(^\\\\d{9}$)|(^[0-9a-zA-Z]{8}-[0-9|X]{1}$)/;
    return this.optional(element) || (orgCode.test(value.trim()));
}, "组织机构代码格式错误！");

$.validator.addMethod("card", function(value, element) {   
    var card = /^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X)?$/;
    return this.optional(element) || (card.test(value.trim()));
}, "身份证格式错误！");

$.validator.addMethod("cn", function(value, element) {   
    var cn = /^[A-Za-z\u4e00-\u9fa5]+$/;
    return this.optional(element) || (cn.test(value.trim()));
}, "只能输入中文或英文！");

//小数位不能超过多少位
$.validator.addMethod("decimalsForOne",function(value,element){    
	var reg =/(^\+?(\d*)$)|(^\+?(\d*\.\d{1})$)/;
	return this.optional(element) || (reg.test(value.trim()));
 }, "请输入一个数值,并且小数位不能超过1位！");

$.validator.addMethod("decimalsForTwo",function(value,element){    
	var reg =/(^\+?(\d*\.\d{2})$)|(^\+?(\d*)$)|(^\+?(\d*\.\d{1})$)/;
	return this.optional(element) || (reg.test(value.trim()));
}, "请输入一个数值,并且小数位不能超过2位！");

$.validator.addMethod("isNotZero",function(value,element){    
	return value.trim() != 0;
}, "请输入一个不等于0的值");

$.validator.messages= {
	required: "该字段为必填项！",
	remote: "请改正这个字段！",
	email: "请输入一个有效的邮箱地址！",
	mobile: "手机号格式错误！",
	orgCode: "组织机构代码格式错误！",
	card: "身份证格式错误！",
	cn: "只能输入中文或英文！",
	url: "请输入一个有效的URL！",
	date: "请输入一个有效的日期！",
	dateISO: "请输入一个有效的日期 (ISO)！",
	number: "请输入一个有效的数字！",
	digits: "请输入一个有效的整数！",
	creditcard: "请输入一个有效的信用卡账号 ！",
	equalTo: "请再次输入一个相同的值！",
	maxlength: $.validator.format("请输入不超过 {0} 字符！"),
	minlength: $.validator.format("请输入至少  {0} 字符！"),
	rangelength: $.validator.format("请输入一个长度在 {0} 和 {1} 之间的字符！"),
	range: $.validator.format("请输入一个在 {0} 和  {1} 之间的值！"),
	max: $.validator.format("请输入一个小于等于  {0} 的值！"),
	min: $.validator.format("请输入一个大于等于 {0}的值！"),
	decimalsForOne: "请输入一个数值,并且小数位不能超过1位！",
	decimalsForTwo: "请输入一个数值,并且小数位不能超过2位！",
	isNotZero: "请输入一个不等于0的值！"
}