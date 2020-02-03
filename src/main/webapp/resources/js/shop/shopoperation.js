/**
 * 
 */
$(function(){
	var shopId = getQueryString('shopId');
	//shopId 如果为空返回false 否则返回true
	var isEdit = shopId?true:false;
	var inittypeUrl = '/sht/shoptype/queryallshoptype';
	var	insertShopUrl = '/sht/shop/insertshop';
	var editShopUrl = '/sht/shop/updateshop';
	var getShopInfoUrl = '/sht/shop/getshopandtypelist?shopId='+shopId;
	//alert(initUrl);
	if(isEdit){
		getShopInfo(shopId);
	}else{
		getTypeInitInfo();
	}
	
	function getShopInfo(shopId){
		$.getJSON(getShopInfoUrl,function(data){
			if(data.success){
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#price').val(shop.price);
				$('#shop-des').val(shop.shopDes);
				$('#shop-number').val(shop.shopNumber);
				var tempHtml = '';
				
				data.typeList.map(function(item,index){
					tempHtml += '<option data-id="'+item.typeId+'">'
					+item.type+'</option>';
				});
				$('#shop-type').html(tempHtml);
				//默认选择现在商品类型
				$("#shop-type option[data-id='"+shop.shoptype.typeId+"']").attr("selected","selected");
			}
		});
	}
	function getTypeInitInfo(){
		$.getJSON(inittypeUrl,function(data){
			if(data.success){
				var tempHtml = '';
				/*用map方式遍历*/
				data.rows.map(function(item,index){
					tempHtml += '<option data-id="'+item.typeId+'">'
					+item.type+'</option>';
				});
				$('#shop-type').html(tempHtml);
			}
		});
		
	}
	$('#submit').click(function(){
		var check = /^\d+$/;
		var shop = {};
		if(isEdit){
			shop.shopId = shopId;
		}		
		shop.shopName = $('#shop-name').val();
		if(shop.shopName == null || shop.shopName == ''){
			alert('请填写名称');
			return;
		}
		
		shop.price = $('#price').val();
		if(shop.price == null || shop.price == ''){
			alert('请填写单价');
			return;
		}
		if(isNaN(shop.price) || !check.test(shop.price)){
			alert('请输入正整数');
			return;
		}
		
		shop.shopNumber = $('#shop-number').val();
		if(shop.shopNumber == null || shop.shopNumber == ''){
			alert('请填写商品数量');
			return;
		}
		if(isNaN(shop.shopNumber) || !check.test(shop.shopNumber)){
			alert('请输入正整数');
			return;
		}
		
		shop.shopDes = $('#shop-des').val();
		if(shop.shopDes == null || shop.shopDes == ''){
			alert('请填写商品信息');
			return;
		}
		shop.shoptype = {
			typeId : $('#shop-type').find('option').not(function(){
				return !this.selected;
			}).data('id')
		};
		/*接收图片*/
		var shopImg = $('#shop-img')[0].files[0];

		if(shopImg == null || shopImg == undefined){
			alert('请添加图片');
			return;
		}
		var formData = new FormData();
		formData.append('shopImg',shopImg);
		/*转成字符流*/
		formData.append('shopStr',JSON.stringify(shop));
		var verifyCodeActual = $('#j_captcha').val();
		if(!verifyCodeActual){
			$.toast('请输入验证码！');
			return;
		}
		formData.append('verifyCodeActual',verifyCodeActual);
		$.ajax({
			url : (isEdit?editShopUrl:insertShopUrl),
			type : 'POST',
			data : formData,
			/*同时上传文字和文件用false*/
			contentType : false,
			processData : false,
			cache : false,
			/*如果成功接收到后台返回的数据*/
			success : function(data){
				if(data.success){
					$.toast('提交成功');
					setTimeout(function(){
						history.back(-1);
					},1000);
				}else{
					$.toast('提交失败'+data.errMsg);
				}
				$('#captcha').click();
			}
		})
	});
})