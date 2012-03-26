<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Coktail - ${coktail.name}</title>

<!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le styles -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" media="screen" rel="stylesheet" type="text/css" />
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/themes/base/jquery-ui.css" rel="Stylesheet" type="text/css" />

<!-- Le javascript -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>

</head>
<body>
    <div>
        <h2>${cocktail.name}</h2>
        <p />
        <pre>${cocktail.instructions}</pre>
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
