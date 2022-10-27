/**
 * 判断非空
 * 
 * @param val
 * @returns {Boolean}
 */
function isEmpty(val) {
	val = $.trim(val);
	if (val == null || val == "null")
		return true;
	if (val == undefined || val == 'undefined')
		return true;
	if (val == "")
		return true;
	if (val.length == 0)
		return true;
	if (!/[^(^\s*)|(\s*$)]/.test(val))
		return true;
	return false;
}

function isNotEmpty(val) {
	return !isEmpty(val);
}


/**
 * 阻止事件冒泡
 * 
 * @param e
 */
function stopBubble(e) {
	// 如果提供了事件对象，则这是一个非IE浏览器
	if (e && e.stopPropagation)
		// 因此它支持W3C的stopPropagation()方法
		e.stopPropagation();
	else
		// 否则，我们需要使用IE的方式来取消事件冒泡
		window.event.cancelBubble = true;
};

/**
 * 将各种形式的json转成字符串
 */
function jsonToString(obj) {
	var THIS = this;
	switch (typeof (obj)) {
	case 'string':
		return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';
	case 'array':
		return '[' + obj.map(THIS.jsonToString).join(',') + ']';
	case 'object':
		if (obj instanceof Array) {
			var strArr = [];
			var len = obj.length;
			for (var i = 0; i < len; i++) {
				strArr.push(THIS.jsonToString(obj[i]));
			}
			return '[' + strArr.join(',') + ']';
		} else if (obj == null) {
			return 'null';

		} else {
			var string = [];
			for ( var property in obj)
				string.push(THIS.jsonToString(property) + ':'
						+ THIS.jsonToString(obj[property]));
			return '{' + string.join(',') + '}';
		}
	case 'number':
		return obj;
	case false:
		return obj;
	}
}



//判断两个元素是否相等
function eqauls(str,tstr){
	if(str == tstr){
		return true;
	}
	return false;
};

/** ******************************数组相关结束*********************************** */
/**
 * 禁止窗体选中
 */
function forbiddenSelect() {
	$(document).bind("selectstart", function() {
		return false;
	});
	document.onselectstart = new Function("event.returnValue=false;");
	$("*").css({
		"-moz-user-select" : "none"
	});
}

/* 窗体允许选中 */
function autoSelect() {
	$(document).bind("selectstart", function() {
		return true;
	});
	document.onselectstart = new Function("event.returnValue=true;");
	$("*").css({
		"-moz-user-select" : ""
	});
};


/**
 * 对Date的扩展，将 Date 转化为指定格式的String 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
 * 可以用 1-2 个占位符 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) eg: (new
 * Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 (new
 * Date()).format("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04 (new
 * Date()).format("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04 (new
 * Date()).format("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04 (new
 * Date()).format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.format = function(fmt) {
	var o = {
		"Y+" : this.getFullYear(),
		"M+" : this.getMonth() + 1,
		// 月份
		"d+" : this.getDate(),
		// 日
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12,
		// 小时
		"H+" : this.getHours(),
		// 小时
		"m+" : this.getMinutes(),
		// 分
		"s+" : this.getSeconds(),
		// 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		// 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	var week = {
		"0" : "/u65e5",
		"1" : "/u4e00",
		"2" : "/u4e8c",
		"3" : "/u4e09",
		"4" : "/u56db",
		"5" : "/u4e94",
		"6" : "/u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt
				.replace(
						RegExp.$1,
						((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f"
								: "/u5468")
								: "")
								+ week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};

/**
 * 将数字转换成对应的中文 将阿拉伯数字翻译成中文的大写数字
 * 
 * @param {Object}
 *            num 比如:1对应一 11：十一 101:一百零一
 * @return {TypeName}
 */
function tm_NumberToChinese(num) {
    var AA = new Array("零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十");
    var BB = new Array("", "十", "百", "仟", "萬", "億", "点", "");
    var a = ("" + num).replace(/(^0*)/g, "").split("."),
    k = 0,
    re = "";
    for (var i = a[0].length - 1; i >= 0; i--) {
        switch (k) {
        case 0:
            re = BB[7] + re;
            break;
        case 4:
            if (!new RegExp("0{4}//d{" + (a[0].length - i - 1) + "}$").test(a[0])) re = BB[4] + re;
            break;
        case 8:
            re = BB[5] + re;
            BB[7] = BB[5];
            k = 0;
            break;
        }
        if (k % 4 == 2 && a[0].charAt(i + 2) != 0 && a[0].charAt(i + 1) == 0) re = AA[0] + re;
        if (a[0].charAt(i) != 0) re = AA[a[0].charAt(i)] + BB[k % 4] + re;
        k++;
    }

    if (a.length > 1) // 加上小数部分(如果有小数部分)
    {
        re += BB[6];
        for (var i = 0; i < a[1].length; i++) re += AA[a[1].charAt(i)];
    }
    if (re == '一十') re = "十";
    if (re.match(/^一/) && re.length == 3) re = re.replace("一", "");
    return re;
};



/**
 * 获取窗体可见度高度
 * 
 * @returns
 */
function getClientHeight() {
	var clientHeight = 0;
	if (document.body.clientHeight && document.documentElement.clientHeight) {
		clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight
				: document.documentElement.clientHeight;
	} else {
		clientHeight = (document.body.clientHeight > document.documentElement.clientHeight) ? document.body.clientHeight
				: document.documentElement.clientHeight;
	}
	return clientHeight;
}


/**
 * 获取窗体可见度宽度
 * 
 * @returns
 */
function getClientWidth() {
	var clientWidth = 0;
	if (document.body.clientWidth && document.documentElement.clientWidth) {
		clientWidth = (document.body.clientWidth < document.documentElement.clientWidth) ? document.body.clientWidth
				: document.documentElement.clientWidth;
	} else {
		clientWidth = (document.body.clientWidth > document.documentElement.clientWidth) ? document.body.clientWidth
				: document.documentElement.clientWidth;
	}
	return clientWidth;
}

//获取滚动条的高度
function getScrollHeight() {
	return Math.max(getClientHeight(), document.body.scrollHeight,
			document.documentElement.scrollHeight);
}

//滚动条距离顶部的距离
function getScrollTop() {
	var scrollTop = 0;
	if (document.documentElement && document.documentElement.scrollTop) {
		scrollTop = document.documentElement.scrollTop;
	} else if (document.body) {
		scrollTop = document.body.scrollTop;
	}
	return scrollTop;
}

