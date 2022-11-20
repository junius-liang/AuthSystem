<template>
  <el-container class="layout-container-demo" style="height: 100%">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu class="dash">
          <div class="dashdiv">
            <el-icon><House /></el-icon>   Dashboard
          </div>
        </el-menu>
        <el-menu>

          <el-sub-menu index="1">
            <template #title>
              <el-icon><message /></el-icon>系统管理
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1" @click="toSysRole">角色管理</el-menu-item>
              <el-menu-item index="1-2" @click="toSysUser">用户管理</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><Edit /></el-icon>菜单管理
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1" @click="toMenusList">菜单列表</el-menu-item>
              <el-menu-item index="2-2">删除角色</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><message /></el-icon>角色管理
            </template>
            <el-menu-item-group>
              <el-menu-item index="3-1">添加角色</el-menu-item>
              <el-menu-item index="3-2">删除角色</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <div class="toolbar">
          <el-dropdown>
            <el-icon style="margin-right: 8px; margin-top: 1px;"
            ><setting
            /></el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="toLogin">登录</el-dropdown-item>
                <el-dropdown-item @click="toRegister">注册</el-dropdown-item>
                <el-dropdown-item>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span>欢迎进入后台管理系统</span>
        </div>
      </el-header>

      <el-main>
        <el-scrollbar>
          <router-view></router-view>
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts" setup>
import { Message, Setting } from '@element-plus/icons-vue'
import router from "../router";
const toLogin = ()=>{
  router.push('/login')
}
const toRegister = () => {
  router.push('/register')
}
const toSysUser = () => {
  router.push("/sysUser")
}
const toSysRole = ()=>{
  router.push('/sysRole')
}
const toMenusList = ()=> {
  router.push('/sysMunusList')
}
</script>

<style scoped>
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}
.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}
.layout-container-demo .el-menu {
  border-right: none;
}
.layout-container-demo .el-main {
  padding: 0;
}
.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
.dash{
  height:56px;
  padding:16px;
}
</style>
