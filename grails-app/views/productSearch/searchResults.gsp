<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>
<body>
Search results...
 ${searchResults.bbyData.size()}
<g:each in="${searchResults[0].bbyData}" var="product" >
	iterating
	<div style="margin:10px">
		Name: ${product.name}
		<img alt="${product.name}" src="${product.mediumImage}">
		<g:if test="${product.shortDescription}">
		<p>${product.shortDescription}</p>
		</g:if>
		<g:if test="${product.regularPrice} == ${product.salePrice}">
			Price: ${product.regularPrice}
		</g:if> 
		<g:else>
			Regular Price : ${product.regularPrice}
			Sale Price : ${product.salePrice}
		</g:else>
		<a href="${product.url}">Click here </a>to go to BestBuy to purchase the item
	</div>
</g:each>
</body>
</html>