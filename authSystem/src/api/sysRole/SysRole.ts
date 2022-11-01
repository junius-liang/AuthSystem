import requests from "../index";
import {SysRoleQueryVo, SysRoleAddVo, SysRoleUpdateVo} from "../../model/SysRole/SysRoleQueryVo";
import {sysRoleStore} from '../../store/SysRoleStore/index'
// 关于axios请求，如果不用async...await，则需要使用定时器刷新页面

const sysRoleStore1 = sysRoleStore();
//角色列表
export const getPageList =  (searchObj:SysRoleQueryVo)=>{
        return  requests({
            url:'/admin/system/sysRole/query',
            method:'post',
            //get请求传的是params,post请求传的是data
            data:searchObj
        }).then(res => { // 处理响应结果
            let data = res.data;
            if(data.code===200){
                sysRoleStore1.list = data.data.records
                sysRoleStore1.total = data.data.total
            }
        }).catch(err => {
                console.log(err)
            });
    }

//角色删除
export const deleteRole =  (id:Number) => {
  return  requests({
      url:`/admin/system/sysRole/remove/${id}`,
      method:'post'
  })
}

//角色添加
export const addRole =  (params:SysRoleAddVo)=>{
    return  requests({
        url:"/admin/system/sysRole/add",
        method:'post',
        data:params
    })
}

//角色修改
export const updateRole = (params:SysRoleUpdateVo)=>{
    return requests({
        url:"/admin/system/sysRole/update",
        method:'post',
        data:params
    })
}

//角色删除
export const batchDelRole = async (params:string[]) => {
    return await requests({
        url:"/admin/system/sysRole/batchRemove",
        method:'post',
        data:params
    })
}

