<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add / Edit / Delete meal</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h3>Add / Edit / Delete meal</h3>

    <form method="post" action='meals' name="frmAddUser" accept-charset="UTF-8">
        <table>
            <tr>
                <td>DateTime: </td>
                <td><input type="datetime-local" name="dateTime" value=${dateTime}><br></td>
            </tr>
            <tr>
                <td>Description: </td>
                <td><input type="text" name="description" value="${description}"/><br></td>
            </tr>
            <tr>
                <td>Calories:</td>
                <td><input type="text" name="calories" value="${calories}"/><br></td>
            </tr>
        </table>
        <input type="hidden" name="mealId" value="${mealId}"/>
        <button type="submit" value="Submit">Save</button>
        <button type="reset" value="Reset">Cancel</button>
    </form>
</body>
</html>