/* 文件大小转换为MB GB KB格式 */
function tm_countFileSize(size) {
	var fsize = parseFloat(size, 2);
	var fileSizeString;
	if (fsize < 1024) {
		fileSizeString = fsize.toFixed(2) + "B";
	} else if (fsize < 1048576) {
		fileSizeString = (fsize / 1024).toFixed(2) + "KB";
	} else if (fsize < 1073741824) {
		fileSizeString = (fsize / 1024 / 1024).toFixed(2) + "MB";
	} else if (fsize < 1024 * 1024 * 1024) {
		fileSizeString = (fsize / 1024 / 1024 / 1024).toFixed(2) + "GB";
	} else {
		fileSizeString = "0B";
	}
	return fileSizeString;
};

/* 获取文件后缀 */
function tm_getExt(fileName) {
	if (fileName.lastIndexOf(".") == -1)
		return fileName;
	var pos = fileName.lastIndexOf(".") + 1;
	return fileName.substring(pos, fileName.length).toLowerCase();
}

/* 获取文件名称 */
function tm_getFileName(fileName) {
	var pos = fileName.lastIndexOf("/") + 1;
	if (pos == -1) {
		return fileName;
	} else {
		return fileName.substring(pos, fileName.length);
	}
}


/**
 * 禁止窗体选中
 */
function tm_forbiddenSelect() {
	$(document).bind("selectstart", function() {
		return false;
	});
	document.onselectstart = new Function("event.returnValue=false;");
	$("*").css({
		"-moz-user-select" : "none"
	});
}


/* 窗体允许选中 */
function tm_autoSelect() {
	$(document).bind("selectstart", function() {
		return true;
	});
	document.onselectstart = new Function("event.returnValue=true;");
	$("*").css({
		"-moz-user-select" : ""
	});
}

//获取几秒钟以前 startTime==== Date
function getTimeFormat(startTime) {
	var startTimeMills = startTime.getTime();
	var endTimeMills = new Date().getTime();
	var diff = parseInt((endTimeMills - startTimeMills) / 1000);//秒
	var day_diff = parseInt(Math.floor(diff / 86400));//天
	var buffer = Array();
	if (day_diff < 0) {
		return "[error],时间越界...";
	} else {
		if (day_diff == 0 && diff < 60) {
			if (diff <= 0)
				diff = 1;
			buffer.push(diff + "秒前");
		} else if (day_diff == 0 && diff < 120) {
			buffer.push("1 分钟前");
		} else if (day_diff == 0 && diff < 3600) {
			buffer.push(Math.round(Math.floor(diff / 60)) + "分钟前");
		} else if (day_diff == 0 && diff < 7200) {
			buffer.push("1小时前");
		} else if (day_diff == 0 && diff < 86400) {
			buffer.push(Math.round(Math.floor(diff / 3600)) + "小时前");
		} else if (day_diff == 1) {
			buffer.push("1天前");
		} else if (day_diff < 7) {
			buffer.push(day_diff + "天前");
		} else if (day_diff < 30) {
			buffer.push(Math.round(Math.floor(day_diff / 7)) + " 星期前");
		} else if (day_diff >= 30 && day_diff <= 179) {
			buffer.push(Math.round(Math.floor(day_diff / 30)) + "月前");
		} else if (day_diff >= 180 && day_diff < 365) {
			buffer.push("半年前");
		} else if (day_diff >= 365) {
			buffer.push(Math.round(Math.floor(day_diff / 30 / 12)) + "年前");
		}
	}
	return buffer.toString();
};

//获取时间区间
function getTimeRangeFormat(startTime) {
	var startTimeMills = startTime.getTime();
	var diff = parseInt(startTimeMills / 1000);//秒
	var day_diff = parseInt(Math.floor(diff / 86400));//天
	var buffer = Array();
	if (day_diff < 0) {
		return "";
	} else {
		if (day_diff == 0 && diff < 60) {
			if (diff <= 0)
				diff = 1;
			buffer.push(diff + "秒");
		} else if (day_diff == 0 && diff < 120) {
			buffer.push("1 分钟");
		} else if (day_diff == 0 && diff < 3600) {
			buffer.push(Math.round(Math.floor(diff / 60)) + "分钟");
		} else if (day_diff == 0 && diff < 7200) {
			buffer.push("1小时");
		} else if (day_diff == 0 && diff < 86400) {
			buffer.push(Math.round(Math.floor(diff / 3600)) + "小时");
		} else if (day_diff == 1) {
			buffer.push("1天");
		} else if (day_diff < 7) {
			buffer.push(day_diff + "天");
		} else if (day_diff < 30) {
			buffer.push(Math.round(Math.floor(day_diff / 7)) + " 星期");
		} else if (day_diff >= 30 && day_diff <= 179) {
			buffer.push(Math.round(Math.floor(day_diff / 30)) + "月");
		} else if (day_diff >= 180 && day_diff < 365) {
			buffer.push("半年");
		} else if (day_diff >= 365) {
			buffer.push(Math.round(Math.floor(day_diff / 30 / 12)) + "年");
		}
	}
	return buffer.toString();
};

/*去掉字符串的前后空格*/
/*String.prototype.trim = function(){
	return  this.replace(/(^\s*)|(\s*$)/g,"");  
};*/

