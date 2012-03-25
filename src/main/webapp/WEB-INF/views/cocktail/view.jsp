<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Coktail - ${coktail.name}</title>
<link type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/themes/base/jquery-ui.css" rel="Stylesheet" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
    <div>
        <h2>${cocktail.name}</h2>
        <p>
        <pre>${cocktail.instructions}</pre>
        </p>
        <c:if test="${not empty cocktail.photoUrl}">
            <img src="${cocktail.photoUrl}" />
        </c:if>
        <p>
            <a href="${pageContext.request.contextPath}/cocktail/${cocktail.id}/edit-form">Edit cocktail</a>
        </p>
        <form:form id="sendByEmail" action="${pageContext.request.contextPath}/cocktail/${id}/mail" method="post" modelAttribute="cocktail">
            <fieldset>
                <legend>Send by email</legend>
                <label for="recipientEmail">Email</label> <input id="recipientEmail" name="recipientEmail" type="text" />
            </fieldset>
            <p>
                <button type="submit">Send</button>
            </p>
        </form:form>
    </div>
</body>
</html>
