/**

 * 
 */			    
$(function(){
	//登入判断及获取初始值
	$.getJSON('/sht/session/getsession',function(data){
		if(data.success){
			var Person ='你好！' +data.session.userName+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="caret"></span>';
			//var uername = "${sessionScope.user.userName}";
			$('#session').html(Person);
			$.getJSON('/sht/log/querylog',function(data){
				model(data);
			})
		}else{
			window.location.href='/sht/main/login';
		}
	})
})


				
function model(data){
	var html = '';
	data.rows.map(function(item,index){
		html += '<div style="width:90%;height:5%;float:left;border:2px solid gray;margin-left:50px;margin-top:10px;padding-top:2px">'
			+ '<p style="float:left;margin-left:20px">操作：'+item.logDes+'</p><p style="float:right;margin-right:20%;">操作时间：'+item.logTime+'</p>'
			+ '</div>';
	})
	$('#logFill').html(html);
}