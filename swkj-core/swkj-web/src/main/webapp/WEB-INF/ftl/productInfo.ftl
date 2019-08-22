<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>产品详情</title>

    <script type="text/javascript" src="${contextPath}/js/bvapi.js"></script>
    <script type="text/javascript" src="${contextPath}/js/common.js"></script>
    <script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${contextPath}/js/jquery.modalEffects.js"></script>
    <script type="text/javascript" src="${contextPath}/js/main.js"></script>
    <script type="text/javascript" src="${contextPath}/js/select2.full.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/animate.css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/global.css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery.bxslider.css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/modal.css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/select2.min.css"/>
</head>
<!--头部开始-->
<header class="animated fadeInDown">
    <div class="container">
        <a href="#" title="广州化妆品有限公司">
            <h1 class="logo"><img src="${contextPath}/image/2017320113510587.jpg" alt="广州化妆品有限公司"/></h1></a>
        <div class="navigation">
            <a href="#" class="navbar-toggle"><span></span></a>
            <ul class="primary">
                <li><a href="${contextPath}/index.html">主页</a></li>
                <li><a class="active" href="${contextPath}/product/products.html">产品</a></li>
                <li><a href="${contextPath}/selling.html">热销</a></li>
                <li><a href="${contextPath}/ODM.html">OEM/ODM</a>
                    <ul>
                        <li class="li-1"><a href="${contextPath}/ODM.html">服务项目</a></li>
                        <li class="li-1"><a href="${contextPath}/process.html">加工流程</a></li>
                    </ul>
                </li>
                <li><a href="${contextPath}/factory.html">工厂</a></li>
                <li><a href="${contextPath}/news/news.html">新闻</a></li>
                <li><a href="${contextPath}/contact.html">联系我们</a></li>
            </ul>
            <ul class="language">
                <li><a href="${contextPath}/index.html">中文版</a></li>
                <li><a href="#">English</a></li>
            </ul>
        </div>
    </div>
</header>

<!--内容开始-->
<div class="container product">
    <div class="product-item">
        <div class="row">
            <div class="col-sm-5 product-item-image-container">
                <img src="${product.picture_url}" class="product-item-image"
                     alt="${product.product_name}"/>
            </div>
            <div class="col-sm-7 product-item-content">
                <h2>${product.product_name}</h2>
                <div class="product-modal-content"><p>
                    <br/>
                </p>
                    <div style="white-space:normal;">
                        <span style="font-size:14px;font-family:'Times New Roman';">
                            <#if product.product_number??><span
                                    style="font-family:'Microsoft YaHei';font-size:16px;">${product.product_number}
                                &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;${product.capacity}</span>ml<br/></#if>
                                <span style="font-family:'Microsoft YaHei';font-size:16px;">&nbsp;</span><br/>

                            <#if product.function!="" && product.function??>
                                <strong><span style="font-family:'Microsoft YaHei';font-size:16px;">功能</span>
                                </strong><span style="font-family:'Microsoft YaHei';font-size:16px;">:</span><br/>
                                <span style="font-family:'Microsoft YaHei';font-size:16px;">${product.function}</span><br/>
                                <br/>
                            </#if>

                            <#if product.usage!="" && product.usage??>
                                <strong><span style="font-family:'Microsoft YaHei';font-size:16px;">使用方法</span>
                                </strong><span style="font-family:'Microsoft YaHei';font-size:16px;">:</span><br/>
                                <span style="font-family:'Microsoft YaHei';font-size:16px;"><#if product.usage??>${product.usage}</#if></span><br/>
                                <br/>
                            </#if>

                            <#if product.product_desc!="" && product.product_desc??>
                                <strong><span style="font-family:'Microsoft YaHei';font-size:16px;">产品描述</span></strong>
                                <span style="font-family:'Microsoft YaHei';font-size:16px;">：</span><br/>
                                <span style="font-family:'Microsoft YaHei';font-size:16px;">${product.product_desc}</span><br/>
                                <br/>
                            </#if>

                            <#if product.target_problem!="" && product.target_problem??>
                                <strong><span style="font-family:'Microsoft YaHei';font-size:16px;">针对</span></strong>
                                <span style="font-family:'Microsoft YaHei';font-size:16px;">：</span><br/>
                                <span style="font-family:'Microsoft YaHei';font-size:16px;">${product.target_problem}</span><br/>
                                <br/>
                            </#if>

                            <#if product.direction!="" && product.direction??>
                                <strong><span style="font-family:'Microsoft YaHei';font-size:16px;">方向</span></strong>
                                <span style="font-family:'Microsoft YaHei';font-size:16px;">：</span><br/>
                                <span style="font-family:'Microsoft YaHei';font-size:16px;">${product.direction}</span><br/>
                                <br/>
                            </#if>
                        </span>
                    </div>
                    <div>
                        <span style="font-size:14px;font-family:'Times New Roman';"><strong></strong></span><br/>
                        <span style="font-size:14px;font-family:'Times New Roman';"></span>
                    </div>
                    <p>
                        <br/>
                    </p>
                    <div>
                        <br/>
                    </div>
                    <p>
                        <br/>
                    </p>
                    <p>
                        <br/>
                    </p></div>
            </div>
        </div>
    </div>
</div>


<!--内容结束-->
<!--版权开始-->
<footer class="container">
    <div class="wrapper">
        <div class="row">
            <div class="col-sm-4 footer-text">版权所有@广州化妆品有限公司，洛杉矶，CA，美国90017&nbsp;&nbsp;<script type="text/javascript"
                                                                                               src="../../cs.ecqun.com/-id=2566358.js"
                                                                                               charset="utf-8"></script>

                <script type="text/javascript">var _paq = _paq || [];
                ;_paq.push(["setDomains", ["*"]]);
                _paq.push(['trackPageView']);
                _paq.push(['enableFormTracking']);
                _paq.push(['enableLinkTracking']);
                _paq.push(['enableTPF']);
                (function () {
                    var u = "//ta.sbird.xyz/";
                    _paq.push(['setTrackerUrl', u + 'ta.gif']);
                    _paq.push(['setSiteId', '006fbd5616dd132d995c1576827a3702']);
                    var d = document, g = d.createElement('script'), s = d.getElementsByTagName('script')[0];
                    g.type = 'text/javascript';
                    g.async = true;
                    g.defer = true;
                    s.parentNode.insertBefore(g, s);
                })();</script>
            </div>
            <div class="col-sm-8 footer-links">
                <ul>
                    <li><a href="${contextPath}/product/products.html">产品</a></li>
                    <li><a href="${contextPath}/selling.html">热销</a></li>
                    <li><a href="${contextPath}/factory.html">工厂</a></li>
                    <li><a href="${contextPath}/contact.html">联系我们</a></li>
                </ul>
            </div>
        </div>
    </div>
</footer>
<#--<script>-->
    <#--(function (i, s, o, g, r, a, m) {-->
        <#--i['GoogleAnalyticsObject'] = r;-->
        <#--i[r] = i[r] || function () {-->
            <#--(i[r].q = i[r].q || []).push(arguments)-->
        <#--}, i[r].l = 1 * new Date();-->
        <#--a = s.createElement(o),-->
                <#--m = s.getElementsByTagName(o)[0];-->
        <#--a.async = 1;-->
        <#--a.src = g;-->
        <#--m.parentNode.insertBefore(a, m)-->
    <#--})(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');-->

    <#--ga('create', 'UA-80279543-1', 'auto');-->
    <#--ga('send', 'pageview');-->

<#--</script>-->
<!--版权结束-->
<body>
</body>
</html>
