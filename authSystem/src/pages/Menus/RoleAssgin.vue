<template>
  <div class="app-container">
    <div style="padding: 20px 20px 0 20px;">
      授权角色：{{ route.params.name }}
    </div>
    <el-tree
        style="margin: 20px 0"
        ref="TreeAttr"
        :data="sysMenuList"
        :props="defaultProps"
        node-key="id"
        show-checkbox
        @check = "a"
        default-expand-all="true"
        :default-checked-keys = checkArr
    />
    <div style="padding: 20px 20px;">
      <el-button  type="primary" icon="Check" size="mini" @click="save">保存</el-button>
      <el-button @click="router.push('/sysRole')" size="mini" icon="Refresh-right">返回</el-button>
    </div>
  </div>
</template>
<script setup>

  import {useRoute} from "vue-router";
  import router from "../../router";
  import {doAssign, toAssign} from "../../api/sysMenu";
  import { reactive, ref, onMounted } from 'vue'
  import { ElMessage } from 'element-plus'

  let checkArr = reactive([])
  const route = useRoute()
  const TreeAttr = ref()
  let roleId  = ref()
  let sysMenuList = reactive([])
  let postDto = reactive({
    menuIdList: [],
    roleId: ""
  })

  const defaultProps = reactive(
      {
        children: 'children',
        label:'name'
      }
  )

  const save = () => {
    doAssign(postDto).then(res=>{
      ElMessage(res.data.message)
    }).then(()=>{
      setTimeout(()=>{
        router.push('/sysRole')
      },2000)
    })
  }

  const a = (a, b)=>{
    checkArr = b.checkedKeys
    postDto.menuIdList = checkArr
    postDto.roleId = route.params.id

  }

const fetchData =()=> {
   roleId.value = route.params.id
  toAssign(roleId.value).then(res => {
    for (let i = 0; i < res.data.data.length; i++) {
      sysMenuList.push(res.data.data[i])
    }
    return sysMenuList;
  }).then(()=>{
    const checkedIds = getCheckedIds(sysMenuList)
    for (let i = 0; i < checkedIds.length; i++) {
      checkArr.push(checkedIds[i])
    }
  })
}

/*
 得到所有选中的id列表
 */
const getCheckedIds = (auths, initArr = reactive([])) => {
  return auths.reduce((pre, item) => {
    if (item.select && item.children.length === 0) {
      pre.push(item.id)
    } else if (item.children) {
      getCheckedIds(item.children, initArr)
    }
    return pre
  }, initArr)
}

  onMounted(()=>{
    fetchData()
  })
</script>
<style scoped>

</style>
