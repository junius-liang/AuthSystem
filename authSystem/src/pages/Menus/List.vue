<template>
  <div>
    <el-table
        :data="tableData"
        style="width: 100%; margin-bottom: 20px;float: left"
        row-key="id"
        border
        :default-expand-all="false"
        :tree-props="{children: 'children'}"
    >
      <el-table-column prop="name" label="菜单名称" sortable width="180" />
      <el-table-column label="图标">
        <template #default="scope">
          <el-button  :icon="scope.row.icon" v-show="scope.row.icon!=null"/>
        </template>
      </el-table-column>
      <el-table-column prop="perms" label="权限标识" width="160"/>
      <el-table-column prop="path" label="路由地址" width="120"/>
      <el-table-column prop="component" label="组件路径" width="160"/>
      <el-table-column prop="sortValue" label="排序" width="60"/>
      <el-table-column label="状态" width="80">
        <template #default="scope">
          <el-switch
              v-model="scope.row.status" disabled="true">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="160"/>
      <el-table-column label="操作" class="clobtns" width="200">
        <template #default="scope">
          <el-button type="success" v-if="scope.row.type !== 2" icon="plus" size="mini" @click="add(scope.row)" title="添加下级节点"/>
          <el-button type="primary" icon="edit" size="mini" @click="edit(scope.row)" title="修改"/>
          <el-button type="danger" icon="delete" size="mini" @click="removeDataById(scope.row.id)" title="删除" :disabled="scope.row.children.length > 0"/>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
        :title="dialogTitle" v-model="dialogVisible" width="40%" style="height: 800px"
    >
      <el-form :model="sysMenu" label-width="120px">
        <el-form-item label="上级部门" v-if="sysMenu.id === ''">
          <el-input v-model="sysMenu.parentName" disabled="true"/>
        </el-form-item>
        <el-form-item label="菜单类型" prop="type">
          <el-radio-group v-model="sysMenu.type" :disabled="typeDisabled">
            <el-radio :label="0" :disabled="type0Disabled">目录</el-radio>
            <el-radio :label="1" :disabled="type1Disabled">菜单</el-radio>
            <el-radio :label="2" :disabled="type2Disabled">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="sysMenu.name"/>
        </el-form-item>
        <el-form-item label="图标" prop="icon" v-if="sysMenu.type !== 2">
          <el-select v-model="sysMenu.icon" clearable>
            <el-option v-for="item in iconList" :key="item.class" :label="item.class" :value="item.class">
            <span style="float: left;">
            <el-button :icon="item.class"></el-button> <!-- 如果动态显示图标，这里添加判断 -->
            </span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="sysMenu.sortValue" controls-position="right" :min="0"/>
        </el-form-item>
        <el-form-item prop="path">
          <span slot="label">
          <el-tooltip content="访问的路由地址，如：`sysUser`" placement="top">
          <i class="el-icon-question"></i>
          </el-tooltip>
          路由地址
           </span>
          <el-input v-model="sysMenu.path" placeholder="请输入路由地址"/>
        </el-form-item>
        <el-form-item prop="component" v-if="sysMenu.type !== 0">
 <span slot="label">
 <el-tooltip content="访问的组件路径，如：`system/user/index`，默认在`views`目录下" placement="top">
 <i class="el-icon-question"></i>
 </el-tooltip>
 组件路径
 </span>
          <el-input v-model="sysMenu.component" placeholder="请输入组件路径"/>
        </el-form-item>
        <el-form-item v-if="sysMenu.type === 2">
          <el-input v-model="sysMenu.perms" placeholder="请输入权限标识" maxlength="100"/>
          <span slot="label">
 <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(hasAuthority('bnt.sysRole.list'))" placement="top">
 <i class="el-icon-question"></i>
 </el-tooltip>
 权限字符
 </span>
        </el-form-item>
        <el-form-item label="状态" prop="type">
          <el-radio-group v-model="sysMenu.status">
            <el-radio :label="1">正常</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false" >取 消</el-button>
        <el-button type="primary"  @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup lang="ts">
