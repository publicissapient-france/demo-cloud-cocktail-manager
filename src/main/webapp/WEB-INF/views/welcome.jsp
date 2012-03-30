<%@ page session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Cocktail Manager</title>

<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/favicon.png">

<!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le styles -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/bootstrap-responsive.min.css" media="screen" rel="stylesheet" type="text/css" />
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/themes/base/jquery-ui.css" rel="Stylesheet" type="text/css" />

<!-- Le javascript -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("input#searchCocktailByName").autocomplete({
            minLength : 2,
            source : "${pageContext.request.contextPath}/cocktail/suggest/name"
        });
        $("input#searchCocktailByIngredient").autocomplete({
            minLength : 2,
            source : "${pageContext.request.contextPath}/cocktail/suggest/ingredient"
        });
    });
</script>
<%@ include file="analyticstracking.jspf" %>
<body>
    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <a class="brand" href="${pageContext.request.contextPath}/"> <img alt='Devoxx France Logo' height='28'
                    src='${pageContext.request.contextPath}/img/devoxx-france-logo.jpg' width='54' /> Cocktail Manager
                </a>
                <ul class="nav">
                    <li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/cocktail/">Cocktails</a></li>
                </ul>
                <form class="navbar-search pull-left">
                    <input id="searchCocktailByName" name="searchCocktailByName" type="text" class="search-query"
                        placeholder="Search by name"> <input id="searchCocktailByIngredient" name="searchCocktailByIngredient"
                        type="text" class="search-query" placeholder="Search by ingredient">
                </form>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="span6">
                <div class="hero-unit">
                    <h2>A to Z cocktails list</h2>
                    <p>A to Z list of cocktail recipes.</p>
                    <p>
                        <a class="btn btn-primary btn-large" href="${pageContext.request.contextPath}/cocktail/"> Visit our cocktails! </a>
                    </p>
                </div>
            </div>
            <div class="span6">
                <div class="hero-unit">
                    <h2>Add your cocktail</h2>
                    <p>Add you cocktail recipe.</p>
                    <p>
                        <a class="btn btn-primary btn-large" href="${pageContext.request.contextPath}/cocktail/create-form"> Write your cocktail recipe </a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>