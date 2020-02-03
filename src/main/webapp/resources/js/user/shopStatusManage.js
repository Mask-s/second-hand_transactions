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
			$.getJSON('/sht/shop/query?shopStatus=0&pageSize=999',function(data){
				if(data.rows.length==0){
					bgFill();
				}else{
					dataModel(data);
				}				
			})
		}else{
			window.location.href='/sht/main/login';
		}
	})
})


//数据填充模板
function dataModel(data){
	var html = '';
	data.rows.map(function(item,index){
		var text = '';
		if(item.shopStatus == 0){
			text = '审核中';
		}
		html += '<div style="width:45%;height:19.7%;float:left;border:2px solid gray;margin-left:20px;margin-top:5px">'
			+ '<img alt="加载失败" src="/'+item.shopImg+'"  height="100%" style="float:left;">'
			+ '<div style="float:left;margin-left:20px;width:155px;">商品名：<strong>'+item.shopName+'</strong></div>'
			+ '<div style="float:left;margin-left:20px;width:100px;">单价:&nbsp;&nbsp;￥<strong>'+item.price+'</strong></div>'
			+ '<div style="float:left;margin-left:20px;width:150px;">剩余库存:&nbsp;&nbsp;<strong>'+item.shopNumber+'</strong>&nbsp;&nbsp;个/斤</div>'
			+ '<div style="float:left;margin-left:20px;width:400px;">商品描述:&nbsp;&nbsp;<strong>'+item.shopDes+'</strong></div>'
			+ '<div style="float:left;width:400px;margin-left:20px">商品类型:&nbsp;&nbsp;<strong>'+item.shoptype.type+'</strong></div>'
			+ '<div style="float:left;margin-left:-174px">商品状态:&nbsp;&nbsp;<strong style="color:red">'+text+'</strong></div>'
			+ '<div style="float:left;margin-top:15px;margin-left:80px;font-size:10px">'
			+ '<button style="border-radius:5px;height:20px;float:left;color:red" onclick="fail('+item.shopId+')">审核不通过</button>'
			+ '<button style="border-radius:5px;float:left;margin-left:120px;color:green" onclick="pass('+item.shopId+')">审核通过</button>'
			+ '</div></div>';
	})
	$('#shopFill').html(html);
}
//审核失败
function fail(shopId){
	$.getJSON('/sht/shop/modifyshop?shopId='+shopId+'&shopStatus=-2',function(data){
		if(data.success){
			alert('操作成功！');
			setTimeout(function(){
				window.location.reload();
			},1000);
		}else{
			alert('操作失败！'+data.errMsg);
		}
	})
}
//审核通过,并上架商品
function pass(shopId){
	$.getJSON('/sht/shop/modifyshop?shopId='+shopId+'&shopStatus=2',function(data){
		if(data.success){
			alert('操作成功！');
			setTimeout(function(){
				window.location.reload();
			},1000);
		}else{
			alert('操作失败！'+data.errMsg);
		}
	})
}

//当没有可操作的商品时的模板
function bgFill(){
	var html = '<div style="width:95%;height:50%;text-align:center;">'
		+'<p style="margin-top:15%;font-size:20px;color:green;">当前没有需要审批的商品！</p></div>';
	$('#shopFill').html(html);
}

