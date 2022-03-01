<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Spring MVC - Hibernate File Upload to Database Demo</title>
</head>
<%@ include file="header.jsp"%>
<body>
<c:if test="${id}>0">
    Just uploaded image with id: ${id}
</c:if>
<div align="center">
    <h1>Spring MVC - Hibernate File Upload to Database Demo</h1>
    <form:form action="${pageContext.request.contextPath}/manager/upload" enctype="multipart/form-data" method="POST">
        <p>Pick file #1:</p>
        <input type="file" name="image" size="50" />
        <input type="submit" value="Upload" />
    </form:form>
</div>
</body>
</html>