<template>
  <div id="top"></div>
  <h3>登录页面</h3>
  <el-form :model="form" label-width="120px">
    <el-form-item label="用户名">
      <el-input v-model="form.userName" />
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="form.pwd" />
    </el-form-item>
    <el-form-item label="确认密码">
      <el-input v-model="form.confirm" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">提交</el-button>
      <el-button @click="unSubmit">取消</el-button>
      <el-button @click="toRegister">注册</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import {login} from '../api/sysUser/Login'
import {SysUserModel} from '../model/SysUser/SysUserModel'
import router from "../router";


const form = reactive({
  userName: '',
  pwd:'',
  confirm:''
})

const onSubmit = () => {
  const sysUserVo:SysUserModel = reactive<SysUserModel>(
      {
        userName:form.userName,
        pwd:form.pwd
      }
  )
  login(sysUserVo) // 发送请求
}

const unSubmit = () => {
  form.userName='';
  form.pwd = '';
  form.confirm = ''
}

const toRegister = ()=>{
  router.push("/register")
}
</script>

<style scoped>
#top{
  margin-top: 20px;
}
</style>
