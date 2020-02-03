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
			$.getJSON('/sht/shop/query?userId='+userId,function(data){
				if(data.success){				
					goodsShelves(data);
				}
			})
		}else{
			window.location.href='/sht/main/login';
		}
	})



	
	//点击我的信息
	$('#myAccount').click(function(){
		window.location.href='/sht/main/user';
	})
	//点击卖家订单管理
	$('#owner').click(function(){
		window.location.href='/sht/main/ownerorder';
	})
	//点击买家订单管理
	$('#buyer').click(function(){
		window.location.href='/sht/main/buyerorder';
	})
	
	//点击账户流水
	$('#waste').click(function(){
		window.location.href='/sht/main/wastebookmanage';
	})
	
	
	//下拉框字体管理
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





//货架模板（数据）
function goodsShelves(data){
	var html = '';
	data.rows.map(function(item,index){
		var shopStatus = '';
		if(item.shopStatus == 0){
			shopStatus = '审核中';
		}else if(item.shopStatus == 1){
			shopStatus = '已下架';
		}else if(item.shopStatus == 2){
			shopStatus = '出售中';
		}else if(item.shopStatus == -2){
			shopStatus = '请修改商品重新审核！';
		}
		html += '<div style="width:49.7%;height:19.7%;float:left;border:2px solid gray;margin-left:2px;margin-top:2px">'
		+'<img alt="加载失败" src="/'+item.shopImg+'"  height="100%" style="float:left;">'
		+'<div style="float:left;margin-left:20px;width:155px;">商品名：<strong>'+item.shopName+'</strong></div>'
		+'<div style="float:left;margin-left:20px;width:100px;">单价:&nbsp;&nbsp;￥<strong>'+item.price+'</strong></div>'
		+'<div style="float:left;margin-left:20px;width:150px;">剩余库存:&nbsp;&nbsp;<strong>'+item.shopNumber+'</strong>&nbsp;&nbsp;个/斤</div>'	
		+'<div style="float:left;margin-left:20px;width:400px;">商品描述:&nbsp;&nbsp;<strong>'+item.shopDes+'</strong></div>'		
		+'<div style="float:left;width:400px;margin-left:20px">商品类型:&nbsp;&nbsp;<strong>'+item.shoptype.type+'</strong></div>'		
		+'<div style="float:left;margin-left:-226px">商品状态:&nbsp;&nbsp;<strong>'+shopStatus+'</strong></div>'		
		+'<div style="float:left;margin-top:8px;margin-left:80px;font-size:10px">'		
		+'<button style="border-radius:5px;height:20px;float:left;" onclick="modifyShop('+item.shopId+')">商品修改</button>&nbsp;&nbsp;'		
		+'<button style="border-radius:5px;float:left;margin-left:60px;" onclick="up('+item.shopId+','+item.shopStatus+')">商品上架</button>&nbsp;&nbsp;'		
		+'<button style="border-radius:5px;float:left;margin-left:60px;" onclick="down('+item.shopId+','+item.shopStatus+')">商品下架</button>'		
		+'</div></div>';
	})
	$("#shopFill").html(html);
}

//商品修改
function modifyShop(shopId){
	window.location.href='/sht/main/operationshop?shopId='+shopId;
}
//商品上架
function up(shopId,shopStatus){
	if(shopStatus == 0 || shopStatus == -2){
		alert("请先通过管理员审核！");
		return;
	}
	$.getJSON('/sht/shop/modifyshop?shopId='+shopId+'&shopStatus=2',function(data){
		if(data.success){
			alert('商品上架成功！');
			setTimeout(function(){				
				window.location.reload();
			},1000);
		}else{
			alert('商品上架失败！'+data.errMsg);
		}
	})
}
//商品下架
function down(shopId,shopStatus){
	if(shopStatus == 0 || shopStatus == -2){
		alert("请先通过管理员审核！");
		return;
	}
	$.getJSON('/sht/shop/modifyshop?shopId='+shopId+'&shopStatus=1',function(data){
		if(data.success){
			alert('商品下架成功！');
			setTimeout(function(){				
				window.location.reload();
			},1000);
		}else{
			alert('商品下架失败！'+data.errMsg);
		}
	})
}




