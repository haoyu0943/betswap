//显示饼图(数字是在里面显示的那种）
function showPiechart(divid,colors,namestr,datas,ifshowlabel){
   //alert(divid);
   var btChart = echarts.init(document.getElementById(divid));
   option = {
       tooltip: {
    	 trigger: 'item',
    	 formatter: '{a} <br/>{b} : {c} ({d}%)'
       },
       color:colors,
       series: [
    	  {
    		name: namestr,
    		type: 'pie',
    		radius: '80%',
    		center: ['50%', '50%'],
    		data: datas,
    		itemStyle:{ 
			  normal:{ 
				label:{ 
					show: ifshowlabel, 
					formatter: '{b}:{c}', //显示不下了
					//formatter: '{b}' //显示不下了
					textStyle : {
					  fontWeight : 'normal',
					  fontSize : 16
					},
					position:'inner'
				}, 
				labelLine :{show:false} 
			  }
    		},
    		emphasis: {
    		  itemStyle: {
    			shadowBlur: 10,
    			shadowOffsetX: 0,
    			shadowColor: 'rgba(0, 0, 0, 0.5)'
    		  }
    		}
    	 }
       ]
   };
   btChart.setOption(option);
}
//显示饼图(数字是在外面显示的那种）
function showLabelPiechart(divid,colors,namestr,datas,ifshowlabel){
	   //alert(divid);
	   var btChart = echarts.init(document.getElementById(divid));
	   option = {
	       tooltip: {
	    	 trigger: 'item',
	    	 formatter: '{a} <br/>{b} : {c} ({d}%)'
	       },
	       color:colors,
	       series: [
	    	  {
	    		name: namestr,
	    		type: 'pie',
	    		radius: '60%',
	    		center: ['50%', '50%'],
	    		data: datas,
	    		itemStyle:{ 
				  normal:{ 
					label:{ 
						show: ifshowlabel, 
						formatter: '{b}:{c}', //显示不下了
						//formatter: '{b}' //显示不下了
						textStyle : {
						  fontWeight : 'normal',
						  fontSize : 14
						}
					}, 
					labelLine :{show:false} 
				  }
	    		},
	    		emphasis: {
	    		  itemStyle: {
	    			shadowBlur: 10,
	    			shadowOffsetX: 0,
	    			shadowColor: 'rgba(0, 0, 0, 0.5)'
	    		  }
	    		}
	    	 }
	       ]
	   };
	   btChart.setOption(option);
	}
//显示柱状图
function showPillarchart(divid,colors,names,titledatas,seriesdatas){
  var zzChart = echarts.init(document.getElementById(divid));
  zzoption = {
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {            
	            type: 'shadow'     
	        }
	    },
	    //grid: {
	        //left: '3%',
	        //right: '4%',
	        //bottom: '3%',
	        //containLabel: true
	    //},
	    legend: {
	        data: names,
	        textStyle:{
                fontSize: 18,
                color: '#ffffff'
            },
	    },
	    toolbox: {
	        show: false,
	        feature: {
	            dataView: {show: true, readOnly: false},
	            magicType: {show: true, type: ['line', 'bar']},
	            restore: {show: true},
	            saveAsImage: {show: true}
	        }
	    },
	    /*
	    xAxis: {
	        data: titledatas,
	        axisTick: {
	            show: true
	        },
	        axisLine: {
	            show: false,
	            lineStyle: {
	                color: '#00ffee'
	            }
	        },
	        axisLabel: {
	            interval: 0,
	            rotate: 45
	        },
	    },
	    yAxis: {
	        show: true
	    },
	    //calculable: true,
	    */
	    xAxis: [
	        {
	            type: 'category',
	            data: titledatas,
	            axisLabel:{
  	            	rotate:45,
  	          			textStyle:{
  	                    		color: '#F9CDFF',
  	                    		fontSize:12,
  	                     		fontWeight:'bold'
  	                    }
  	            }
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value',
	            show:true,
	            //minInterval: 1,
	            axisLabel: {
	               formatter: '{value} '
	            }
	        }
	    ],
	    series: seriesdatas
	};
   zzChart.setOption(zzoption);
}

//显示嵌套的饼图，需要内外两次数据
function showNestPiechart(divid,ndatas,wdatas){
	var curChart = echarts.init(document.getElementById(divid));
    option = {
	    tooltip: {
	        trigger: 'item',
	        formatter: '{a} <br/>{b}: {c} ({d}%)'
	    },
	    color:['gray','#00CCFF','white'],
	    series: [
	        {
	            name: '',
	            type: 'pie',
	            //selectedMode: 'single',
	            radius: [0, '50%'],
	            itemStyle:{ 
	  			  normal:{ 
	  				label:{ 
	  					show: true, 
	  					formatter: '{b}:{c}', //显示不下了
	  					//formatter: '{b}' //显示不下了
	  					textStyle : {
	  					  fontWeight : 'normal',
	  					  fontSize : 18
	  					},
	  					position:'inner'
	  				}, 
	  				labelLine :{show:false} 
	  			  }
	      		},
	            data:ndatas
	        },
	        {
	            name: '',
	            type: 'pie',
	            radius: ['60%', '75%'],
	            itemStyle:{ 
					  normal:{ 
						label:{ 
							show: true, 
							formatter: '{b}:{c}',
							textStyle : {
							  fontWeight : 'normal',
							  fontSize : 18
							}
						}, 
						labelLine :{show:false} 
					  }
		    	},
	            data: wdatas
	        }
	    ]
	};
    curChart.setOption(option);
}