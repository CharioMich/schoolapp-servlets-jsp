<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Επιτυχής Εισαγωγή</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/success.css">
</head>
<body class="bg-gray-50 min-h-screen flex flex-col">
    <%@ include file="header.jsp"%>
    <div class="success m-auto">
        <div class="m-auto text-center">
            <h1 class="font-bold text-2xl mb-5">Επιτυχής Ενημέρωση</h1>
            <p>Κωδικός: ${sessionScope.teacherInfo.id}</p>
            <p>Όνομα: ${sessionScope.teacherInfo.firstname}</p>
            <p>Επώνυμο: ${sessionScope.teacherInfo.lastname}</p>
        </div>

    </div>
    <div class="text-center text-m underline text-blue-800 hover:text-blue-600 mb-5">
		<a href="${pageContext.request.contextPath}/school-app/dashboard">Επιστροφή</a>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>
