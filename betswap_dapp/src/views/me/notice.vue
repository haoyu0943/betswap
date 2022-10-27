<template>
  <div>
    <div class="wrap">
      <div class="header">
        <img src="../../assets/img/betwap/fanhui.png" alt="fanhui.png" v-on:click="$router.back()">
        <p>公告</p>
      </div>
      <div class="content">
          <div class="xinxi_list">
            <p>一条未读消息</p>
          </div>
          <div class="xinxi_list">
            <p>一条未读消息</p>
          </div>
          <div class="xinxi_list">
            <p>一条未读消息</p>
          </div>
          <div class="xinxi_list xinxi_list_a">
            <p>该消息已读</p>
          </div>
          <div class="xinxi_list xinxi_list_a">
            <p>该消息已读</p>
          </div>
          <div class="xinxi_list xinxi_list_a">
            <p>该消息已读</p>
          </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {}
  },
  mounted() {

  },
  created() {
    this.init();
  },
  methods: {
    init(){

    }
  },
}
</script>

<style lang="less" scoped>
.header{
  padding: 30px 30px 20px 20px;
  background-color: #0A0A0A;
  display: flex;
  align-items: center;
  img{
    width: 18px;
  }
  p{
    font-size: 19px;
    font-family: FZLanTingHei-DB-GBK;
    font-weight: 400;
    color: #FFFFFF;
    margin-left: 10px;
  }
  margin-bottom: 11px;
}
.content{
  // width: 346px;
  // min-height: 80vh;
  background: #2A2A2A;
  border-radius: 4px;
  margin: 0 10px;
  .xinxi_list{
    padding: 14px 24px;
    font-size: 12px;
    font-family: PingFang SC;
    font-weight: 400;
    color: #FFFFFF;
    border-bottom: 1px solid #202020;
  }
  .xinxi_list_a{
    opacity: 0.5;
  }
}
</style>