//扩展endsWith
String.prototype.endsWith = function(suffix) {
	return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

String.prototype.isEmpty = function(){
	var val = this;
	val = $.trim(val);
	if (val== null)
		return true;
	if (val == undefined || val == 'undefined')
		return true;
	if (val == "")
		return true;
	if (val.length == 0)
		return true;
	if (!/[^(^\s*)|(\s*$)]/.test(val))
		return true;
	return false;
};

String.prototype.isNotEmpty = function(){
	return !this.isEmpty();
};

String.prototype.replaceAll = function(str,target){
	return this.replace(new RegExp(str,"ig"),target);
};

String.prototype.subTarStr = function(s,e){
	   var thisLen = this.length;
	   if(thisLen > e){
		   return this.substring(s, e)+"...";
	   }
	   return this;
}

var tzUtil = {
	_position : function($dom,amark){//居中定位
		var windowWidth = $(window).width();
		var windowHeight= $(window).height();
		var width = $dom.width();
		var height = $dom.height();
		var left = (windowWidth - width)/2;
		var top = (windowHeight - height)/2;
		if(!amark)$dom.css("top",top).animate({left:left});
		if(amark==0)$dom.animate({left:left,"top":top});
		if(amark==1)$dom.css("left",left).animate({"top":top});
		if(amark==2)$dom.css({left:left,"top":top});
		return this;
	},

	_positionParent : function($dom,$parent){//居中定位--大盒子套小盒子$dom是小盒子$parent是大盒子
		var parentWidth = $parent.width();
		var parentHeight= $parent.height();
		var width = $dom.width();
		var height = $dom.height();
		var left = (parentWidth - width)/2;
		var top = (parentHeight - height)/2;
		$dom.css({left:left,top:top});
		return this;
	},

	resize : function($dom){
		var $this = this;
		$(window).resize(function(){
			$this._position($dom);
		});
	},
	animates:function($dom,mark){
		switch(mark){
			case "fadeOut":$dom.toggleClass(tz_animateOut()).fadeOut("slow",function(){$(this).remove();});break;
			case "slideUp":$dom.toggleClass(tz_animateOut()).slideUp("slow",function(){$(this).remove();});break;
			case "fadeIn":$dom.toggleClass(tz_animateOut()).fadeIn("slow");break;
			case "slideDown":$dom.toggleClass(tz_animateOut()).slideDown("slow");break;
			case "left":$dom.toggleClass(tz_animateOut()).animate({left:0},300,function(){$(this).remove();});break;
			case "top":$dom.toggleClass(tz_animateOut()).animate({top:0},300,function(){$(this).remove();});break;
		}
	},

	isEmpty:function(str){
		val = $.trim(val);
		if (val == null)
			return true;
		if (val == undefined || val == 'undefined')
			return true;
		if (val == "")
			return true;
		if (val.length == 0)
			return true;
		if (!/[^(^\s*)|(\s*$)]/.test(val))
			return true;
		return false;
	},

	isNotEmpty:function(str){
		return !this.isEmpty(str);
	},
	//颜色随机
	getRandomColor : function(){
	  return '#'+Math.floor(Math.random()*16777215).toString(16);
	},	
	
	forbiddenSelect : function(){
		$(document).bind("selectstart", function() {
			return false;
		});
		document.onselectstart = new Function("event.returnValue=false;");
		$("*").css({
			"-moz-user-select" : "none"
		});
	},
	
	autoSelect : function() {
		$(document).bind("selectstart", function() {
			return true;
		});
		document.onselectstart = new Function("event.returnValue=true;");
		$("*").css({
			"-moz-user-select" : ""
		});
	}
};

/* 判断一个元素是否包含在数组中。 */
Array.prototype.contains = function(obj) {
	var i = this.length;
	while (i--) {
		if (this[i] === obj) {
			return true;
		}
	}
	return false;
};

/**
 * 判断一个对象是否是数组
 */
var isArray = function(obj) { 
	return Object.prototype.toString.call(obj) === '[object Array]'; 
}; 

/**
 * 判断一个对象是否是数组
 */
var is_array = function(value) { 
	return value && 
	typeof value === 'object' && 
	typeof value.length === 'number' && 
	typeof value.splice === 'function' && 
	!(value.propertyIsEnumerable('length')); 
}; 

/*日期工具类*/
$.tmDate = {
 /*转换日期*/
 _transferDate : function(date){
	if(typeof date =="string"){
		return new Date(date.replace(/-/ig,"/"));
	}else{
		return date;
	}
 },
  /*格式化日期*/
 _toString : function(date,pattern){
	var d = this._transferDate(date);
	if(d != null && d != undefined && d != 'undefined')
		return d.format(pattern);
	return "";
 },

 /*获取两个时间相减的时间*/
 _Date : function(date1,date2){
	var dateTime = this._numMillSecond(date1,date2);
	return new Date(dateTime).format("yyyy-MM-dd");
 },
 //获取两个时间相减的年份
 _Datsyear : function(date1,date2){
	 var dateTime = this._numYear(date1,date2);
	 return  dateTime;
 },
//获取两个时间相减的月份
 _Datsmonth : function(date1,date2){
	 var dateTime = this._numMonth(date2,date1);
	 return  dateTime;
 },

 //间隔年份
 _numYear : function(date1,date2){
	var times = this._numDay(date1,date2);
	return  Math.floor(times/365);
 },

  //间隔月份
 _numMonth : function(date1,date2){
	var times = this._numDay(date1,date2);
	return  Math.floor(times/30);
 },

 //间隔天数
 _numDay : function(date1,date2){
	var times = this._numSecond(date1,date2);
	var hour = this._var().hour;
	var mills = this._var().mills;
	return Math.ceil(times/(mills * hour));
 },

//间隔时
 _numHour : function(date1,date2){
	return Math.floor(this._numMillSecond(date1,date2)/(1000*60*60));
 },

 //间隔分
 _numMinute : function(date1,date2){
	return Math.floor(this._numMillSecond(date1,date2)/(1000*60));
 },

 //间隔秒数
 _numSecond : function(date1,date2){
	 return Math.floor(this._numMillSecond(date1,date2) / 1000);
 },

  //间隔毫秒
 _numMillSecond : function(date1,date2){
	var stimes = this._getTime(this._transferDate(date1));
	var etimes = this._getTime(this._transferDate(date2));
	return etimes - stimes;
 },

 _var : function(){
	return {hour:24,second:60,mills:3600,format:"yyyy-MM-dd",dateFormat:"yyyy-MM-dd HH:mm:ss"};
 },

/*某个日期加上多少毫秒*/
 _plusMillisSeconds : function(date,millisSeconds){
	var dateTime = this._getTime(date);
	var mintimes = millisSeconds;
	var rdate = dateTime*1 + mintimes*1;
	return this._format(new Date(rdate));
 },
 /*某个日期加上多少秒*/
 _plusSeconds : function(date,seconds){
	var dateTime = this._getTime(date);
	var mintimes = seconds*1000;
	var rdate = dateTime*1 + mintimes*1;
	return this._format(new Date(rdate));
 },
  /*某个日期加上多少分钟*/
 _plusMinutes : function(date,minutes){
	var dateTime = this._getTime(date);
	var mintimes = minutes*60*1000;
	var rdate = dateTime*1 + mintimes*1;
	return this._format(new Date(rdate));
 },
  /*某个日期加上小时数*/
 _plusHours : function(date,hours){
	var dateTime = this._getTime(date);
	var mintimes = hours*60*60*1000;
	var rdate = dateTime + mintimes;
	return this._format(new Date(rdate));
 },
 /*某个日期加上天数*/
 _plusDays : function(date,days){
	var dateTime = this._getTime(date);
	var mintimes = days*60*60*1000*24;
	var rdate = dateTime*1 + mintimes*1;
	return this._format(new Date(rdate));
 },

 /*某个日期加上多少个月,这里是按照一个月30天来计算天数的*/
 _plusMonths : function(date,months){
	var dateTime = this._getTime(date);
	var mintimes = months*30*60*60*1000*24;
	var rdate = dateTime + mintimes*1;
	return this._format(new Date(rdate));
 },

 /*某个日期加上多少个年,这里是按照一个月365天来计算天数的，如果loop为true则按闰年计算*/
 _plusYears : function(date,years,isLoop){
	var dateTime = this._getTime(date);
	var day = 365;
	if(isLoop)day =366;
	var mintimes = years*day*60*60*1000*24;
	var rdate = dateTime + mintimes;
	return this._format(new Date(rdate));
 },

 /*某个日期加上某个日期，这样的操作视乎没什么意义*/
 _plusDate : function(date1,date2){
	var dateTime = this._getTime(date1);
	var dateTime2 = this._getTime(date2);;
	var rdate = dateTime + dateTime2;
	return this._format(new Date(rdate));
 },

 /*某个日期减去多少毫秒秒*/
 _minusMillisSeconds : function(date,millisSeconds){
	var dateTime = this._getTime(date);
	var mintimes = millisSeconds*1;
	var rdate = dateTime - mintimes;
	return this._format(new Date(rdate));
 },
 /*某个日期减去多少秒*/
 _minusSeconds : function(date,seconds){
	var dateTime = this._getTime(date);
	var mintimes = seconds*1000;
	var rdate = dateTime - mintimes;
	return this._format(new Date(rdate));
 },
  /*某个日期减去多少分钟*/
 _minusMinutes : function(date,minutes){
	var dateTime = this._getTime(date);
	var mintimes = minutes*60*1000;
	var rdate = dateTime - mintimes;
	return this._format(new Date(rdate));
 },
  /*某个日期减去小时数*/
 _minusHours : function(date,hours){
	var dateTime = this._getTime(date);
	var mintimes = hours*60*60*1000;
	var rdate = dateTime - mintimes;
	return this._format(new Date(rdate));
 },
 /*某个日期减去天数*/
 _minusDays : function(date,days){
	var dateTime = this._getTime(date);
	var mintimes = days*60*60*1000*24;
	var rdate = dateTime - mintimes;
	return this._format(new Date(rdate));
 },

 /*某个日期减去多少个月,这里是按照一个月30天来计算天数的*/
 _minusMonths : function(date,months){
	var dateTime = this._getTime(date);
	var mintimes = months*30*60*60*1000*24;
	var rdate = dateTime - mintimes;
	return this._format(new Date(rdate));
 },

 /*某个日期减去多少个年,这里是按照一个月365天来计算天数的*/
 _minusYears : function(date,years,isLoop){
	var dateTime = this._getTime(date);
	var day = 365;
	if(isLoop)day =366;
	var mintimes = years*day*60*60*1000*24;
	var rdate = dateTime - mintimes;
	return this._format(new Date(rdate));
 },

 /*某个日期减去某个日期，这样的操作视乎没什么意义*/
 _minusDate : function(date1,date2){
	var dateTime = this._getTime(date1);
	var dateTime2 = this._getTime(date2);;
	var rdate = dateTime - dateTime2;
	return this._format(new Date(rdate));
 },

 /*获取一个月有多少天*/
 _getMonthOfDay :function(date1){
	var currentMonth = this._getFirstDayOfMonth(date1);
	var nextMonth = this._getNextDayOfMonth(date1);
	return this._numDay(currentMonth,nextMonth);
 },

 /*获取一年又多少天*/
 _getYearOfDay : function(date){
	var firstDayYear = this._getFirstDayOfYear(date);
	var lastDayYear = this._getLastDayOfYear(date);
	return Math.ceil(this._numDay(firstDayYear,lastDayYear));
 },

 /*某个日期是当年中的第几天*/
 _getDayOfYear : function(date1){
	return Math.ceil(this._numDay(this._getFirstDayOfYear(date1),date1));	
 },

 /*某个日期是在当月中的第几天*/
  _getDayOfMonth : function(date1){
	return Math.ceil(this._numDay(this._getFirstDayOfMonth(date1),date1));	
 },

 /*获取某个日期在这一年的第几周*/
 _getDayOfYearWeek : function(date){
	var numdays = this._getDayOfYear(date);
	return Math.ceil(numdays / 7);
 },
 
 _getDayOfMonthWeek : function(year,month,day){
	 var d = new Date();
     // what day is first day
     d.setFullYear(year, month-1, 1);
     var w1 = d.getDay();
     if (w1 == 0) w1 = 7;
     // total day of month
     if(isNotEmpty(day)){
    	 d.setFullYear(year, month, day);
     }else{
    	 d.setFullYear(year, month, 0);
     }
     var dd = d.getDate();
     // first Monday
     if (w1 != 1) d1 = 7 - w1 + 2;
     else d1 = 1;
     week_count = Math.ceil((dd-d1+1)/7);
     var array = [];
     for (var i = 0; i < week_count; i++) {
         var monday = d1+i*7;
         var sunday = monday + 6;
         var from = year+"/"+month+"/"+monday;
         var to;
         if (sunday <= dd) {
             to = year+"/"+month+"/"+sunday;
         } else {
             d.setFullYear(year, month-1, sunday);
             to = d.getFullYear()+"/"+(d.getMonth()+1)+"/"+d.getDate();
         }
         array.push(from + " 到 " + to);
     }
     return array;
 },

  /*某个日期是在当月中的星期几*/
  _getDayOfWeek : function(date1){
	return this._getWeek(date1);
 },

 /*获取在当前日期中的时间*/
 _getHourOfDay : function(date){
	 return this._getHour(date);
 },
 _eq : function(date1,date2){
	 var stime = this._getTime(this._transferDate(date1));
	 var etime = this._getTime(this._transferDate(date2));
	 return stime == etime ? true :false; 
 },
 /*某个日期是否晚于某个日期*/
 _after : function(date1,date2){
	 var stime = this._getTime(this._transferDate(date1));
	 var etime = this._getTime(this._transferDate(date2));
	 return  stime < etime ? true :false; 
 },

  /*某个日期是否早于某个日期*/
 _before : function(date1,date2){
	 var stime = this._getTime(this._transferDate(date1));
	 var etime = this._getTime(this._transferDate(date2));
	 return  stime > etime ? true :false; 
 },
 
 /*获取某年的第一天*/
 _getFirstDayOfYear : function(date){
	var year = this._getYear(date);
	var dateString = year+"-01-01 00:00:00";
	return dateString;
 },

  /*获取某年的最后一天*/
 _getLastDayOfYear : function(date){
	var year = this._getYear(date);
	var dateString = year+"-12-01 00:00:00";
	var endDay = this._getMonthOfDay(dateString);
	return year+"-12-"+endDay+" 23:59:59";
 },

  /*获取某月的第一天*/
 _getFirstDayOfMonth: function(date){
	var year = this._getYear(date);
	var month = this._getMonth(date);
	var dateString = year +"-"+month+"-01 00:00:00";
	return dateString;
 },

 /*获取某月最后一天*/
 _getLastDayOfMonth : function(date){
	var endDay = this._getMonthOfDay(date);
	var year = this._getYear(date);
	var month = this._getMonth(date);
	return year +"-"+month+"-"+endDay+" 23:59:59";
 },
 /*一天的开始时间*/
 _getFirstOfDay : function(date){
	 var year = this._getYear(date);
	 var month = this._getMonth(date);
	 var date = this._getDay(date);
	 return year+"-"+month+"-"+date+" 00:00:00";
 },

 /*一天的结束时间*/
 _getLastOfDay : function(date){
	 var year = this._getYear(date);
	 var month = this._getMonth(date);
	 var date = this._getDay(date);
	 return year+"-"+month+"-"+date+" 23:59:59";
 },
 
 /*获取下个月的第一天*/
 _getNextDayOfMonth: function(date){
	var year = this._getYear(date);
	var month = this._getMonth(date);
	month = month * 1 +1;
	if(month>12){
		year = year+1;
		month = month - 12;
	}
	month = month>9 ? month : "0"+month;
	var dateString = year +"-"+month+"-01 00:00:00";
	return dateString;
 },

 _getFirstOfWeek : function(date1){
	 var week = this._getWeek(date1);
	 var date = this._minusDays(date1,week);
	 var year = this._getYear(date);
	 var month = this._getMonth(date);
	 var date = this._getDay(date);
	 return year+"-"+month+"-"+date+" 00:00:00";
 },
 
 _getLastOfWeek : function(date1){
	 var week = 6-this._getWeek(date1);
	 var date = this._minusDays(date1,week);
	 var year = this._getYear(date);
	 var month = this._getMonth(date);
	 var date = this._getDay(date);
	 return year+"-"+month+"-"+date+" 23:59:59";
 },
 
 _getNow : function(){
	return new Date();	
 },
 _format : function(date){
	return this._getYear(date)+"-"+this._getMonth(date)+"-"+this._getDay(date)+" "+this._getHour(date)+":"+this._getMinute(date)+":"+this._getSecond(date);
 },
 _getDate :function(){
	 return this._getNow();
 },
 /*年*/
 _getYear:function(date){
	 return this._transferDate(date).getFullYear();
 },

 /*月*/
 _getMonth:function(date){
	 var month = this._transferDate(date).getMonth()+1;
	 return month>9 ? month : "0"+month;
 },

 /*日*/
 _getDay:function(date){
	 var day = this._transferDate(date).getDate();
	 return day >9 ? day : "0"+day;
 },

  /*获取今天星期几,如果为0代表星期日*/
 _getWeek : function(date){
	 return this._transferDate(date).getDay();
 },

 /*时*/
 _getHour : function(date){
	 var hour = this._transferDate(date).getHours();
	 return hour >9 ? hour : "0"+hour;
 },

 /*12小时制时*/
 _getHour12 : function(date){
	 var hour = this._transferDate(date).getHours();
	 return hour%12 == 0 ? 12 : hour % 12
 },

 /*分*/
 _getMinute : function(date){
	 var minutes = this._transferDate(date).getMinutes();
	 return minutes >9 ? minutes : "0"+minutes;
 },

 /*秒*/
 _getSecond : function(date){
	var seconds = this._transferDate(date).getSeconds();
	return seconds >9 ? seconds : "0"+seconds;
 },

 /*毫秒*/
 _getMillisecond : function(date){
	return this._transferDate(date).getMilliseconds();
 },

 /*获取今天在当年是第几季度*/
 _getPeriod : function(date){
	var month = this._getMonth(date)*1;
	return  Math.floor((month+3)/3)
 },

 /*星期*/
 _nowWeekChinies : function(date){
	var nowWeek = this._getWeek(date);
	var day = "";
	switch (nowWeek){
		case 0:day="日";break;
		  break;
		case 1:day="一";break;
		  break;
		case 2:day="二";break;
		  break;
		case 3:day="三";break;
		  break;
		case 4:day="四";break;
		  break;
		case 5:day="五";break;
		  break;
		case 6:day="六";break;
	 }
	 return day;
 },
 _getMessage : function(date){
	 var now = date||new Date();
	 var hour = now.getHours() ;
	 if(hour < 6){return "凌晨好！";} 
	 else if (hour < 9){return "早上好！";} 
	 else if (hour < 12){return "上午好！";} 
	 else if (hour < 14){return "中午好！";} 
	 else if (hour < 17){return "下午好！";} 
	 else if (hour < 19){return "傍晚好！";} 
	 else if (hour < 22){return "晚上好！";} 
	 else {return "夜里好！";} 
 },
 /*返回 1970 年 1 月 1 日至今的毫秒数。*/
 _getTime : function(date){
	 return this._transferDate(date).getTime();
 }
};
/*date end*/
/*cookie*/
$.tmCookie = {
	setCookie : function(name, value,time,option){
	    var str=name+'='+escape(value); 
	    var date = new Date();
	    date.setTime(date.getTime()+this.getCookieTime(time)); 
	    str += "; expires=" + date.toGMTString();
	    if(option){ 
	        if(option.path) str+='; path='+option.path; 
	        if(option.domain) str+='; domain='+option.domain; 
	        if(option.secure) str+='; true'; 
	    } 
	    document.cookie=str; 
	},
	getCookie : function(name){
		var arr = document.cookie.split('; '); 
	    if(arr.length==0) return ''; 
	    for(var i=0; i <arr.length; i++){ 
	        tmp = arr[i].split('='); 
	        if(tmp[0]==name) return unescape(tmp[1]); 
	    } 
	    return ''; 
	},
	delCookie : function(name){
		$.tmCookie.setCookie(name,'',-1); 
		var date=new Date();
        date.setTime(date.getTime()-10000);
		document.cookie=name+"=; expire="+date.toGMTString()+"; path=/";
	},
	
	getCookieTime : function(time){//s4 m4 h5 d4
	   if(time<=0)return time;
	   var str1=time.toString().substring(1,time.length)*1;
	   var str2=time.toString().substring(0,1);
	   if (str2=="s"){//秒
	        return str1*1000;
	   }
	   else if (str2=="m"){//分
	       return str1*60*1000;
	   }
	   else if (str2=="h"){//时
		   return str1*60*60*1000;
	   }
	   else if (str2=="d"){//天
	       return str1*24*60*60*1000;
	   }
	}
};
/*array*/

$.tmArray = {
/*each和map的功能是一样的*/	
each : function(arr,fn){
	fn = fn || Function.K;
	var a = [];
	var args = Array.prototype.slice.call(arguments, 1);
	for(var i = 0; i < arr.length; i++){
		var res = fn.apply(arr,[arr[i],i].concat(args));
		if(res != null) a.push(res);
	}
	return a;
},
/*each和map的功能是一样的*/	
map : function(arr,fn,thisObj){
	var scope = thisObj || window;
	var a = [];
	for ( var i=0, j=arr.length; i < j; ++i ) {
		var res = fn.call(scope, arr[i], i, this);
		if(res != null) a.push(res);
	}
	return a;
},
orderBy : function(array,sortFlag){
	var $arr = array;
	if(sortFlag=='asc'){
		$arr.sort(this._numAscSort);
	}else if(sortFlag=='desc'){
		$arr.sort(this._numDescSort);
	}else{
		$arr.sort(this._numAscSort);
	}
	return $arr;
},
// 求两个集合的并集
union : function(a, b){
	 var newArr = a.concat(b);
	 return this.unique2(newArr);
},
// 求两个集合的补集
complement :function(a,b){
	return this.minus(this.union(a,b),this.intersect(a,b));	
},
// 求两个集合的交集
intersect : function(a,b){
   a = this.unique(a);	
	return this.each(a,function(o){
		return b.contains(o) ? o : null;
	});
},
//求两个集合的差集
minus : function(a,b){
	a = this.unique(a);	
	return this.each(a,function(o){
		return b.contains(o) ? null : o;
	});
},
max : function(arr){
	return Math.max.apply({},arr) ;
},
min : function(arr){
	return Math.min.apply({},arr) ;
},
unique :function(arr){
	 var ra = new Array();
	 for(var i = 0; i < arr.length; i ++){
		 if(!ra.contains(arr[i])){
		 //if(this.contains(ra,arr[i])){	
			ra.push(arr[i]);
		 }
	 }
	 return ra;
},
unique2 : function(arr){
	for ( var i = 0; i < arr.length; i++) {
		for ( var j = i + 1; j < arr.length;) {
			if (arr[j] == arr[i]) {
				arr.splice(j, 1);
			} else {
				j++;
			}
		}
	}
	return arr;
},
indexOf : function(arr,val){
	for ( var i = 0; i < arr.length; i++) {
		if (arr[i] == val)
			return i;
	}
	return -1;	
},
contains : function(arr,val){
	return this.indexOf(arr,val) !=-1 ? true : false;
},
remove : function(arr,index){
	var index = this.indexOf(arr,index);
	if (index > -1) {
		arr.splice(index, 1);
	}
	return arr;
},
removeObject : function(arr,item){
	for ( var i = 0; i < arr.length; i++) {
		var jsonData = arr[i];
		for ( var key in jsonData) {
			if (jsonData[key] == item) {
				arr.splice(i, 1);
			}
		}
	}
	return arr;
},
toArray : function(arrString,sp){
	if(sp==undefined)sp=",";
	if(arrString==undefined)return this;
	var arrs = arrString.split(sp);
	return arrs;
},
_numAscSort :function(a,b){
	 return a-b;
},
_numDescSort :function(a,b){
	return b-a;
},
_sortAsc : function(x, y){
	if(x>y){
		return 1;
	}else{
		return -1;
	}
},
_sortDesc : function (x, y){
	if(x>y){
		return -1;
	}else{
		return 1;
	}
}

};
/*手机*/
function is_cellphoneNum(str)
{
    var regExp = /^(\+86)?(13|18|15)\d{9}(?!\d)$/;
    return regExp.test(str);
}

 /*邮件格式*/ 
function is_email(str)
{ 
    var regExp = /^([\w\.])+@\w+\.([\w\.])+$/;
    return regExp.test(str);
}
/*array end*/


function tz_animateIn(index){
	var animateIn = [];
	animateIn.push("animated bounce");//0
	animateIn.push("animated tada");//1
	animateIn.push("animated swing");//2
	animateIn.push("animated wobble");//3
	animateIn.push("animated flip");//4
	animateIn.push("animated flipInX");//5
	animateIn.push("animated flipInY");//6
	animateIn.push("animated fadeIn");//7
	animateIn.push("animated fadeInUp");//8
	animateIn.push("animated fadeInDown");//9
	animateIn.push("animated fadeInLeft");//10
	animateIn.push("animated fadeInRight");//11
	animateIn.push("animated fadeInUpBig");//12
	animateIn.push("animated fadeInDownBig");//13
	animateIn.push("animated fadeInLeftBig");//14
	animateIn.push("animated fadeInRightBig");//15
	animateIn.push("animated bounceIn");//16
	animateIn.push("animated bounceInUp");//17
	animateIn.push("animated bounceInDown");//18
	animateIn.push("animated bounceInLeft");//19
	animateIn.push("animated bounceInRight");//20
	animateIn.push("animated rotateIn");//21
	animateIn.push("animated rotateInUpLeft");//22
	animateIn.push("animated rotateInDownLeft");//23
	animateIn.push("animated rotateInUpRight");//24
	animateIn.push("animated rotateInDownRight");//25
	animateIn.push("animated rollIn");//26
	if(!index){
		var len = animateIn.length;
		var r = Math.floor(Math.random()*(len-1)+1);
		return animateIn[r];
	}else{
		return animateIn[index];
	}
}

function tz_animateOut(index){
	var animateOut = [];
//	animateOut.push("animated flipOutX");//0
//	animateOut.push("animated flipOutY");//1
	animateOut.push("animated fadeOut");//2
	animateOut.push("animated fadeOutUp");//3
	animateOut.push("animated fadeOutDown");//4
	animateOut.push("animated fadeOutLeft");//5
	animateOut.push("animated fadeOutRight");//6
	animateOut.push("animated fadeOutUpBig");//7
	animateOut.push("animated fadeOutDownBig");//8
	animateOut.push("animated fadeOutLeftBig");//9
	animateOut.push("animated fadeOutRightBig");//10
	animateOut.push("animated bounceOut");//11
	animateOut.push("animated bounceOutUp");//12
	animateOut.push("animated bounceOutDown");//13
	animateOut.push("animated bounceOutLeft");//14
	animateOut.push("animated bounceOutRight");//15
	animateOut.push("animated rotateOut");//16
	animateOut.push("animated rotateOutUpLeft");//17
	animateOut.push("animated rotateOutDownLeft");//18
	animateOut.push("animated rotateOutDownRight");//19
	animateOut.push("animated rollOut");//21
//	animateOut.push("animated hinge");//20
	if(!index){
		var len = animateOut.length;
		var r = Math.floor(Math.random()*(len-1)+1);
		return animateOut[r];
	}else{
		return animateOut[index];
	}
};

function getTitle(year,month,day){
	var title = "";
	if(isNotEmpty(year)){
		title = year+"年度";
	}
	if(isNotEmpty(month)){
		title += month+"月份";
	}
	if(isNotEmpty(day)){
		title += day+"日";
	}
	return title;
};

/*
获取鼠标位置x---y
*/
function getMouseXY(e){
	var ev= e || window.event;
	//坐标的获取
	//ie678 clientX clientY(不包含滚动条的scrollLeft scrollTop)
	//ie9+ pageX pageY(包含滚动条的scrollLeft scrollTop)
	
	var x = 0,y=0;
	if(ev.pageX){
		x = ev.pageX;
		y = ev.pageY;
	}else{
		var stop = 0,sleft = 0;
		if(document.documentElement){//ie678
			stop = document.documentElement.scrollTop;
			sleft = document.documentElement.scrollLeft;
		}else{//ie9+谷歌
			stop = document.body.scrollTop;
			sleft = document.body.scrollLeft;
		}
		x =	ev.clientX + sleft;
		y= ev.clientY + stop;
	}
	return {"x":x,"y":y};
};

/*范围随机*/
function rangeRandom(start,end){
	return Math.floor(Math.random()*(end-start+1)+start);
}

//关闭当前网页
function closeCurrentPage(isClose) {
	if(isClose){
		closePageFun();
	}else{
		yyConfirm({content:"您确定要退出当前页面吗？",style:"q",flag:"1",callback:function(ok){
			if(ok){
				closePageFun();
			}
		}});
	}
}

function closePageFun(){
	if (navigator.userAgent.indexOf("MSIE") > 0) {
        if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
             window.opener = null;
             window.close();
        }
        else {
             window.open('', '_top');
              if(window.top!=null){
                window.top.close();
             }
        }
     }
	  else if (navigator.userAgent.indexOf("Firefox") > 0) {
        window.location.href = 'about:blank ';
     } 
     else {
        window.opener = null;
        window.open('', '_self', '');
        window.close();
     }
}

