/**
 * 
 */
function changeVerifyCode(img){
	//交给servlet生成4位随机数
	img.src="../Kaptcha?" + Math.floor(Math.random()*100);
}

//根据name即key在url中取出key对应的值
function getQueryString(name){
	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null){
		return decodeURIComponent(r[2]);
	}
	return '';
}