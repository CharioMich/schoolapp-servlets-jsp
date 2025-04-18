<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Εισαγωγή Μαθητή</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50">
<%@ include file="header.jsp"%>

<main class="container mx-auto px-4 py-8">
    <div class="max-w-4xl mx-auto">
        <h1 class="text-2xl font-bold text-gray-800 mb-6">Εισαγωγή Μαθητή</h1>

        <!-- Error Message -->
        <c:if test="${not empty requestScope.errorMessage or not empty sessionScope.errorMessage}">
            <div class="mb-6 p-4 bg-red-50 border-l-4 border-red-500 text-red-700 rounded">
                <p>${fn:escapeXml(not empty requestScope.errorMessage ? requestScope.errorMessage : sessionScope.errorMessage)}</p>
            </div>
        </c:if>

        <!-- Teacher Form -->
        <div class="bg-white shadow-md rounded-lg p-6 mb-8">
            <form method="POST" action="${pageContext.request.contextPath}/school-app/students/insert" class="space-y-6">
                <!-- Name Row -->
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label for="firstname" class="block text-sm font-medium text-gray-700 mb-1">Όνομα</label>
                        <input id="firstname" name="firstname" type="text"
                               value="${fn:escapeXml(not empty requestScope.insertDTO.firstname ? requestScope.insertDTO.firstname : '')}"
                               class="w-full px-3 py-2 border ${not empty requestScope.firstnameMessage ? 'border-red-300' : 'border-gray-300'} rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                               placeholder="Όνομα">
                        <c:if test="${not empty requestScope.firstnameMessage}">
                            <p class="mt-1 text-sm text-red-600">${requestScope.firstnameMessage}</p>
                        </c:if>
                    </div>
                    <div>
                        <label for="lastname" class="block text-sm font-medium text-gray-700 mb-1">Επώνυμο</label>
                        <input id="lastname" name="lastname" type="text"
                               value="${fn:escapeXml(not empty requestScope.insertDTO.lastname ? requestScope.insertDTO.lastname : '')}"
                               class="w-full px-3 py-2 border ${not empty requestScope.lastnameMessage ? 'border-red-300' : 'border-gray-300'} rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                               placeholder="Επώνυμο">
                        <c:if test="${not empty requestScope.lastnameMessage}">
                            <p class="mt-1 text-sm text-red-600">${requestScope.lastnameMessage}</p>
                        </c:if>
                    </div>
                </div>

                <!-- Contact Info -->
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <label for="email" class="block text-sm font-medium text-gray-700 mb-1">E-mail</label>
                        <input id="email" name="email" type="text"
                               value="${fn:escapeXml(not empty requestScope.insertDTO.email ? requestScope.insertDTO.email : '')}"
                               class="w-full px-3 py-2 border ${not empty requestScope.emailMessage ? 'border-red-300' : 'border-gray-300'} rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                               placeholder="E-mail">
                        <c:if test="${not empty requestScope.emailMessage}">
                            <p class="mt-1 text-sm text-red-600">${requestScope.emailMessage}</p>
                        </c:if>
                    </div>
                    <div>
                        <label for="cityId" class="block text-sm font-medium text-gray-700 mb-1">Πόλη</label>
                        <select id="cityId" name="cityId"
                                class="w-full px-3 py-2 border ${not empty requestScope.cityIdMessage ? 'border-red-300' : 'border-gray-300'} rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500">
                            <option value="" disabled ${empty requestScope.insertDTO.cityId ? 'selected' : ''}>
                                Επιλέξτε Πόλη
                            </option>
                            <c:forEach items="${requestScope.cities}" var="city">
                                <option value="${city.id}"
                                        ${city.id eq requestScope.insertDTO.cityId ? 'selected' : ''}>
                                    ${fn:escapeXml(city.name)}
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${not empty requestScope.cityIdMessage}">
                            <p class="mt-1 text-sm text-red-600">${requestScope.cityIdMessage}</p>
                        </c:if>
                    </div>
                </div>

                <!-- Submit Button -->
                <div class="pt-4 text-center">
                    <button type="submit"
                            class="w-full md:w-auto px-6 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 transition-colors duration-200">
                        Εισαγωγή
                    </button>
                </div>
            </form>
        </div>

        <!-- Back Link -->
        <div class="text-center">
            <a href="${pageContext.request.contextPath}/school-app/students/view"
               class="text-blue-600 hover:text-blue-800 font-medium transition-colors duration-200">
                Επιστροφή στη λίστα μαθητών
            </a>
        </div>
    </div>
</main>

<%@ include file="footer.jsp"%>
</body>
</html>