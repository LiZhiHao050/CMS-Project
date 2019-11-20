<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<link rel="stylesheet" href="/resource/css/bootstrap.min.css">
<title>Insert title here</title>
</head>
<body>
	
	<center><h1>专题编辑</h1></center>
	
	<div style="margin-left: 50px;">
	<form action="" method="post">
		<input type="hidden" name="id" value="${special.id}">
		<label>标题</label> 
		<input type="text" name="title" value="${special.title}"><br/><br/>
		<label>摘要</label>
		<textarea rows="10" cols="25" name="digest"></textarea>
		<br/><br/>
		<input type="submit" value="修改" onclick="upd()">
	</form>
	</div>
</body>

<script type="text/javascript">

	$("textarea").val('${special.digest}');       // 回显摘要
	
	function upd() {
		
		$.post("/special/modify",$("form").serialize(),function(res) {
			if (res.result == 1) {
				alert(res.message);
				$("#content-wrapper").load("/special/managerSpecial");
			} else {
				alert(res.message);
			}
		},"json");
		
	}


</script>

</html>