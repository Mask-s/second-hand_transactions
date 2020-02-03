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
	buyerOrderModel();
	//登入判断及获取初始值
	$.getJSON(getsessionurl,function(data){
		if(data.success){
			var Person ='你好！' +data.session.userName+'&nbsp;&nbsp;<span id="left" style="font-size:20px">◂</span><span class="caret"></span>';
			$('#session').html(Person);
			if(data.session.priority == 99){
				$('#superAdmin').css('display','inline');
			}
			userId = data.session.userId;
			$.getJSON('/sht/order/queryorders?shopStatus=0&buyerId='+userId+'&orderStatus=0',function(data){
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
			text += '?shopStatus=0&buyerId='+userId+'&orderStatus=0';
		}else if(checkValue==2){
			text += '?buyerId='+userId+'&orderStatus=1';
		}else if(checkValue==3){
			text += '?shopStatus=1&buyerId='+userId+'&orderStatus=2';
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
	$('#owner').click(function(){
		window.location.href='/sht/main/ownerorder';
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
	$.getJSON('/sht/order/queryorders?orderNumber='+text+'&buyerId='+userId,function(data){
		if(data.success){
			$("#table-info").html(tr);
			orderData(data);
		}else{
			alert('没有找到对应的订单！');
		}
	})
}




//买家订单模板(格局)
function buyerOrderModel(){
	var html = '';
	html = '<div id="title" style="width:100%;height:35px;font-size:30px;margin:20px"><p style="float:left;margin-left:40%">买家订单管理</p></div>'
		+'<hr style="margin-top:20px;margin-bottom:20px">'
		+'<form id="form" action="" method="get" style="margin-left:20px;"> '
		+'请选择您想查看的类型：'
		+'<label style="font-size:17px"><input name="orders" type="radio" checked="checked" value="1" />&nbsp;&nbsp;需要付款 （订单未生效）</label> '
		+'<label style="font-size:17px"><input name="orders" type="radio" value="2" />&nbsp;&nbsp;已付款 （待收货）</label> '
		+'<label style="font-size:17px"><input name="orders" type="radio" value="3" />&nbsp;&nbsp;历史订单（已完成流程） </label>'
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
			button = '<th id="btn" style="text-align:center"><button onclick="receivedGoods('+item.orderId+')">确认收货</button></th>';
		}else if(item.orderStatus == 1 && item.shopStatus == 0){
			button = '<th id="btn" style="text-align:center">等待卖家发货!</th>';
		}else if(item.orderStatus == 0 && item.shopStatus == 0){
			button = '<th id="btn" style="text-align:center"><button onclick="delOrder('+item.orderId+')">取消订单</button><button onclick="payOrder('+item.orderId+','+item.buyer.userId+','+item.owner.userId+')">去付款</button></th>';
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

function payOrder(orderId,buyerId,ownerId){
	$.getJSON('/sht/order/queryorders?orderId='+orderId,function(data){
		data.rows.map(function(item,index){
			checkAndPay(item.orderId,item.buyer.money,item.price,item.owner.money,buyerId,ownerId,item.number,item.shop.shopName);
		})
	})	
}



//点击已收货，修改订单状态
function receivedGoods(orderId){
	if(!confirm('您真的已经收到宝贝了吗？')){
		return;
	}
	//将orderStatus从1改为2：订单完成
	$.ajax({
		url : '/sht/order/modifyorder',
		type : 'POST',
		data : {'orderStatus':2,'orderId':orderId},
		cache : false,
		success : function(data){
			if(data.success){
				setTimeout(function(){
					alert('恭喜！交易完成。');
					window.location.reload();
				})
			}else{
				setTimeout(function(){
					alert('交易失败！'+data.errMsg);
				})
			}			
		}
	})
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


//确认支付
function checkAndPay(orderId,nowprice,orderprice,ownerAccount,buyerId,ownerId,buyNum,shopName){
	if(!confirm('是否立即支付使订单生效？'))
		return;
	if(nowprice < orderprice){
		if(!confirm('余额不足现在去充值？'))
			return;
		//充值功能
	}else{
		var person = {};
		person.money = parseInt(nowprice)-parseInt(orderprice);
		person.userId = buyerId;
		var formData1 = new FormData();
		formData1.append('personStr',JSON.stringify(person));
		//修改用户余额
		$.ajax({
			url : '/sht/person/modifyaccount',
			type : 'POST',
			data : formData1,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data){
				if(data.success){
					//修改订单状态为1，即用户付款完毕等待发货
					var formData2 = new FormData();
					formData2.append('orderId',orderId);
					formData2.append('orderStatus',1);
					$.ajax({
						url : '/sht/order/modifyorder',
						type : 'POST',
						data : formData2,
						contentType : false,
						processData : false,
						cache : false,
						success : function(data){
							if(data.success){
								setTimeout(function(){
									alert('支付成功！');
									createWasteBook(0,orderprice,'购买'+buyNum+'个/斤'+shopName,buyerId);
								},1000);
								setTimeout(function(){
									window.location.reload();
								},1000);								
							}
						}
					})
				}else{
					alert('支付失败'+data.errMsg);
				}
			}
		});
		var person = {};
		person.money = parseInt(ownerAccount)+parseInt(orderprice);
		person.userId = ownerId;
		var formData3 = new FormData();
		formData3.append('personStr',JSON.stringify(person));
		//修改用户余额
		$.ajax({
			url : '/sht/person/modifyaccount',
			type : 'POST',
			data : formData3,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data){
				if(!data.success){
					alert('货主收款失败，'+data.errMsg);
				}else{
					createWasteBook(1,orderprice,'卖出'+buyNum+'个/斤'+shopName,ownerId);
				}
			}
		})
	}
	
}

//wasteStatus 1:+;0:-
//创建流水
function createWasteBook(wasteStatus,wastePrice,wasteDes,userId){
	$.getJSON('/sht/wastebook/insertwastebook?wasteStatus='+wasteStatus+'&wastePrice='+wastePrice+'&wasteDes='+wasteDes+'&userId='+userId,function(data){
		if(!data.success){
			alert('创建流水失败'+errMsg);
			return;
		}
	})
}
