<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>联系我们</title>

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
<body>

<!--头部开始-->
<header class="animated fadeInDown">
    <div class="container">
        <a href="${contextPath}/index.html" title="广州化妆品有限公司">
            <h1 class="logo"><img src="${contextPath}/image/2017320113510587.jpg" alt="广州化妆品有限公司"/></h1></a>
        <div class="navigation">
            <ul class="primary">
                <li><a href="${contextPath}/index.html">主页</a></li>
                <li><a href="${contextPath}/product/products.html">产品</a></li>
                <li><a href="${contextPath}/selling.html">热销</a></li>
                <li><a href="${contextPath}/ODM.html">OEM/ODM</a>
                    <ul>
                        <li class="li-1"><a href="${contextPath}/ODM.html">服务项目</a></li>
                        <li class="li-1"><a href="${contextPath}/process.html">加工流程</a></li>
                    </ul>
                </li>
                <li><a href="${contextPath}/factory.html">工厂</a></li>
                <li><a href="${contextPath}/news/news.html">新闻</a></li>
                <li><a class="active" href="${contextPath}/contact.html">联系我们</a></li>
            </ul>
            <ul class="language">
                <li><a href="${contextPath}/index.html">中文版</a></li>
                <li><a href="#">English</a></li>
            </ul>
        </div>
    </div>

</header>
<!--内容开始-->
<div class="wrapper container">
    <div class="content" style="margin-left:15%"><p>
        <img src="<#if contact.picture_url?? >${contact.picture_url}<#else></#if>" alt="" width="500" height="485"
             title="" align=""/>
        <p>美国办公室：<#if contact.USA_office?? >${contact.USA_office}<#else></#if></p>
        <p>电子邮件：<#if contact.USA_email?? >${contact.USA_email}<#else></#if></p><br/>
        <p>亚洲办事处：<#if contact.CN_agency?? >${contact.CN_agency}<#else></#if></p>
        <p>工厂：<#if contact.factory_address?? >${contact.factory_address}<#else></#if></p>
        <p>办公室：<#if contact.CN_office?? >${contact.CN_office}<#else></#if></p>
        <p>电子邮件：<#if contact.CN_email?? >${contact.CN_email}<#else></#if></p>
        <p>办公电话：<#if contact.phone?? >${contact.phone}<#else></#if></p>
        <p>网站：<#if contact.website_address?? >${contact.website_address}<#else></#if></p>
        </p>
    </div>

    <!--内容结束-->
    <!--版权开始-->
    <footer class="container">
        <div class="wrapper">
            <div class="row">
                <div class="col-sm-4 footer-text">版权所有@<#if contact.CN_office?? >${contact.CN_office}<#else></#if>&nbsp;&nbsp;

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
</body>
<#--<script>-->
<#--(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){-->
<#--(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),-->
<#--m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)-->
<#--});-->

<#--</script>-->
<!--版权结束-->
</html>
