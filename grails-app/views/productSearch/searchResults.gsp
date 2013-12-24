<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<%-- TODO: move the styles to css file--%>
<r:require modules="application, searchResults" />
</head>
<body>
	<div class="resultsContainer">
		<div class="searchFilters">
			<ul>
				<li>One</li>
				<li>Two</li>
			</ul>
		</div>
		<div class="searchResults">
			<g:each in="${searchResults.bbyData}" var="product">
				<div class="searchResultDiv">
					<div class="productDetails">
						<span class="productName">
							${product.name}
						</span>
						<g:if test="${product.shortDescription}">
							<p>
								${product.shortDescription}
							</p>
						</g:if>
						<span class="price"> <g:if
								test="${product.regularPrice} == ${product.salePrice}">
                    Price: ${product.regularPrice}
							</g:if> <g:else>
                    Regular Price : ${product.regularPrice}
                    Sale Price : ${product.salePrice}
							</g:else>
						</span> <a href="${product.url}" target="_blank">More details on
							BestBuy</a>
					</div>
					<div class="productImage">
						<a href="${product.url}"> <g:if test="${product.largeImage}">
								<img alt='"${product.name}"' src="${product.largeImage}">
							</g:if> <g:elseif test="${product.mediumImage}">
								<img alt='"${product.name}"' src="${product.mediumImage}">
							</g:elseif> <g:elseif test="${product.image}">
								<img alt='"${product.name}"' src="${product.image}">
							</g:elseif>
						</a>
					</div>
				</div>
			</g:each>
		</div>
	</div>
</body>
</html>