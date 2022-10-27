
function showqbLoading(id){
	var html = $("#qbgzryLoading").html();
	$("#"+id).css("position","relative");
	$("#"+id).find("tbody").html(html);
	$("#"+id).find("tbody").css("width","100%");
	$("#"+id).find("#qbgzryLoadingImg").css({
		display:"block",
		position:"absolute",
		left:"43%",
		top:"46px"
	}).find("img").css("width","50px");
};

//隐藏loading
function hideqbLoading(id){
	$("#"+id).find("#qbgzryLoadingImg").css({
		display:"none"
	});
}

//隐藏西安市多少条和搜索
function hideMenuAndSearch(tableid){
	$(".dataTables_length").remove();
	$("#"+tableid+"_filter").remove();
}
