<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://webapp/WEB-INF/functions" %>
<html lang="ru">
<head>
    <link href="CSS/styles.css" rel="stylesheet" type="text/css">
    <title>Meals</title>
</head>
<body>
<h3><a href="http://localhost:8080/topjava">Home</a></h3>
<hr>
<h3>Meals</h3>

<a href="meals?action=add">Add Meal</a>

<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Id</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="mealsWithExcess" scope="request" type="java.util.List"/>
    <c:forEach items="${mealsWithExcess}" var="meal">
        <c:set var="tr_class" value="tr_ordinary"/>
        <c:set var="date" value="${meal.getDateTime()}"/>
        <c:if test="${meal.isExcess()}">
            <c:set var="tr_class" value="tr_excess"/>
        </c:if>
        <tr class="${tr_class}">
            <td>${f:formatLocalDateTime(date, 'yyyy-MM-dd HH:mm')}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td>${meal.getId()}</td>
            <td>
                <a href="meals?action=edit&mealId=<c:out value="${meal.getId()}"/>">Update</a>
            </td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.getId()}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
