<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>

    <style><%@include file="/WEB-INF/css/MyCSS.css"%></style>

</head>

<body>

<h2>All Employees</h2>
<br>

<table>                     <%--таблица--%>
    <tr>                    <%--строка--%>
        <th>Name</th>       <%--заголовок--%>
        <th>Surname</th>
        <th>Department</th>
        <th>Salary</th>
        <th>Operations</th>
    </tr>

        <c:forEach var="emp" items="${allEmps}">                <%--цикл, var временная переменная, items - список--%>

            <c:url var="updateButton" value="/updateInfo">      <%--линк в котором содержится id--%>
                <c:param name="empId" value="${emp.id}"/>
            </c:url>

            <c:url var="deleteButton" value="/deleteEmployee">
                <c:param name="empId" value="${emp.id}"/>
            </c:url>

            <tr>
                <td>${emp.name}</td>                             <%--ячейка в таблице--%>
                <td>${emp.surname}</td>
                <td>${emp.department}</td>
                <td>${emp.salary}</td>
                <td>
                    <input type="button" class="b1" value="Update"                     <%--кнопка ссылается на линк с id--%>
                    onclick="window.location.href = '${updateButton}'"/>

                    <input type="button" class="b2" value="Delete"
                    onclick="window.location.href = '${deleteButton}'"/>
                </td>
            </tr>

        </c:forEach>

</table>

<br>

<input type="button" class="b3" value="Add"
    onclick="window.location.href = 'addNewEmployee'"/>


</body>

</html>