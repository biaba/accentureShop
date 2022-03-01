<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>THIS PAGE IS FOR TESTING HIBERNATE MAPPINGS</title>
</head>
<body>
<%@ include file="header.jsp"%>

<div align="center">
    <form:form action="${pageContext.request.contextPath}/test/go" method="POST">
        <input type="text" name="val"/>
        <input type="submit">

    </form:form>
</br>
    <form action="${pageContext.request.contextPath}/test/go" method="POST">
        <input type="text" name="val"/>
        <input type="submit">
    </form>

    <p>
        CSRF value: ${_csrf.getToken()}</br>
        CSRF header name: ${_csrf.getHeaderName()}</br>
        CSRF param name: ${_csrf.getParameterName()}

    </p>
    <table border="1" cellpadding="5">
        <caption><h2>THIS PAGE IS FOR TESTING HIBERNATE MAPPINGS</h2></caption>
        <tr>
            <th>ID</th>
            <th>UserName</th>
            <th>Email</th>
        </tr>
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td><c:out value="${customer.id}" /></td>
                <td><c:out value="${customer.userName}" /></td>
                <td><c:out value="${customer.email}" /></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>