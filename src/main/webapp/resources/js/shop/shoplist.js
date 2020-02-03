/**
 * 
 */
var getiniturl = '/sht/shop/query?pageIndex=1&pageSize=10&shopStatus=2';
var getlunboiniturl = '/sht/shop/query?pageIndex=1&pageSize=3&shopStatus=2';
var gettypeiniturl = '/sht/shoptype/queryallshoptype';
var getsessionurl = '/sht/session/getsession';
var text = '';
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
	var index = 0;
	lunbo();
	typeinit();
	//轮播图start
	setInterval(function(){
		index++;
		var a = document.getElementsByClassName('img-slide');
		var p = document.getElementsByClassName('p');
		var s = document.getElementsByClassName('s');
		for(var i=0;i<index;i++){
			a[i].style.display='none';
			p[i].style.display='none';
			s[i].style.display='none';
		}
		if(index>=a.length){
			index = 0;
		}
		a[index].style.display='block';
		p[index].style.display='block';
		s[index].style.display='block';
	},2000);
	//轮播图end
	//商品初始化
	shopinit();
	//pagination();
	//模糊查询商品名及说明
	$('#query1').click(function(){
		var a = $('#queryCondition').val();
		if(a == ''){
			alert("请输入宝贝的大致名称或描述");
		}else{
			$.getJSON('/sht/shop/query?pageIndex=1&pageSize=999&condition='+a,function(data){
				if(data.success){
					var html = '';
					data.rows.map(function(item,index){
						html += items(item.shopImg,item.shopName,item.shopDes,item.price,item.counts,item.shopId);
					})
					$('#shoplist').html(html);
					$('.font').css({'font-size':'17px','margin-top':'15px'});
					text = '';
				}
			})
		}		
	})
	//价格查询可分类
	$('#query2').click(function(){
		var c = text;
		var a = $('#lowPrice').val();
		var b = $('#highPrice').val();
		if(a == '' && b == ''){
			alert("请输入价格");
		}else{
			$.getJSON('/sht/shop/query?pageIndex=1&pageSize=999&lowPrice='+a+'&highPrice='+b+'&type='+c,function(data){
				if(data.success){
					var html = '';
					data.rows.map(function(item,index){
						html += items(item.shopImg,item.shopName,item.shopDes,item.price,item.counts,item.shopId);
					})
					$('#shoplist').html(html);
				}
			})
		}		
	})
	
	$('.fonts').mouseover(function(){
		$(this).css('color','red');
	})
	$('.fonts').mouseout(function(){
		$(this).css('color','black');
	})
})



function lunbo(){
	$.getJSON(getlunboiniturl,function(data){
		if(data.success){
			var html = '';
			data.rows.map(function(item,index){
				if(index == 0){
					html += '<a href="/sht/main/detail?shopId='+item.shopId+'"><img class="img-slide img'+(index+1)+'" src="/'+item.shopImg+'" alt="'+(index+1)+'" style="display:block"></a>'
					+ '<p class="p '+(index+1)+'" style="text-align:center;font-size:20px;display:block"><strong>'+item.shopName+'</strong></p>'
					+ '<p class="s '+(index+1)+'" style="text-align:center;word-break:break-all;font-size:15px;line-height:20px;display:block;color:red">￥'+item.price+'</p>';
				}else{
					html += '<a href="/sht/main/detail?shopId='+item.shopId+'"><img class="img-slide img'+(index+1)+'" src="/'+item.shopImg+'" alt="'+(index+1)+'" style="display:none"></a>'
					+ '<p class="p '+(index+1)+'" style="text-align:center;font-size:20px;display:none"><strong>'+item.shopName+'</strong></p>'
					+ '<p class="s '+(index+1)+'" style="text-align:center;word-break:break-all;font-size:15px;line-height:20px;display:none;color:red">￥'+item.price+'</p>';
				}
				
			})
			$('.imgBox').html(html);
		}
	})
}
/*function fontOnclick(typeItems){
	Array.from(document.getElementById(typeItems).children).forEach(function(item){
		item.onclick = function(){
			$('.font').css({'font-size':'17px','margin-top':'15px'});
			item.style.fontSize=25+'px';
			item.style.marginTop = 10+'px';
			var text1 = $(this).find('a').text();
			text = text1;
		}		
	})
}*/

function font(a){		
	$('.font').css({'font-size':'17px','margin-top':'15px'});
	a.style.fontSize=25+'px';
	a.style.marginTop = 10+'px';
	var text1 = $(a).find('a').text();
	text = text1;
	$.ajax({
		url : '/sht/shop/query?pageIndex=1&pageSize=999&shopStatus=2&shoptype='+text,
		type : 'GET',
		data : {},
		success : function(data){
			if(data.success){
				var html = '';
				data.rows.map(function(item,index){
					html += items(item.shopImg,item.shopName,item.shopDes,item.price,item.counts,item.shopId);
				})
				$('#shoplist').html(html);
			}else{
				alert(data.errMsg);
			}
		}
	})
}


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


function shopinit(){
	$.getJSON(getiniturl,function(data){
		if(data.success){
			var html = '';
			data.rows.map(function(item,index){
				html += items(item.shopImg,item.shopName,item.shopDes,item.price,item.counts,item.shopId);
			})
			$('#shoplist').html(html);
		}
	})
}

function typeinit(){
	$.getJSON(gettypeiniturl,function(data){
		if(data.success){
			var html = '';
			data.rows.map(function(item,index){
				html += '<div class="font" onclick="font(this)"><a href="#">'+item.type+'</a></div>';
			})
			$('#typeItems').html(html);
		}
	})
}

/*function pagination(){
	 var pagination = '<li><a href="javascript:;" onclick=query(1)>1</a></li>'
	 +'<li><a href="javascript:;" onclick=query(2)>2</a></li>'
	 +'<li><a href="javascript:;" onclick=query(3)>3</a></li>'
	 +'<li><a href="javascript:;" onclick=query(4)>4</a></li>'
	 +'<li><a href="javascript:;" onclick=query(5)>5</a></li>';
	 $('#pagination').html(pagination);
}*/

/*function query(x){
	var a = text;
	var b = $('#queryCondition').val();
	var c = $('#lowPrice').val();
	var d = $('#highPrice').val();
	var url = '/sht/shop/query?pageIndex='+x+'&pageSize=10&condition='+b;
	if (a != ''){
		url = '/sht/shop/query?pageIndex='+x+'&pageSize=10&shoptype='+a;
		if (c != '' || d != ''){
			url = '/sht/shop/query?pageIndex='+x+'&pageSize=10&lowPrice='+c+'&highPrice='+d+'&type='+a;
		}
	}
	if(c != '' || d != ''){
		url = '/sht/shop/query?pageIndex='+x+'&pageSize=10&lowPrice='+c+'&highPrice='+d+'&type='+a;
	}
	if(b != ''){
		url = '/sht/shop/query?pageIndex='+x+'&pageSize=10&condition='+b;
	}
	alert(url);
	$.getJSON(url,function(data){
		if(data.success){
			var html = '';
			data.rows.map(function(item,index){
				html += items(item.shopImg,item.shopName,item.shopDes,item.price,item.counts,item.shopId);
			})
			$('#shoplist').html(html);
		}
	})
}*/






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


