//截取字符,区分中英文  
function subStr(str, startp, endp) {  
    var i=0; c = 0; unicode=0; rstr = '';  
    var len = str.length;  
    var sblen = strlen(str);  
  
    if(sblen <= endp){
    	return str;
    }
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

/**
 * ajax方法统一请求
 * @param url 
 * @param jsonData
 * @param success 成功回调
 * @param error 失败回调
 */
function ajaxFunction(url,jsonData,success,error){
	var win = window;
	while(win != win.top){
		win = win.top;
	}
	$.ajax({
		type:"post",
		url:url,
		data:jsonData,
		success:function(data){
			if(data.nosession == 'n'){
				yyConfirm({flag:"1",style:"q",content:"当前会话已过期，点击【确定】重新登录，点击【取消】关闭当前窗口，请选择",callback:function(ok){
					  if(ok){
						  win.location.href=ctxPath+"/login";
					  }else{
						  win.close();
					  }
				}});
				return;
			}else{
				if(typeof(success) == "function"){
					success(data);
				}
			}
		},
		error:error
	}); 
}

/**
 * 表单数据封装成json
 * @param formObj 表单
 * @returns {___anonymous53_54}
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

function formToJson(formObj){
	   var o={};
	   var a=$("#"+formObj).serializeArray();
	   $.each(a, function() {
	       if(this.value){ //form没填写的处理，不然json取值为空
	           if (o[this.name]) {
	               if (!o[this.name].push) {
	                   o[this.name]=[ o[this.name] ];
	               }
	                   o[this.name].push(this.value || null);
	           }else {
	               if($("[name='"+this.name+"']:checkbox",$("#"+formObj)).length){
	                   o[this.name]=[this.value];
	               }else{
	                   o[this.name]=this.value || null;
	               }
	           }
	       }
	   });
	   return o;
};

function showMessageAndDofun(jqueryDom, res,successCallBack){
	if(("exception" in res  ) || ("msg" in res)){
		var message = " --- ";
		message += "操作失败，";
		var msg = "";
		if("msg" in res){
			msg = res.msg;
		}else{
			var exception  = res.exception;
			if("rootCause" in exception){
				var rootCause = exception.rootCause;
				msg = rootCause.message;
				if(msg.indexOf(" - ")>-1){
					msg = msg.substring(msg.lastIndexOf(" - ")+1, msg.length);
				}
			}else{
				msg = exception.localizedMessage;
			}
			if(msg==null){
				msg = "请与管理员联系！";
			}
			if(msg.indexOf("not-null property references a null or transient value")>-1){
				msg = "尚有属性未设置";
			}
		}
		message += msg;
		jqueryDom.text(message);
	}else{
		successCallBack();
	}
}

function checkUserFun(funId,isHaveDoFun){
	$.ajax({
		url:"function/checkUserFun.json?funId="+funId
		,success:function(res){
			if(res.isHave){
				isHaveDoFun();
			}else{
				showMessage("对不起，您没有该功能使用权限");
			}
		}
	});
}
/*---------------------------
功能:停止事件冒泡
---------------------------*/
function stopBubble(e) {
    //如果提供了事件对象，则这是一个非IE浏览器
    if ( e && e.stopPropagation )
        //因此它支持W3C的stopPropagation()方法
        e.stopPropagation();
    else
        //否则，我们需要使用IE的方式来取消事件冒泡
        window.event.cancelBubble = true;
}
//阻止浏览器的默认行为
function stopDefault( e ) {
    //阻止默认浏览器动作(W3C)
    if ( e && e.preventDefault )
        e.preventDefault();
    //IE中阻止函数器默认动作的方式
    else
        window.event.returnValue = false;
    return false;
}

function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g,'');
}

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

//null转"",如果repStr不为空，替换为repStr		
function nulltostr(str,repStr){
	if(isEmpty(str)){
		return isNotEmpty(repStr)?repStr:"";
	}else{
		return str;
	}
}



//JS判断变量是否以某个字符串结尾
String.prototype.endWith=function(endStr){
  var d=this.length-endStr.length;
  return (d>=0&&this.lastIndexOf(endStr)==d)
}



