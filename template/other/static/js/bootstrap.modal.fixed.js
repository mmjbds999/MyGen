(function($){
	$(function(){
		$('.modal').on('show.bs.modal', function (e) {
	        $(this).css("margin-left", "-"+($(this).width()/2)+"px"  );
	    });
	});
})(jQuery);