<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


  
	<!-- 所有分类下的文章 -->
	<ul class="list-unstyled">
		<hr>
	   <!-- 栏目下所有文章 -->
		<c:forEach items="${articles.list }" var="a">
			<li class="media"><img  width="120px" height="80px" class="mr-3" src="/pic/${a.picture }"
				alt="no pic">
				<div class="media-body">
					<h5 class="mt-0 mb-1"><small><a href="javascript:myopen(${a.id })"> ${a.title }</a></small></h5>
					<br>
					<c:if test="${a.tags != null}">
						标签: ${a.tags}
					</c:if>
					<br>
					<h5 class="mt-0 mb-1"><small>  ${a.user.username}  &nbsp;  <fmt:formatDate value="${a.created }" pattern="yyyy-MM-dd"/> </small></h5>
					
				</div>
			</li>
			<hr>
		</c:forEach>
		<div>
			${pageStr}
			<a href="index?chnId=${chnId}&catId=${catId}&key=${key}&pageNum=1">首页</a>&nbsp;
			<a href="index?chnId=${chnId}&catId=${catId}&key=${key}&pageNum=${articles.prePage == 0 ? 1 : articles.prePage}">上一页</a>
			&emsp;${articles.pages == 0 ? 0 : articles.pageNum}/${articles.pages}&emsp;
			<a href="index?chnId=${chnId}&catId=${catId}&key=${key}&pageNum=${articles.nextPage == 0 ? articles.pages : articles.nextPage}">下一页</a>&nbsp;
			<a href="index?chnId=${chnId}&catId=${catId}&key=${key}&pageNum=${articles.pages}">尾页</a>
		</div>
	</ul> 

	<script type="text/javascript">
	 function myopen(id){
		 //在新窗口打开文章的详情J
		 window.open("/article/getDetail?aId="+id,"_blank")
	 }
   </script>
 <jsp:include page="/WEB-INF/view/common/includejs.jsp"></jsp:include>
