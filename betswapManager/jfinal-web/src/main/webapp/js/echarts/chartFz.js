function zhtEcharts(id, titData, xArr, serieJsons, xRotate) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line' ], function(
			ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		//var zrColor = require('zrender/tool/color');
		option = {
			title : {
				text : '',
				subtext : ''
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : titData,
				textStyle : {
					color : "#fff"
				},
				borderColor : "#000"
			},
			toolbox : {
				show : false,
				feature : {
					mark : {
						show : false
					},
					dataView : {
						show : false,
						readOnly : true
					},
					magicType : {
						show : false,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : false
					},
					saveAsImage : {
						show : false
					}
				}
			},
			grid : {
				x : 40,
				y : 20,
				x2 : 10,
				y2 : 60,
				borderWidth:0
			},
			calculable : false,
			xAxis : [ {
				type : 'category',
				splitLine : false,
				data : xArr,
				axisLabel : {
					show : true,
					rotate : xRotate,
					interval : 0,
					textStyle : {
						color : '#fff'
					}
				}
			}, ],
			yAxis : [ {
				type : 'value',
				splitLine : false,
				axisLabel : {
					show : true,
					textStyle : {
						color : '#fff'
					}
				}
			} ],
			series : serieJsons
		};
		// 为echarts对象加载数据 
		myChart.setOption(option);
		myChart.hideLoading({
			text : '正在努力的读取数据中...'
		});
	});
}

//shananximap

function shananaxiMap(id, datas, splitArr) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/map' ], function(ec) {
		// --- 地图 ---
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		option = {
			title : {
				text : '陕西10个地市',
				subtext : '',
				textStyle : {
					color : "#fff"
				}
			},
			tooltip : {
				trigger : 'item'
			},
			borderColor : "#fff",
			dataRange : {
				min : 0,
				max : 1600,
				/* color:['#ff8463','#666'],
				text:['高','低'],           // 文本，默认为数值文本
				calculable : false, */
				// 自定义分割方式，支持不等距分割。splitList被指定时，splitNumber将被忽略。  
				splitList : splitArr,
				textStyle : {
					color : "#fff"
				}
			},
			series : [ {
				name : '',
				type : 'map',
				mapType : '陕西',
				selectedMode : 'single',
				itemStyle : {
					normal : {
						label : {
							show : true,
							textStyle : {
								color : "#fff"
							}
						},
						borderColor : "#fff"
					},
					emphasis : {
						label : {
							show : true
						}
					}
				},
				data : datas
			} ]
		}
		myChart.setOption(option, true);
		myChart.hideLoading();
	});
};

function pieChart(id, legendData, datas, nameStr) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		option = {
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : legendData,
				textStyle : {
					color : "#fff",
					fontSize : "8px"
				},
				orient : "horizontal"
			},
			toolbox : {
				show : false,
				feature : {
					mark : {
						show : false
					},
					dataView : {
						show : false,
						readOnly : true
					},
					magicType : {
						show : false,
						type : [ 'pie', 'funnel' ],
						option : {
							funnel : {
								x : '25%',
								width : '50%',
								funnelAlign : 'center',
								max : 1548
							}
						}
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : false,
			series : [ {
				name : nameStr,
				type : 'pie',
				radius : [ '50%', '70%' ],
				center: ['50%', '58%'],
				itemStyle : {
					normal : {
						label : {
							show : true,
							formatter:'{b} : {c} ({d}%)' 
						},
						labelLine : {
							show : true
						}
					},
					emphasis : {
						label : {
							show : true,
							formatter: "{b}\n{c} ({d}%)",
							position : 'center',
							textStyle : {
								fontSize : '20',
								fontWeight : 'bold'
							}
						}
					}
				},
				data : datas
			} ]
		};
		myChart.setOption(option, true);
		myChart.hideLoading({
			text : '正在努力的读取数据中...'
		});
	});
};

function lineChart(id, xArr, yArr) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line' ], function(
			ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		//var zrColor = require('zrender/tool/color');
		option = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [],
				textStyle : {
					color : "#fff"
				}
			},
			grid : {
				x : 40,
				y : 50,
				x2 : 10,
				y2 : 60
			},
			toolbox : {
				show : false,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar', 'stack', 'tiled' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				interval : 0,
				splitLine : false,
				data : xArr,
				axisLabel : {
					show : true,
					rotate : 0,
					interval : 0,
					textStyle : {
						color : '#fff'
					}
				}
			} ],
			yAxis : [ {
				type : 'value',
				splitLine : false,
				axisLabel : {
					show : true,
					interval : 0,
					textStyle : {
						color : '#fff'
					}
				}
			} ],
			series : [ {
				name : '操作数',
				type : 'line',
				smooth : true,
				itemStyle : {
					normal : {
						lineStyle : {
							color : '#666'
						},
						areaStyle : {
							type : 'default',
							color : "#666"
						},
						borderColor : "#000",
						label : {show: true, position: 'top'}
					}
				},
				data : yArr
			} ]
		};

		// 为echarts对象加载数据 
		myChart.setOption(option);
		myChart.hideLoading({
			text : '正在努力的读取数据中...'
		});
	});
}

