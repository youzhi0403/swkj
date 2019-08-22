//获取浏览量
function showNum(cid,ctype,cshow,showid){
	$.get('http://www.beaver-cn.com/config/count.asp',{id:cid,stype:ctype,show:cshow},function(data){
		$(showid).html(data);
	});
}


//搜索
function chkseach(sn){
if(sn.skw.value==""){
alert("请输入搜索关键词！"); 
sn.skw.focus(); 
return false;
}
if(sn.skw.value=="请输入搜索关键词"){
alert("请输入搜索关键词！"); 
sn.skw.focus(); 
return false;
}
return true;
}
function clearsearch(sn){
if(sn.value=="请输入搜索关键词")
sn.value="";
}
function redosearch(sn){
if(sn.value=="")
sn.value="请输入搜索关键词";
}
//end

//邮箱格式
function is_email(str)
{ if((str.indexOf("@")==-1)||(str.indexOf(".")==-1))
{
return false;
}
return true;
}

//feedback
function Checkfeedback(form1){
if(form1.name.value==""){
alert("请输入你的姓名！");
form1.name.focus();
return false;
}
if(form1.email.value==""){
alert("请输入你的邮箱！");
form1.email.focus();
return false;
}
if(!is_email(form1.email.value))
{ alert("邮箱格式错误！");
form1.email.focus();
return false;
}

if(form1.tel.value==""){
alert("请输入你的电话！");
form1.tel.focus();
return false;
}
if(form1.CompanyName.value==""){
alert("请输入你的公司！");
form1.CompanyName.focus();
return false;
}
if(form1.address.value==""){
alert("请输入你的地址！");
form1.address.focus();
return false;
}
if(form1.title.value==""){
alert("请输入你的标题");
form1.title.focus();
return false;
}
if(form1.content.value==''){
alert("请输入你的内容！");
form1.content.focus();
return false;
}
if(form1.validatecode.value==""){
alert("请输入你的验证码！");
form1.validatecode.focus();
return false;
}
return true;
}
//end

//切换
function nTabs(thisObj,Num){
if(thisObj.className == "active")return;
var tabObj = thisObj.parentNode.id;
var tabList = document.getElementById(tabObj).getElementsByTagName("li");
for(i=0; i <tabList.length; i++){
  if (i==Num){
   thisObj.className = "active"; 
      document.getElementById(tabObj+"_Content"+i).style.display = "block";
  }else{
   tabList[i].className = "normal"; 
   document.getElementById(tabObj+"_Content"+i).style.display = "none";
  }
} 
}

//加入收藏 
function AddFavorite() {
sURL = encodeURI(window.location);
try{  
window.external.addFavorite(window.location,document.title);  
}catch(e) {  
try{  
window.sidebar.addPanel(document.title,window.location,"");  
}catch (e) {  
alert("加入收藏失败，请使用Ctrl+D进行添加,或手动在浏览器里进行设置.");
}  
}
}

//设为首页
function SetHome(){
if (document.all) {
document.body.style.behavior='url(#default#homepage)';
document.body.setHomePage(window.location);
}else{
alert("您好,您的浏览器不支持自动设置页面为首页功能,请您手动在浏览器里设置该页面为首页!");
}
}