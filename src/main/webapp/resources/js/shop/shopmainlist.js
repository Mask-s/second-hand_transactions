/**
 * 
 */
var getiniturl = '/sht/shop/query?pageIndex=1&pageSize=5&shopStatus=2';
var getsessionurl = '/sht/session/getsession';
$(function(){
	$.getJSON(getsessionurl,function(data){
		if(data.success){
			var Person ='你好！' +data.session.userName+'&nbsp;&nbsp;<span id="left" style="font-size:20px">◂</span><span class="caret"></span>';
			$('#session').html(Person);
			if(data.session.priority == 99){
				$('#superAdmin').css('display','inline');
			}
		}else{
			window.location.href='/sht/main/login';
		}
	})
	
	
	$.getJSON(getiniturl,function(data){
		if(data.success){
			var html = '';
			data.rows.map(function(item,index){
				html += items(item.shopImg,item.shopName,item.shopDes,item.price,item.counts,item.shopId);
			})
			$('#shoplist').html(html);
		}
	})
	
	$('.font').mouseover(function(){
		$(this).css('color','red');
	})
	$('.font').mouseout(function(){
		$(this).css('color','black');
	})
})




function items(imgSrc,shopName,shopDes,price,counts,shopId){
	var html = '<div style="width:20%;float:left">'
		+ '<a href="/sht/main/detail?shopId='+shopId+'"><img alt="无法显示" src="/'+imgSrc+'" style="margin-bottom:10px"></a>'
		+ '<p style="text-align:center;font-size:20px;"><strong>'+shopName+'</strong></p>'
		+ '<p style="text-align:center;word-break:break-all;font-size:15px;line-height:20px;">'+shopDes+'</p>'
		+ '<p style="font-size:20px;color:red">￥'+price+'</p>'
		+ '<p style="font-size:10px;color:gray">该商品当前有&nbsp;'+counts+'&nbsp;人气</p>'
		+ '</div>';
	return html;
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


