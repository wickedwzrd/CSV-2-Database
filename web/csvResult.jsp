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
<style>
body{
font-family:arial, helvetica, sans-serif;
background-color: #1e1e1e;
color: #00dc00;
}
h1 {
color:#00daff;
}
table, th, td {
border-collapse:collapse;   
border:1px solid #aaa;
padding:0 10px;
background-color: transparent;
}
tr:nth-child(odd) {
color: #008cff; 
}
</style>
</head>
<body>

<h1>CSV Upload Result</h1>
<p>Parsed Records: ${parsedCount}</p>
<p>Added Records: ${addedCount}</p>

<%-- print all records using EL --%>
<table>
<tr>
<th>#</th><th>First Name</th> <th>Last Name</th> <th>Company Name</th> 
<th>Address</th><th>City</th> <th>Province</th> <th>Postal</th><th>Phone 1</th>
<th>Phone 2</th><th>Email</th> <th>Web</th>
</tr>

<%-- foreach loop to print added persons --%>
<c:forEach var="person" items="${addedPersons}" varStatus="status">
    <tr>
    <td>${status.count}</td>
    <td>${person.firstName}</td>
    <td>${person.lastName}</td>
    <td nowrap>${person.companyName}</td>
    <td nowrap>${person.address}</td>
    <td nowrap>${person.city}</td>
    <td>${person.province}</td>
    <td nowrap>${person.postal}</td>
    <td>${person.phone1}</td>
    <td>${person.phone2}</td>
    <td>${person.email}</td>
    <td>${person.web}</td>
    </tr>
</c:forEach>

</table>

</body>
</html>