//横向柱图
function hxLineChart(id,legendData,yArr,xArr1,xArr2) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line' ], function(
			ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		//var zrColor = require('zrender/tool/color');
		option = {
			grid : {
				x : 40,
				x2 : 40,
				y : 40,
				y2 : 40,
				show:true,
		    	borderWidth:0
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : legendData,
				textStyle : {
					color : "#fff"
				}
			},
			toolbox : {
				show : false,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar', 'stack', 'tiled' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			yAxis : [ {
				type : 'category',
				data : yArr,
				splitLine:{show: false},//去除网格线
				axisLabel : {
					show : true,
					rotate : 0,
					interval : 0,
					textStyle : {
						color : '#fff'
					}
				}
			} ],
			xAxis : [  {
	            type : 'value',
	            boundaryGap : [0, 0.01],
	            splitLine:{show: false},//去除网格线
	            axisLabel : {
					show : true,
					rotate : 0,
					interval : 0,
					textStyle : {
						color : '#fff'
					}
				}
	        } ],
			series : [
	                     {
                             name:'终结任务数',
                             type:'bar',
                             data:xArr2,
                             itemStyle:{
                                 normal:{
                                	 label : {show: true}
                                 }
                             }
                         },
                         {
                             name:'发起任务数',
                             type:'bar',
                             data:xArr1,
                            itemStyle:{
                                 normal:{
                                	 label : {show: true},
                                     color:'#4ad2ff'
                                 }
                             }
                         }
                     ]
		};

		// 为echarts对象加载数据 
		myChart.setOption(option);
		myChart.hideLoading({
			text : '正在努力的读取数据中...'
		});
	});
}


//饼图套柱图
function pieLineChart(id,data1) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/pie' ], function(
			ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		//var zrColor = require('zrender/tool/color');
		option1 = {
			    calculable : false,
			    color: ['#018487','#01adb2','#01f2f9','#89fcfe',' #e4ffff'],
			    series : [
			        {
			            type:'pie',
			            radius : ['72%', '82%'],
			            //center: ['50%', '55%'],//控制上下左右的距离
			            data:data1,
			            itemStyle: {  
			                normal: { 
			                    label: {  
			                        show: true,  
			                        textStyle : {
										fontSize: 15,
										color:"white",
										fontWeight : 'bold',
										fontFamily :"Microsoft YaHei"
									},
//			                        position: 'inner',  
			                        formatter : function (params) {
//			                        	var newParamsName = "";
//			                            var paramsNameNumber = params.name.length;
//			                            var provideNumber = 2;
//			                            var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
//			                            if (paramsNameNumber > provideNumber) {
//			                                for (var p = 0; p < rowNumber; p++) {
//			                                    var tempStr = "";
//			                                    var start = p * provideNumber;
//			                                    var end = start + provideNumber;
//			                                    if (p == rowNumber - 1) {
//			                                        tempStr = params.name.substring(start, paramsNameNumber);
//			                                    } else {
//			                                        tempStr = params.name.substring(start, end) + "\n";
//			                                    }
//			                                    newParamsName += tempStr;
//			                                }
//
//			                            } else {
//			                                newParamsName = params.name;
//			                            }
			                            return params.name+"("+params.value+")";
			                            //return (params.percent - 0).toFixed(0) + '%'
			                          },
//			                        distance:0.59
			                    },
			                    labelLine : {
			                        show : true
			                    }
			                } 
//			        	,emphasis : {
//	                            label : {
//	                                show : true
//	                            }
//	                        } 
			            }
			            
			        }
			    ]
			};
		// 为echarts对象加载数据 
		myChart.setOption(option1);
		myChart.hideLoading({
			text : '正在努力的读取数据中...'
		});
		
//		var ecConfig = require('echarts/config');
//		myChart.on(ecConfig.EVENT.HOVER, function (param){
//		   console.log("1");
//		})
		
		
		
	});
}

