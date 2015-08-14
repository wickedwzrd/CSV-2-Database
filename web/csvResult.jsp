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

<%-- print all records using EL --%>
<table>
<tr>
<th>First Name</th> <th>Last Name</th> <th>Company Name</th> <th>Address</th>
<th>City</th> <th>Province</th> <th>Postal</th> <th>Phone 1</th> <th>Phone 2</th>
<th>Email</th> <th>Web</th>
</tr>

<%-- foreach loop to print added persons --%>
<c:forEach var="person" items="${addedPersons}" varStatus="status">
    <tr>
    <td>${person.firstName}</td>
    <td>${person.lastName}</td>
    <td>${person.companyName}</td>
    <td>${person.address}</td>
    <td>${person.city}</td>
    <td>${person.province}</td>
    <td>${person.postal}</td>
    <td>${person.phone1}</td>
    <td>${person.phone2}</td>
    <td>${person.email}</td>
    <td>${person.web}</td>
    </tr>
</c:forEach>

</table>

</body>
</html>
