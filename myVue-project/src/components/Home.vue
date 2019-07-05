<template>
  <el-container class="home-container">
    <div style="width:100%">
      <header-menu></header-menu>

      <el-main style="padding: 32px 32px 16px 32px;">
        <el-row :gutter="40" class="panel-group">
          <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel" @click="dialogTableVisible = true">
              <div class="card-panel-icon-wrapper icon-people">
                <svg-icon icon-class="equip" class-name="card-panel-icon"/>
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">当前设备ID</div>
                <div class="card-panel-num">{{equipId}}</div>
              </div>
            </div>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel">
              <div class="card-panel-icon-wrapper icon-message">
                <svg-icon icon-class="tem" class-name="card-panel-icon"/>
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">当前温度</div>

                <div class="card-panel-num">{{temperature}}°C</div>
              </div>
            </div>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel">
              <div class="card-panel-icon-wrapper icon-money">
                <svg-icon icon-class="hum" class-name="card-panel-icon"/>
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">当前湿度</div>

                <div class="card-panel-num">{{humidity}}%</div>
              </div>
            </div>
          </el-col>
          <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
            <div class="card-panel">
              <div class="card-panel-icon-wrapper icon-shopping">
                <svg-icon icon-class="status" class-name="card-panel-icon"/>
              </div>
              <div class="card-panel-description">
                <div class="card-panel-text">设备状态</div>
                <div class="card-panel-num">{{status}}</div>
              </div>
            </div>
          </el-col>
        </el-row>
<air-control style="margin-bottom:32px" :table-data="tableData"></air-control>
        <el-row :gutter="32" >
          <el-col :xs="24" :sm="24" :lg="12">
            <div class="chart-wrapper">
              <tem-chart :chart-data="datas"/>
            </div>
          </el-col>
          <el-col :xs="24" :sm="24" :lg="12">
            <div class="chart-wrapper">
              <hum-chart :chart-data="humDatas"/>
            </div>
          </el-col>
        </el-row>
        <h4 style="margin-top:0px">自动监控日志</h4>
        <el-table :data="tableData" style="width: 100%;" max-height="250" :default-sort = "{prop: 'time', order: 'descending'}">
      <el-table-column prop="time" sortable label="发生时间" width="180"></el-table-column>
      <el-table-column prop="tem" label="当前温度" width="180"></el-table-column>
      <el-table-column prop="settingtem" label="空调温度"></el-table-column>
      <el-table-column prop="status" label="状态"></el-table-column>
      
      <el-table-column prop="operation" label="操作"></el-table-column>
    </el-table>
      </el-main>
    </div>
    

    <el-dialog title="设备列表" :visible.sync="dialogTableVisible">
      <equip-table v-on:selectEquip="changEquip"></equip-table>
    </el-dialog>

    
  </el-container>
</template>

<script>
import axios from "axios";
import CountTo from "vue-count-to";
import TemChart from "./TemChart";
import HumChart from "./HumChart";
import EquipTable from "./EquipTable";
import AirControl from "./AirCondition";
import Menu from "./Menu";
var timeTip = "  ";
var firstTip = 0;

var socket;
var ms = new Array(); //后端传来的消息
if (typeof WebSocket == "undefined") {
  console.log("您的浏览器不支持WebSocket");
} else {
  console.log("您的浏览器支持WebSocket");
  //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
  //等同于socket = new WebSocket("ws://localhost:8081/checkcentersys/websocket/20");
  socket = new WebSocket("ws://localhost:8880/websocket/aaa");
  //打开事件
  socket.onopen = function() {
    console.log("Socket 已打开");
    //socket.send("这是来自客户端的消息" + location.href + new Date());
  };
  //获得消息事件
  
}

export default {
  components: {
    CountTo,
    TemChart,
    HumChart,
    EquipTable,
    AirControl,
    "header-menu": Menu
  },
  data() {
    return {
      tableData: [],
      equipCount: 100,
      temperature: 29.1,
      humidity: 59.3,
      status: "良好",
      equipId: "",
      datas: {},
      humDatas: {},
      temChartData: {
        time: [],
        data: []
      },
      humChartData: {
        time: [],
        data: []
      },

      dialogTableVisible: false,
      gridData: [{}],
      currentRow: null
    };
  },
  methods: {
    showData() {
      var _this = this;

      axios
        .get("/api/data/show")
        .then(response => {
          if (firstTip == 0) {
            this.show(0, response);
          } else {
            for (var i = 0; i < response.data.length; i++) {
              if (this.equipId === response.data[i].deviceid) {
                this.show(i, response);
              }
            }
          }

          //console.log(response.data);
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    changEquip(val) {
      var _this = this;
      _this.equipId = val.id;
      _this.dialogTableVisible = false;
      console.log(val);
    },
    show(i, response) {
      var _this = this;
      _this.equipCount = response.data.length;
      _this.temperature = response.data[i].tem;
      _this.humidity = response.data[i].hum;
      _this.equipId = response.data[i].deviceid;
      if (response.data[i].status == "0000000000000000") {
        _this.status = "良好";
      } else {
        _this.status = "异常";
      }

      if (timeTip != response.data[i].time) {
        var time1 = response.data[i].time.split(" ");
        _this.temChartData.time.push(time1[1]);
        _this.temChartData.data.push(response.data[0].tem);
        _this.datas = _this.temChartData;

        _this.humChartData.time.push(time1[1]);
        _this.humChartData.data.push(response.data[0].hum);
        _this.humDatas = _this.humChartData;
        timeTip = response.data[i].time;
      }
    },
    getLog(){
            axios
        .get("/api/mlog/find")
        .then(response => {
          console.log(response.data)
          this.tableData = response.data;
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  },

  mounted() {
    var _this = this
    this.timer = setInterval(this.showData, 1000);
    this.getLog();
    socket.onmessage = function(msg) {
    console.log(msg.data);
    var ms1 = msg.data.split(",");
    var tmp = {
      time: ms1[0],
      tem: ms1[1],
      settingtem: ms1[2],
      status: "已超过限定温度",
      operation: "已自动调低1度。"
    };
    console.log("adsffafffdd")
    _this.tableData.push(tmp);
    //发现消息进入    开始处理前端触发逻辑
  };
  },
  beforeDestroy() {
    clearInterval(this.timer);
  }
};
</script>
<style lang="scss" scoped>
.home-container {
  height: 100%;
  position: absolute;
  background-color: rgb(240, 242, 245);
  top: 0px;
  left: 0px;
  width: 100%;
  position: relative;
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #32c089;
      }

      .icon-money {
        background: #5bbb23;
      }

      .icon-shopping {
        background: #34bfa3;
      }
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