//获取鼠标的位置。兼容ie678
function getXY(e){
	var ev = e || window.event;
	var x=0,y=0;
	if(ev.pageX){
		x = ev.pageX;
		y = ev.pageY;
	}else{
		//拿到scrollTop 和scrollLeft
		var sleft = 0,stop = 0;
		//ie678---
		if(document.documentElement){
			stop =document.documentElement.scrollTop;
			sleft = document.documentElement.scrollLeft;
		}else{
		//ie9+ 谷歌 
			stop = document.body.scrollTop;
			sleft = document.body.scrollLeft;
		}	
		x = ev.clientX + sleft;
		y = ev.clientY + stop;
	}
	return {x:x,y:y};
};

/*获取本月的日期字符串*/
function findThisMonths(){
	var dateArr=[];//本月的所有日期(string)
	var date = new Date();
	var year = date.getFullYear();//年份
	var month = date.getMonth()+1;//月份
	var date2 = new Date(year,month,0);
	var monthDays = date2.getDate();//本月的最大日期
	for(var i=1;i<=monthDays;i++){
		var date3 = new Date(year,month-1,i);
		dateArr.push(date3.format("yyyy-MM-dd"));
	}
	return dateArr;
}
//本月的day
function findThisMonthDaies(){
	var dateArr=[];//本月的所有日期(string)
	var date = new Date();
	var year = date.getFullYear();//年份
	var month = date.getMonth()+1;//月份
	var date2 = new Date(year,month,0);
	var monthDays = date2.getDate();//本月的最大日期
	for(var i=1;i<=monthDays;i++){
		var date3 = new Date(year,month-1,i);
		dateArr.push(date3.format("dd"));
	}
	return dateArr;
}

