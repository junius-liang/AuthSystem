<template>
  <div>
    <h3 class="nameList">用户列表</h3>
    <!--   搜索框-->
    <div class="searchForm">
      <el-form :inline="true" :model="sysUserQuery" class="demo-form-inline">
        <el-form-item label="角色名称">
          <el-input v-model="sysUserQuery.keyword" placeholder="输入用户名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="userQuery">查询</el-button>
          <el-button type="primary" @click="addUser">添加</el-button>
          <el-button type="primary" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" border style="width: 100%">
     <el-table-column type="index" label="编号" width="180" />
     <el-table-column prop="username" label="用户名" width="180" />
     <el-table-column label="用户状态">
       <template #default="scope">
         <el-switch v-model="scope.row.show" @change="changeSwith(scope.row.show,scope.row.id)" />
       </template>
     </el-table-column>
     <el-table-column prop="name" label="姓名" width="180" />
     <el-table-column prop="phone" label="手机" width="180" />
     <el-table-column prop="createTime" label="创建时间" width="180" />
     <el-table-column label="操作">
       <!--scope代表整个表格，scope.row代表表格的每一行数据-->
       <template #default="scope">
       <el-button type="primary" :icon="Edit" circle style="margin-right: 30px" @click="updateUser(scope.row.id)"/>
       <el-button type="danger" :icon="Delete" style="margin-right: 30px" circle @click="delBtn(scope.row.id)"/>
         <el-button type="warning" :icon="Star" circle  @click="assginBtn(scope.row)"/>
       </template>
     </el-table-column>
   </el-table>

    <div class="myPagation">
      <el-pagination background layout="prev, pager, next" :total="total"
                     :page-size="limit" :current-page="page"
                     @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"/>
    </div>

    <el-dialog v-model="addDialogVisible" title="添加角色" width="30%" draggable>
      <el-form :model="addVo" label-width="120px">
        <el-form-item label="用户名称">
          <el-input v-model="addVo.name" />
        </el-form-item>
        <el-form-item label="用户昵称">
          <el-input v-model="addVo.username" />
        </el-form-item>
        <el-form-item label="用户密码">
          <el-input v-model="addVo.password" />
        </el-form-item>
        <el-form-item label="用户电话">
          <el-input v-model="addVo.phone" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addRoleHandler">
          添加
        </el-button>
      </span>
      </template>
    </el-dialog>

    <el-dialog v-model="updateDialogVisible" title="添加角色" width="30%" draggable>
      <el-form :model="updateVo" label-width="120px">
        <el-form-item label="用户名称">
          <el-input v-model="updateVo.name" />
        </el-form-item>
        <el-form-item label="用户昵称">
          <el-input v-model="updateVo.username" />
        </el-form-item>
        <el-form-item label="用户密码">
          <el-input v-model="updateVo.password" />
        </el-form-item>
        <el-form-item label="用户电话">
          <el-input v-model="updateVo.phone" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateUserDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updateRoleHandler">
          修改
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>

  <el-dialog
      v-model="assginDialogVisible"
      title="角色分配"
      width="30%"
  >
    <el-form :model="assginForm" label-width="120px">
      <el-form-item label="用户名">
        <el-input v-model="assginForm.name" disabled />
      </el-form-item>
      <el-form-item label="角色">
        <el-checkbox-group v-model="assginForm.roleIdList" v-for="list in assginForm.roleIdList" key="list.id">
          <el-checkbox :label="list.roleName" name="type" @change="assginFormLabel($event,list.id)" v-if="list.roleName!=undefined"/>
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="assginDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="doAssign">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { onMounted, reactive } from '@vue/runtime-core';
import { storeToRefs } from 'pinia';
import {
  addUserApi,
  changeUserStatusApi,
  delUserApi, doAssignApi,
  finUserByQuery,
  modUserApi,
  toAssign
} from '../api/sysUser/SysUser';
import {SysUserByAdd, SysUserByAddOrUpdate, SysUserByQuery} from '../model/SysUser/SysUserModel';
import { sysUserStore } from '../store/SysUserStore';
import {Edit,Delete,Star} from '@element-plus/icons-vue'
import {ref,computed} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {SysUserRole} from "../model/SysRole/SysUserRole";


const SysUserStore =  sysUserStore();
const {list} = storeToRefs(SysUserStore)
let {keyword} = storeToRefs(SysUserStore)
const {page} = storeToRefs(SysUserStore)
const {limit} = storeToRefs(SysUserStore)
let {total} = storeToRefs((SysUserStore));
let addDialogVisible= ref(false);
let assginDialogVisible= ref(false);
let updateDialogVisible = ref(false)
let userState = ref(false);
const assginForm = reactive({
  id:'',
  name: '',
  roleIdList: [],
  roleIdRegin:[]
})

