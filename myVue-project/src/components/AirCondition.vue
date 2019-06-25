<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <div
        class="grid-content text"
        style="text-align:center;margin-top:25px;font-weight: bold;"
      >空调控制：当前状态：{{status}},当前设定温度：{{tem}}℃</div>
    </el-col>
    <el-col :span="18">
      <el-col :span="6">
        <el-card shadow="hover" class="grid-content" style="text-align:center" @click.native="turnOn">开启空调</el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="grid-content" style="text-align:center" @click.native="turnOff">关闭空调</el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="grid-content" style="text-align:center" @click.native="turnUp">升高温度</el-card>
      </el-col>
      <el-col :span="6">
        <el-card
          shadow="hover"
          class="grid-content"
          style="text-align:center"
          @click.native="turnDown"
        >降低温度</el-card>
      </el-col>
    </el-col>
  </el-row>
</template>
<script>
import axios from "axios";
export default {
    name: "airCondition",
  data() {
    return {
      status: "",
      tem: 0
    };
  },
  methods: {

    turnOn() {
        
      axios
        .get("/api/control/turnOn")
        .then(response => {
          this.getStatus();
          this.$message(response.data);
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    turnOff() {
      axios
        .get("/api/control/turnOff")
        .then(response => {
          this.getStatus();
          this.$message(response.data);
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    turnDown() {
      axios
        .get("/api/control/turnDown")
        .then(response => {
          this.getStatus();
          this.$message(response.data);
        })
        .catch(function(error) {
          alert(error);
        });
    },
    turnUp() {
      axios
        .get("/api/control/turnUp")
        .then(response => {
          this.getStatus();
          this.$message(response.data);
          
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    getStatus(){
      axios
        .get("/api/setting/getStatus")
        .then(response => {
          if(response.data.status === 0){
            this.status = "关闭";
          }else{
            this.status = "开启";
          }
          this.tem = response.data.settingTem;
          console.log(response.data);
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  },
  mounted(){
    this.getStatus();
  }
};
</script>

<style>
.text {
  line-height: 18px;
  color: rgba(0, 0, 0, 0.795);
  font-size: 16px;
  font-family: "微软雅黑";
}
.el-card {
  border-radius: 4px;
}

.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #ffffff;
}
.bg-purple {
  background: #ffffff;
}
.bg-purple-light {
  background: #ffffff;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>