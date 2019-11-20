<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<title></title>
</head>
<body>

	<h2>个人设置</h2>
	<hr/>
	
	<div>
		
	<form action="" method="post">
		<input type="hidden" name="id" value="${user.id}">
		昵称:&emsp;<input type="text" name="nickname"><br><br>
		生日:&emsp;<input type="date" name="birthday"><br><br>
		性别:&emsp;
		<input type="radio" name="gender" value="1">先生
		<input type="radio" name="gender" value="2">女士
		<br><br>
		手机:&emsp;<input type="text" name="mobile"><br><br>
		邮箱:&emsp;<input type="text" name="mail"><br><br>
		地址:&emsp;<input type="text" name="address"><br><br>
		星座:&emsp;<input type="text" name="constellation"><br><br>
		座右铭:&nbsp;<textarea rows="10" cols="50" name="motto"></textarea><br><br>
		<input type="button" value="保存" onclick="save()">
	</form>
	
	</div>

</body>

<script type="text/javascript">
	
	function save() {
		$.post("/user/options",$("form").serialize(),function(res) {
			if (res.result == 1) {
				alert(res.message);
				//location.href = "user/updateSession?name=${user.username}";          // 更新Session信息
			} else {
				alert(res.message);
			}
		},"json");
	}

</script>

</html>