<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Coktails</title>
</head>
<body>
    <div>
        <h2>Coktails</h2>
        <table>
            <c:forEach items="${cocktails}" var="cocktail">
                <tr>
                    <td>&nbsp;<c:if test="${not empty cocktail.photoUrl}">
                            <img src="${cocktail.photoUrl}" />
                        </c:if></td>
                    <td><a href="${cocktail.id}">${cocktail.name}</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
