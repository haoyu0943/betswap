//初始化连接融云
var ctxpath;
function init(token,ctxpathpara){
	ctxpath=ctxpathpara;
	RongIMLib.RongIMClient.init('3argexb63g1le');// 公司的:p5tvi9dsph3i4  个人：mgb7ka1nmd6hg
	// 连接状态监听器
	RongIMClient.setConnectionStatusListener({
	    onChanged: function (status) {
	        // status 标识当前连接状态
	        switch (status) {
	            case RongIMLib.ConnectionStatus.CONNECTED:
	                console.log('链接成功');
	                break;
	            case RongIMLib.ConnectionStatus.CONNECTING:
	                console.log('正在链接');
	                break;
	            case RongIMLib.ConnectionStatus.DISCONNECTED:
	                console.log('断开连接');
	                break;
	            case RongIMLib.ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT:
	                console.log('其他设备登录');
	                break;
	            case RongIMLib.ConnectionStatus.DOMAIN_INCORRECT:
	                console.log('域名不正确');
	                break;
	            case RongIMLib.ConnectionStatus.NETWORK_UNAVAILABLE:
	                console.log('网络不可用');
	                break;
	        }
	    }
	});
	RongIMClient.connect(token, {
	    onSuccess: function(userId) {
	        console.log('Connect successfully. ' + userId);
	        getConversationList();
	    },
	    onTokenIncorrect: function() {
	        console.log('token 无效');
	    },
	    onError: function(errorCode){
	        var info = '';
	        switch (errorCode) {
	            case RongIMLib.ErrorCode.TIMEOUT:
	                info = '超时';
	                break;
	            case RongIMLib.ConnectionState.UNACCEPTABLE_PAROTOCOL_VERSION:
	                info = '不可接受的协议版本';
	                break;
	            case RongIMLib.ConnectionState.IDENTIFIER_REJECTED:
	                info = 'appkey不正确';
	                break;
	            case RongIMLib.ConnectionState.SERVER_UNAVAILABLE:
	                info = '服务器不可用';
	                break;
	        }
	        console.log(info);
	    }
	});
	
	var massagecount=0;//会话页面消息个数
	var massagecontent="";//回话页面最后一条消息内容
	var massagetargetId="";
	//消息监听器
	RongIMClient.setOnReceiveMessageListener({
	    // 接收到的消息
	    onReceived: function (message) {
	        // 判断消息类型
	        switch(message.messageType){
	            case RongIMClient.MessageType.TextMessage:
	            	 massagecount++;
	            	 massagecontent=message.content.content;
	            	 massagetargetId=message.targetId;
	            	 //$("#"+massagetargetId).html("");
	                // message.content.content => 文字内容
	            	console.log("消息监听:"+JSON.stringify(message));
	                console.log("消息监听:"+message.content.content);
	                //添加监听的内容到页面中
		                $("#"+message.targetId).text(RongIMLib.RongIMEmoji.symbolToHTML(message.content.content));
		                var bodyhtml="";
		    			bodyhtml='<div class=\"clearfloat\" >'
				               +' <div class="author-name">'
				               +'     <small class="chat-date">'+transformTime(message.sentTime)+'</small>'
				               +' </div>'
				               +'<div class="left">'
				               +'    <div class="chat-avatars"><img src=\"'+"icon"+'\" alt="头像"/></div>'
				               +'     <div class="chat-message">'
				               +'        '+RongIMLib.RongIMEmoji.symbolToHTML(message.content.content)+' '
				               +'    </div>'
				               +' </div>'
				               +'</div>';
	            	 //添加到聊天页面中
	    			      $("#chatBox-content-demo").append(bodyhtml);
	    			      //让div随着内容的增加自动滑动到最下面
	    			      var div = document.getElementById('chatBox-content-demo');
	    			      div.scrollTop = div.scrollHeight;
	    			 //添加到会话列表中
	    			     $("."+massagetargetId).html(RongIMLib.RongIMEmoji.symbolToHTML(message.content.content));
	            	 //获取当前会话消息数量
	    			     console.log($("."+massagetargetId).parent().next().html());
		            	 var getmassagecount=$("."+massagetargetId).parent().next().html();
		            	 $("."+massagetargetId).parent().next().html("");//清空消息提示
		            	 if(getmassagecount==null||getmassagecount==""||getmassagecount=="undefined")getmassagecount=0;
	            	 //消息提示个数
		            	 $("."+massagetargetId).parent().next().html(++getmassagecount);
		            //设置总消息提示个数
		            	var unreadMessageCountTotal= $(".chat-message-num").html();
		            	$(".chat-message-num").html(++unreadMessageCountTotal)
	                break;
	            case RongIMClient.MessageType.VoiceMessage:
	                // message.content.content => 格式为 AMR 的音频 base64
	            	 console.log("消息监听:"+message.content.content);
	                break;
	            case RongIMClient.MessageType.ImageMessage:
	            	 console.log("消息监听:"+message.content.content);
	                // message.content.content => 图片缩略图 base64
	                // message.content.imageUri => 原图 URL
	                break;
	            case RongIMClient.MessageType.LocationMessage:
	            	 console.log("消息监听:"+message.content.content);
	                // message.content.latiude => 纬度
	                // message.content.longitude => 经度
	                // message.content.content => 位置图片 base64
	                break;
	            case RongIMClient.MessageType.RichContentMessage:
	            	 console.log("消息监听:"+message.content.content);
	                // message.content.content => 文本消息内容
	                // message.content.imageUri => 图片 base64
	                // message.content.url => 原图 URL
	                break;
	            case RongIMClient.MessageType.InformationNotificationMessage:
	            	 console.log("消息监听1:");
	                // do something
	                break;
	            case RongIMClient.MessageType.ContactNotificationMessage:
	            	 console.log("消息监听2:");
	                // do something
	                break;
	            case RongIMClient.MessageType.ProfileNotificationMessage:
	            	 console.log("消息监听3:");
	                // do something
	                break;
	            case RongIMClient.MessageType.CommandNotificationMessage:
	            	 console.log("消息监听4:");
	                // do something
	                break;
	            case RongIMClient.MessageType.CommandMessage:
	            	 console.log("消息监听5:");
	                // do something
	                break;
	            case RongIMClient.MessageType.UnknownMessage:
	            	 console.log("消息监听6:");
	                // do something
	                break;
	            default:
	            	 console.log("消息监听7:");
	                // do something
	        }
	    }
	});
	
	// 表情初始化
	RongIMLib.RongIMEmoji.init();
	var list = RongIMLib.RongIMEmoji.list;
	initBiaoqing(list);
	
}
//将初始化的表情添加的div中
function initBiaoqing(list){
	var message ;
	var html="";
	$.each(list,function(index,item){
		message=item.symbol;
		// 将 message 中的原生 Emoji名称 (包含 Unicode ) 转化为 HTML后才能正确显示
		//RongIMLib.RongIMEmoji.symbolToHTML(message)
		html+='<a onclick="sendEmoji(\''+message+'\')">'+RongIMLib.RongIMEmoji.symbolToHTML(message)+'</a>&nbsp;'
		
	})
	$(".biaoqing-photo").append(html);
}
//发表情消息
function sendEmoji(symbol){
	var textvalue=$(".div-textarea").text();
	 textvalue+=symbol;
	$(".div-textarea").text(textvalue);
}
//从聊天界面返回会话列表界面
function returnConversation(){
	//获取两人回话
	getConversationList();
	var targetId=$("#sendtargetId").val();
	//清除所读消息
	clearUnreadCount(targetId);
}
//获取会话列表
function  getConversationList(){
	$(".chatBox-list").html("");
	RongIMClient.getInstance().getConversationList({
	    onSuccess: function(list) {
	        // list => 会话列表集合
	    	console.log("回话列表："+JSON.stringify(list));
	    	var i=1;
	    	var name="联系人";//默认名称
	    	var icon=ctxpath+"/message/img/icon01.png";//默认头像
	    	var unreadMessageCountTotal=0;//总会话未读消息数量
	    	$.each(list,function(index,item){
	    		i++;
	    		name=item.latestMessage.senderUserId;
	    		//icon=item.latestMessage.user.icon;//对方头像
	    		//console.log(item.unreadMessageCount);//未读消息数"'+item.targetId+'"
	    		$(".chatBox-list").append('<div id="'+i+'" class=\"chat-list-people\" onclick="getHistoryMessages(\''+item.targetId+'\',\''+i+'\',\''+name+'\',\''+icon+'\');" >'
				+'<div><img src=\"'+icon+'\"/></div>'
				+'<div class=\"chat-name\">'
				+' <div style=\"margin-top: 4%;font-weight:bold;"\>'+name+'</div>'
				+' <div style=\"text-overflow:ellipsis;white-space:nowrap;overflow:hidden;\" class=\"'+item.targetId+'\" >'+RongIMLib.RongIMEmoji.symbolToHTML(item.latestMessage.content.content)+'</div>'
				+'</div>'
				//+'<div class=\"message-num\">'+item.unreadMessageCount+'</div>'
				+'<div class=\"clear-massage\" ><img onclick="removeConversation(\''+item.targetId+'\');" src=\"'+ctxpath+'/message/img/delete.png\" class=\"clear-img\" style=\" vertical-align:middle;\"/></div>'
				+'</div>');
	    		if(item.unreadMessageCount!=0){
	    			$(".chat-name").after('<div class=\"message-num\">'+item.unreadMessageCount+'</div>');
	    		}else{
	    			$(".chat-name").after('<div class=\"message-num\">0</div>');
	    		}
	    		unreadMessageCountTotal+=item.unreadMessageCount;
	    	})
	    	
	    	$(".chat-message-num").html(unreadMessageCountTotal);//设置总未读消息提示
	    	
	    },
	    onError: function(error) {
	        // do something
	    }
	}, null);
}
//发送二人聊天消息
function sendmassage(textContent,targetId){
	var msg = new RongIMLib.TextMessage({ content: textContent, extra: '附加信息' });
	var conversationType = RongIMLib.ConversationType.PRIVATE; // 单聊 PRIVATE CUSTOMER_SERVICE客户会话, 其他会话选择相应的消息类型即可
	var targetId = targetId; // 目标 Id
	console.log("targetId--"+targetId+"  textContent"+textContent);
	var index=0;
	var clearfloatid=1;
	RongIMClient.getInstance().sendMessage(conversationType, targetId, msg, {
	    onSuccess: function (message) {
	        // message 为发送的消息对象并且包含服务器返回的消息唯一 Id 和发送消息时间戳
	        console.log('targetId:'+targetId+'--发送成功!');
	        //成功后追加消息在列表框
	        $(".chatBox-content-demo").append("<div class=\"clearfloat\" onmousedown='chehui("+index+",\""+clearfloatid+"\",\""+targetId+"\");' >" +
	                "<div class=\"author-name\"><small class=\"chat-date\">"+transformTime(new Date())+"</small> </div> " +
	                "<div class=\"right\"> <div class=\"chat-message\"> " +RongIMLib.RongIMEmoji.symbolToHTML(textContent) + " </div> " +
	                "<div class=\"chat-avatars\"><img src=\"'+ctxpath+'/message/img/icon01.png\" alt=\"头像\" /></div> </div> </div>");
	            //发送后清空输入框
	            $(".div-textarea").html("");
	            //聊天框默认最底部
	            $(document).ready(function () {
	                $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
	            });
	    },
	    onError: function (errorCode, message) {
	        var info = '';
	        switch (errorCode) {
	            case RongIMLib.ErrorCode.TIMEOUT:
	                info = '超时';
	                break;
	            case RongIMLib.ErrorCode.UNKNOWN:
	                info = '未知错误';
	                break;
	            case RongIMLib.ErrorCode.REJECTED_BY_BLACKLIST:
	                info = '在黑名单中，无法向对方发送消息';
	                break;
	            case RongIMLib.ErrorCode.NOT_IN_DISCUSSION:
	                info = '不在讨论组中';
	                break;
	            case RongIMLib.ErrorCode.NOT_IN_GROUP:
	                info = '不在群组中';
	                break;
	            case RongIMLib.ErrorCode.NOT_IN_CHATROOM:
	                info = '不在聊天室中';
	                break;
	        }
	        console.log('发送失败: ' + info + errorCode);
	        alert('发送失败: ' + info + errorCode);
	    }
	});
}
//打开撤回消息弹窗
var j=1;
function chehui(index,clearfloatid,messageUId){
	j++;
/*	var btnNum = event.button;
	if (btnNum==2)//右键点击事件
	  {
		if(j%2==0){
		    $("#"+clearfloatid).append(' <div class="'+clearfloatid+'"  style="height: auto;background: gainsboro;text-align: center;z-index:2000;width: 50px;margin-left:250px;margin-top:40px;">'
				                   +' <a onclick="recallMessage(\''+messageUId+'\');">撤&nbsp;回</a><br>'
				                   +' <a>清&nbsp;屏</a>'
				                   +'</div>');
		}else{
			$("."+clearfloatid).remove();
		}
	  }*/
	
	
	
}
//撤回消息

