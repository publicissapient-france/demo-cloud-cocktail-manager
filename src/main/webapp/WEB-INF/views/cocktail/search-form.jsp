<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Cocktails Search</title>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
<link type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/themes/base/jquery-ui.css" rel="Stylesheet" />
<script type="text/javascript">
	$(document).ready(function() {
		$("input#cocktailName").autocomplete({
			minLength : 2,
			// source : [ "vodka", "orange juice", "cranberry juice" ],
			source : "${pageContext.request.contextPath}/cocktail/completion"
		});
	});
</script>
</head>
<body>
    <form:form id="searchCocktailByName" action="${pageContext.request.contextPath}/cocktail/search" method="get">
        <fieldset>
            <legend>Search Cocktail by Name</legend>
            <label for="cocktailName">Name</label><input id="cocktailName" name="cocktailName" type="text" class="ui-autocomplete-input" />
            <p />
            <input type="submit" value="Search">
        </fieldset>
    </form:form>
</body>
</html>


