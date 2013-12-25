<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <r:require modules="application, searchResults, searchIndex" />
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
</head>
<body>
<div id="status" role="complementary">
    <g:form controller="productSearch" action="search">
        <h1>Narrow your search results</h1>

        <div id="accordion">
            <h3>Manufacturer</h3>
            <div id="manufacturers" class="manufacturerList">
                <g:each in="${productSearchCriteriaVO.manufacturers}" var="manufacturer" status="i">
                    <label ><input type="checkbox"
                           name="manufacturers[${i}].${manufacturer}"/> ${manufacturer}</label>
                </g:each>
            </div>

            <h3>Category</h3>
            <div>To be determined</div>

            <h3>PriceRange</h3>
            <div></div>

            <h3>Vendors</h3>
            <div class="vendors">
                <label><g:checkBox checked="${productSearchCriteriaVO.bestBuy ?: false}" name="bestBuy"/>
                BestBuy</label>
                <label><g:checkBox checked="${productSearchCriteriaVO.amazon ?: false}" name="amazon"/>
                Amazon</label>
                <label><g:checkBox checked="${productSearchCriteriaVO.walmart ?: false}" name="walmart"/>
                Walmart</label>
            </div>
        </div>
        <input type="hidden" name="searchQuery" value="${productSearchCriteriaVO.searchQuery}">
        <g:submitButton name="Filter Search Results"/>
    </g:form>
</div>
<div id="page-body">
<g:each in="${searchResults.bbyData}" var="product">
	<div class="searchResultDiv">
        <div class="productDetails">
            <span class="productName">${product.name}</span>
            <g:if test="${product.shortDescription}">
                <p>${product.shortDescription}</p>
            </g:if>
            <span class="price">
                <g:if test="${product.regularPrice} == ${product.salePrice}">
                    Price: ${product.regularPrice}
                </g:if>
                <g:else>
                    Regular Price : ${product.regularPrice}
                    Sale Price : ${product.salePrice}
                </g:else>
            </span>
            <g:if test="${product.modelNumber}">
                Model Number: ${product.modelNumber}
            </g:if>
            <a href="${product.url}" target="_blank">More details on BestBuy</a>
        </div>
        <div class="productImage">
            <a href="${product.url}">
                <g:if test="${product.largeImage}">
                    <img alt='"${product.name}"' src="${product.largeImage}">
                </g:if>
                <g:elseif test="${product.mediumImage}">
                    <img alt='"${product.name}"' src="${product.mediumImage}">
                </g:elseif>
                <g:elseif test="${product.image}">
                    <img alt='"${product.name}"' src="${product.image}">
                </g:elseif>
            </a>
        </div>
	</div>
</g:each>
</div>
<script>
    $(function() {
        $( "#accordion" ).accordion({
            collapsible: true
        });
    });
</script>
</body>
</html>