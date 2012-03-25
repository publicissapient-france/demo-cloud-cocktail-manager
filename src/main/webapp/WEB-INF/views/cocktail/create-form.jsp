<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Coktail Manager</title>
</head>
<body>
    <div>
        <h2>Coktail</h2>
        <form:form id="form" method="post" modelAttribute="cocktail" action="${pageContext.request.contextPath}/cocktail">
            <fieldset>
                <legend>Cocktail details</legend>
                <form:label path="name">
		  			Name <form:errors path="name" cssClass="error" />
                </form:label>
                <form:input path="name" />

                <form:label path="instructions">
		  			Instructions <form:errors path="instructions" cssClass="error" />
                </form:label>
                <form:textarea path="instructions" />
            </fieldset>
            <p>
                <button type="submit">Submit</button>
            </p>
        </form:form>
    </div>
</body>
</html>