//null转"",如果repStr不为空，替换为repStr		
function nulltostr(str,repStr){
	if(isEmpty(str)){
		return isNotEmpty(repStr)?repStr:"";
	}else{
		return str;
	}
}

//根据浏览器容器宽度自动截取文本宽度
function getAutoContent(content,contentWidth,px){
	var subInfo = nulltostr(content);
	var numLength = subInfo.replace(/[^0-9]/g,"").length;//包含数字的个数 两个数字比有一个中文多两个像素
	var upperLength = subInfo.replace(/[^A-Z]/g,"").length;//大写字母个数 两个大写字母比有一个中文多两个像素
	var lowLength = subInfo.replace(/[^a-z]/g,"").length;//小写字母个数 两个大写字母比有一个中文多两个像素
	if(lowLength > 0){
		lowLength = lowLength/2;
	}
	var width = contentWidth - numLength - upperLength - lowLength;
    var maxLength = parseInt(width/(px/2));//7是font-size为14时单字节宽度，虽然特殊符号的宽度可能不够7。如果字体大小有调整，7也需要调整
    if(strlen(subInfo) >= maxLength && maxLength > 0){
    	subInfo = sb_substr(subInfo, 0, maxLength);
    }
    return subInfo;
}

//判断长度,支持中英文，中文两个字节
function strlen(str){
    var len = 0;
    for (var i=0; i<str.length; i++) { 
     var c = str.charCodeAt(i); 
    //单字节加1 
     if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
       len++; 
     } 
     else { 
      len+=2; 
     } 
    } 
    return len;
}

