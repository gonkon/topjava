<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${meal.id == null ? "Add meal" : "Edit meal"}</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h3>${meal.id == null ? "Add meal" : "Edit meal"}</h3>

    <form method="post" action='meals' name="frmAddMeal" accept-charset="UTF-8">
        <table>
            <tr>
                <td>DateTime: </td>
                <td><input type="datetime-local" name="dateTime" value=${meal.dateTime}><br></td>
            </tr>
            <tr>
                <td>Description: </td>
                <td><input type="text" name="description" value="${meal.description}"/><br></td>
            </tr>
            <tr>
                <td>Calories:</td>
                <td><input type="number" name="calories" value="${meal.calories}"/><br></td>
            </tr>
        </table>
        <input type="hidden" name="mealId" value="${meal.id}"/>
        <button type="submit" value="Submit">Save</button>
        <button type="reset" value="Reset">Cancel</button>
    </form>
</body>
</html>
