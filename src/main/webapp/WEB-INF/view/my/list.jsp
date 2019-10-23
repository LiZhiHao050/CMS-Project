<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">
	// 新窗口打开
	function myopen(aId){
		window.open("/article/getDetail?aId="+aId,"_blank");
	}
	
	// 修改回显
	function toUpdate(id) {
		$('#center').load("/article/update?id="+id);
	}
	
	// 删除
	function delAtr(id) {
		var result = confirm("您确定要删除文章么？");
		if(!result)            // 判断是否确定删除文章,不删除则返回
			return;
		
		$.post("/user/delArticle",{id:id},function(data){
			if(data){
				alert("删除成功!");
				$("#center").load("/user/myArticles");
			}else{
				alert("删除失败");
			}
		},"json");
	}
</script>
</head>
<body>
	
	<%-- <c:forEach items="${articles.list}" var="article">
		<dl>
			<dt><a href="javascript:myopen(${article.id})">${article.title}</a></dt>
			<dd>作者:${article.user.username} 发布时间:
			  <fmt:formatDate value="${article.created}"/>
				频道:${article.channel.name}  分类:${article.category.name}
			    <a href="javascript:toUpdate(${article.id})">修改</a>
			    <a href="javascript:delAtr(${article.id})">删除</a>
			</dd>
		</dl>
		<hr>
	</c:forEach> --%>
	
	
	<table>
	  <c:forEach items="${articles.list}" var="article">
	  	<tr style="border-bottom: 2px solid #e5e5e5;">
		    <td>
		    	<img src="../pic/${article.picture}" width="160px" height="90px">
		    </td>
		    <td>
		    	<dl>
					<dt><a href="javascript:myopen(${article.id})">${article.title}</a></dt>
					<dd>作者:${article.user.username} 发布时间:
					  <fmt:formatDate value="${article.created}"/>
						频道:${article.channel.name}  分类:${article.category.name}
					    <a href="javascript:toUpdate(${article.id})">修改</a>
					    <a href="javascript:delAtr(${article.id})">删除</a>
					</dd>
				</dl>
		    </td>
	 	</tr>
	</c:forEach>
	</table>
	
	<input type="button" onclick="page(1)" value="首页">&nbsp;
	<input type="button" onclick="page(${articles.prePage == 0 ? 1 : articles.prePage})" value="上一页">
	&emsp;${articles.lastPage == 0 ? 0 : articles.pageNum}/${articles.lastPage}&emsp;
	<input type="button" onclick="page(${articles.nextPage == 0 ? articles.lastPage : articles.nextPage})" value="下一页">&nbsp;
	<input type="button" onclick="page(${articles.lastPage})" value="尾页">

</body>
<script type="text/javascript">
	$(function(){
	    $('.page-link').click(function (e) {
	    	
	    	
	    	  //获取点击的的url
	        var url = $(this).attr('data');
	        // alert(url);
	    
	       //在中间区域显示地址的内容
	       $('#center').load(url);
	    });
		
	})
	
	// 翻页
	function page(url) {
		$('#center').load("myArticles?pageNum="+url);
	}
	
</script>
</html>