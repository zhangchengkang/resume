<template>
    <div id="projectexoerience">
      <el-row>
        <div class="page-title">项目经验</div>
      </el-row>
      <el-row class="breadcrumb">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/Home/homaepage' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>项目经验</el-breadcrumb-item>
        </el-breadcrumb>
      </el-row>
      <div class="table-container">
        <el-row>
          <div class="form-title"><i class="el-icon-edit"></i>项目经验</div>
        </el-row>
        <div class="form-container">
          <el-row>
            <div class="btn-container">
              <el-button type="warning" size="small" @click="handleAdd">增加<i class="el-icon-plus"></i></el-button>
            </div>
          </el-row>
          <br/>
          <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" border stripe
                    @selection-change="selectionChange">
            <el-table-column align="center" type="selection" width="50"></el-table-column>
            <el-table-column align="center" prop="name" label="项目名">
              <template slot-scope="scope">
                <el-input v-if="showEdit[scope.$index]" v-model="scope.row.name" size="small" placeholder="请输入内容"></el-input>
                <span v-if="!showEdit[scope.$index]">{{scope.row.name}}</span>
              </template>
            </el-table-column>
             <el-table-column align="center" prop="description" label="项目描述">
              <template slot-scope="scope">
                <el-input v-if="showEdit[scope.$index]" v-model="scope.row.description" size="small" placeholder="请输入内容"></el-input>
                <span v-if="!showEdit[scope.$index]">{{scope.row.description}}</span>
              </template>
            </el-table-column>
             <el-table-column align="center" prop="time" label="项目时间段">
              <template slot-scope="scope">
                <el-input v-if="showEdit[scope.$index]" v-model="scope.row.time" size="small" placeholder="请输入内容"></el-input>
                <span v-if="!showEdit[scope.$index]">{{scope.row.time}}</span>
              </template>
            </el-table-column>
            <el-table-column align="center" label="操作" width="150%">
              <template slot-scope="scope">
                <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"><span  class="edit-btn">编辑</span></el-button>
                <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="block">
            <el-pagination layout="prev,pager,next" :total="10" @current-change="currentChange"></el-pagination>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import common from "../js/common";
export default {
  name: "projectexoerience",
  mixins: [common],
  data() {
    return {
      input: "",
      isPopover: false,
      tableData: [], //渲染数据
      allTableData: [],
      checked: [], //表格行是否勾选
      showEdit: [false]
    };
  },
  created() {
    this.initTableData();
  },
  methods: {
    // 初始化数据
    async initTableData() {
      this.axios
        .post("/projectexperience/findall", {
          userId: this.getCookieValue("userId")
        })
        .then(res => {
          this.tableData = res.data.data;
        });
    },
    currentChange(currentPage) {},
    handleAdd() {
      this.tableData.push({});
    },
    handleSearch() {
      if (this.input == "") this.tableData = this.allTableData;
      else {
        this.tableData = [];
        this.allTableData.forEach(function(dataItem, index) {
          if (dataItem.siteName.indexOf(this.input) != -1) {
            this.tableData.push(this.allTableData[index]);
          }
        }, this);
      }
    },
    handleDelete(index, row) {
      this.axios.get("/projectexperience/delete/" + row.id).then(res => {});
      this.tableData.splice(index, 1);
    },
    handleEdit(index, row) {
      var editBtn = document.getElementsByClassName("edit-btn")[index];
      if (editBtn.innerHTML == "编辑") {
        editBtn.innerHTML = "确定";
        this.$set(this.showEdit, index, true);
      } else {
        editBtn.innerHTML = "编辑";
        this.axios.post("/projectexperience/save", row).then(res => {});
        this.$set(this.showEdit, index, false);
      }
    },
    selectionChange(selection) {
      this.checked = selection;
    }
  },
  mounted() {
    this.tableData = this.allTableData;
    if (document.documentElement.clientWidth < 1490) {
      this.$set(this, "isPopover", true);
    }
    window.onresize = () => {
      var clientWidth = document.documentElement.clientWidth;
      if (clientWidth < 1490) {
        this.$set(this, "isPopover", true);
      } else this.$set(this, "isPopover", false);
    };
  },
  watch: {
    input: function inputChange() {
      if (this.input == "") this.tableData = this.allTableData;
      else {
        this.tableData = [];
        this.allTableData.forEach(function(dataItem, index) {
          if (dataItem.siteName.indexOf(this.input) != -1) {
            this.tableData.push(this.allTableData[index]);
          }
        }, this);
      }
    }
  }
};
</script>

<style scoped lang="less">
.page-title {
  font-size: 32px;
  text-align: left;
  font-weight: bold;
  margin-bottom: 10px;
}
.breadcrumb {
  background-color: white;
  padding: 0.5rem;
  margin-bottom: 25px;
}
.table-container {
  border: 1px solid blue;
}
.form-container {
  padding: 10px;
}
.form-title {
  background-color: blue;
  color: white;
  padding: 0.5rem;
  text-align: left;
  margin-bottom: 5px;
}
.btn-container {
  overflow: hidden;
  .el-button {
    float: left;
  }
}
.search-input {
  width: 300px;
  float: left;
  margin-left: 10px;
  margin-right: 5px;
}
</style>
