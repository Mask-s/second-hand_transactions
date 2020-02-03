/**

 * 
 */			    
$(function(){
	queryall();
})
function queryall(){
	$.ajax({
		url: '/sht/person/getallperson',
		type: 'GET',
		data: {},		
		success:function(data){			
			//登入判断及获取初始值
			$.getJSON('/sht/session/getsession',function(data){
				if(data.success){
					var Person ='你好！' +data.session.userName+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="caret"></span>';
					//var uername = "${sessionScope.user.userName}";
					$('#session').html(Person);
				}else{
					window.location.href='/sht/main/login';
				}
			})		
			var rows = data.rows;
			var x='';
			rows.map(function(item,index){
				x=x+'<tr><th>'+item.userId+'</th>'
				+'<th>'+item.userName+'</th>'
				+'<th>'+item.passWord+'</th>'
				+'<th>'+(item.priority==99 ?'超级管理员':'一般用户')+'</th>'
				+'<th>'+item.trueName+'</th>'
				+'<th>'+item.money+'</th>'
				+'<th>'+item.phone+'</th>'
				+'<th>'+item.addr+'</th>'
				+'<th>&nbsp;&nbsp;'
				+'<a href="/sht/main/operationperson?personId='+item.userId+'"><span class="glyphicon glyphicon-pencil" style="color:green;"></span></a>&nbsp;&nbsp;&nbsp;'
				+'<a href="#" onclick=del('+item.userId+')> <span class="glyphicon glyphicon-remove" style="color:red;"></span></a></th></tr>'
			});
			var tr = document.getElementById("table-info");
			tr.innerHTML = tr.innerHTML+x;
			//$('#tr').html(x);
			//document.getElementById("tr").innerHTML=x;
			//$("#table-info").load(location.href+" #table-info");
			
			 var pagination = '<li><a href="javascript:;" onclick=query(1)>1</a></li>'
				 +'<li><a href="javascript:;" onclick=query(2)>2</a></li>'
				 +'<li><a href="javascript:;" onclick=query(3)>3</a></li>'
				 +'<li><a href="javascript:;" onclick=query(4)>4</a></li>'
				 +'<li><a href="javascript:;" onclick=query(5)>5</a></li>';
			document.getElementById("pagination").innerHTML=pagination;
			//$('#pagination').html(pagination);
			return;
		}
	})
}
	






function query(pageIndex){
	var condition = $('#search').val();
	$.ajax({
		url: '/sht/person/getconditionperson',
		type: 'GET',
		data: {'condition':condition,'pageIndex':pageIndex},		
		success:function(data){
			var rows = data.rows;
			var x='';
			rows.map(function(item,index){
				x=x+'<tr><th>'+item.userId+'</th>'
				+'<th>'+item.userName+'</th>'
				+'<th>'+item.passWord+'</th>'
				+'<th>'+(item.priority==99 ?'超级管理员':'一般用户')+'</th>'
				+'<th>'+item.trueName+'</th>'
				+'<th>'+item.money+'</th>'
				+'<th>'+item.phone+'</th>'
				+'<th>'+item.addr+'</th>'
				+'<th>&nbsp;&nbsp;'
				+'<a href="/sht/main/operationperson?personId='+item.userId+'"><span class="glyphicon glyphicon-pencil" style="color:green;"></span></a>&nbsp;&nbsp;&nbsp;'
				+'<a href="#" onclick=del('+item.userId+')> <span class="glyphicon glyphicon-remove" style="color:red;"></span></a></th></tr>'
			});
			var tb = document.getElementById("table-info");
			 var rowNum=tb.rows.length;
		     for (i=2;i<rowNum;i++)
		     {
		         tb.deleteRow(i);
		         rowNum=rowNum-1;
		         i=i-1;
		     }
		     var tr = document.getElementById("table-info");
		     tr.innerHTML = tr.innerHTML+x;
			//$("#table-info").load(location.href+" #table-info");
			 var pagination = '<li><a href="javascript:;" onclick=query(1)>1</a></li>'
				 +'<li><a href="javascript:;" onclick=query(2)>2</a></li>'
				 +'<li><a href="javascript:;" onclick=query(3)>3</a></li>'
				 +'<li><a href="javascript:;" onclick=query(4)>4</a></li>'
				 +'<li><a href="javascript:;" onclick=query(5)>5</a></li>';
			document.getElementById("pagination").innerHTML=pagination;
		    
			return;
		}
	})
	
}
function btn(){
	window.location.href='/sht/main/operationperson';
}

function del(userId){
	$.getJSON('/sht/person/delpersonbyid?personId='+userId,function(data){
		if(data.success){
			alert('成功删除'+data.count+'个用户！');
			setTimeout(function(){
				window.location.href='/sht/main/getperson';
			},2000);
		}else{
			alert('删除失败'+data.errMsg);
		}
	})
}




