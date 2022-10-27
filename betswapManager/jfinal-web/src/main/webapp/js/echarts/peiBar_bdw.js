//饼图
function pieChart(id, data1,colors) {
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
			color : colors,
			tooltip : {
				trigger : 'axis',
				textStyle:{fontSize:10}
			},
			series : [ {
				type : 'pie',
				radius : [ '75%', '90%' ],
				center : [ '50%', '50%' ],//控制上下左右的距离
				data : data1,
				itemStyle : {
					normal : {
						label : {
							show : true,
							textStyle : {
								fontSize : 12,
								color : "#fff",
								fontWeight : 'bold',
								fontFamily : "Microsoft YaHei"
							},
							position : 'inner',
							formatter : function(params) {
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
								return params.name + params.value;
								//return (params.percent - 0).toFixed(0) + '%'
							},
						//			                        distance:0.59
						},
						labelLine : {
							show : false
						}
					},
					/*emphasis : {
                            label : {
                                show : true,
                                formatter : "{b}\n{d}%",
                                position : 'center'
                            }
                        }*/
				}

			} ]
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
function barChart(id, xAxisData, yArrData1,yArrData2,legendData,colors) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line',
			'echarts/chart/pie' ], function(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		//var zrColor = require('zrender/tool/color');
		option1 = {
			calculable : false,
			grid : {
				x : 40,
				x2 : 40,
				y : 20,
				y2 : 55,
				show : true,
				borderWidth : 0
			},
			tooltip : {
				trigger : 'axis',
				textStyle:{fontSize:10}
			},
			//					title : {
			//						text : '',
			//						subtext : '',
			//						x:'center',
			//					    y:'top',
			//						textStyle : {
			//							fontSize: 16,
			//							color : "#fffc07"
			//						}
			//					},
			//formatter:function(val){    return val.split("-").join("\n");},
			legend : {
				show:false,
				data : legendData,
				orient:"horizontal",//horizontal vertical
				x:"center",
				y:"top",
				itemGap:2,
				itemHeight:10,
				padding:0,
				textStyle : {
					fontSize: 10,
					color : "#fff",
					fontFamily :"微软雅黑"
				}
			},
			xAxis : [ {
				type : 'category',
				data : xAxisData,
				axisLine:{show:false},
				barCategoryGap : 30,
				splitLine : {
					show : false
				},//去除网格线
				axisLabel : {
					show : true,
					rotate : 45,
					interval : 0,
					textStyle : {
						fontSize : 10,
						color : '#fff',
						fontFamily : "Microsoft YaHei"
					},
//							formatter : function(params){
//	                            var newParamsName = "";
//	                            var paramsNameNumber = params.length;
//	                            var provideNumber = 2;
//	                            var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
//	                            if (paramsNameNumber > provideNumber) {
//	                                for (var p = 0; p < rowNumber; p++) {
//	                                    var tempStr = "";
//	                                    var start = p * provideNumber;
//	                                    var end = start + provideNumber;
//	                                    if (p == rowNumber - 1) {
//	                                        tempStr = params.substring(start, paramsNameNumber);
//	                                    } else {
//	                                        tempStr = params.substring(start, end) + "\n";
//	                                    }
//	                                    newParamsName += tempStr;
//	                                }
//
//	                            } else {
//	                                newParamsName = params;
//	                            }
//	                            return newParamsName
//	                        }
				}
			} ],
			yAxis : [ {
				show : false,
				type : 'value',
				boundaryGap : [ 0, 0.01 ],
				axisLine:{show:false},
				splitLine : {
					show : false
				},//去除网格线
				axisLabel : {
					show : true,
					rotate : 0,
					interval : 0,
					textStyle : {
						color : '#fff',
						fontFamily : "Microsoft YaHei"
					}
				}
			} ],
			series : [ {
				name : legendData[0],
				type : 'bar',
				data : yArrData1,
				itemStyle : {
					normal : {
						label : {
							show : true
						},
						color : colors[0]
					}
				}
			},
			{
				name : legendData[1],
				type : 'bar',
				data : yArrData2,
				itemStyle : {
					normal : {
						label : {
							show : true
						},
						color : colors[1]
					}
				}
			}]
		};
		// 为echarts对象加载数据 
		myChart.setOption(option1);
		myChart.hideLoading({
			text : '正在努力的读取数据中...'
		});
	});
}

