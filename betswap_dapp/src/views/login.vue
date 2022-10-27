<template>
    <div>
        <div class="logo w1240">
            <img :src="data.logo2" alt="" @click="toPage('/')">
        </div>
        <div class="login_wrap">
            <div class="login w1240">
                <div class="box_wrap">
                    <div class="box_text">
                        <p>登录</p>
                        <span @click="toPage('/register')">免费注册</span>
                    </div>
                    <div class="input_wrap">
                        <div class="input_img">
                            <img src="../assets/img/login1.png" alt="">
                        </div>
                        <div class="input">
                            <input type="text" placeholder="手机号" v-model="from.name">
                        </div>
                    </div>
                    <div class="input_wrap">
                        <div class="input_img">
                            <img src="../assets/img/login3.png" alt="">
                        </div>
                        <div class="input">
                            <input type="password" placeholder="密码" v-model="from.password">
                        </div>
                    </div>
                    <div class="yzm_wrap">
                        <div class="yzm_input">
                            <input type="text" placeholder="不区分大小写" v-model="mycode">
                        </div>
                        <div class="yzm">{{code}}</div>
                        <i class="el-icon-refresh-right" @click="createCode"></i>
                    </div>
                    <div class="box_p"><span  @click="toPage('/forgetpsd')">忘记密码</span></div>
                    <div class="box_bt" @click="submit">登录</div>
                </div>
            </div>
        </div>


        <div class="beian">{{data.ICP}}</div>

    </div>
</template>

<script>
    export default {
        data() {
            return {
                fullPath:'',
                from:{
                    name:'',
                    password:'',
                },
                mycode:'',
                code:'',
                data:[]
                
            }
        },
        created() {
            console.log(this.$route.query.fullPath);
            if(this.$route.query.fullPath){
                this.fullPath = this.$route.query.fullPath
            }
            this.createCode()
            this.getlogo()
        },
        methods: {
            toPage(url,query){
                this.$router.push({
                    path:url,
                    query:query
                })
            },
            getlogo(){
                this.$api("setting").then(res=>{
                    if(res.code==0){
                        this.data = res.data
                    }
                })
            },
            // 本地验证码
            createCode(){
                let code='';
                var codeLength = 4;//验证码的长度
                let  selectChar = new Array('a','b','c','d','e','f','g','h','i','j','k',
                        'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
                        'Q','R','S','T','U','V','W','X','Y','Z',);//所有候选组成验证码的字符                
 
                for(let i=0;i<codeLength;i++)
                {     
                    let charIndex =Math.floor(Math.random()*52);
                    code += selectChar[charIndex];     
                }
                this.code = code;
            },
            submit(){
                if (this.from.name=='') {
                    this.$message.error('请输入手机号');
                    return false;                    
                }
                if (!(/^1[3-9]\d{9}$/.test(this.from.name))) {
                    this.$message.error('请输入正确的手机号');
                    return false;                    
                }
                if (this.from.password=='') {
                    this.$message.error('请输入密码');
                    return false;                    
                }
                if (this.mycode=='') {
                    this.$message.error('请输入验证码');
                    return false;                    
                }
                if (this.mycode.toLowerCase() !== this.code.toLowerCase()) {
                    this.$message.error('验证码错误');
                    return false;                    
                }
                this.$api("user/login",{...this.from}).then((res)=>{
                    let { code, data, message } = res;
                    if(code==0){
                        this.$message.success('登录成功');
                        this.$store.commit("setUserInfo", data);
                        // this.$store.dispatch("vuex_get_user_info");
                        setTimeout(() => {
                            if(this.fullPath){
                                this.$router.replace(this.fullPath);
                            }else{
                                this.$router.replace('/');
                            }                            
                        }, 500);
                    }else{
                        this.$message.error(res.message)
                    }
                })
            }
        },
    }
</script>

<style lang="less" scoped>
.logo{
    margin: auto;
    img{
        height: 122px;
        cursor: pointer;
    }
}
.beian{
    height: 120px;
    text-align: center;
    line-height: 120px;
    font-size: 16px;
    color: #7E7E7E;
}
.login_wrap{
    width: 100%;
    min-height: calc(100vh - 242px);
    background: url('../assets/img/loginbg.png') no-repeat;
    background-size: cover;
    background-position: center;
    display: flex;
    align-items: center;
    justify-content: center;
    .login{
        margin: auto;
        display: flex;
        align-items: center;
        justify-content: flex-end;

    }
}
.box_wrap{
    min-width: 408px;
    padding: 0 25px;
    background: rgba(255, 255, 255, .72);
    .box_text{
        padding: 35px 0 25px 0;
        display: flex;
        align-items: center;
        justify-content: space-between;
        p{
            font-size: 21px;
            color: #434343;
        }
        span{
            font-size: 16px;
            color: #FF5000;
            cursor: pointer;
        }
    }
}

.input_wrap{
    height: 50px;
    display: flex;
    border: 1px solid #C0C0C0;
    background: #fff;
    margin-bottom: 23px;
    .input_img{
        width: 50px;
        height: 100%;
        border-right: 1px solid #C0C0C0;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-shrink: 0;
        img{
            width: 26px;
        }
    }
    .input{
        height: 100%;
        flex: 1;
        margin-left: 10px;
        input{
            width: 100%;
            height: 100%;
            font-size: 16px;
        }
    }
}

.yzm_wrap{
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .yzm_input{
        height: 100%;
        width: 150px;
        background: #fff;
        border: 1px solid #C0C0C0;
        padding: 0 10px;
        input{
            width: 100%;
            height: 100%;
            font-size: 16px;
        }
    }
    .yzm{
        font-size: 22px;
        color: #6C6C6C;
        font-weight: bold;
        letter-spacing: 10px;
    }
    i{
        font-size: 30px;
        color: #008ACD;
        cursor: pointer;
    }
}
.box_p{
    margin: 18px 0;
    text-align: right;
    font-weight: 16px;
    color: #000000;
    span{
        cursor: pointer;
    }
}
.box_bt{
    width: 100%;
    height: 48px;
    background: linear-gradient(90deg, #FF8600 0%, #FF5500 100%);
    line-height: 48px;
    text-align: center;
    font-size: 21px;
    color: #fff;
    cursor: pointer;
    margin-bottom: 55px;
}

</style>