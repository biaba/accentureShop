<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ include file="js.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="icon" type="image/x-icon" href="/images/icon.png" >
   <!-- <link rel="stylesheet" href="css/style.css">-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/<spring:theme code='stylesheet'/>"/>

</head>
<sec:authorize access="hasAnyRole('ADMIN', 'CUSTOMER','LYCUSTOMER')" var="isAuthenticated"/>
<sec:authorize access="hasRole('ADMIN')" var="isManager"/>
<sec:authorize access="hasAnyRole('CUSTOMER','LYCUSTOMER')" var="isCustomer"/>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="">KukuDuku</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="nav navbar-nav navbar-left">
            <li class="nav-item active"><a class="nav-link" href="home">Home <span class="sr-only">(current)</span></a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/shop">Shop</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/products/category/clothes">Clothes</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/products/category/toys">Toys</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/products/category/accessories">Accessories</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/registration">Registration</a></li>
            <li class="nav-item"><a class="nav-link" href="contact">Contacts</a></li>
            <li class="nav-item"><a class="nav-link" href="about">About</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/cart">Cart</a></li>
            <c:if test="${isAuthenticated}">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/logout"><i class="glyphicon glyphicon-log-in"></i>Logout</a></li>
            </c:if>
            <c:if test="${!isAuthenticated}">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/login"><i class="glyphicon glyphicon-log-in"></i>Login</a></li>
            </c:if>
            <c:if test="${isCustomer}">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/customer/profile"><i class="glyphicon glyphicon-log-in"></i>Profile</a></li>
            </c:if>
            <c:if test="${isManager}">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/manager/customers"><i class="glyphicon glyphicon-log-in"></i>Customers</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/manager/products/add"><i class="glyphicon glyphicon-log-in"></i>Create Product</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/manager/discounts/add"><i class="glyphicon glyphicon-log-in"></i>Create Discount</a></li>
            </c:if>
        </ul>
        <a href="?theme=dark">dark</a> | <a href="?theme=light">light</a>
    </div>
</nav>
</body>
</html>
