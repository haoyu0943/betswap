<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="../common/taglib.jsp"%>
<%@ include file="../common/resource.jsp"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Chart</title>
  </head>
  <body>

    <div id="main" style="width: 100%;height:100%;"></div>

  </body>
  <script src="<%=ctxPath%>/js/echarts/echarts.min.js"></script>
  <script>
    <%--var moneyType="${moneyType}";--%>
    const data0 = getHistory();
    function getHistory(){
      const categoryData = [];
      const values = [];
      $.ajax({
        type:"post",
        url:"<%=ctxPath%>/chart/getTBTpriceHistory",
        data:{
          // "moneyType":moneyType
        },
        success:function(res) {
          // for (var i = res.length-1; i>0; i--) {
          //   console.log(res[i]);
          //   categoryData.push(res[i][0]);
          //   values.push([res[i][1],res[i-1][1]].concat(fakeLH(res[i][1],res[i-1][1])));
          // }
          for (var i = res.length-1; i>=0; i--) {
            categoryData.push(datetimeFormat(res[i].t * 1000));
            values.push([res[i].o,res[i].c,res[i].l,res[i].h]);
          }
          drawcall();
        }
      });
      return {
        categoryData: categoryData,
        values: values
      };
    }



    var myChart = echarts.init(document.getElementById('main'),'dark');

    const upColor = '#ec0000';
    const upBorderColor = '#8A0000';
    const downColor = '#00da3c';
    const downBorderColor = '#008F28';

    function drawcall(){
      console.log(data0);
      var option = {
        title: {
          text: "BET",
          left: 0
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        grid: {
          left: '10%',
          right: '10%',
          bottom: '15%'
        },
        xAxis: {
          type: 'category',
          data: data0.categoryData,
          boundaryGap: false,
          axisLine: { onZero: false },
          splitLine: { show: false },
          min: 'dataMin',
          max: 'dataMax'
        },
        yAxis: {
          scale: true,
          splitArea: {
            show: true
          }
        },
        dataZoom: [
          {
            type: 'inside',
            start: 50,
            end: 100
          },
          {
            show: true,
            type: 'slider',
            top: '90%',
            start: 50,
            end: 100
          }
        ],
        series: [
          {
            type: 'candlestick',
            data: data0.values,
            itemStyle: {
              color: upColor,
              color0: downColor,
              borderColor: upBorderColor,
              borderColor0: downBorderColor
            },
          }
        ]
      };
      myChart.setOption(option);
    }


    function datetimeFormat(longTypeDate){
      var dateTypeDate = "";
      var date = new Date();
      date.setTime(longTypeDate);
      dateTypeDate += date.getFullYear(); //年
      dateTypeDate += "-" + getMonth(date); //月
      dateTypeDate += "-" + getDay(date); //日
      dateTypeDate += " " + getHours(date); //时
      return dateTypeDate;
    }


    function getMonth(date){
      var month = "";
      month = date.getMonth() + 1; //getMonth()得到的月份是0-11
      if(month<10){
        month = "0" + month;
      }
      return month;
    }
    //返回01-30的日期
    function getDay(date){
      var day = "";
      day = date.getDate();
      if(day<10){
        day = "0" + day;
      }
      return day;
    }
    //小时
    function getHours(date){
      var hours = "";
      hours = date.getHours();
      if(hours<10){
        hours = "0" + hours;
      }
      return hours;
    }



  </script>
</html>
