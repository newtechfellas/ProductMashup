<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="ProductSearch"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
		<g:layoutHead/>
		<r:layoutResources />
		<style>
		.logoText {
			display : inline;
			float:right;
			font-size:2.5em;
			margin:20px;
			padding-right:39%;
			color:#48802C;
		}
		</style>
		<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
		<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	</head>
	<body>
        <div class="main">
            <div id="grailsLogo" role="banner">
                   <img src="${resource(dir: 'images', file: 'SearchIcon.png')}" alt="SearchYourProducts"/>
                    <span class="logoText">Comparison made easy</span>
            </div>
            <div class="appPageBody">
            <g:layoutBody/>
            </div>
            <div class="footer" role="contentinfo">Copyright Newtechfellas 2013</div>
            <g:javascript library="application"/>
            <r:layoutResources />
        </div>
	</body>
</html>
