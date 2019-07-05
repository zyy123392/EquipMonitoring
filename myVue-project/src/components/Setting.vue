<template>
  <el-container class="setting-container">
    <div style="width:100%">
      <header-menu></header-menu>
      <el-main style="padding: 32px 200px 16px;">
        <div style="margin-top:50px">
          <span class="demonstration">温度阈值(℃): {{value2}}℃</span>
          <el-slider v-model="value2" :min="16" :max="50">></el-slider>
        </div>
        <div style="margin-top:50px">
          <div>
            <label>监听时间间隔:</label>
          </div>
          <div style="text-align:center;margin-top:40px">
            <el-input-number v-model="timesolt1" @change="handleChange" :min="1"></el-input-number>
            <span>秒</span>
          </div>
        </div>
        <div style="text-align:center;margin-top:140px">
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </el-main>
    </div>
  </el-container>
</template>
<script>
import Menu from "./Menu";
import axios from "axios";

let solt ;

export default {
  solt,
  components: {
    "header-menu": Menu
  },
  data() {
    return {
      value2: 25,
      timesolt1: 1,
      settingData: {
        timeslot: 0,
        temthreshold: 0
      }
    };
  },
  methods: {
    handleChange(value) {
      var _this = this;
      _this.timesolt1 = value;
      console.log(value);
    },

    save() {
      var _this = this;
      _this.settingData.timeslot = _this.timesolt1;
      _this.settingData.temthreshold = _this.value2;
      axios
        .post("/api/setting/update", _this.settingData)
        .then(response => {
          if (response.data.code == 1) {
            this.$message("设置成功！");
          } else {
            this.$message("设置失败");
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    getSetting() {
      axios
        .get("/api/setting/getSetting")
        .then(response => {
          this.value2 = response.data.temthreshold;
          this.timesolt1 = response.data.timeslot;
          solt = this.timesolt1;
        })
        .catch(function(error) {
          alert(error);
        });
    }
  },
  mounted() {
    this.getSetting();
  }
};
</script>

<style>
.setting-container {
  height: 100%;

  background-color: rgb(240, 242, 245);
  top: 0px;
  left: 0px;
  width: 100%;
  position: absolute;
}
</style>