//截取字符,区分中英文  
function sb_substr(str, startp, endp) {  
    var i=0; c = 0; unicode=0; rstr = '';  
    var len = str.length;  
    var sblen = strlen(str);  
  
    if (startp < 0) {  
        startp = sblen + startp;  
    }  
  
    if (endp < 1) {  
        endp = sblen + endp;// - ((str.charCodeAt(len-1) < 127) ? 1 : 2);  
    }  
    // 寻找起点  
    for(i = 0; i < len; i++) {  
        if (c >= startp) {  
            break;  
        }  
        var unicode = str.charCodeAt(i);  
        if (unicode < 127) { 
            c += 1;  
        } else {  
            c += 2;  
        }  
    }  
  
    // 开始取  
    for(i = i; i < len; i++) {  
        var unicode = str.charCodeAt(i);  
        if (unicode < 127) {  
            c += 1;  
        } else {  
            c += 2;  
        }  
        rstr += str.charAt(i);  
  
        if (c >= endp) {  
            break;  
        }  
    }  
  
    return rstr+"...";  
}  

/**
 * from2json
 * @param formObj from的id
 * @param prefix 后台接收对象的实例名
 * @returns {___anonymous37399_37400}
 */
function formToJson(formObj,prefix){
	   var o={};
	   var a=$("#"+formObj).serializeArray();
	   $.each(a, function() {
	       if(this.value){ //form没填写的处理，不然json取值为空
	           if (o[this.name]) {
	               if (!o[this.name].push) {
	                   o[prefix+'.'+this.name]=[ o[this.name] ];
	               }
	                   o[prefix+'.'+this.name].push(this.value || null);
	           }else {
	               if($("[name='"+this.name+"']:checkbox",$("#"+formObj)).length){
	                   o[prefix+'.'+this.name]=[this.value];
	               }else{
	                   o[prefix+'.'+this.name]=this.value || null;
	               }
	           }
	       }
	   });
	   return o;
};


