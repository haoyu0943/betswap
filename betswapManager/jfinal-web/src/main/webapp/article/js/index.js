$(document).ready(function () {

  // 导航
  $("#footer a.nav-a").on("click", function () {
    $(this).addClass("cur").siblings().removeClass("cur");
    isValue = $(this);
  });


  var counter = 0;
  // 每页展示6个
  var num = 6;
  var pageStart = 0;

  // dropload
  $('.nContent').dropload({
      scrollArea : window,
      loadDownFn : function(me){
          // alert(counter);
          $.ajax({
              type: 'POST',
              url:ctxPath+"/system/qryPageArticle",
              data: {
                  "start":pageStart,
                  "length":num,
              },
              // dataType: 'json',
              success: function(data){
                  var _html="";
                  var result = data.data;
                  if(result.length>0){
                      counter++;
                      pageStart = counter*num;
                      for(var i=0;i<result.length;i++){
                          _html +=
                              '<li class="item"><a href="'+ctxPath+"/system/detailArticle?id="+result[i].id+'">' +
                              '<div class="wz"><h3 class="title">' +
                              result[i].title +
                              "</h3>" ;
                          if(result[i].subtitle!=null&&result[i].subtitle!=''){
                              _html +='<p class="desc">' +
                                  result[i].subtitle +
                                  '</p>' ;
                          }else{
                              if(result[i].type_flag==1){
                                  _html +='<p class="desc">' +
                                      result[i].web_url +
                                      '</p>' ;
                              }else{
                                  if(result[i].content.length>40){
                                      _html +='<p class="desc">' +
                                          result[i].content.substr(0,40) +
                                          '...</p>' ;
                                  }
                                  else{
                                      _html +='<p class="desc">' +
                                          result[i].content +
                                          '</p>' ;
                                  }
                              }
                          }
                          _html +='<p class="time">' +
                              result[i].create_time +
                              '</p></div><div class="img"><img src="' +
                          serviceUrl+result[i].cover +
                              '" alt=""> </div>' +
                              "</a></li>";
                      }
                      if(result.length<num){
                          // 锁定
                          me.lock();
                          // 无数据
                          me.noData();
                      }
                  }else{
                      // 锁定
                      me.lock();
                      // 无数据
                      me.noData();
                  }
                  $('#newsList').append(_html);
                  // 每次数据加载完，必须重置
                  me.resetload();
              },
              error: function(xhr, type){
                  alert('Ajax error!');
                  // 即使加载出错，也得重置
                  me.resetload();
              }
          });
      }
  });
});
