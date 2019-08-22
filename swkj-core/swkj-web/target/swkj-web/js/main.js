$(function() {

	navigation = $('.navigation ul.primary');

	$('.navbar-toggle').click(function() {
		$(this).toggleClass('active');
		navigation.slideToggle(250);

		return false;
	})

	navigation.find('.dropdown-toggle').click(function() {
		elem = $(this);
		elem.parent().find('.dropdown').slideToggle(250);
		elem.toggleClass('active');
		return false;
	});

	// slide down the nav dropdown
	$("li.dropdown ul").hide();
	$("li.dropdown").hover(function() {
		$(this).find('ul.dropdown').addClass('animated fadeInRight').show();
	}, function() {
		$(this).find('ul.dropdown').hide();
	});

	$(".sliderContainer").hover(function() {
		$(this).find('.cap-text').slideToggle(200);
	});
	// $(".newsletter .pull-right").hide();
	// $(".newsletter").hover(function() {
	// 	$(this).find('.pull-right').slideDown();
	// }, function() {
	// 	$(this).find('.pull-right').slideUp();
	// });

	if ( $("ul.dropdown.products").length > 0 ) {
		var parent = $("ul.dropdown.products");
		if ( parent.children().length < 3 ) {
			var newWidth = parent.children().length * 176;
			parent.width(newWidth);
		}
	}

	$(".map .ingredients ul li").hover(function() {
		var id = $(this).attr('id');
		$(".spot#" + id).addClass('hover');
	}, function() {
		var id = $(this).attr('id');
		$(".spot#" + id).removeClass('hover');
	});

	// boot up the slider
	if ( $(".slides_container").length > 0 ) {
		if ( $(".slides_container li").length > 1 ) {
			$(".slides_container").bxSlider({
				controls: false,
				auto: true,
				pause: 4000,
				speed: 1500
			});
		}
	}
	if ( $(".slides_container_small").length > 0 ) {
		if ( $(".slides_container_small li").length > 1 ) {
			$(".slides_container_small").bxSlider({
				auto: true,
				pause: 4000,
				controls: false,
				speed: 1500
			});
		}
	}
	if ( $(".slider_container").length > 0 ) {
		if ( $(".slider_container li").length > 1 ) {
			$(".slider_container").bxSlider({
				pager: false
			});
		}
	}

	$('.faq-questions li').click(function(e) {
		$(this).find('.answer').slideToggle();
	});
});

var polyfilter_scriptpath = '/assets/js/'

if ( $(".md-trigger").length ) {
	$('.md-trigger').modalEffects();
}

//搜索
function chkemail(sn){
if(sn.email.value==""){
alert("邮箱地址不能为空！"); 
sn.email.focus(); 
return false;
}
if(!is_email(sn.email.value))
{ alert("邮箱地址错误！");
sn.email.focus();
return false;
}
return true;
}

//邮箱格式
function is_email(str)
{ if((str.indexOf("@")==-1)||(str.indexOf(".")==-1))
{
return false;
}
return true;
}