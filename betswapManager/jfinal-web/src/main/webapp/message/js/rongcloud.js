function RongCloud(){

}

RongCloud.prototype = {
    constructor: RongCloud,
    /**
     * 初始化融云服务,并启动消息监听
     * @param appkey app的唯一标识
     */
    initRong: function (appkey) {
        RongIMClient.init(appkey); //appkey
        // 设置连接监听状态 （ status 标识当前连接状态）
        // 连接状态监听器
        RongIMClient.setConnectionStatusListener({
            onChanged: function (status) {
                switch (status) {
                    //链接成功
                    case RongIMLib.ConnectionStatus.CONNECTED:
                        console.log('链接成功');
                        break;
                    //正在链接
                    case RongIMLib.ConnectionStatus.CONNECTING:
                        console.log('正在链接');
                        break;
                    //重新链接
                    case RongIMLib.ConnectionStatus.DISCONNECTED:
                        console.log('断开连接');
                        break;
                    //其他设备登录
                    case RongIMLib.ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT:
                        console.log('其他设备登录');
                        break;
                    //网络不可用
                    case RongIMLib.ConnectionStatus.NETWORK_UNAVAILABLE:
                        console.log('网络不可用');
                        break;
                }
            }
        });
        this.monitor();
    },

    /**
     * 用户用token连接融云服务器
     * @param token 用户的聊天token
     */
    connectSession: function (callback,token) {
        // 连接融云服务器。
        RongIMClient.connect(token, {
            onSuccess: function (userId) {
                console.log("Login successfully." + userId);
                callback  && callback(true, userId);
            },
            onTokenIncorrect: function () {
                console.log('token无效' + userId);
                callback  && callback(false, userId);
            },
            onError: function (errorCode) {
                var info = '';
                switch (errorCode) {
                    case RongIMLib.ErrorCode.TIMEOUT:
                        info = '超时';
                        break;
                    case RongIMLib.ErrorCode.UNKNOWN_ERROR:
                        info = '未知错误';
                        break;
                    case RongIMLib.ErrorCode.UNACCEPTABLE_PaROTOCOL_VERSION:
                        info = '不可接受的协议版本';
                        break;
                    case RongIMLib.ErrorCode.IDENTIFIER_REJECTED:
                        info = 'appkey不正确';
                        break;
                    case RongIMLib.ErrorCode.SERVER_UNAVAILABLE:
                        info = '服务器不可用';
                        break;
                }
                console.log(errorCode);
            }
        });
    },

    /**
     * 消息监听
     */
    monitor:function (){
        // 消息监听器
        RongIMClient.setOnReceiveMessageListener({
            // 接收到的消息
            onReceived: function (message) {
                // 判断消息类型
                switch (message.messageType) {
                    case RongIMClient.MessageType.TextMessage:
                        // 发送的消息内容将会被打印
                        console.log("message.content.content------" + JSON.stringify(message.content.content));
                        console.log('收到发送的消息');
                        if(typeof writeInContent != 'undefined'){ //在聊天页面才写入数据
                            writeInContent(1,message.content.content,message.senderUserId,'');//给接收者写入最新数据
                        }
                        break;
                    case RongIMClient.MessageType.VoiceMessage:
                        // 对声音进行预加载
                        // message.content.content 格式为 AMR 格式的 base64 码
                        RongIMLib.RongIMVoice.preLoaded(message.content.content);
                        break;
                    case RongIMClient.MessageType.ImageMessage:
                        // do something...
                        break;
                    case RongIMClient.MessageType.DiscussionNotificationMessage:
                        // do something...
                        break;
                    case RongIMClient.MessageType.LocationMessage:
                        // do something...
                        break;
                    case RongIMClient.MessageType.RichContentMessage:
                        // do something...
                        break;
                    case RongIMClient.MessageType.DiscussionNotificationMessage:
                        // do something...
                        break;
                    case RongIMClient.MessageType.InformationNotificationMessage:
                        // do something...
                        console.log('收到小灰条信息')
                        break;
                    case RongIMClient.MessageType.ContactNotificationMessage:
                        // do something...
                        break;
                    case RongIMClient.MessageType.ProfileNotificationMessage:
                        // do something...
                        break;
                    case RongIMClient.MessageType.CommandNotificationMessage:
                        // do something...
                        break;
                    case RongIMClient.MessageType.CommandMessage:
                        // do something...
                        break;
                    case RongIMClient.MessageType.UnknownMessage:
                        // do something...
                        break;
                    case RongIMClient.RegisterMessage.PersonMessage:
                        // do something...
                        break;
                    default:
                    // 自定义消息
                    // do something...

                }
            }
        });
    },

    /**
     * 获取会话列表
     * @param callback 消息回调参数
     * @return boolean 是否成功的状态码
     * @return list 会话列表集合
     */
    getSessionList: function (callback) {
        RongIMClient.getInstance().getConversationList({
            onSuccess: function(list) {
                console.log("会话列表--"+ JSON.stringify(list))
                callback  && callback(true, list);
            },
            onError: function(error) {
                // do something...r
                result = "获取会话列表失败！"
                callback  && callback(false, result);
            }
        },null);
    },

    /**
     * 获取指定的会话
     * @param callback 消息回调参数
     * @return boolean 是否成功的状态码
     * @return list 会话列表集合
     */
    getSessionListBytargetId: function(callback, targetId) {
        var conversationType = RongIMLib.ConversationType.PRIVATE;
        RongIMLib.RongIMClient.getInstance().getUnreadCount(conversationType,targetId,{
            onSuccess:function(count){
                // count => 指定会话的总未读数。
                callback  && callback(true, count);
            },
            onError:function(){
                result = "获取会话列表失败！"
                callback  && callback(false, result);
            }
        });
    },

    /**
     * 清空和莫个人的未读消息数
     * @param callback 消息回调参数
     * @param targetId 要清楚和谁的会话消息数
     */
    cleanMessageCount: function (targetId) {
        var conversationType = RongIMLib.ConversationType.PRIVATE;
        RongIMClient.getInstance().clearUnreadCount(conversationType,targetId,{
            onSuccess:function(){
                console.log("清除未读消息数成功！");
            },
            onError:function(error){
                console.log("清除未读消息数错误！"+ error);
            }
        });
    },

    /**
     * 获取自己和莫个人的聊天记录
     * @param callback 消息回调参数
     * @param conversationType 私聊,其他会话选择相应的消息类型即可。
     * @param targetId 想获取自己和谁的历史消息，targetId 赋值为对方的 Id。
     * @param timestrap  默认传 null，若从头开始获取历史消息，请赋值为 0 ,timestrap = 0;
     * @param count 每次获取的历史消息条数，范围 0-20 条，可以多次获取。
     * @return list 聊天数据集合
     * @return hasMag 是否还有历史记录 有位true
     */
    getHistoryById: function (callback,conversationType,targetId,timestrap,count) {
        RongIMLib.RongIMClient.getInstance().getHistoryMessages(conversationType, targetId, timestrap, count, {
            onSuccess: function(list, hasMsg) {
                // list => Message 数组。
                // hasMsg => 是否还有历史消息可以获取。
                // console.log("targetId------" + targetId);
                // console.log("GetHistoryMessages:list" + JSON.stringify(list));
                callback && callback(hasMsg, list);
            },
            onError: function(error) {
                console.log("获取历史" , error);
                callback && callback(false, error);
            }
        });
    },

    sendMessage :function(targetId,content,extra) {
        var msg = new RongIMLib.TextMessage({content:content,extra:extra});
        var conversationtype = RongIMLib.ConversationType.PRIVATE; // 单聊,其他会话选择相应的消息类型即可。
        var targetId = targetId; // 目标 Id
        RongIMClient.getInstance().sendMessage(conversationtype, targetId, msg, {
                onSuccess: function (message) {
                    //message 为发送的消息对象并且包含服务器返回的消息唯一Id和发送消息时间戳
                    console.log("Send successfully");
                },
                onError: function (errorCode,message) {
                    var info = '';
                    switch (errorCode) {
                        case RongIMLib.ErrorCode.TIMEOUT:
                            info = '超时';
                            break;
                        case RongIMLib.ErrorCode.UNKNOWN_ERROR:
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
                        default :
                            info = x;
                            break;
                    }
                    console.log('发送失败:' + info);
                }
            }
        );
    }
}