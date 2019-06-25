<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";

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
    };
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
          if(val.data.length > 9){
              val.data.shift()
              val.time.shift()
          }
        this.setOptions(val);
        console.log(val)
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
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
        console.log(time)
        console.log(data)
      this.chart.setOption({
        xAxis: {
          type: 'category',
          data: time,
          boundaryGap: false,
          
        },
        title : {
            show:true,//显示策略，默认值true,可选为：true（显示） | false（隐藏）
            text: '湿度(%)',//主标题文本，'\n'指定换行
            
        },
        grid: {
          left: 10,
          right: 10,
          bottom: 20,
          top: 30,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [5, 10]
        },
        yAxis: { 
        boundaryGap: [0, '50%'],
        type: 'value'
        },
        series: [
        {
          name: 'actual',
          smooth: true,
          type: 'line',
          itemStyle: {
            normal: {
              color: '#3888fa',
              lineStyle: {
                color: '#3888fa',
                width: 2
              },
              areaStyle: {
                color: '#f3f8ff'
              },
              stack: 'a',
            }
          },
          data: data,
          animationDuration: 2800,
          animationEasing: 'quadraticOut'
        }]
      })
    }
  }
};
</script>