function barHxChart(id, yArr, xArr1,xArr2,legendData) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line',
			'echarts/chart/pie' ], function(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		//var zrColor = require('zrender/tool/color');
		option = {
			calculable : false,
			grid : {
				x : 60,
				x2 : 20,
				y : 20,
				y2 : 50,
				show : false,
				borderWidth : 0
			},
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        },
		        textStyle:{fontSize:10}
		    },
		    color:['#00f5fe','#fff'],
		    legend: {
		        data:legendData,
		        textStyle : {
					fontSize : 10,
					color : '#fff',
					fontFamily : "Microsoft YaHei"
				}
		    },
		    toolbox: {
		        show : false
		    },
		    calculable : true,
		    xAxis : [
		        {
		        	show:false,
		            type : 'value',
		            axisLine:{show:false},
		            splitLine : {
						show : false
					},//去除网格线
					axisLabel:{
						rotate : 0,
						interval : 0,
						textStyle : {
							fontSize : 10,
							color : '#fff',
							fontFamily : "Microsoft YaHei"
						}
					}
		        }
		    ],
		    yAxis : [
		        {
		            type : 'category',
		            data : yArr,
		            axisLine:{show:false},
		            interval :0,
		            splitLine : {
						show : false
					},//去除网格线
					itemStyle : {
						normal : {
							label : {
								show : true,
								textStyle : {
									fontSize : 10,
									color : "#fff",
									fontWeight : 'bold',
									fontFamily : "Microsoft YaHei"
								}
							}
						}
					},
					axisLabel:{
						interval :0,
						textStyle : {
							fontSize : 10,
							color : '#fff',
							fontFamily : "Microsoft YaHei"
						}
					}
		        }
		    ],
		    series : [
		        {
		            name:legendData[0],
		            type:'bar',
		            stack: '总量',
		            itemStyle : { normal: {
			            	/*color: function(params) {
	                            // build a color map as your need.
	                            var colorList = [
	                              '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
	                               '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
	                               '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
	                            ];
	                            return colorList[params.dataIndex]
	                        },*/
		            		label : 
		            			{
		            				show: true, 
		            				position: 'insideRight',
		            				textStyle : {
										fontSize : 10,
										color : "#fff",
										fontWeight : 'bold',
										fontFamily : "Microsoft YaHei"
									}
		            			}
		            	}
		            },
		            data:xArr1
		        },
		        {
		            name:legendData[1],
		            type:'bar',
		            stack: '总量',
		            itemStyle : { 
		            		normal: {
		            				label : {
		            						show: true, 
		            						position: 'right',
				            				textStyle : {
												fontSize : 10,
												color : "#00f5fe",
												fontWeight : 'bold',
												fontFamily : "Microsoft YaHei"
											}
		            				}
		            			}
		            		},
		            data:xArr2
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


function ypbgLineChart(id, xArr, yArr1,yArr2,yArr3,yArr4,legendData,colors) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line',
			'echarts/chart/pie' ], function(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		//var zrColor = require('zrender/tool/color');
		option = {
			    tooltip : {
			        trigger: 'axis',
			        textStyle:{fontSize:10}
			    },
			    color : colors,
			    legend: {
			      data:legendData,
			      textStyle : {
						fontSize : 10,
						color : '#fff',
						fontFamily : "Microsoft YaHei"
					}
			    },
			    calculable : false,
				grid : {
					x : 60,
					x2 : 20,
					y : 20,
					y2 : 50,
					show : false,
					borderWidth : 0
				},
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            axisLine:{
			            		show:true,
			            		lineStyle:{
			            			type:"solid",
			            			width:1
			            		}
			            },
			            splitLine : {
							show : false
						},//去除网格线
						axisLabel:{
							rotate : 0,
							interval : 0,
							textStyle : {
								fontSize : 10,
								color : '#fff',
								fontFamily : "Microsoft YaHei"
							}
						},
			            data : xArr
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            axisLine:{
			            		show:true,
			            		lineStyle:{
			            			type:"solid",
			            			width:1
			            		}
			            },
			            splitLine : {
							show : false
						},//去除网格线
						axisLabel:{
							rotate : 0,
							interval : 0,
							textStyle : {
								fontSize : 10,
								color : '#fff',
								fontFamily : "Microsoft YaHei"
							}
						}
			        }
			    ],
			    series : [
			        {
			            name:legendData[0],
			            type:'line',
			            stack: '总量',
			            data:yArr1
			        },
			        {
			            name:legendData[1],
			            type:'line',
			            stack: '总量',
			            data:yArr2
			        },
			        {
			            name:legendData[2],
			            type:'line',
			            stack: '总量',
			            data:yArr3
			        },
			        {
			            name:legendData[3],
			            type:'line',
			            stack: '总量',
			            data:yArr4
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

function ypbgLineChart2(id, xArr, yArr1,yArr2,yArr3,yArr4,legendData,colors) {
	require.config({
		paths : {
			echarts : ctxPath + '/js/echarts'
		}
	});
	// 使用
	require([ 'echarts', 'echarts/chart/bar', 'echarts/chart/line',
			'echarts/chart/pie' ], function(ec) {
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById(id));
		myChart.showLoading({
			text : '正在努力的读取数据中...'
		});
		//var zrColor = require('zrender/tool/color');
		option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    color : colors,
		    	legend: {
			      data:legendData,
			      textStyle : {
						fontSize : 10,
						color : '#fff',
						fontFamily : "Microsoft YaHei"
				 }
			    },
			    calculable : false,
				grid : {
					x : 60,
					x2 : 20,
					y : 20,
					y2 : 50,
					show : false,
					borderWidth : 0
				},
			    xAxis : [
			        {
			            /*type : 'category',
			            boundaryGap : false,
			            axisLine:{show:true},*/
			            splitLine : {
							show : false
						},//去除网格线
						/*axisLabel:{
							rotate : 0,
							interval : 0,
							textStyle : {
								fontSize : 10,
								color : '#fff',
								fontFamily : "Microsoft YaHei"
							}
						},*/
			            data : xArr
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            axisLine:{show:true},
			            splitLine : {
							show : false
						},//去除网格线
						/*axisLabel:{
							rotate : 0,
							interval : 0,
							textStyle : {
								fontSize : 10,
								color : '#fff',
								fontFamily : "Microsoft YaHei"
							}
						}*/
			        }
			    ],
			    series : [
			        {
			            name:legendData[0],
			            type:'line',
			            //stack: '总量',
			            data:yArr1
			        },
			        {
			            name:legendData[1],
			            type:'line',
			            //stack: '总量',
			            data:yArr2
			        },
			        {
			            name:legendData[2],
			            type:'line',
			            //stack: '总量',
			            data:yArr3
			        },
			        {
			            name:legendData[3],
			            type:'line',
			            //stack: '总量',
			            data:yArr4
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

//K线
function  myKchart(id, xArr, yArr1,yArr2,yArr3,yArr4,legendData,colors){
	/*基于准备好的dom，初始化echarts实例*/
    var myChart = echarts.init(document.getElementById(id));
    // 数据意义：开盘(open)，收盘(close)，最低(lowest)，最高(highest)
    option = {
        title: {    //标题
        	show:false,
            text: '上证指数',
            left: 0
        },
        tooltip: {  //提示框
            trigger: 'axis',    //触发类型：坐标轴触发
            textStyle:{fontSize:10},
            axisPointer: {  //坐标轴指示器配置项
                type: 'cross'   //指示器类型，十字准星
            }
        },
        color:colors,
        legend: {   //图例控件，点击图例控制哪些系列不现实
            data: legendData,
            textStyle : {
				fontSize : 10,
				color : '#fff',
				fontFamily : "Microsoft YaHei"
            }
        },
        calculable : false,
		grid : {
			x : 60,
			x2 : 20,
			y : 20,
			y2 : 50,
			show : false,
			borderWidth : 0
		},
        xAxis: {
            type: 'category',   //坐标轴类型，类目轴
            data: xArr,
            //scale: true,  //只在数字轴中有效
            boundaryGap : false,    //刻度作为分割线，标签和数据点会在两个刻度上
//            axisLine: {onZero: true},
            axisLabel:{
            	onZero: true,
//				rotate : 0,
//				interval : 0,
				textStyle : {
					fontSize : 10,
					color : '#fff',
					fontFamily : "Microsoft YaHei"
				}
			},
            splitLine: {show: false},   //是否显示坐标轴轴线
            //splitNumber: 20,    //坐标轴的分割段数，预估值，在类目轴中无效
            min: 'dataMin', //特殊值，数轴上的最小值作为最小刻度
            max: 'dataMax'  //特殊值，数轴上的最大值作为最大刻度
        },
        yAxis: {
            scale: true,    //坐标刻度不强制包含零刻度
//            axisLine: {onZero: true},
            axisLabel:{
            	onZero: true,
//				rotate : 0,
//				interval : 0,
				textStyle : {
					fontSize : 10,
					color : '#fff',
					fontFamily : "Microsoft YaHei"
				}
			},
            splitLine: {show: false},   //是否显示坐标轴轴线
            splitArea: {
                show: false  //显示分割区域
            }
        },
        dataZoom: {     //用于区域缩放
                        show : true,
                        realtime : true,
//                        orient: 'vertical',   // 'horizontal'
                        //x: 0,
//                        y: 0,
//                        width: 400,
                        height: 20,
                        position: 'bottom',
                        backgroundColor: 'rgba(4,80,253,0.5)',
                        //dataBackgroundColor: 'rgba(138,43,226,0.5)',
                        //fillerColor: 'rgba(38,143,26,0.6)',
                        //handleColor: 'rgba(128,43,16,0.8)',
                        //xAxisIndex:[],
                        //yAxisIndex:[],
                        start : 0,
                        end : 100,
            /*{
                filterMode:'filter',    //当前数据窗口外的数据被过滤掉来达到数据窗口缩放的效果  默认值filter
                type: 'inside', //内置型数据区域缩放组件
                start: 0,      //数据窗口范围的起始百分比
                end: 100        //数据窗口范围的结束百分比
            },
            {
                show: true,
                type: 'slider', //滑动条型数据区域缩放组件
                y: '80%',
                start: 0,
                end: 100
            }*/
        },
        series: [   //图表类型 '本级审核','上报','上级审核通过','上级采用'

            {
                name: legendData[0],
                type: 'line',
                data: yArr1,
//                smooth: true,
                lineStyle: {
                    normal: {opacity: 1}
                }
            },
            {
                name: legendData[1],
                type: 'line',
                data: yArr2,
//                smooth: true,
                lineStyle: {    //标线的样式
                    normal: {opacity: 1}
                }
            },
            {
                name: legendData[2],
                type: 'line',
                data: yArr3,
//                smooth: true,
                lineStyle: {
                    normal: {opacity: 1}
                }
            },
            {
                name: legendData[3],
                type: 'line',
                data: yArr4,
//                smooth: true,
                lineStyle: {
                    normal: {opacity: 1}
                }
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表
    myChart.setOption(option);
}



//K线
function  myKchart2(id, xArr, yArr1,yArr2,yArr3,legendData,colors){
	/*基于准备好的dom，初始化echarts实例*/
    var myChart = echarts.init(document.getElementById(id));
    // 数据意义：开盘(open)，收盘(close)，最低(lowest)，最高(highest)
    option = {
        title: {    //标题
        	show:false,
            text: '上证指数',
            left: 0
        },
        tooltip: {  //提示框
            trigger: 'axis',    //触发类型：坐标轴触发
            textStyle:{fontSize:10},
            axisPointer: {  //坐标轴指示器配置项
                type: 'cross'   //指示器类型，十字准星
            }
        },
        color:colors,
        legend: {   //图例控件，点击图例控制哪些系列不现实
            data: legendData,
            textStyle : {
				fontSize : 10,
				color : '#fff',
				fontFamily : "Microsoft YaHei"
            }
        },
        calculable : false,
		grid : {
			x : 60,
			x2 : 20,
			y : 20,
			y2 : 50,
			show : false,
			borderWidth : 0
		},
        xAxis: {
            type: 'category',   //坐标轴类型，类目轴
            data: xArr,
            //scale: true,  //只在数字轴中有效
            boundaryGap : false,    //刻度作为分割线，标签和数据点会在两个刻度上
//            axisLine: {onZero: true},
            axisLabel:{
            	onZero: true,
//				rotate : 0,
//				interval : 0,
				textStyle : {
					fontSize : 10,
					color : '#fff',
					fontFamily : "Microsoft YaHei"
				}
			},
            splitLine: {show: false},   //是否显示坐标轴轴线
            //splitNumber: 20,    //坐标轴的分割段数，预估值，在类目轴中无效
            min: 'dataMin', //特殊值，数轴上的最小值作为最小刻度
            max: 'dataMax'  //特殊值，数轴上的最大值作为最大刻度
        },
        yAxis: {
            scale: true,    //坐标刻度不强制包含零刻度
//            axisLine: {onZero: true},
            axisLabel:{
            	onZero: true,
//				rotate : 0,
//				interval : 0,
				textStyle : {
					fontSize : 10,
					color : '#fff',
					fontFamily : "Microsoft YaHei"
				}
			},
            splitLine: {show: false},   //是否显示坐标轴轴线
            splitArea: {
                show: false  //显示分割区域
            }
        },
        dataZoom: {     //用于区域缩放
                        show : true,
                        realtime : true,
//                        orient: 'vertical',   // 'horizontal'
                        //x: 0,
//                        y: 0,
//                        width: 400,
                        height: 20,
                        position: 'bottom',
                        backgroundColor: 'rgba(4,80,253,0.5)',
                        //dataBackgroundColor: 'rgba(138,43,226,0.5)',
                        //fillerColor: 'rgba(38,143,26,0.6)',
                        //handleColor: 'rgba(128,43,16,0.8)',
                        //xAxisIndex:[],
                        //yAxisIndex:[],
                        start : 0,
                        end : 100,
            /*{
                filterMode:'filter',    //当前数据窗口外的数据被过滤掉来达到数据窗口缩放的效果  默认值filter
                type: 'inside', //内置型数据区域缩放组件
                start: 0,      //数据窗口范围的起始百分比
                end: 100        //数据窗口范围的结束百分比
            },
            {
                show: true,
                type: 'slider', //滑动条型数据区域缩放组件
                y: '80%',
                start: 0,
                end: 100
            }*/
        },
        series: [   //图表类型 '本级审核','上报','上级审核通过','上级采用'

            {
                name: legendData[0],
                type: 'line',
                data: yArr1,
//                smooth: true,
                lineStyle: {
                    normal: {opacity: 1}
                }
            },
            {
                name: legendData[1],
                type: 'line',
                data: yArr2,
//                smooth: true,
                lineStyle: {    //标线的样式
                    normal: {opacity: 1}
                }
            },
            {
                name: legendData[2],
                type: 'line',
                data: yArr3,
//                smooth: true,
                lineStyle: {
                    normal: {opacity: 1}
                }
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表
    myChart.setOption(option);
}