/**
 * ajax方法统一请求
 * @param url 
 * @param jsonData
 * @param success 成功回调
 */
function ajaxFunction(url,jsonData,success,error){
	$.ajax({
		type:"post",
		url:url,
		data:jsonData,
		success:function(data){
			if(data.flag != "0"){//controller没有报错
				if(data.nosession == 'n'){
 					yyConfirm({flag:"1",style:"q",content:"当前会话已过期，点击【确定】重新登录，点击【取消】关闭当前窗口，请选择",callback:function(ok){
						  if(ok){
							  window.location.href=ctxPath+"/login";
						  }else{
							  window.close();
						  }
					}});
					return;
 				}else{
 					if(typeof(success) == "function"){
 						success(data);
 					}
 				}
			}
		},
		error:error
	}); 
}

//表格rowspan
jQuery.fn.rowspan = function(colIdx) { //封装的一个JQuery小插件 
	return this.each(function(){ 
		var that; 
		$('tr', this).each(function(row) { 
			$('td:eq('+colIdx+')', this).filter(':visible').each(function(col) { 
				if (that!=null 
						&& isNotEmpty($(this).html()) && isNotEmpty($(that).html()) 
						&& $(this).html()!="---" && $(that).html()!="---"
						&& $(this).html() == $(that).html()) { 
					rowspan = $(that).attr("rowSpan"); 
					if (rowspan == undefined) { 
						$(that).attr("rowSpan",1); 
						rowspan = $(that).attr("rowSpan"); 
					} 
					rowspan = Number(rowspan)+1; 
					$(that).attr("rowSpan",rowspan); 
					$(this).hide(); 
				} else { 
					that = this; 
				} 
			}); 
		}); 
	}); 
} 

