/**
 * 
 */
var getsessionurl = '/sht/session/getsession';
var userId = null;
var tr = '<thead>'
	+'<tr style="font-size:12px;">'
	+'<th style="width: 200px; height: 30px;text-align:center">订单编号</th>'
	+'<th style="width: 120px;text-align:center">发货人</th>'
	+'<th style="width: 120px;text-align:center">收货人</th>'
	+'<th style="width: 150px;text-align:center">交易商品</th>'
	+'<th style="width: 100px;text-align:center">单价</th>'
	+'<th style="width: 100px;text-align:center">数量</th>'
	+'<th style="width: 100px;text-align:center">总价</th>'
	+'<th style="width: 200px;text-align:center">收货地址</th>'
	+'<th style="width: 200px;text-align:center">创建时间</th>'
	+'<th style="width: 150px;text-align:center">订单状态</th>'
	+'<th style="width: 150px;text-align:center">是否发货</th>'
	+'<th style="width: 250px;text-align:center">操作</th>'
	+'</tr>'
	+'</thead>';
$(function(){	
	ownerOrderModel();
	//登入判断及获取初始值
	$.getJSON(getsessionurl,function(data){
		if(data.success){
			var Person ='你好！' +data.session.userName+'&nbsp;&nbsp;<span id="left" style="font-size:20px">◂</span><span class="caret"></span>';
			$('#session').html(Person);
			if(data.session.priority == 99){
				$('#superAdmin').css('display','inline');
			}
			userId = data.session.userId;
			$.getJSON('/sht/order/queryorders?shopStatus=0&ownerId='+userId+'&orderStatus=1',function(data){
				if(data.success){				
					orderData(data);
				}
			})
		}else{
			window.location.href='/sht/main/login';
		}
	})

	
	
	$(':radio').click(function(){
		var checkValue = $(this).val(); 
		var text = '/sht/order/queryorders';
		if(checkValue==1){
			text += '?shopStatus=0&ownerId='+userId+'&orderStatus=1';
		}else if(checkValue==2){
			text += '?shopStatus=1&ownerId='+userId+'&orderStatus=1';
		}else if(checkValue==3){
			text += '?shopStatus=0&ownerId='+userId+'&orderStatus=0';
		}else{
			text += '?shopStatus=1&ownerId='+userId+'&orderStatus=2';
		}
		$.getJSON(text,function(data){
			if(data.success){
				$("#table-info").html(tr);
				orderData(data);			
			}else{
				$("#table-info").html('');
				$("#table-info").html(tr);
			}
		})
	});

	
	//点击我的信息
	$('#myAccount').click(function(){
		window.location.href='/sht/main/user';
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



function search(){
	var text = $('#orderNumberSearch').val();
	$.getJSON('/sht/order/queryorders?orderNumber='+text+'&ownerId='+userId,function(data){
		if(data.success){
			$("#table-info").html(tr);
			orderData(data);
		}else{
			alert('没有找到对应的订单！');
		}
	})
}




//卖家订单模板(格局)
function ownerOrderModel(){
	var html = '';
	html = '<div id="title" style="width:100%;height:35px;font-size:30px;margin:20px"><p style="float:left;margin-left:40%">卖家订单管理</p></div>'
		+'<hr style="margin-top:20px;margin-bottom:20px">'
		+'<form id="form" action="" method="get" style="margin-left:20px;"> '
		+'请选择您想查看的类型：'
		+'<label style="font-size:17px"><input name="orders" type="radio" checked="checked" value="1" />&nbsp;&nbsp;需要发货 （已付款，需发货）</label> '
		+'<label style="font-size:17px"><input name="orders" type="radio" value="2" />&nbsp;&nbsp;等待确认收货 （已发货）</label> '
		+'<label style="font-size:17px"><input name="orders" type="radio" value="3" />&nbsp;&nbsp;待生效订单（未付款） </label>'
		+'<label style="font-size:17px"><input name="orders" type="radio" value="4" />&nbsp;&nbsp;历史订单（已完成流程） </label>'
		+'</form>'
		+'<div class="input-group" style="width:40%;height:30px;float:right;margin-right:150px;margin-top:10px">'
		+'<span class="input-group-addon">订单号查询：</span>'
		+'<input id="orderNumberSearch" placeholder="请输入完整的订单号" type="text" class="form-control" style="height:30px;">'
		+'<button onclick="search()" class="input-group-addon" style="border:0px;border-radius:10px;font-size:12px">Search</button>'
		+'</div>'
		+'<div style="width:95%;height:500px;float:left;margin-left:20px;margin-top:20px;">'
		+'<table id="table-info" class="table table-striped" style="width: 100%;">'
		+'<thead>'
		+'<tr style="font-size:12px;">'
		+'<th style="width: 200px; height: 30px;text-align:center">订单编号</th>'
		+'<th style="width: 120px;text-align:center">发货人</th>'
		+'<th style="width: 120px;text-align:center">收货人</th>'
		+'<th style="width: 150px;text-align:center">交易商品</th>'
		+'<th style="width: 100px;text-align:center">单价</th>'
		+'<th style="width: 100px;text-align:center">数量</th>'
		+'<th style="width: 100px;text-align:center">总价</th>'
		+'<th style="width: 200px;text-align:center">收货地址</th>'
		+'<th style="width: 200px;text-align:center">创建时间</th>'
		+'<th style="width: 150px;text-align:center">订单状态</th>'
		+'<th style="width: 150px;text-align:center">是否发货</th>'
		+'<th style="width: 250px;text-align:center">操作</th>'
		+'</tr>'
		+'</thead>'		
		+'<!-- 数据填充 -->'		
		+'</table>'							
		+'</div>'		
		+'</div>';
	$('#mainBody').html(html);
	//alert($('#form input[name="orders"]:checked').val());
}

//卖家订单模板（数据）
function orderData(data){
	var html = '';
	data.rows.map(function(item,index){
		var stutas1 = '';
		if(item.orderStatus == 0){
			stutas1 = '买家未付款';
		}else if(item.orderStatus == 1){
			stutas1 = '买家已付款';
		}else if(item.orderStatus == 2){
			stutas1 = '订单已完成';
		}
		var button = '';
		if(item.orderStatus == 1 && item.shopStatus == 1){
			button = '<th id="btn" style="text-align:center">等待买家收货!</th>';
		}else if(item.orderStatus == 1 && item.shopStatus == 0){
			button = '<th id="btn" style="text-align:center"><button onclick="checkDelivery('+item.orderId+')">确认发货</button></th>';
		}else if(item.orderStatus == 0 && item.shopStatus == 0){
			button = '<th id="btn" style="text-align:center"><button onclick="delOrder('+item.orderId+')">删除订单</button></th>';
		}else if(item.orderStatus == 2 && item.shopStatus == 1){
			button = '<th id="btn" style="text-align:center">订单已完成！</th>';
		}
		html += '<tr style="font-size:10px;">'
		+'<th style="text-align:center">'+item.orderNumber+'</th>'
		+'<th style="text-align:center">'+item.owner.userName+'</th>'
		+'<th style="text-align:center">'+item.buyer.userName+'</th>'
		+'<th style="text-align:center">'+item.shop.shopName+'</th>'	
		+'<th style="text-align:center">'+item.price+'</th>'		
		+'<th style="text-align:center">'+item.number+'</th>'		
		+'<th style="text-align:center">'+parseInt(item.price)*parseInt(item.number)+'</th>'		
		+'<th style="text-align:center">'+item.address+'</th>'		
		+'<th style="text-align:center">'+item.orderCreateTime+'</th>'		
		+'<th style="text-align:center">'+stutas1+'</th>'		
		+'<th style="text-align:center">'+(item.shopStatus==0 ? '等待卖家发货':'卖家已发货')+'</th>'		
		+button
		+'</tr>';
	})
	tr = $("#table-info").html();
	$("#table-info").html(tr+html);
}

//点击确认发货，修改发货状态
function checkDelivery(orderId){
	if(!confirm('如果作假将追究法律责任！')){
		return;
	}
	var buynum = null;
	var shopId = null;
	var totalnum = null;
	$.getJSON('/sht/order/queryorders?orderId='+orderId,function(data){
		data.rows.map(function(item,index){
			buynum = item.number;
			shopId = item.shop.shopId;
		})
	})
	$.getJSON('/sht/shop/query?shopId='+shopId,function(data){
		data.rows.map(function(item,index){
			totalnum = item.shopNumber;
		})
	})
	if((parseInt(totalnum)-parseInt(buynum)) < 0){
		alert('库存不足请退款！');
		return;
	}
	
	//将shopStatus从0改为1：发货完毕
	setTimeout(function(){
		$.ajax({
			url : '/sht/order/modifyorder',
			type : 'POST',
			data : {'shopStatus':1,'orderId':orderId},
			cache : false,
			success : function(data){
				if(data.success){
					setTimeout(function(){
						alert('发货成功！');
						//修改数量
						$.getJSON('/sht/shop/modifyshop?shopNumber='+(parseInt(totalnum)-parseInt(buynum))+'&shopId='+shopId,function(data){
							if(data.success){
								window.location.reload();
							}else{
								alert(data.errMsg);
							}
						})				
					})
				}else{
					setTimeout(function(){
						alert('发货失败！'+data.errMsg);
					})
				}			
			}
		})
	},50)
}

//删除订单
function delOrder(orderId){
	if(!confirm("真的要删除这个订单吗？")){
		return;
	}
	$.getJSON('/sht/order/delorderbyid?orderId='+orderId,function(data){
		if(data.success){
			setTimeout(function(){
				alert("删除成功！");
				window.location.reload();
			},1500)
		}else{
			alert("删除失败！"+data.errMsg);
		}
	})
}