//撤回消息
function recallMessage(messageUId){
	console.log(messageUId+"撤回");
	RongIMClient.getInstance().sendRecallMessage(messageUId, {
	    onSuccess: function (message) {
	        console.log('撤回成功', message, start);
	    },
	    onError: function (errorCode,message) {
	        console.log('撤回失败', message, start);
	    }
	});
}
//刷新得到更多历史消息
function reflushHistoryMessages(targetId,id,name,icon,reflushHistoryMessagesId){
	$("#"+reflushHistoryMessagesId).remove();
	reflushHistoryMessagesId++;
	var conversationType = RongIMLib.ConversationType.PRIVATE; //PRIVATE 单聊, CUSTOMER_SERVICE客户会话 其他会话选择相应的消息类型即可
	var targetId = targetId; // 想获取自己和谁的历史消息，targetId 赋值为对方的 Id
	var timestrap =null; // 默认传 null，若从头开始获取历史消息，请赋值为 0, timestrap = 0;如果要能获取刷新获取历史消息，不能传0，只能传null
	var count = 20; // 每次获取的历史消息条数，范围 0-20 条，可以多次获取
	RongIMLib.RongIMClient.getInstance().getHistoryMessages(conversationType, targetId, timestrap, count, {
		// list => Message 数组。
        // hasMsg => 是否还有历史消息可以获取。
		onSuccess: function(list, hasMsg) {
	    	var html="";
	    	//判断是否有历史消息获取
	    	if(hasMsg){
	    		html+='<div class="reflushHistoryMessages" id="'+reflushHistoryMessagesId+'" style="text-align: center;" onclick="reflushHistoryMessages(\''+targetId+'\',\''+id+'\',\''+name+'\',\''+icon+'\',\''+reflushHistoryMessagesId+'\');" ><a>查看历史消息</a></div>';
	    	}if(!hasMsg){
	    		//去掉查看历史消息div
		    	$(".reflushHistoryMessages").remove();
		    	html+='<div class="reflushHistoryMessages" style="text-align: center;" >暂无历史消息</div>';
	    	}
	    	var clearfloatid=-1000;
	    	$.each(list,function(index,item){
	    		clearfloatid++;
	    		//拼接内容
	    		//用户消息
	    		//RongIMLib.RongIMEmoji.symbolToHTML如果是表情消息就显示表情
	    		if(item.targetId!=null&&item.targetId!=""){
	    			html+='<div class=\"clearfloat\" >'
			               +' <div class="author-name">'
			               +'     <small class="chat-date">'+transformTime(item.sentTime)+'</small>'
			               +' </div>'
			               +'<div class="left">'
			               +'    <div class="chat-avatars"><img src=\"'+icon+'\" alt="头像"/></div>'
			               +'     <div class="chat-message">'
			               +'        '+RongIMLib.RongIMEmoji.symbolToHTML(item.content.content)+' '
			               +'    </div>'
			               +' </div>'
			               +'</div>';
	    		}//自己发的消息
	    		else{
            	html+='<div class="clearfloat" id="'+clearfloatid+'" onmousedown="chehui('+index+',\''+clearfloatid+'\',\''+item.messageUId+'\');">'
		               +' <div class="author-name">'
		               +'     <small class="chat-date">'+transformTime(item.sentTime)+'</small>'
		               +' </div>'
		               +'<div class="right">'
		               +'    <div class="chat-message">'+RongIMLib.RongIMEmoji.symbolToHTML(item.content.content)+'</div>'
		               +'     <div class="chat-avatars"><img src="'+ctxpath+'/message/img/icon02.png" alt="头像"/></div>'
		               +' </div>'
		               +'</div>';
	    		}
	    		
	    	});
	    	$("#chatBox-content-demo").prepend(html);
	    	html="";
	    	//设置接收人sendtargetId
	    	$("#sendtargetId").val(targetId);
	    	
	    },
	    onError: function(error) {
	        console.log('GetHistoryMessages, errorcode:' + error);
	    }
	});
}
//单击回话列表获取二人历史消息targetId
var reflushHistoryMessagesId=1000;
function getHistoryMessages(targetId,id,name,icon){
	$("#chatBox-content-demo").html("");
	var conversationType = RongIMLib.ConversationType.PRIVATE; //PRIVATE 单聊, CUSTOMER_SERVICE客户会话 其他会话选择相应的消息类型即可
	var targetId = targetId; // 想获取自己和谁的历史消息，targetId 赋值为对方的 Id
	var timestrap = 0; // 默认传 null，若从头开始获取历史消息，请赋值为 0, timestrap = 0;  开始获取只能从 0开始，不然每次点击返回会话页面再到聊天页面都是循环获取历史消息
	var count = 20; // 每次获取的历史消息条数，范围 0-20 条，可以多次获取
	RongIMLib.RongIMClient.getInstance().getHistoryMessages(conversationType, targetId, timestrap, count, {
		// list => Message 数组。
        // hasMsg => 是否还有历史消息可以获取。
		onSuccess: function(list, hasMsg) {
	    	//console.log(JSON.stringify(list));
	    	var html="";
	    	//判断是否有历史消息获取
	    	if(hasMsg){
	    		html+='<div class="reflushHistoryMessages" id="'+reflushHistoryMessagesId+'" style="text-align: center;" onclick="reflushHistoryMessages(\''+targetId+'\',\''+id+'\',\''+name+'\',\''+icon+'\',\''+reflushHistoryMessagesId+'\');" ><a>查看历史消息</a></div>';
	    	}if(!hasMsg){
	    		//去掉查看历史消息div
		    	$(".reflushHistoryMessages").remove();
		    	html+='<div class="reflushHistoryMessages" style="text-align: center;" >暂无历史消息</div>';
	    	}
	    	var clearfloatid=-1000;
	    	$.each(list,function(index,item){
	    		clearfloatid++;
	    		//拼接内容
	    		//用户消息
	    		//messageDirection 1 为发送的消息、2 为接收的消息。
	    		//RongIMLib.RongIMEmoji.symbolToHTML如果是表情消息就显示表情
	    		if(item.messageDirection=="2"){
	    			html+='<div class=\"clearfloat\" >'
			               +' <div class="author-name">'
			               +'     <small class="chat-date">'+transformTime(item.sentTime)+'</small>'
			               +' </div>'
			               +'<div class="left">'
			               +'    <div class="chat-avatars"><img src=\"'+icon+'\" alt="头像"/></div>'
			               +'     <div class="chat-message">'
			               +'        '+RongIMLib.RongIMEmoji.symbolToHTML(item.content.content)+' '
			               +'    </div>'
			               +' </div>'
			               +'</div>';
	    		}//自己发的消息
	    		else if (item.messageDirection=="1"){
            	html+='<div class="clearfloat" id="'+clearfloatid+'" onmousedown="chehui('+index+',\''+clearfloatid+'\',\''+item.messageUId+'\');">'
		               +' <div class="author-name">'
		               +'     <small class="chat-date">'+transformTime(item.sentTime)+'</small>'
		               +' </div>'
		               +'<div class="right">'
		               +'    <div class="chat-message">'+RongIMLib.RongIMEmoji.symbolToHTML(item.content.content)+'</div>'
		               +'     <div class="chat-avatars"><img src="'+ctxpath+'/message/img/icon02.png" alt="头像"/></div>'
		               +' </div>'
		               +'</div>';
	    		}
	    		
	    	});
	    	//显示两个聊天页面
	    	$("#chatBox-content-demo").append(html);
	    	html="";
	    	//设置接收人sendtargetId
	    	$("#sendtargetId").val(targetId);
  		    var n = $("#"+id).index();
  	    
	  		  $(".chatBox-head-one").toggle();
	  	      $(".chatBox-head-two").toggle();
	  	      $(".chatBox-list").fadeToggle();
	  	      $(".chatBox-kuang").fadeToggle();
	
	  	      //传名字
	  	      $(".ChatInfoName").text(name);
	  	      //传头像
	  	      $(".ChatInfoHead>img").attr("src", icon);
	  	      //聊天框默认最底部
	  	      $(document).ready(function () {
	  	          $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
	  	      });
  	        //清除未读消息
  	      clearUnreadCount(targetId);
	    },
	    onError: function(error) {
	        console.log('GetHistoryMessages, errorcode:' + error);
	    }
	});

}
//清空未读消息数

