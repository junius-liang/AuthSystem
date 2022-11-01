<template>
 <div>
   <h3 class="nameList">角色列表</h3>
    <!--   搜索框-->
    <div class="searchForm">
      <el-form :inline="true" :model="data" class="demo-form-inline">
        <el-form-item label="角色名称">
          <el-input v-model="data.roleName" placeholder="输入角色名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
          <el-button type="primary" @click="addBtn">添加</el-button>
          <el-button type="primary" @click="resetSubmit">重置</el-button>
          <el-button type="primary" @click="del">批量删除</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--   列表-->
   <el-table :data="list" border style="width: 100%" @selection-change="selectionHandler">
     <el-table-column type="selection" width="60" />
     <el-table-column type="index" label="编号" width="180" />
     <el-table-column prop="roleName" label="角色名称" width="180" />
     <el-table-column prop="roleCode" label="角色编码" width="180" />
     <el-table-column prop="description" label="角色描述" width="180" />
     <el-table-column prop="createTime" label="创建时间" width="180" />
     <el-table-column label="操作">
       <!--scope代表整个表格，scope.row代表表格的每一行数据-->
       <template #default="scope">
       <el-button type="primary" :icon="Edit" circle style="margin-right: 50px" @click="updateBtn(scope.row)"/>
       <el-button type="danger" :icon="Delete" circle  @click="delBtn(scope.row.id)"/>
       </template>
     </el-table-column>
   </el-table>
   <div class="myPagation">
     <el-pagination background layout="prev, pager, next" :total="total"
                    :page-size="limit" :current-page="page"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"/>
   </div>
    <!-- 添加弹出框  -->
   <el-dialog v-model="addDialogVisible" title="添加角色" width="30%" draggable>
     <el-form :model="addVo" label-width="120px">
       <el-form-item label="角色名称">
         <el-input v-model="addVo.roleName" />
       </el-form-item>
       <el-form-item label="角色编码">
         <el-input v-model="addVo.roleCode" />
       </el-form-item>
       <el-form-item label="角色描述">
         <el-input v-model="addVo.description" />
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
   <!-- 修改弹出框  -->
   <el-dialog v-model="updateDialogVisible" title="修改角色" width="30%" draggable>
     <el-form :model="updateVo" label-width="120px">
       <el-form-item label="角色名称">
         <el-input v-model="updateVo.roleName" />
       </el-form-item>
       <el-form-item label="角色编码">
         <el-input v-model="updateVo.roleCode" />
       </el-form-item>
       <el-form-item label="角色描述">
         <el-input v-model="updateVo.description" />
       </el-form-item>
     </el-form>
     <template #footer>
      <span class="dialog-footer">
        <el-button @click="updateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updateHandler">
          更新
        </el-button>
      </span>
     </template>
   </el-dialog>
 </div>
</template>

<script setup lang="ts">
  import {onBeforeMount, reactive,ref} from "vue";
  import {SysRoleQueryVo, SysRoleAddVo, SysRoleUpdateVo} from '../model/SysRole/SysRoleQueryVo'
  import {getPageList,deleteRole,addRole,updateRole,batchDelRole} from "../api/sysRole/SysRole";
  import {sysRoleStore} from "../store/SysRoleStore/index"
  import {storeToRefs} from 'pinia'
  import {Edit,Delete} from '@element-plus/icons-vue'
  import { ElMessage, ElMessageBox } from 'element-plus'

  const sysRoleStore1 = sysRoleStore();
  let {curRowId} = storeToRefs(sysRoleStore1)
  let {page} = storeToRefs(sysRoleStore1);
  let {limit} = storeToRefs(sysRoleStore1);
  let {roleName} = storeToRefs(sysRoleStore1);
  let {list} = storeToRefs(sysRoleStore1);
  let {total} = storeToRefs((sysRoleStore1));
  let data:SysRoleQueryVo = reactive<any>({
    "limit": limit,
    "page": page,
    "roleName": roleName
  });
  let addDialogVisible= ref(false);
  let addVo:SysRoleAddVo = reactive<any>({
    roleName: '',
    roleCode: '',
    description: '',
  });
  let updateDialogVisible = ref(false);
  let updateVo:SysRoleUpdateVo = reactive<any>({
    id:'',
    roleName: '',
    roleCode: '',
    description: '',
  })
  let idArr = ref<any>([]);

  const clearAddVo = () => {
    addVo.roleName = ''
    addVo.roleCode = ''
    addVo.description = ''
  }
  const clearUpdateVo = ()=>{
    updateVo.id = '';
    updateVo.roleName = '';
    updateVo.roleCode = '';
    updateVo.description = '';
  }
  const clearData = ()=>{
    data.limit=3;
    data.page=1;
    data.roleName=''
  }
  const pagationChange = (val:Number) => {
    data.page = val;
    getPageList(data)
  }
  const handleSizeChange = (val: number) => {
    pagationChange(val)
  }
  const handleCurrentChange = (val: number) => {
    pagationChange(val)
  }
  const onSubmit = () => {
    getPageList(data)
  }
  const resetSubmit = () => {
    data.limit = 3;
    data.page = 1;
    data.roleName = '';
    getPageList(data)
  }
  const deleteOneRole = (a:Number) => {
    deleteRole(a);
    setTimeout(()=>{
      getPageList(data);
    },100)
  }
  //消息弹出
  const delBtn = (a:Number) => {
    ElMessageBox.confirm(
      '该操作将永久删除该角色，是否继续？',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
      ).then(()=>{
      deleteOneRole(a)
      }).then(() => {
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
      });
  }
  //添加角色
  const addBtn = () =>{
    addDialogVisible.value = true
  }
  const addRoleHandler = () => {
    addDialogVisible.value = false
    addRole(addVo)
    clearAddVo()
    clearData()
    setTimeout(()=>{
      getPageList(data)
    },100)
  }
  //修改角色
  const updateBtn = (data:any)=>{
    let id = data.id
    updateVo.roleName = data.roleName
    updateVo.description = data.description
    updateVo.roleCode = data.roleCode
    updateDialogVisible.value = true
    curRowId.value=id as string
  }
  const updateHandler = (id:string) => {
    updateVo.id = curRowId.value;
    updateRole(updateVo)
    clearUpdateVo()
    clearData()
    setTimeout(()=>{
      getPageList(data)
    },100)
    updateDialogVisible.value = false
  }
  const selectionHandler = (selection:any[]) => {
    idArr.value.splice(0,idArr.value.length)
    for (let i = 0; i < selection.length; i++) {
      idArr.value.push(selection[i].id)
    }
  }
  const del = () => {
    if (idArr.value.length>0){
      batchDelRole(idArr.value).then(res=>{
        if(res.data.code===200){
          idArr.value = [];
          clearData();
          getPageList(data)
        }
      })
    }
  }

  onBeforeMount(function (){
    getPageList(data);
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
</style>
