<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Update</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/teacher-update.css">
</head>
<body>
<%@ include file="header.jsp"%>
<div class="main-content">
	<div class="form p-10 m-auto border-2 border-blue-500/75 flex-auto">
		<form method="POST" action="${pageContext.request.contextPath}/school-app/students/update">
            <input type="hidden" name="id" value="${requestScope.updateDTO.id}" >
            <div class="row m-bottom justify-between ">
                <div class="w-1/2">
                    <label for="firstname">Όνομα:</label>
                    <input class="m-bottom p-1 border-2 rounded-md border-gray-500/75 focus:outline-2 focus:outline-blue-500" type="text" name="firstname" value="${requestScope.updateDTO.firstname}" placeholder="Όνομα">
                    <p class="validation-error">${sessionScope.firstnameMessage}</p>
                </div>
                <div class="w-1/2">
                    <label for="lastname">Επώνυμο:</label>
                    <input class="m-bottom p-1 border-2 rounded-md border-gray-500/75 focus:outline-2 focus:outline-blue-500" type="text" name="lastname" value="${requestScope.updateDTO.lastname}" placeholder="Επώνυμο">
                    <p class="validation-error">${sessionScope.lastnameMessage}</p>
                </div>
            </div>
            <div class="row m-bottom justify-between">
                <div class="w-1/2">
                    <!-- City Dropdown -->
                    <label for="cityId">Πόλη:</label>
                    <select class="m-bottom w-[192px] p-1 border-2 rounded-md border-gray-500/75 focus:outline-2 focus:outline-blue-500" name="cityId">
                    <%-- The Select City option appears in the dropdown but cannot be chosen after a real selection is made --%>
                        <option value="" disabled ${empty updateDTO.cityId ? 'selected' : ''}>
                            Επιλογή Πόλης
                        </option>
                        <c:forEach items="${cities}" var="city">
                            <option value="${city.id}"
                                    ${city.id eq updateDTO.cityId ? 'selected' : ''}>
                                ${city.name}
                            </option>
                        </c:forEach>
                    </select>
                    <p class="validation-error">${sessionScope.cityIdMessage}</p>
                </div>
                <div class="w-1/2">
                    <label for="email">E-mail:</label>
                    <input class="m-bottom p-1 border-2 rounded-md border-gray-500/75 focus:outline-2 focus:outline-blue-500" type="text" name="email" value="${requestScope.updateDTO.email}" placeholder="E-mail">
                    <p class="validation-error">${sessionScope.emailMessage}</p>
                </div>
            </div>
            <div class="row justify-center">
                <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" type="submit">Εισαγωγή</button>
            </div>

		</form>	
	</div>
    <div class="pt-6 text-center text-m underline text-blue-800 hover:text-blue-600 mb-1">
        <a href="${pageContext.request.contextPath}/school-app/students/view">Επιστροφή</a>
    </div>
    <div>
        ${requestScope.errorMessage}
    </div>
</div>
</body>
</html>