//条件分页查询对象
let sysUserQuery:SysUserByQuery = reactive<any>({
    keyword: keyword,
    page: page,
    size: limit
})

let addVo = reactive({
  "name": '',
  "phone": '',
  "username": '',
  "password":''
})

let sysUserRole:SysUserRole = {
  "roleIdList": [],
  "userId": ""
};

//查询用户
const userQuery = ()=>{
  finUserByQuery(sysUserQuery)
      .then(data=>{
        console.log(data.data.data)
        if(data.data.code===200){
          SysUserStore.list =  data.data.data.records;
          SysUserStore.total = data.data.data.total;
        }
      })
}



//重置
const resetQuery = ()=>{
  SysUserStore.aaa();
  finUserByQuery(sysUserQuery)
      .then(data=>{
        console.log(data.data.data)
        if(data.data.code===200){
          SysUserStore.list =  data.data.data.records;
          SysUserStore.total = data.data.data.total;
        }
      })
}

//添加用户按钮，用来显示弹出框
const addUser = ()=>{
  addDialogVisible.value = true;
}
const addRoleHandler = () => {
  addUserApi(addVo as SysUserByAdd).then(()=>{
    resetQuery();
  }).then(()=>{
    ElMessage.success('添加成功')
  });
  addDialogVisible.value = false;

}

let updateVo :SysUserByAddOrUpdate = reactive<any>({
  id:'',
  name:'',
  password: '',
  phone: '',
  username:''
})
const updateUser = (a:any)=>{
  SysUserStore.curRowId=a
  updateDialogVisible.value = true;
}
const updateRoleHandler = () => {
  updateVo.id=SysUserStore.curRowId
  modUserApi(updateVo).then(res=>{
    console.log(res)
    resetQuery()
  }).then(()=>{
    ElMessage.success('操作成功')
  })
  updateDialogVisible.value = false;
}

//删除按钮
const delBtn = (a:any) => {
  ElMessageBox.confirm(
      '该操作将永久删除该角色，是否继续？',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(()=>{
    delUserApi(a).then(res=>{
      console.log(res)
      resetQuery()
    });
  }).then(() => {

    ElMessage({
      type: 'success',
      message: '删除成功',
    })
  });
}


const pagationChange = (val:Number) => {
  sysUserQuery.page = val as any;
  resetQuery()
}
const handleSizeChange = (val: number) => {
  pagationChange(val)
}
const handleCurrentChange = (val: number) => {
  pagationChange(val)
}
const test1 = (a:any) => {
  console.log(a)
}

//用户状态开关
const changeSwith = (val:any,b:any) => {
  changeUserStatusApi(val,b).then(res=>{
    ElMessage.success('操作成功')
  })
}
let newList = reactive({
  list:[]
})
//分配角色
const assginBtn = (val:any)=>{
  console.log(val)
  assginForm.name = val.username
  assginDialogVisible.value = true
  assginForm.id = val.id
  toAssign(val.id).then(res=>{
    let data = res.data.data;
    assginForm.roleIdList = data.all
    assginForm.roleIdRegin = data.one
    newList.list = data.new
    let fir = assginForm.roleIdRegin;
    fir.forEach((item)=>{
      sysUserRole.roleIdList.push(item)
    })
    console.log(newList.list)
  })
}

const doAssign = () => {
  doAssignApi(sysUserRole).then(res=>{
    console.log(res)
    sysUserRole.roleIdList = []
    sysUserRole.userId=''
    assginDialogVisible.value = false
  })
}

const assginFormLabel = (a:any,b:any) => {
  if (b!=undefined){
    sysUserRole.userId = assginForm.id;
    if (a){
      sysUserRole.roleIdList.forEach((item,index,arr)=>{
        if (item===b){
          arr.splice(index,1)
        }
      })
      sysUserRole.roleIdList.push(b)
    }else {
      sysUserRole.roleIdList.forEach((item,index,arr)=>{
        if (item===b){
          arr.splice(index,1)
        }
      })
    }
    console.log(sysUserRole)
  }
}


onMounted(function(){
  finUserByQuery(sysUserQuery)
  .then(data=>{
    if(data.data.code===200){
      SysUserStore.list =  data.data.data.records;
      SysUserStore.total = data.data.data.total;
    }
  })
})
</script>

<style scoped>
.myPagation{
 float: right;
  margin-right: 20px;
}
.nameList{
  float: left;
  margin-left: 5px;
}
.searchForm{
  float: right;
  margin-top: 20px;
}
.el-checkbox{
  display: flex;
  margin-right: 10px;
}
</style>
