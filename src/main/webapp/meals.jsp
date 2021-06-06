<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://webapp/WEB-INF/functions" %>
<html lang="ru">
<head>
    <link href="CSS/styles.css" rel="stylesheet" type="text/css">
    <title>Meals</title>
</head>
<body>
    <h2><a href="http://localhost:8080/topjava">Home</a></h2>
    <br>
    <h2>Meals</h2>

    <table>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        <jsp:useBean id="mealsWithExcess" scope="request" type="java.util.List"/>
        <c:forEach items="${mealsWithExcess}" var="meal">
            <c:set var="tr_class" value="tr_ordinary"/>
            <c:set var="date" value="${meal.getDateTime()}"/>
            <c:if test="${meal.isExcess()}" >
               <c:set var="tr_class" value="tr_excess"/>
            </c:if>
            <tr class="${tr_class}">
                <td>${f:formatLocalDateTime(date, 'yyyy-MM-dd HH:mm')}</td>
                <td>${meal.getDescription()}</td>
                <td>${meal.getCalories()}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
