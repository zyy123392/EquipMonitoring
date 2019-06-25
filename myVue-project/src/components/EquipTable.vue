<template>
  <el-table
    ref="singleTable"
    :data="tableData"
    highlight-current-row
    @current-change="handleCurrentChange"
    style="width: 100%"
  >
    <el-table-column type="index" width="50"></el-table-column>
    <el-table-column property="id" label="设备ID" width="250"></el-table-column>
    <el-table-column property="ip" label="设备IP"></el-table-column>
  </el-table>
</template>

<script>
import axios from "axios";
export default {
  
  data() {
    return {
        tableData: [{}],
      currentRow: null
    };
  },

  methods: {
    showEquip() {
      var _this = this;

      axios
        .get("/api/equip/show")
        .then(response => {
            this.tableData = response.data
          console.log(response.data)
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    handleCurrentChange(val) {
      this.currentRow = val;
      this.$emit("selectEquip",val)
      console.log(val);
    },
    
  },
  created(){
        this.showEquip()
    }
};
</script>