//饼图套柱图
function pieLineChart2(id,xAxisData,yArrData1,yArrData2,legendData) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line','echarts/chart/pie' ], function(
			ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		//var zrColor = require('zrender/tool/color');
		option1 = {
			    calculable : false,
//			    grid:{
//			    	show:true,
//			    	borderWidth:0
//			    },
			    grid : {
					x : 40,
					x2 : 40,
					show:true,
			    	borderWidth:0
				},
			    tooltip : {
					trigger : 'axis'
				},
//				title : {
//					text : '',
//					subtext : '',
//					x:'center',
//				    y:'top',
//					textStyle : {
//						fontSize: 16,
//						color : "#fffc07"
//					}
//				},
				//formatter:function(val){    return val.split("-").join("\n");},
			    legend : {
					data : legendData,
					textStyle : {
						fontSize: 14,
						color : "#fff",
						fontFamily :"微软雅黑"
					}
				},
			    xAxis : [ {
					type : 'category',
					data :  xAxisData,
					barCategoryGap : 30,
					splitLine:{show: false},//去除网格线
					axisLabel : {
						show : true,
						rotate : 45,
						interval : 0,
						textStyle : {
							fontSize: 14,
							color : '#fff',
							fontFamily :"Microsoft YaHei"
						},
//						formatter : function(params){
//                            var newParamsName = "";
//                            var paramsNameNumber = params.length;
//                            var provideNumber = 2;
//                            var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
//                            if (paramsNameNumber > provideNumber) {
//                                for (var p = 0; p < rowNumber; p++) {
//                                    var tempStr = "";
//                                    var start = p * provideNumber;
//                                    var end = start + provideNumber;
//                                    if (p == rowNumber - 1) {
//                                        tempStr = params.substring(start, paramsNameNumber);
//                                    } else {
//                                        tempStr = params.substring(start, end) + "\n";
//                                    }
//                                    newParamsName += tempStr;
//                                }
//
//                            } else {
//                                newParamsName = params;
//                            }
//                            return newParamsName
//                        }
					}
				} ],
				yAxis : [  {
		            type : 'value',
		            boundaryGap : [0, 0.01],
		            splitLine:{show: false},//去除网格线
		            axisLabel : {
						show : true,
						rotate : 0,
						interval : 0,
						textStyle : {
							color : '#fff',
							fontFamily :"Microsoft YaHei"
						}
					}
		        } ],
			    series : [
			        {
			            name:'已完成数',
			            type:'bar',
			            data:yArrData1,
			            itemStyle:{
                            normal:{
                           	 label : {show: true},
                           	 color : '#01f2f9'
                            }
                        }
			        },
			        {
			            name:'在办数',
			            type:'bar',
			            data:yArrData2,
			            itemStyle:{
                            normal:{
                           	 label : {show: true},
                             color:'#e5fffe'
                            }
                        }
			        }
			    ]
			};


		// 为echarts对象加载数据 
		myChart.setOption(option1);
		myChart.hideLoading({
			text : '正在努力的读取数据中...'
		});
	});
}

//横向柱图3比对
function hxLineChart3(id,legendData,yArr,xArr1,xArr2,xArr3) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line' ], function(
			ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		//var zrColor = require('zrender/tool/color');
		option = {
				grid : {
					x : 40,
					x2 : 40,
					y : 40,
					y2 : 40,
					show:true,
			    	borderWidth:0
				},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : legendData,
				textStyle : {
					color : "#fff"
				}
			},
			toolbox : {
				show : false,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar', 'stack', 'tiled' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,	
			yAxis : [ {
				type : 'category',
				data : yArr,
				splitLine:{show: false},//去除网格线
				axisLabel : {
					show : true,
					rotate : 0,
					interval : 0,
					textStyle : {
						color : '#fff',
					},
					formatter : function(params){
	                      var newParamsName = "";
	                      var paramsNameNumber = params.length;
	                      var provideNumber = 2;
	                      var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
	                      if (paramsNameNumber > provideNumber) {
	                          for (var p = 0; p < rowNumber; p++) {
	                              var tempStr = "";
	                              var start = p * provideNumber;
	                              var end = start + provideNumber;
	                              if (p == rowNumber - 1) {
	                                  tempStr = params.substring(start, paramsNameNumber);
	                              } else {
	                                  tempStr = params.substring(start, end) + "\n";
	                              }
	                              newParamsName += tempStr;
	                          }
	
	                      } else {
	                          newParamsName = params;
	                      }
	                      return newParamsName;
						}
					}
			} ],
			xAxis : [  {
	            type : 'value',
	            boundaryGap : [0, 0.01],
	            splitLine:{show: false},//去除网格线
	            axisLabel : {
					show : true,
					rotate : 0,
					interval : 0,
					textStyle : {
						color : '#fff'
					}
				}
	        } ],
			series : [		
							{
							    name:legendData[2],
							    type:'bar',
							    data:xArr3,
							    itemStyle:{
							        normal:{
							       	 label : {show: true},
							            color:'#ff8834'
							        }
							    }
							},
							{
				                name:legendData[1],
				                type:'bar',
				                data:xArr2,
				                itemStyle:{
				                    normal:{
				                   	 label : {show: true},
				                        color:'#c9c9c9'
				                    }
				                }
				            },
							{
							    name:legendData[0],
							    type:'bar',
							    data:xArr1,
							    itemStyle:{
							        normal:{
							       	 label : {show: true},
							       	 color:'#07c8ff'
							        }
							    }
							    
							}
							
			            
                     ]
		};

		// 为echarts对象加载数据 
		myChart.setOption(option);
		myChart.hideLoading({
			text : '正在努力的读取数据中...'
		});
	});
}