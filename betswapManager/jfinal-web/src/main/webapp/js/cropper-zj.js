//弹出框水平垂直居中
(window.onresize = function () {
    var win_height = $(window).height();
    var win_width = $(window).width();
    if (win_width <= 768){
        $(".tailoring-content").css({
            "top": (win_height - $(".tailoring-content").outerHeight())/2,
            "left": 0
        });
    }else{
        $(".tailoring-content").css({
            // "top": (win_height - $(".tailoring-content").outerHeight())/2,
            "top": 30,//临时处理 窗口在最下面的问题
            "left": (win_width - $(".tailoring-content").outerWidth())/2
        });
    }
})();

//弹出图片裁剪框
$("#replaceImg").on("click",function () {
    //alert("1111");
    $(".tailoring-container").toggle();
});
//图像上传
function selectImg(file,x,y) {
    if (!file.files || !file.files[0]){
        return;
    }
    var reader = new FileReader();
    reader.onload = function (evt) {
        var replaceSrc = evt.target.result;
        //更换cropper的图片
        $('#tailoringImg').cropper('replace', replaceSrc,false);//默认false，适应高度，不失真
        // $('#tailoringImg').cropper('destroy').cropper({ aspectRatio: 0.2 / 1 })
    }
    reader.readAsDataURL(file.files[0]);
    if(x!=undefined&&y!=undefined){
        $('#tailoringImg').cropper('destroy').cropper({ aspectRatio: x / y })
    }
}
//cropper图片裁剪
$('#tailoringImg').cropper({
    aspectRatio: 0.8/1,//默认比例
    preview: '.previewImg',//预览视图
    guides: false,  //裁剪框的虚线(九宫格)
    autoCropArea: 0.8,  //0-1之间的数值，定义自动剪裁区域的大小，默认0.8
    movable: false, //是否允许移动图片
    dragCrop: true,  //是否允许移除当前的剪裁框，并通过拖动来新建一个剪裁框区域
    movable: true,  //是否允许移动剪裁框
    resizable: true,  //是否允许改变裁剪框的大小
    zoomable: true,  //是否允许缩放图片大小
    mouseWheelZoom: true,  //是否允许通过鼠标滚轮来缩放图片
    touchDragZoom: true,  //是否允许通过触摸移动来缩放图片
    rotatable: true,  //是否允许旋转图片
    crop: function(e) {
        // 输出结果数据裁剪图像。
    }
});
//旋转
$(".cropper-rotate-btn").on("click",function () {
    $('#tailoringImg').cropper("rotate", 45);
});
//复位
$(".cropper-reset-btn").on("click",function () {
    $('#tailoringImg').cropper("reset");
});
//换向
var flagX = true;
$(".cropper-scaleX-btn").on("click",function () {
    if(flagX){
        $('#tailoringImg').cropper("scaleX", -1);
        flagX = false;
    }else{
        $('#tailoringImg').cropper("scaleX", 1);
        flagX = true;
    }
    flagX != flagX;
});

//裁剪后的处理
$("#sureCut").on("click",function () {
    if ($("#tailoringImg").attr("src") == null ){
        return false;
    }else{
        var cas = $('#tailoringImg').cropper('getCroppedCanvas');//获取被裁剪后的canvas
        var base64url = cas.toDataURL('image/png'); //转换为base64地址形式
        if($("#replaceImg").hasClass("updateUser")){
            var  avatar=dataURLtoFile(base64url,"file.png");
            // var  avatar=dataURLtoBlob(base64url);
            var formData=new FormData();
            formData.append("avatar",avatar);
            formData.append("userToken",$.tmCookie.getCookie("userToken"));
            $.ajax({
                type:"post",
                url:serviceUrl+"/user/info/updateUserInfo",
                data:formData,
                cache: false, //上传文件不需要缓存
                processData: false, // 告诉jQuery不要去处理发送的数据
                contentType: false, // 告诉jQuery不要去设置Content-Type请求头
                success:function(res){
                    if(res.code==1000){
                        $("#finalImg").prop("src",base64url);//显示为图片的形式
                        $.tmCookie.setCookie("userPhoto",res.data.userAvatar,cookieTime,null);
                        chechlogin();
                        //关闭裁剪框
                        closeTailor();
                    }
                    else{
                        yyAlert({title:"提示",style:"e",content:"头像修改失败"});
                    }
                },
                error:function(res){
                    alert(res);

                }
            });

        }else{
            $("#finalImg").prop("src",base64url);//显示为图片的形式
            //关闭裁剪框
            closeTailor();
        }
    }
});
//关闭裁剪框
function closeTailor() {
    $(".tailoring-container").toggle();
}
function dataURLtoFile(dataurl, filename) {
    var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, {
        type: mime
    });
}