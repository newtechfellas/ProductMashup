<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>Your ProductSearch</title>
<r:require modules="application, searchIndex"/>
</head>
<body>
	<div id="status" role="complementary">
		<h1>Get mashup data for</h1>
		<ul>
			<li>BestBuy</li>
			<li>Amazon</li>
			<li>Target</li>
			<li>Overstock</li>
			<li>Walmart</li>
			<li>...many more</li>
		</ul>
	</div>
	<div id="page-body" role="main">
		<h1>Search for products</h1>
		<g:form action="search">
			<g:each in="${productTypes}" var="productType">
				<g:checkBox name="${productType}" />
				<label for="${productType}">
					${productType}
				</label>
			</g:each>
			<fieldset class="form">
				<div>
					<g:textField size="60" name="searchQuery"
						value="Enter Your Search Criteria" />
					<g:submitButton style="float:right" name="Search" />
				</div>
				<g:checkBox name="bestBuy" />
				<label for="bestBuy">BestBuy</label>
				<g:checkBox name="amazon" />
				<label for="amazon">Amazon</label>
				<g:checkBox name="walmart" />
				<label for="walmart">Walmart</label>
			</fieldset>
		</g:form>
	</div>
</body>
</html>
