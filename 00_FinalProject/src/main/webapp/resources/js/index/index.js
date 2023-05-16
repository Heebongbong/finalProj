/**
 * 
 */
$(document).ready(function(){
	$(".index_camping_list").not('.slick-initialized').slick({
		dots: true,
		dotsClass : "slick-dots", 
		infinite: true,
		speed: 1000,
		slidesToShow: 1,
		adaptiveHeight: true,
		autoplay: true,
		autoplaySpeed: 4000,
		arrows: false
	});
});