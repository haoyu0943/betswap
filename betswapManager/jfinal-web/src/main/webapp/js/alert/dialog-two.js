//alert
var yyAlert = function(options){
	var opts = $.extend({},$.yyDialog.method,$.yyDialog.defaults,options);
	opts.init(opts);
};

//confirm
var yyConfirm = function(options){
	var opts = $.extend({},$.yyDialog.method,$.yyDialog.defaults,options);
	opts.init(opts);
}

$.yyDialog = {};
$.yyDialog.method = {
	init:function(opts){
		$(".yy-alert,#alert-mark").remove();
		var $dialog = this.template(opts);
		this.params($dialog,opts);
		this.event($dialog,opts);
	},
	params : function($dialog,opts){
		if(opts.width)$dialog.find(".yy-alert").width(opts.width);
		if(opts.height){
			if(opts.height < 180)opts.height=180;
			$dialog.find(".yy-alert").height(opts.height);
		}
		$dialog.find(".ac-con").css({"min-height":"20px"});
		$dialog.Drag({handler:".ah-title"});
	},
	template : function(opts){
		var $dialog;
		//报错
		if(opts.style=="e")
		        $dialog = $("<div class='yy-alert'>"+
			  	"	<div class='a-header'>"+
			  	"		<span class='ah-title'>"+opts.title+"</span>"+
			  	"       <span class='ah-close'>×</span>"+
			  	"	</div>"+
			  	"	<div class='a-content'>"+
			  	"		<div class='ac-con'>"+
			  	"			<span class='ac-message'>"+opts.content+"</span>"+
			  	"		</div>"+
			  	"		<div class='ac-btns'>"+
			  	"			<div class='ab-box'>"+
			  	"				<span class='b-sure anniugred'>"+opts.sureText+"</span>"+
			  	"			</div>"+
			  	"		</div>"+
			  	"	</div>"+
			  	"</div>");
			//选择
		 if(opts.style=="q")
		 $dialog = $("<div class='yy-alert'>"+
				  	"	<div class='a-header bggreen'>"+
				  	"		<span class='ah-title'>"+opts.title+"</span>"+
				  	"       <span class='ah-close'>×</span>"+
				  	"	</div>"+
				  	"	<div class='a-content'>"+
				  	"		<div class='ac-con'>"+
				  	"			<span class='ac-message'>"+opts.content+"</span>"+
				  	"		</div>"+
				  	"		<div class='ac-btns'>"+
				  	"			<div class='ab-box'>"+
				  	"				<span class='b-sure'>"+opts.sureText+"</span>"+
					"  				<span class='b-cancel'>"+opts.cancelText+"</span>"+
    			  	"			</div>"+
				  	"		</div>"+
				  	"	</div>"+
				  	"</div>");
			//提示
		 if(opts.style=="i")
		 $dialog = $("<div class='yy-alert'>"+
					  	"	<div class='a-header bacgblue'>"+
					  	"		<span class='ah-title'>"+opts.title+"</span>"+
					  	"       <span class='ah-close'>×</span>"+
					  	"	</div>"+
					  	"	<div class='a-content'>"+
					  	"		<div class='ac-con'>"+
					  	"			<span class='ac-message'>"+opts.content+"</span>"+
					  	"		</div>"+
					  	"		<div class='ac-btns'>"+
					  	"			<div class='ab-box'>"+
					  	"				<span class='b-sure anniublue' >"+opts.sureText+"</span>"+
						"  				<div class='clear'></div>"+
					  	"			</div>"+
					  	"		</div>"+
					  	"	</div>"+
					  	"</div>");
		 if(opts.style=="s")
			 $dialog = $("<div class='yy-alert'>"+
						  	"	<div class='a-header bggreen'>"+
						  	"		<span class='ah-title'>"+opts.title+"</span>"+
						  	"       <span class='ah-close'>×</span>"+
						  	"	</div>"+
						  	"	<div class='a-content'>"+
						  	"		<div class='ac-con'>"+
						  	"			<span class='ac-message'>"+opts.content+"</span>"+
						  	"		</div>"+
						  	"		<div class='ac-btns'>"+
						  	"			<div class='ab-box'>"+
						  	"				<span class='b-sure' >"+opts.sureText+"</span>"+
							"  				<div class='clear'></div>"+
						  	"			</div>"+
						  	"		</div>"+
						  	"	</div>"+
						  	"</div>");
		 
		if(opts.flag == "0"){
			$dialog.find(".b-cancel").remove();
		}
		
		if(!opts.isClose){
			$dialog.find(".a-header .ah-close").remove();
		}
		
		$("body").append($dialog);//left: 38%;top: 20%;
		if(opts.mark){
			$("body").append("<div id='alert-mark'></div>");
		}
		//获取滚动条的高度
		var stop = document.body.scrollTop || document.documentElement.srcollTop;
		//获取可视区域的高度
		var cheight = window.innerHeight || document.documentElement.clientHeight;
		var dh = stop + cheight + 2;
		$("#alert-mark").height(dh);
		//动画
		$dialog.animate({
			left:"38%",
			top:"25%"
		},300);
		return $dialog;
	},
	event : function($dialog,opts){
		//确定按钮
		$dialog.find(".ab-box .b-sure").on("click",function(){
			if(opts.callback)opts.callback(true);
			$dialog.remove();
			$("#alert-mark").remove();
		});
		//取消按钮
		if($dialog.find(".ab-box .b-cancel")){
			$dialog.find(".ab-box .b-cancel").on("click",function(){
				if(opts.callback)opts.callback(false);
				$dialog.remove();
				$("#alert-mark").remove();
			});
		}
		//关闭按钮
		$dialog.find(".a-header .ah-close").on("click",function(){
			$dialog.remove();
			$("#alert-mark").remove();
		});
		//定时关闭
		if($dialog.find(".ab-box .b-cancel").length <= 0 || !$dialog.find(".ab-box .b-cancel").is(":visible")){
			var closeAlertTimer = null;
			var count = 3;
			$dialog.find(".ab-box .b-sure").html(opts.sureText+"("+count+"s)");
			closeAlertTimer = setInterval(function(){
				$dialog.find(".ab-box .b-sure").html(opts.sureText+"("+count+"s)");
				if(count <= 0){
					clearInterval(closeAlertTimer);
					$dialog.find(".ab-box .b-sure").click();
				}
				count--;
			},1000);
		}
	}
};

