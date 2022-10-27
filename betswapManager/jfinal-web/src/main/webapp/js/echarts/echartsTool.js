var myChart;
var domMain = document.getElementById('graphContainer');
var needRefresh = false;

var enVersion = location.hash.indexOf('-en') != -1;
var hash = location.hash.replace('-en','');
hash =  'macarons';
hash += enVersion ? '-en' : '';

var curTheme;
function requireCallback (ec, defaultTheme) {
    curTheme = themeSelector ? defaultTheme : {};
    echarts = ec;
    refresh();
    window.onresize = myChart.resize;
}

var themeSelector = $('#theme-select');
if (themeSelector) {
    themeSelector.html(
        '<option selected="true" name="macarons">macarons</option>'
        + '<option name="infographic">infographic</option>'
        + '<option name="shine">shine</option>'
        + '<option name="dark">dark</option>'
        + '<option name="blue">blue</option>'
        + '<option name="green">green</option>'
        + '<option name="red">red</option>'
        + '<option name="gray">gray</option>'
        + '<option name="helianthus">helianthus</option>'
        + '<option name="roma">roma</option>'
        + '<option name="mint">mint</option>'
        + '<option name="macarons2">macarons2</option>'
        + '<option name="sakura">sakura</option>'
        + '<option name="default">default</option>'
    );
    $(themeSelector).on('change', function(){
        selectChange($(this).val());
    });
    function selectChange(value){
        var theme = value;
        myChart.showLoading();
        $(themeSelector).val(theme);
        if (theme != 'default') {
            window.location.hash = value + (enVersion ? '-en' : '');
            require(['js/dist/theme/' + theme], function(tarTheme){
                curTheme = tarTheme;
                setTimeout(refreshTheme, 500);
            })
        }
        else {
            window.location.hash = enVersion ? '-en' : '';
            curTheme = {};
            setTimeout(refreshTheme, 500);
        }
    }
    function refreshTheme(){
        myChart.hideLoading();
        myChart.setTheme(curTheme);
    }
    if ($(themeSelector).val(hash.replace('-en', '')).val() != hash.replace('-en', '')) {
        $(themeSelector).val('macarons');
        hash = 'macarons' + enVersion ? '-en' : '';
        window.location.hash = hash;
    }
}

function refresh(isBtnRefresh){
    myChart = echarts.init(domMain, curTheme);
    myChart.on('click',function(params){
    	if(params.name.indexOf('C')==0){
    		var cbzName = params.name.split("_")[0];
    		var url="queryCBZGJDetailAction.action?tbXwCbyp.cbajbh="+cbzName;
    		$.ajax({
    			url:url,
    	   		type:"POST", 
    	   		dataType:"json",
    	   		error: function(data) {
    	            jAlert("串并组已经合并");
    	        },
    			success:function(data){
    				if(data.cbzgjDetail=="error"){
    					jAlert("串并组已经被合并");
    				}else if(data.cbzgjDetail=="ok"){
    					var url="queryCBYPDetailAction.action?tbXwCbyp.cbajbh="+cbzName;
    					window.open(url);
    				}
    			}
    		});
    	}else if(params.name.indexOf('A')==0){
    		var ajName = params.name.split("_")[0];
    		//var url="toAjzb.action?asjbh="+ajName;
    		var url="ajxxDetailAction.action?asjbh="+ajName;
    		window.open(url);
    	}
    });
    window.onresize = myChart.resize;
    
    myChart.setOption(option, true)
   
}

var echarts;
var developMode = false;
if (developMode) {
}
else {
	
    // for echarts online home page
    require.config({
        paths: {
            echarts: './js/dist'
        }
    });
    launchExample();
}

var isExampleLaunched;
function launchExample() {
    if (isExampleLaunched) {
        return;
    }

    isExampleLaunched = 1;
    require(
        [
            'echarts',
            'echarts/chart/tree',
            'echarts/theme/macarons'
        ],
        requireCallback
    );
}

