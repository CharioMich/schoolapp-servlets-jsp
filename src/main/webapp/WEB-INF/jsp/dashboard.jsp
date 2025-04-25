<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">-->
</head>
<body class="min-h-screen flex flex-col">
    <%@ include file="header.jsp"%>
    <main class="flex-grow justify-center h-auto mx-auto px-4 py-8">
        <h1 class="text-2xl font-bold text-gray-800 text-center">Επιλογή Κατηγορίας</h1>
        <div class="flex flex-row items-center justify-center h-64 text-center space-x-10">
            <a class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-5 px-8 rounded" href="<c:url value='${pageContext.request.contextPath}/school-app/teachers/view' />">Προβολή Καθηγητών</a>
            <a class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-5 px-8 rounded" href="<c:url value='${pageContext.request.contextPath}/school-app/students/view' />">Προβολή Μαθητών</a>
        </div>
    </main>
    <%@ include file="footer.jsp"%>

</body>
</html>
