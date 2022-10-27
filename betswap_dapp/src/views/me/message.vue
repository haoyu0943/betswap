<template>
  <div>
    <div class="wrap">
      <div class="header">
        <img
          src="../../assets/img/betwap/fanhui.png"
          alt="fanhui.png"
          v-on:click="$router.back()"
        />
        <p v-html="langdata['grxx']">个人信息</p>
      </div>
      <div class="content">
        <ul class="content_list">
          <li>
            <div class="content_list_right">
              <p v-html="langdata['tx']">头像</p>
            </div>
            <div class="content_list_left">
              <!-- <van-uploader
                ><img
                  class="touxiang"
                  src="../../assets/img/betwap/wallet/USDT.png"
                  alt="BET.png"
              /></van-uploader> -->


              <van-uploader
                class="touxiang"
                v-model="fileList"
                :after-read="afterRead"
                :before-delete="upload_delete"
                accept="image/*"
                max-count="1"
                :deletable="false"
              >
                <img
                  class="touxiang"
                  :src="userData.userAvatar"
                />
              </van-uploader>
              <img src="../../assets/img/betwap/me/you.png" alt="" />
            </div>
          </li>
          <li v-on:click="$router.push('/setname')">
            <div class="content_list_right">
              <p v-html="langdata['mz']">名字</p>
            </div>
            <div class="content_list_left">
              {{userData.userName}} <img src="../../assets/img/betwap/me/you.png" alt="" />
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import {langdata} from "@/utils/lang";

export default {
  data() {
    return {
      lang:localStorage.getItem("lang"),
      langdata:langdata(localStorage.getItem("lang")),
      userData:[],

      list_upload: [], //上传图片
      fileList: [], //上传图片 用于组件展示
    };
  },
  mounted() {},
  created() {
    if(localStorage.getItem("lang")=="ja_JP" || localStorage.getItem("lang")=="ms_MY"){
      this.lang="en_US";
    }
    this.getuser()
  },
  methods: {
    // 获取个人信息
    getuser(){
      this.$api("user/userDetail",{
      },"post").then(res=>{
        this.userData = res.data
        console.log(res.data)
      })
    },
    //图片上传
    afterRead(file, detail) {
      console.log("处理上传", file, detail);
      //file 单个上传为文件   多个上传为数组
      let fileArr = [file].flat();
      let promiseArr = [];

      fileArr.forEach(file => {
        const formData = new FormData();
        formData.append("userToken", localStorage.getItem('userToken'));
        formData.append("avatar", file.file);
        const uploaderConfig = {
          headers: {
            "Content-Type":
              "multipart/form-data;boundary=" + new Date().getTime(),
          },
        };
        let promise_curr =  this.$api("user/info/updateUserInfo", formData, 'post', uploaderConfig).then(res=>{
          // console.log(res);
          return res
        })
        promiseArr.push(promise_curr);
      });
      Promise.all(promiseArr)
        .then(res => {
          console.log("所有文件上传完成", res);
          let srcArr = res.filter(v => v.code == 1000).map(v => v.data.userAvatar);
          this.list_upload = [...this.list_upload, ...srcArr];
        })
        .catch(err => {
          console.log("上传出错", err);
        });
    },

    //删除房源图片
    upload_delete(file, detail) {
      let index = detail.index;
      this.list_upload.splice(index, 1); // 已上传
      this.fileList.splice(index, 1); // 组件展示
    }
  },
};
</script>

<style lang="less" scoped>
.header {
  padding: 30px 30px 20px 20px;
  background-color: #0a0a0a;
  display: flex;
  align-items: center;
  img {
    width: 18px;
  }
  p {
    font-size: 19px;
    font-family: FZLanTingHei-DB-GBK;
    font-weight: 400;
    color: #ffffff;
    margin-left: 10px;
  }
}
.content {
  //margin-top: 40px;
}
.content_list {
  height: 100%;

  li {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 2px solid #0a0a0a;
    padding: 0 30px;
    height: 45px;
    img {
      width: 13px;
      height: 13px;
    }
    .content_list_right {
      display: flex;
      align-items: center;
      //border-bottom: 1px solid #353535;
      img {
        width: 20px;
        height: 20px;
      }

      p {
        font-size: 14px;
        font-family: PingFang SC;
        font-weight: 400;
        color: #ffffff;
        margin-left: 10px;
      }
    }
    .content_list_left {
      display: flex;
      align-items: center;
      font-size: 12px;
      font-family: FZLanTingHei-DB-GBK;
      font-weight: 400;
      color: #ffffff;
      opacity: 0.5;
      img {
        margin-left: 20px;
        opacity: 1;
      }
    }
  }
  li:nth-child(1) {
    border-top: 2px solid #0a0a0a;
    height: 80px;
    .touxiang {
      width: 50px;
      height: 50px;
      object-fit: cover;
      border-radius: 50%;
    }
  }
}
.touxiang {
  width: 50px;
  height: 50px;
  /deep/ .van-uploader__preview-image{
    width: 50px;
    height: 50px;
    object-fit: cover;
      border-radius: 50%;
  }
  /deep/ .van-uploader__preview{
    margin: 0;
  }
}

</style>
