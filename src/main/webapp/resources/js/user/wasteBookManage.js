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
			}
			userId = data.session.userId;
		}else{
			window.location.href='/sht/main/login';
		}
	})
	//点击卖家订单管理
	$('#owner').click(function(){
		window.location.href='/sht/main/ownerorder';
	})
	
	//点击买家订单管理
	$('#buyer').click(function(){
		window.location.href='/sht/main/buyerorder';
	})
	//点击卖家商品管理
	$('#shopManage').click(function(){
		window.location.href='/sht/main/shopmanage';
	})
	//点击账户流水
	$('#waste').click(function(){
		window.location.href='/sht/main/wastebookmanage';
	})
	
	$('.font').mouseover(function(){
		$(this).css('color','red');
	})
	$('.font').mouseout(function(){
		$(this).css('color','black');
	})
	setTimeout(function(){
		initWasteBook(userId);
	},10)

})



//数据初始化
function initWasteBook(userId){
	$.getJSON('/sht/wastebook/querywastebook?userId='+userId,function(data){
		if(data.success){
			model(data);
		}
	})
}


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

//数据模板
function model(data){
	var html = '';
	var status = '';
	var color = '';
	data.rows.map(function(item,index){
		if(item.wasteStatus == 0){
			status = '-';
			color = 'red';
		}else{
			status = '+';
			color = 'green';
		}
		html += '<div style="width:90%;border-radius:6px;height:7%;float:left;border:2px solid '+color+';margin-left:50px;margin-top:10px;padding-top:2px">'
			+ '<p style="float:left;margin-left:20px;width:30%">操作：'+item.wasteDes+'</p>'
			+ '<p style="float:left;margin-left:100px">金额变动：'+status+item.wastePrice+'</p>'
			+ '<p style="float:right;margin-right:5%;">操作时间：'+item.exchangeHour+'</p>'
			+ '</div>';
	})
	$('#wasteBookFill').html(html);
}

