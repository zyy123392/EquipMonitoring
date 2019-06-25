<template>
  <el-header style="text-align: right; font-size: 12px">
    <el-row type="flex" justify="start" style="font-size:20px;">
      <el-col :span="3" style="text-align:left">
        <i class="el-icon-camera-solid" style="margin-top:20px;margin-right:5px;"></i>
        温湿度监控平台
      </el-col>
      <el-col :span="19">
        <el-menu
          :default-active="activeIndex2"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          background-color="#409eff"
          text-color="#fff"
          active-text-color="#ffd04b"
        >
          <el-menu-item index="Home">首页</el-menu-item>
          <el-menu-item index="Log">历史记录</el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="2" style="text-align: right; font-size: 12px" @click="setting">
        <i class="el-icon-setting" style="margin-right: 10px"></i><el-link href="/#/setting">设置</el-link>
      </el-col>
    </el-row>
  </el-header>
</template>
<script>
export default {
  data() {
    return {
      activeIndex2: this.$route.name
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      var _this = this;
      if (key == "Home") {
        var path = _this.$route.query.redirect;
        _this.$router.replace({
          path: "/home"
        });
      } else if (key == "Log") {
        var path = _this.$route.query.redirect;
        _this.$router.replace({
          path: "/log"
        });
      }
      console.log(key, keyPath);
    },
    setting() {
      var path = _this.$route.query.redirect;
      _this.$router.replace({
        path: "/setting"
      });
    }
  }
};
</script>

<style>
.el-header {
  background-color: #409eff;
  color: rgb(255, 255, 255);
  line-height: 60px;
}
.chart-wrapper {
  background: #fff;
  padding: 16px 16px 0;
  margin-bottom: 32px;
}
</style>

