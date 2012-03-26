<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Cocktail Manager</title>
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
        <h2>Cocktail</h2>
        <form:form id="form" action="${pageContext.request.contextPath}/cocktail/${id}" method="put" modelAttribute="cocktail">
            <fieldset>
                <legend>Cocktail details</legend>
                <form:label path="name">
		  			Name <form:errors path="name" cssClass="error" />
                </form:label>
                <form:input path="name" />
                <br />
                <form:label path="instructions">
		  			Instructions <form:errors path="instructions" cssClass="error" />
                </form:label>
                <form:textarea path="instructions" />
                <br />
                <form:label path="photoUrl">
                    Photo Url <form:errors path="photoUrl" cssClass="error" />
                </form:label>
                <form:input path="photoUrl" />
                <br />
            </fieldset>
            <p>
                <button type="submit">Submit</button>
            </p>
        </form:form>

        <form:form action="${pageContext.request.contextPath}/cocktail/${id}/photo" method="put" enctype="multipart/form-data">
            <fieldset>
                <legend>Cocktail's Photo</legend>
                <input name="photo" type="file" />
            </fieldset>
            <input type="submit" />
        </form:form>
    </div>
</body>
</html>
