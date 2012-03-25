<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Cocktail Manager</title>
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
