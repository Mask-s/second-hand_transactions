/**
 * 
 */
var getsessionurl = '/sht/session/getsession';
var userId = null;
$(function(){
	//登入判断及获取初始值
	$.getJSON(getsessionurl,function(data){
		if(data.success){
			var Person ='你好！' +data.session.userName+'&nbsp;&nbsp;<span id="left" style="font-size:20px">◂</span><span class="caret"></span>';
			$('#session').html(Person);
			if(data.session.priority == 99){
				$('#superAdmin').css('display','inline');
				$('#priority').html('管理员');
			}else{
				$('#priority').html('用户');
			}
			$('#userName').html(data.session.userName);
			$('#trueName').html(data.session.trueName);
			$('#phone').html(data.session.phone);
			$('#addr').html(data.session.addr);
			$('#money').html(data.session.money);
			userId = data.session.userId;
		}else{
			window.location.href='/sht/main/login';
		}
	})
	//修改用户信息
	$('#modify').click(function(){
		window.location.href='/sht/main/operationperson?personId='+userId;
	})
	//退出
	$('#exit').click(function(){
		window.location.href='/sht/main/login';
	})
	//充值
	$('#recharge').click(function(){
		window.location.href='/sht/main/pay';
	})
	//点击卖家订单管理
	$('#owner').click(function(){
		window.location.href='/sht/main/ownerorder';
	})
	
	//点击账户流水
	$('#waste').click(function(){
		window.location.href='/sht/main/wastebookmanage';
	})
	
	//点击买家订单管理
	$('#buyer').click(function(){
		window.location.href='/sht/main/buyerorder';
	})
	//点击卖家商品管理
	$('#shopManage').click(function(){
		window.location.href='/sht/main/shopmanage';
	})
	
	$('.font').mouseover(function(){
		$(this).css('color','red');
	})
	$('.font').mouseout(function(){
		$(this).css('color','black');
	})
})




function onclickSession(e){
	$('#left').text('▾');
	$('#selectItem').css('display','block');
	//防止全局点击隐藏触发
	stopPropagation(e);
}
//全局点击隐藏
$(document).bind('click', function () {
	$('#left').text('◂');
    $("#selectItem").hide();
});
//点击自身不触发隐藏
$("#selectItem").click(function (e) {
    stopPropagation(e);
});
//阻止浏览器冒泡
function stopPropagation(e) {
    var ev = e || window.event;
    if (ev.stopPropagation) {
        ev.stopPropagation();
    }
    else if (window.event) {
        window.event.cancelBubble = true;//兼容IE
    }
}




