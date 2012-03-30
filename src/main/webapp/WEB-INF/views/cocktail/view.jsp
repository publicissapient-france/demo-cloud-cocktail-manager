<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Cocktail - ${cocktail.name}</title>

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
<%@ include file="../analyticstracking.jspf" %>
</head>
<body>

    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <a class="brand" href="${pageContext.request.contextPath}/"> <img alt='Devoxx France Logo' height='28'
                    src='${pageContext.request.contextPath}/img/devoxx-france-logo.jpg' width='54' /> Cocktail Manager
                </a>
                <ul class="nav">
                    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li class="active"><a href="${pageContext.request.contextPath}/cocktail/">Cocktails</a></li>
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
        <div class="page-header">
            <h1>${cocktail.name}</h1>
        </div>

        <div class="row">
            <div class="span2">
                <c:if test="${not empty cocktail.photoUrl}">
                    <img src="${cocktail.photoUrl}" width="100" />
                </c:if>
            </div>
            <div class="span4">
                <h2>Instructions</h2>
                <p>${cocktail.instructionsAsHtml}</p>
            </div>
            <div class="span4">
                <h2>Ingredients</h2>
                <ul>
                    <c:forEach items="${cocktail.ingredients}" var="ingredient">
                        <li>${ingredient.quantity} ${ingredient.name}</li>
                    </c:forEach>
                </ul>
            </div>
            <div class="span2">
                <h2>Mail a friend</h2>
                <form:form id="sendByEmail" action="${pageContext.request.contextPath}/cocktail/${id}/mail" method="post"
                    modelAttribute="cocktail">
                    <fieldset>
                        <input id="recipientEmail" name="recipientEmail" type="text" placeholder="Email" class="span2" />
                    </fieldset>
                    <div class="btn-group">
                        <button type="submit" class="btn js-btn">Send</button>
                    </div>
                </form:form>
            </div>

        </div>
        <div class="row">
            <div class="span12">
                <div class="btn-group">
                    <a href="${pageContext.request.contextPath}/cocktail/${cocktail.id}/edit-form" class="btn js-btn">Edit</a>
                </div>
                <em><a href="${cocktail.sourceUrl}" target="_blank">${cocktail.sourceUrl}</a></em>
            </div>
        </div>
    </div>
</body>
</html>
