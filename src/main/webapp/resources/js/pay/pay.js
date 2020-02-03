/**
 * 
 */
var getsessionurl = '/sht/session/getsession';
$(function(){
	$.getJSON(getsessionurl,function(data){
		if(!data.success){
			window.location.href='/sht/main/login';
		}
	})
})




