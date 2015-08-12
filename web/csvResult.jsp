<%-- 
    Document   : csvResult
    Created on : Aug 12, 2015, 2:43:51 PM
    Author     : thomas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Csv2Database Result</title>
</head>
<body>

<h1>CSV Upload Result</h1>
<p>${parsedCount}</p>
<p>${addedPersons}</p>

<%-- foreach loop to print added persons --%>
<c:forEach var="person" items="${addedPersons}" varStatus="status">
    <p>${status.count} : ${person.firstName} ${person.lastName}</p>
</c:forEach>
</body>
</html>
