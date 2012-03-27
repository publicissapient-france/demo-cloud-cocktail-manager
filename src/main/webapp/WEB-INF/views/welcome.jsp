<%@ page session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Coktail Manager</title>

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

</head>
<body>
    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <a class="brand" href="${pageContext.request.contextPath}/">Cocktail Manager</a>
                <ul class="nav">
                    <li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/cocktail/">Cocktails</a></li>
                </ul>
                <form class="navbar-search pull-left">
                    <input id="searchCocktail" name="searchCocktail" type="text" class="search-query" placeholder="Search cocktails">
                </form>
            </div>
        </div>
    </div>

    <div class="container">
        <ul>
            <li><a href="${pageContext.request.contextPath}/cocktail/create-form">Create Cocktail</a></li>
            <li><a href="${pageContext.request.contextPath}/cocktail/">Cocktails</a></li>
        </ul>
    </div>
</body>
</html>