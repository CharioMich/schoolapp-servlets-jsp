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
    <div class="m-auto success">
        <div class="m-auto text-center">
            <h1 class="font-bold text-2xl mb-5">Επιτυχής Διαγραφή</h1>
            <p>Κωδικός: ${requestScope.id}</p>
        </div>
    </div>

    <div class="text-center text-m underline text-blue-700 hover:text-blue-900 mb-5">
        <a href="${pageContext.request.contextPath}/school-app/teachers/view">Επιστροφή</a>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
