/**
 * 
 */
var getsessionurl = '/sht/session/getsession';
var addr = null;
var buyerId = null;
var userName = null;
var shopId = null;
var ownerId = null;
var nowprice = null;
var ownerAccount = null;
var shopName = null;
var buyNum = null;
$(function(){		
	var price = null;
	shopId = getQueryString('shopId');
	var total = 10;
	$.getJSON(getsessionurl,function(data){
		if(data.success){
			addr = data.session.addr;
			buyerId = data.session.userId;
			userName = data.session.userName;
			nowprice = data.session.money;
		}else{
			window.location.href='/sht/main/login';
		}
	})
	if(shopId == null || shopId == undefined){
		alert('shopId不能为空！');
	}else{
		initShop(shopId);
	}

	//返回事件
	$('#back').click(function(){
		window.location.href='/sht/main/shoplist';
	})
	
	setTimeout(function(){
		$.getJSON('/sht/shop/query?shopId='+shopId,function(data){
			var count = data.shop.counts;
			$.getJSON('/sht/shop/modifyshop?counts='+(parseInt(count)+parseInt(1))+'&shopId='+shopId,function(data){
				if(!data.success){
					alert(data.errMsg);
				}
			})
		})
	},50)

	
	
	
})

window.onload=function(){
	initMessage();
	//滚动条默认到底部
	$('#message').scrollTop($('#message')[0].scrollHeight);
	//加
	$('#plus').click(function(){
		var numCount = $('#numCount').val();
		if(numCount >= total){
			alert('不能超过库存!');
			return;
		}else{
			$('#numCount').val(parseInt(numCount)+parseInt(1));
		}
	})
	//减
	$('#minus').click(function(){
		var numCount = $('#numCount').val();
		if(numCount <= 1){
			alert('购买数量不能小于1！');
			return;
		}else{
			$('#numCount').val(parseInt(numCount)-parseInt(1));
		}
	})
	
	$('#buy').click(function(){
		buyNum = $('#numCount').val();
		$('#Modal').modal('show');
		$('#addr2').html(addr);
		$('#shopName2').html(shopName);
		$('#num2').text(buyNum);
		$('#price2').html(price);
		$('#totalPrices2').text(parseInt(price)*parseInt(buyNum));
		$('#userName2').html(userName);
	})
	
	
	$('#createOrder').click(function(){
		$('#wait').css('display','block');
		setTimeout(function(){
			$('#wait').css('display','none');
			$('#Modal').modal('hide');
		},2000);
		//创建订单
		var orderId = null;
		var orderprice = parseInt(price)*parseInt(buyNum);
		var orders = {};
		orders.address = addr;
		orders.price = parseInt(price)*parseInt(buyNum);
		orders.number = buyNum;
		var formData = new FormData();
		formData.append('orderStr',JSON.stringify(orders));
		formData.append('ownerId',ownerId);
		formData.append('buyerId',buyerId);
		formData.append('shopId',shopId);
		$.ajax({
			url : '/sht/order/createorder',
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data){
				if(!data.success){
					alert(data.errMsg);
				}else{
					orderId = data.orderId;
					setTimeout(function(){
						alert('下单成功！');
					},2000);
					//支付操作
					setTimeout(function(){
						checkAndPay(orderId,nowprice,orderprice,ownerAccount);
						setTimeout(function(){
							window.location.href='/sht/main/shoplist';					
						},2500);
					},2000);
					
				}
			}
		})
		
	})
}

function initShop(shopId){
	$.getJSON('/sht/shop/query?shopId='+shopId,function(data){
		if(data.success){
			total = data.shop.shopNumber;
			shopName = data.shop.shopName;
			price = data.shop.price;
			ownerId = data.shop.person.userId;
			ownerAccount = data.shop.person.money;
			$('#shopName').html(data.shop.shopName);
			$('#price').html(data.shop.price);
			$('#counts').html(data.shop.counts);			
			$('#shopImg').attr("src",'/'+data.shop.shopImg);
			$('#shopNumber').html(data.shop.shopNumber);
			$('#phone').html(data.shop.person.phone);
			$('#addr').html(data.shop.person.addr);
			$('#userName').html(data.shop.person.userName);
		}
	})
}



function checkAndPay(orderId,nowprice,orderprice,ownerAccount){
	if(!confirm('是否立即支付使订单生效？'))
		return;
	if(nowprice < orderprice){
		if(!confirm('余额不足现在去充值？'))
			return;
		//充值功能
		window.location.href='/sht/main/pay';
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



//评论消息模板
function modelMessage(data){
	var html = '';
	data.rows.map(function(item,index){
		var position = '';
		var color = '';
		if(item.person.userId == buyerId){
			position = 'right';
			color = 'green';
		}else{
			position = 'left';
			color = 'blue';
		}
		html += '<div style="width:100%;line-height:25px;text-align:'+position+';margin-top:20px">'
			+ '<strong style="font-size:15px;color:'+color+';margin-'+position+':30px;">'+item.person.userName+':</strong><br>'
			+ '<span style="font-size:20px;margin-'+position+':60px;">'+item.details+'</span><br>'
			+ '<p style="font-size:8px;margin-'+position+':30px">评论时间：'+item.messageCreateTime+'</p>'
			+ '</div>';
	})
	$('#message').html(html);
}



//初始化评论区
function initMessage(){
	$.getJSON('/sht/message/querymessage?shopId='+shopId,function(data){
		if(data.success){
			modelMessage(data);
		}		
	})
}


//提交评论
function submitMessage(){
	var details = $('#messageText').val();
	if(details == '' || details == undefined || details == null){
		alert('请输入...');
		return;
	}
	$.getJSON('/sht/message/insertmessage?shopId='+shopId+'&details='+details,function(data){
		if(data.success){
			window.location.reload();
		}
	})
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