// 清除未读消息成功
function clearUnreadCount(targetId){
	var conversationType = RongIMLib.ConversationType.PRIVATE;//二人聊天 PRIVATE
	var targetId = targetId;
	RongIMClient.getInstance().clearUnreadCount(conversationType, targetId, {
	    onSuccess: function(){
	        // 清除未读消息成功
	    	console.log("清除成功");
	    },
	    onError: function(error){
	        // error => 清除未读消息数错误码
	    	console.log("清除失败:"+error);
	    }
	});
}
//删除聊天记录

//删除会话
function removeConversation(targetId){
	var e = window.event;  
     if(e.stopPropagation) { //W3C阻止冒泡方法  
         e.stopPropagation();  
     } else {  
         e.cancelBubble = true; //IE阻止冒泡方法  
      }  
	var conversationType = RongIMLib.ConversationType.PRIVATE;
	var targetId = targetId;
	if(confirm("你确定清空聊天记录吗？")){
		RongIMClient.getInstance().removeConversation(conversationType, targetId, {
		    onSuccess: function(bool) {
		        // 删除会话成功
		    	console.log("删除成功!");
		    	getConversationList();
		    },
		    onError: function(error){
		        // error => 删除会话的错误码
		    	console.log("删除失败："+error);
		    }
		});
	}
}
//时间格式化

