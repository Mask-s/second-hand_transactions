/**
 * 
 */
var userId = null;
var isEdit = null;
var personId = null;
var	addUrl = '/sht/person/addperson';
var queryPersonByIdUrl = null;
var getsessionurl = '/sht/session/getsession';
var modifyUrl = '/sht/person/modifyperson';
$(function(){	
	personId = getQueryString('personId');
	queryPersonByIdUrl = '/sht/person/getpersonbyid?personId='+personId;
	//shopId 如果为空返回false 否则返回true
	isEdit = personId?true:false;
	if(isEdit){
		getPersonInfo(personId);
	}
	
	$.getJSON(getsessionurl,function(data){
		if(data.success){
			userId = data.session.userId;
		}else{
			window.location.href='/sht/main/login';
		}
	})
	
	function getPersonInfo(personId){
		$.getJSON(queryPersonByIdUrl,function(data){
			if(data.success){
				var person = data.person;
				$('#user-name').val(person.userName);
				$('#password').val(person.passWord);
				$('#true-name').val(person.trueName);
				$('#phone').val(person.phone);
				var addr = person.addr;
				var addrs = new Array();
				addrs = addr.split(' ');
				$("#province").val(addrs[0]);
		        $("#province").trigger("change");
		        $("#city").val(addrs[1]);
		        $("#city").trigger("change");
		        $("#district").val(addrs[2]);
		        $("#district").trigger("change");
			}
		});
	}


})
window.onload = function(){


	$('#submit').click(function(){
		var person = {};
		if(isEdit){
			person.userId = personId;
		}
		var province = $('#province').val();
		var city = $('#city').val();
		var district = $('#district').val();
		person.userName = $('#user-name').val();
		person.passWord = $('#password').val();
		person.trueName = $('#true-name').val();
		person.phone = $('#phone').val();
		person.addr = province+' '+city+' '+district;
		if(!person.userName){
			alert('请输入昵称！');
			return;
		}		
		if(!person.passWord){
			alert('请输入密码！');
			return;
		}		
		if(!person.trueName){
			alert('请输入真实姓名！');
			return;
		}		
		if(!person.phone){
			alert('请输入手机号码！');
			return;
		}
		eval("var reg = /^1[34578]\\d{9}$/;");
		if(!RegExp(reg).test(person.phone)){
			alert('不是手机号码！');
			return;
		}
		if(province==null || province=='' || city==null || city=='' || district==null || district=='' ){
			alert('请正确选择地址');
			return;
		}
		var formData = new FormData();
		/*转成字符流*/
		formData.append('personStr',JSON.stringify(person));
		var verifyCodeActual = $('#j_captcha').val();
		if(!verifyCodeActual){
			alert('请输入验证码！');
			return;
		}
		formData.append('verifyCodeActual',verifyCodeActual);
		$.ajax({
			url : (isEdit?modifyUrl:addUrl),
			type : 'POST',
			data : formData,
			/*同时上传文字和文件用false*/
			contentType : false,
			processData : false,
			cache : false,
			/*如果成功接收到后台返回的数据*/
			success : function(data){
				if(data.success){
					alert('提交成功');
				}else{
					alert('提交失败'+data.errMsg+data.redi);
				}
				$('#captcha').click();
				setTimeout(function(){
					//window.location.href='/sht/main/getperson';
					if(userId == personId){
						window.location.href='/sht/main/login';
					}else{
						//返回并刷新
						window.location.href=document.referrer;
						//history.back(-1);
					}
				},2000);
			}
		})
	});
	
	
	
}