//JS判断变量是否以某个字符串结尾
String.prototype.endWith=function(endStr){
    var d=this.length-endStr.length;
    return (d>=0&&this.lastIndexOf(endStr)==d)
}

//放入ifream左侧菜单的点击值
function setLocalItem(key,value){//
	if(localStorage){
		localStorage.setItem(key,value);
	}else{
		$.tmCookie.setCookie(key,value,1000*60*60*24*30);
	}
}

//获取ifream左侧菜单的点击值
function getLocalItem(key){
	var tabvalue = 0;
	if(localStorage){
		tabvalue = localStorage.getItem(key);
	}else{
		tabvalue = $.tmCookie.getCookie(key);
	}
	return tabvalue;
}

function html2Escape(sHtml) {
	if(isNotEmpty(sHtml)){
		return sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
	}else{
		return "";
	}
}

//限制输入字符method
function textAreaChange(obj){
    var $this = $(obj);
    var count_total = $this.siblings('.textwords').find('span').text();
    var count_input = $this.siblings('.textwords').find('em');
    var area_val = $this.val();
    if(area_val.len()>count_total){
        area_val = autoAddEllipsis(area_val,count_total);//根据字节截图内容
        $this.val(area_val);
        count_input.text(area_val.len());//显示可输入数
    }else{
        count_input.text(area_val.len());//显示可输入数
    }
}
//得到字符串的字节长度
String.prototype.len = function(){
    return this.replace(/[^\x00-\xff]/g, "xx").length;
};

function autoAddEllipsis(pStr, pLen) {
    var _ret = cutString(pStr, pLen);
    var _cutFlag = _ret.cutflag;
    var _cutStringn = _ret.cutstring;
    return _cutStringn;
}

function cutString(pStr, pLen) {
    var _strLen = pStr.length;
    var _tmpCode;
    var _cutString;
    var _cutFlag = "1";
    var _lenCount = 0;
    var _ret = false;
    if (_strLen <= pLen/2){_cutString = pStr;_ret = true;}
    if (!_ret){
        for (var i = 0; i < _strLen ; i++ ){
            if (isFull(pStr.charAt(i))){_lenCount += 2;}
            else {_lenCount += 1;}
            if (_lenCount > pLen){_cutString = pStr.substring(0, i);_ret = true;break;}
            else if(_lenCount == pLen){_cutString = pStr.substring(0, i + 1);_ret = true;break;}
        }
    }
    if (!_ret){_cutString = pStr;_ret = true;}
    if (_cutString.length == _strLen){_cutFlag = "0";}
    return {"cutstring":_cutString, "cutflag":_cutFlag};
}

function isFull (pChar){
    if((pChar.charCodeAt(0) > 128)){return true;}
    else{return false;}
}

//图片等比例缩放
function getResizePicWh2(JqObjImgTag, FitWidth ,FitHeight) {
    var image = new Image();
    image.src = JqObjImgTag.attr("src");
    var w,h;
    if (image.width > 0 && image.height > 0) {
        if (image.width / image.height >= FitWidth / FitHeight) {
            if (image.width > FitWidth) {
            	w = FitWidth;
            	h = (image.height * FitWidth) / image.width;
            } else {
            	w = image.width;
            	h = image.height;
            }
        } else {
            if (image.height > FitHeight) {
            	w = (image.width * FitHeight) / image.height;
            	h = FitHeight;
            } else {
            	w = image.width;
            	h = image.height;
            }
        }
    }
    return {"w":w, "h":h};
}