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
    <form:form id="form" action="${pageContext.request.contextPath}/cocktail/search" method="get">

        <form id="searchCocktailByName" action="cocktail/search">
            <fieldset>
                <legend>Search Cocktail by Name</legend>
                <label for="cocktailName">Name</label><input id="cocktailName" name="cocktailName" type="text" class="ui-autocomplete-input" />
                <p />
                <input type="submit" value="Search">
            </fieldset>
        </form:form>
</body>
</html>


