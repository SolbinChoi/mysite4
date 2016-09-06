<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
	String no = request.getParameter("no");
	String result = request.getParameter("r");
	
%>

<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite4/assets/css/guestbook.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div id="container">
		<c:import url='/WEB-INF/views/include/header.jsp'/>

		<div id="content">
			<div id="guestbook"  class="delete-form">
				<form method="post" name="deleteform" action="/mysite4/guestbook/delete">
					<input type='hidden' name="no" value="${param.no }"> 
					<label>비밀번호</label>
					<input type="password" name="password">
					 <input type="submit" value="확인">

						<%
						if ("false".equals(result)) {
					%>
					<p>비밀번호를 다시 입력해주세요</p>
					<%
						}
					%>
				</form>
				<a href="/mysite4/guestbook/list">방명록 리스트</a>


			</div>

		</div>
		<c:import url='/WEB-INF/views/include/navi.jsp'/>
		<c:import url='/WEB-INF/views/include/footer.jsp'/>

	</div>
</body>
</html>