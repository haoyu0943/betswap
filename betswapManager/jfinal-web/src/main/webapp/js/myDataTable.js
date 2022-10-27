//服务器分页方法
var tabelDataServer = null;
function getTableDataServer(div_id,url,columns,data,ifneedbtn,order){
	if (tabelDataServer == undefined || typeof(tabelDataServer) == "undefined" || tabelDataServer == null) {
	}
	else{
		tabelDataServer.destroy();//每次查询重新创建前，必须销毁
	}
	if(typeof(order) == "undefined"){
		order = 0;
	}
	var btn=[];
	if(ifneedbtn==true){
		btn=[
			{
				'extend': 'excelHtml5',
				//'extend': 'excel',
				'text': '导出excel',//定义导出excel按钮的文字 ,
				'title': '下载查询结果', //导出的excel标题
				'className': 'layui-btn',
				'exportOptions': {
					'modifier': {
						'page': 'all'
					},
					//columns: exportArr, //上面这种是不导出第一列，下面这种方法更好
					//format: {
					//body: function ( data, row, column, node ) {
					//return column === 0 ?row+1:data.replace(/<[^>]+>/g,"");//替换所有html元素
					//}
				}
			}
		]
	}
	//alert("tbl columns="+columns.length);
	var lan = $.tmCookie.getCookie('lan');
	var jsonurl=ctxPath+"/js/datatable/"+lan+".json";
	//alert(jsonurl);
	tabelDataServer = $('#'+div_id).DataTable({
		ajax: {
			type:"post",
			url: url,
			data: data
		},
		"dom": 'Bfrtip',
		"buttons":btn,
		"bFilter": false,    //去掉搜索框方法
		"bLengthChange": false, //改变每页显示数据数量
		"bSort": true,
		"order": [[order, 'desc']],// dt默认是第一列升序排列 这里第一列为序号列，所以设置为不排序，并把默认的排序列设置到后面
		"aoColumns": columns,
		"serverSide": true,
		"processing": true,
		"scrollCollapse": "true",
		//"scrollY": $(window).height()-250+"px",
		//"columns": columns,
		"lengthMenu": [[10, 50], [10, 50]],
		"language": {
			url: jsonurl
		},
		/*
		"oLanguage": {//国际语言转化
			"oAria": {
				"sSortAscending": " - click/return to sort ascending",
				"sSortDescending": " - click/return to sort descending"
			},
			"sLengthMenu": "显示 _MENU_ 记录",
			"sZeroRecords": "对不起，查询不到任何相关数据",
			"sEmptyTable": "未有相关数据",
			"sLoadingRecords": "正在加载数据-请等待...",
			"sProcessing": "<img src='"+ctxPath+"/images/loading.gif'/>",
			"sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录。",
			"sInfoEmpty": "当前显示0到0条，共0条记录",
			"sInfoFiltered": "（数据库中共为 _MAX_ 条记录）",
			"processing": true,
			"sSearch": "模糊查询：",
			"sUrl": "", //多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
			"oPaginate": {
				"sFirst": "首页",
				"sPrevious": " 上一页 ",
				"sNext": " 下一页 ",
				"sLast": " 尾页 "
			}
		},
		 */
	});
}