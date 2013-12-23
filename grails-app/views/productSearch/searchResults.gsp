<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <%-- TODO: move the styles to css file--%>
<style>
    div.searchResultDiv {
        background-color: #E5EECC;
        background-image: linear-gradient(#FFFFFF, #E5EECC 100px);
        border: 1px solid #D4D4D4;
        color: #000000;
        margin: 15px;
        padding: 5px;
        width: auto;
        overflow: auto;
    }
    span.productName {
        font-family: Arial,Helvetica,sans-serif;
        font-size: 25px;
        font-weight: normal;
        line-height: 1.5;
        margin: 0;
        padding: 0 0 15px;
    }
    div.productDetails {
        float:right;
        width: 70%;
    }
    div.productImage {
        float:left;
    }
    span.price {
        font-size: 15px;
        font-weight: bold;
        line-height: 1.5;
        display: block;
    }
   
</style>
</head>
<body>
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
</body>
</html>