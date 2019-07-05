<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";
import axios from "axios";
import setting from "./Setting"
export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart"
    },
    width: {
      type: String,
      default: "100%"
    },
    height: {
      type: String,
      default: "350px"
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      chart: null,
      temthreshold : 30
    };
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        if (val.data.length > 9) {
          val.data.shift();
          val.time.shift();
        }
        this.setOptions(val);
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
this.getSetting()
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");
      this.setOptions(this.chartData);
    },
    setOptions({ time, data } = {}) {
      
      var _this = this;
      this.chart.setOption({
        xAxis: {
          type: "category",
          data: time,
          boundaryGap: false,
          axisLine: {
            lineStyle: {
              color: "#e42b0a"
            }
          }
        },
        title: {
          show: true, //显示策略，默认值true,可选为：true（显示） | false（隐藏）
          text: "温度(°C)", //主标题文本，'\n'指定换行
          textStyle: {
            //主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
            color: "#e42b0a"
          }
        },
        grid: {
          left: 10,
          right: 10,
          bottom: 20,
          top: 30,
          containLabel: true
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross"
          },
          padding: [5, 10]
        },
        yAxis: {
          boundaryGap: [0, "50%"],
          type: "value",
          axisLine: {
            lineStyle: {
              color: "#e42b0a"
            }
          }
        },
        visualMap: {
                    show: false,
                    pieces: [
                      {
                        gt: 0,
                        lte: _this.temthreshold,          //这儿设置基线上下颜色区分 基线下面为绿色
                        color: '#03d6d6'
                    }, {
                                
                        gt: _this.temthreshold,          //这儿设置基线上下颜色区分 基线上面为红色
                        color: '#e91642'
                    }]
                },

        series: [
          {
            type: "line",
            name: "actual",
            smooth: true,
            
            
            data: data,
            animationDuration: 2800,
            animationEasing: "quadraticOut",
            markLine: {
              data: [{ name: "温度阈值", yAxis: _this.temthreshold }],
              label: {
                normal: {
                  formatter: "温度\n阈值" // 这儿设置安全基线
                }
              }
            }
          }
        ]
      });
    },
getSetting() {
      axios
        .get("/api/setting/getSetting")
        .then(response => {
          console.log(response.data)
          this.temthreshold = response.data.temthreshold
        })
        .catch(function(error) {
          alert(error);
        });
    }
  },
  
};
</script>