//弹出层的默认参数
$.yyDialog.defaults = {
	width:272,
	height:186,
	flag:"0",//0=alert,1=confirm
	mark:true,//阴影
	title:"提示",
	style:"i",
	icon:"icon-waring",//icon-waring--icon-fail
	cancelText:"取消",
	sureText:"确定",
	content:"请输入内容...",
	isClose:true,
	callback:function(ok){
	}
};


(function($){
	var tzDrag = {};
	$.fn.Drag = function(options){
		var opts = $.extend({},tzDrag.defaults,options);
		var $dialog = $(this);
		var $dialogClone  = null;
		var mark = false;
		$dialog.on("mousedown",opts.handler,function(e){
			var $this = $(this).parent();
			if(!opts.handler){
				$this=$(this);
			}
			$dialogClone = $dialog.clone();
			$("body").append($dialogClone);
			var x = getXY(e).x;
			var y = getXY(e).y;
			var left = $dialog.offset().left;
			var top = $dialog.offset().top;
			var w = $dialog.width();
			var h = $dialog.height();
			var offsetHeight = getClientHeight() - h-2;
			var offsetWidth= getClientWidth() - w-2;
			var stop= $(window).scrollTop();
			var jsonData = {};
			jsonData.width=w;
			jsonData.height=h;
			mark = true;
			$(document).on("mousemove",function(e){
				if(mark){
					var nx = getXY(e).x;
					var ny = getXY(e).y;
					var nl = nx + left - x;
					var nt = ny + top - y - stop;
					if(nl<=0)nl=1;
					if(nt<=0)nt=1;
					if(nl>=offsetWidth)nl = offsetWidth;
					if(nt>=offsetHeight)nt = offsetHeight;
					$dialogClone.css({left:nl,top:nt,opacity:0.4,filter:"alpha(opacity=40)"});
					jsonData.left=nl;
					jsonData.top=nt;
				}
			}).on("mouseup",function(){
				$dialogClone.remove();
				$dialog.css({left:jsonData.left,top:jsonData.top});
				mark = false;
			});
	});
	};

	tzDrag.defaults = {
	handler:".ah-title",//拖动代理
	ghost:true//是否产生镜像
   };
})(jQuery)