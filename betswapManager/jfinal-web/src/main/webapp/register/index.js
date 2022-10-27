var elementById = document.getElementById("world_text_button");
var s=10;
var type=0;
elementById.onclick=function (){
    if(type==1){return;}
    type=1;
    var ds=setInterval(function (){
        s--;
        if(s<=0){
            clearInterval(ds);
            type=0;
            s=60;
            elementById.value="Get Code";
            return;
        }
        elementById.value=s;
    },1000)
}