//创建时间格式化显示
function transformTime(timestamp) {
    if (timestamp) {
        var time = new Date(timestamp);
        var y = time.getFullYear(); //getFullYear方法以四位数字返回年份
        var M = time.getMonth() + 1; // getMonth方法从 Date 对象返回月份 (0 ~ 11)，返回结果需要手动加一
        var d = time.getDate(); // getDate方法从 Date 对象返回一个月中的某一天 (1 ~ 31)
        var h = time.getHours(); // getHours方法返回 Date 对象的小时 (0 ~ 23)
        var m = time.getMinutes(); // getMinutes方法返回 Date 对象的分钟 (0 ~ 59)
        var s = time.getSeconds(); // getSeconds方法返回 Date 对象的秒数 (0 ~ 59)
        return y + '-' + M + '-' + d + ' ' + h + ':' + m + ':' + s;
      } else {
          return '';
      }
}
//按enter发送消息
function getKey()
{
    if(event.keyCode==13){
    	 var textContent = $(".div-textarea").html().replace(/[\n\r]/g, '<br>')
         if (textContent != "") {
        	 //得到接收人ID
             var targetId= $("#sendtargetId").val();
         	  //发送消息到融云服务
            sendmassage(textContent,"96");// targetId KEFU150535341165880 zhichi01
         }
    }   
}