import {findNodes, removeById, save, updateById} from "../../api/sysMenu";
import {onBeforeMount, ref} from "vue";
import {reactive} from "@vue/runtime-core";

interface User {
  component: string
  icon: string
  createTime: string
  isDeleted: number
  param: any
  parentId: any
  path: string
  perms: any
  select: boolean
  sortValue: number
  status: number
  type: number
  updateTime: string
  id: string
  name: string
  children?: User[]
}

const defaultForm = reactive({
  id: '',
  parentId: '' as any,
  name: '',
  type: 0,
  path: '',
  component: '',
  perms: '',
  icon: '',
  sortValue: 1,
  status: 1,
  parentName:''
})
let tableData: any = reactive([]);
let typeDisabled = ref(false)
let type0Disabled= ref(false)
let type1Disabled= ref(false)
let type2Disabled= ref(false)
let dialogTitle= ref('')
let dialogVisible= ref(false)
let sysMenu = reactive({
  id: '',
  parentId: '' as any,
  name: '',
  type: 0,
  path: '',
  component: '',
  perms: '',
  icon: '',
  sortValue: 1,
  status: 1,
  parentName:''
})
let saveBtnDisabled = ref(false)
let iconList = ref([
  {
    class: "Aim",
  },
  {
    class: "House",
  },
  {
    class: "Link",
  },
  {
    class: "Service",
  },
  {
    class: "Star",
  },
  {
    class: "Setting",
  },
  {
    class: "Clock",
  },
  {
    class: "Compass",
  },
  {
    class: "Menu",
  }
])

//methods
const add = (row:any)=>{
  typeDisabled.value = false
  dialogTitle.value = '添加下级节点'
  dialogVisible.value = true
  sysMenu.id = ''
  if(row) {
    sysMenu.parentId = row.id
    sysMenu.parentName = row.name
    //this.sysMenu.component = 'ParentView'
    if(row.type === 0) {
      sysMenu.type = 1
      typeDisabled.value = false
      type0Disabled.value = false
      type1Disabled.value = false
      type2Disabled.value = true
    } else if(row.type === 1) {
      sysMenu.type = 2
      typeDisabled.value = true
    }
  } else {
    dialogTitle.value = '添加目录节点'
    sysMenu.type = 0
    sysMenu.component = 'Layout'
    sysMenu.parentId = 0
    typeDisabled.value = true
  }
}
const edit = (row:any)=>{
  console.log(row)
  sysMenu.id = row.id
  sysMenu.component = row.component
  sysMenu.icon = row.icon
  sysMenu.name = row.name
  sysMenu.path = row.path
  sysMenu.sortValue = row.sortValue
  sysMenu.type = row.type
  dialogVisible.value = true
}
const saveOrUpdate = ()=>{
 if(sysMenu.id === ''){
  save(sysMenu).then(res=>{
    dialogVisible.value=false
  }).then(()=>{
    location.reload()
  })
 }else{
  updateById(sysMenu).then(()=>{
    sysMenu.id = ''
  sysMenu.component = ''
  sysMenu.icon = ''
  sysMenu.name = ''
  sysMenu.path = ''
  sysMenu.sortValue = 0
  sysMenu.type = 0
    location.reload()
  })
 }
}
const removeDataById = (id:string)=>{
  removeById(id).then(()=>{
    location.reload()
  })
}
const reflesh = ()=>{
  findNodes().then(res => {
    if (res.data.code === 200) {
      for (let i = 0; i < res.data.data.length; i++) {
        tableData.push(res.data.data[i])
      }
      console.log(tableData)
    }
  })
}
onBeforeMount(function () {
  reflesh()
})
</script>
<style scoped>
*{
  float: left;
}
.clobtns{

  display: inline-block;
}

</style>
