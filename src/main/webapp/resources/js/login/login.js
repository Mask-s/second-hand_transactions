


$(function(){
	$('#entry_btn').click(function(){
		var username = $('#entry_name').val();
		var password = $('#entry_password').val();
		var url = '/sht/person/logincheck?userName='+username+'&passWord='+password;
		$.getJSON(url,function(data){
			if(data.success){
				alert("登入成功！");
				setTimeout(function(){
					window.location.href='/sht/main/shopmainlist';
				},1500);
			}else{
				alert(data.errMsg);
			}
		})
	})
})