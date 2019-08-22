<%
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()
		+":"+request.getServerPort()+path+"/"; 
%> 
<base href="<%=basePath%>">