<template>
    <div id="home">
      <div class="aside-col">
        <el-menu class="menu" :default-active="$route.path" router :collapse="isCollapse" background-color="#545c64"
                 text-color="#fff"
                 active-text-color="#ffd04b">
          <el-menu-item index="/Home/homepage" @click="changeCollapse">
            <i><img src="../assets/icons/catalog.png" width="15px" height="15px" /></i>
          </el-menu-item>
          <el-menu-item index="/Home/homepage">
            <i class="el-icon-menu"></i>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-menu-item index="/Home/userinfo">
            <i class="el-icon-message"></i>
            <span slot="title">个人信息</span>
          </el-menu-item>
          <el-menu-item index="/Home/workexprience">
            <i class="el-icon-edit-outline"></i>
            <span slot="title">工作经验</span>
          </el-menu-item>
          <el-menu-item index="/Home/projectexoerience">
            <i class="el-icon-setting"></i>
            <span slot="title">项目经验</span>
          </el-menu-item>
          <el-menu-item index="/Home/skills">
            <i class="el-icon-edit"></i>
            <span slot="title">职业技能</span>
          </el-menu-item>
          <el-menu-item index="/Home/sellmyself">
            <i class="el-icon-search"></i>
            <span slot="title">扩展信息</span>
          </el-menu-item>
        </el-menu>
      </div>
        <div class="main-col">
          <router-view></router-view>
        </div>
    </div>
</template>

<script>
export default {
    name: "Home",
  data () {
      return{
        isCollapse:false,
      }
  },
  methods:{
      changeCollapse () {
        var mainCol = document.getElementsByClassName('main-col')[0];
        this.isCollapse = !this.isCollapse
        if(this.isCollapse) {
          mainCol.style.left = '90px';
          mainCol.style.width='calc(100% - 110px)'
        }
        else {
          mainCol.style.left = '220px';
          mainCol.style.width = 'calc(100% - 240px)'
        }
      }
  }
}
</script>

<style scoped lang="less">
  .menu{
    height: 100vh;
  }
.el-menu-item{
  text-align: left;
  border-bottom: 1px solid gray;
}
.aside-col{
  display: inline-block;
  float: left;
  width: 200px;
}
.main-col{
  position: fixed;
  left: 220px;
  display: inline-block;
  width: calc(100% - 240px);
}
</style>
