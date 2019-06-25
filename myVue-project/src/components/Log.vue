<template>
  <el-container class="setting-container">
    <div style="width:100%">
      <header-menu></header-menu>
      <el-main style="padding: 32px 200px 16px;">
        <el-dropdown size="medium" split-button type="primary" @command="handleCommand">
          选择范围
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="week">过去一周</el-dropdown-item>
            <el-dropdown-item command="month">按月查看</el-dropdown-item>
            <el-dropdown-item command="year">按年一年</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <el-row style="background:#fff;padding:16px 16px 0;margin-top:32px;">
          <history-chart :chart-data="historyDatas"/>
        </el-row>
      </el-main>
    </div>
  </el-container>
</template>
<script>
import Menu from "./Menu";
import HistoryChart from "./HistoryChart";
import axios from "axios";
export default {
  components: {
    "header-menu": Menu,
    "history-chart": HistoryChart
  },
  data() {
    return {
      historyData: {
        tem: [],
        hum: [],
        date: []
      },
      historyDatas: {}
    };
  },
  methods: {
    handleCommand(command) {
      this.getWeek(command);
    },
    getWeek(types) {
      axios
        .get("/api/data/statistics", {
          params: {
            type: types
          }
        })
        .then(response => {
          console.log(response);
          this.historyData = {
            tem: [],
            hum: [],
            date: []
          };
          for (var i = 0; i < response.data.length; i++) {
            this.historyData.tem.push(response.data[i].tem);
            this.historyData.hum.push(response.data[i].hum);
            this.historyData.date.push(response.data[i].date);
          }
          this.historyDatas = this.historyData;
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  },
  mounted() {
    this.getWeek("week");
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