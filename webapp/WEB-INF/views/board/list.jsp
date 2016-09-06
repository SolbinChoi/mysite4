<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite4/assets/css/board.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div id="container">
		<c:import url='/WEB-INF/views/include/header.jsp' />
		<div id="content">
			<div id="board">
				<form id="search_form" action="/mysite4/board/list" method="get">
					<input type="text" id="kwd" name="kwd" value="${map.keyword }"> 
					<input type="submit" value="찾기">
				</form>
				<h4>
					전체 글수 : <span>${map.totalCount }</span>
				</h4>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>

					<c:set var="firstIndex"
						value="${map.totalCount - (map.currentPage - 1)*map.sizeList }" />
					<c:forEach var='vo' items='${map.list}' varStatus='status'>
						<tr>
						
							<c:choose>
							<c:when test='${vo.depth == 1 }'>
								<td><img src="/mysite4/assets/images/que.PNG"></td>
							</c:when>
							<c:otherwise>
								<td><img src="/mysite4/assets/images/an.PNG"></td>
							</c:otherwise>
							</c:choose>
							
							<c:choose>
							<c:when test='${vo.depth > 1 }'>
							<td style="text-align:left; padding-left:${vo.depth*10}px">
								<img src="/mysite4/assets/images/re2.png">
								<c:choose>
									<c:when test='${authUser.no == vo.userNo}'>
                   					<a href="/mysite4/board/view?no=${vo.no}">${vo.title }</a>
                   					</c:when>
                   				<c:otherwise>
                   					<a href="/mysite4/board/right">${vo.title }</a>
                   				</c:otherwise>
                   				</c:choose>
							</td>
							 </c:when>
							 <c:otherwise>
							 	<td style="text-align:left">
							 	<c:choose>
							 	<c:when test='${authUser.no == vo.userNo}'>
									<a href="/mysite4/board/view?no=${vo.no}">${vo.title }</a>
									</c:when>
										<c:otherwise>
                   					<a href="/mysite4/board/right">${vo.title }</a>
                   				</c:otherwise>
									
								</c:choose>
									</td>
							 </c:otherwise>
							 </c:choose>
							<td>${vo.name }</td>
							<td>${vo.count }</td>
							<td>${vo.date }</td>
							<td>
							<c:choose>
							<c:when test='${not empty authUser && authUser.no == vo.userNo }'>
								<a href="/mysite4/board/delete?no=${vo.no}" class="del">삭제</a>
							</c:when>
							<c:otherwise>
            						&nbsp;
              				</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>

				<!-- begin:paging -->
				<div class="pager">
					<ul>
						<c:if test="${map.prevPage > 0 }">
							<li><a href="/mysite4/board/list?p=${map.prevPage }">◀</a></li>
						</c:if>
						<c:forEach begin='${map.firstPage }' end='${map.lastPage }' step='1' var='i'>
							<c:choose>
								<c:when test='${map.currentPage == i }'>
									<li class="selected">${i }</li>
								</c:when>
								<c:when test='${i > map.pageCount }'>
									<li>${i }</li>
								</c:when>
								<c:otherwise>
									<li><a href="/mysite4/board/list?p=${i }">${i }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<c:if test='${map.nextPage > 0 }'>
							<li><a href="/mysite4/board/list?p=${map.nextPage }">▶</a></li>
						</c:if>
					</ul>
				</div>

				<!-- end:paging -->
				<c:choose>
					<c:when test='${empty authUser }'>

   &nbsp;      
   </c:when>
					<c:otherwise>
						<div class="bottom">

							<a href="/mysite4/board/write"
								id="new-book">글쓰기</a>
						</div>

					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<c:import url='/WEB-INF/views/include/navi.jsp' />
		<c:import url='/WEB-INF/views/include/footer.jsp' />
	</div>
</body>
</html>