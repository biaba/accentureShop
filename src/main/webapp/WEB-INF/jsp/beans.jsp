<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ALL APP CONTEXT BEANS</title>
</head>
<body>

<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>ALL APP CONTEXT BEANS</h2></caption>
        <tr>
            <th>No</th>
            <th>Bean</th>
        </tr>
        <c:forEach var="bean" items="${beans}" varStatus="loop">
            <tr>
                <td><c:out value="${loop.index}" /></td>
                <td><c:out value="${bean}" /></td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
