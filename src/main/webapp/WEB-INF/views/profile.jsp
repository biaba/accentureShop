<%@ include file="header.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Profile</title>
</head>

<body>

</br>
<div align="center">
    </br>
   <h2>Profile</h2>
    <form:form action="/lastName" method="POST" modelAttribute="user">
        <form:select path="userName">
            <form:options items ="${lastNames.values()}"></form:options>
        </form:select>
        <input type = "submit" value = "Submit"/>
    </form:form>


    <table border="1" cellpadding="5">
        <tr>
            <th>Username</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Purchases</th>
        </tr>

        <tr>
            <td>
                ${user.userName}
            </td>
            <td>
                ${user.firstName}
            </td>
            <td>
                ${user.lastName}
            </td>
            <td>
                <c:forEach var="purchase" items="${user.purchases}" varStatus="index">
                    <p>
                        <form:form method="GET" action="${pageContext.request.contextPath}/customer/purchase/${purchase.id}">
                            ${index.index+1} <input type="submit" value="${purchase.feDateCreated}" />
                        </form:form>
                    </p>
                </c:forEach>
            </td>
        </tr>
    </table>
</div>
</body>
</html>