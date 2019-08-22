<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>主页</title>

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
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/pageSwitch.min.css"/>
</head>

<body>
<!--头部开始-->
<header class="animated fadeInDown">
    <div class="container">
        <a href="${contextPath}/index.html" title="广州化妆品有限公司">
            <h1 class="logo"><img src="${contextPath}/image/2017320113510587.jpg" alt="广州化妆品有限公司"/></h1></a>
        <div class="navigation">
            <ul class="primary">
                <li><a class="active" href="${contextPath}/index.html">主页</a></li>
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
<div class="wrapper container">
    <div id="container">
        <div class="sections">
        <#if (slideImgs?size > 0)>
            <#list slideImgs as slideImg>
                <a href="javascript:void(0)">
                    <div class="section" id="section${slideImg.id}"></div>
                </a>
            </#list>
        <#else >
            暂无此内容！
        </#if>
        </div>
    </div>
    <script type="text/javascript" src="${contextPath}/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${contextPath}/js/pageSwitch.min.js"></script>
    <script>
        $("#container").PageSwitch({
            direction: 'horizontal',
            easing: 'ease-in',
            duration: 1000,
            autoPlay: true,
            loop: 'false'
        });
    </script>

    <hr>
    <ul class="social-homepage row">
    <#if recProducts??>
        <#list recProducts as recProduct>
            <li class="col-sm-3 col-xs-6">
                <div class="item-container">
                    <div class="image-container">
                        <a href="${contextPath}${recProduct.page_url}" title="${recProduct.product_name}"><img
                                src="${recProduct.picture_url}" alt="${recProduct.product_name}"/></a>
                    </div>
                    <div class="item-content">
                        <h3>${recProduct.product_name}</h3>
                    </div>
                </div>
            </li>
        </#list>
    <#else >
        暂无此内容！
    </#if>
    </ul>
    <div class="clearfix"></div>
    <div class="newsletter">
        <div class="row">
            <div class="col-md-8"><i class="icon-newsletter"></i>
                <h3>我们的博客！</h3>
                <p>
                <p>查看更多关于<a href="#">我们的博客！</a></p>
                </p>
            </div>
            <div class="col-md-4 newsletter-form">
                <h3>订阅我们的电子报</h3>
                <form name="emailform" method="POST" action="#" onSubmit="return chkemail(this);">
                    <input name="email" id="email" type="email" value="">
                    <button type="submit" class="btn">提交</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!--内容结束-->
<!--版权开始-->
<footer class="container">
    <div class="wrapper">
        <div class="row">
            <div class="col-sm-4 footer-text">版权所有@广州化妆品有限公司，洛杉矶，CA，美国90017&nbsp;&nbsp;

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
<script>
    //  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    //  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    //  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    //  });

</script>
<!--版权结束-->
<style type="text/css">
    * {
        padding: 0;
        margin: 0;
    }

    html, body {
        height: 100%;
    }

    #container {
        width: 100%;
        height: 500px;
        overflow: hidden;
    }

    .sections, .section {
        height: 100%;
    }

    #container, .sections {
        position: relative;
    }

    .section {
        background-color: #000;
        background-size: cover;
        background-position: 50% 50%;
        text-align: center;
        color: white;
    }
    <#list slideImgs as slideImg>
    #section${slideImg.id} {
        background-image: url(${contextPath}${slideImg.img_url})
    }
    </#list>
</style>

</body>
</html>
