import requests from "../index";
import {SysMenu} from "../../model/SysMenu/SysMenu";

const profix = '/admin/system/sys-menu'

//菜单列表
export const findNodes = async () => {
  return await requests({
      url:`${profix}/findNodes`,
      method:'get'
  })
}

//根据id查询菜单
export const findMenuById = async (id:string) => {
    return await requests({
        url:`${profix}/findNodesById/${id}`,
        method:'get'
    })
}

//根据id删除菜单
export const removeById = async (id:string) => {
    return await requests({
        url:`${profix}/removeById/${id}`,
        method:'get'
    })
}

//添加菜单
export const save = async (data:any) => {
    return await requests({
        url:`${profix}/save`,
        method:'post',
        data
    })
}

//修改菜单
export const updateById = async (data:any) => {
    return await requests({
        url:`${profix}/update`,
        method:'post',
        data
    })
}

//根据角色获取菜单
export const toAssign = async (roleId:string)=>{
    return await requests({
        url:`/admin/system/sys-menu/toAssign/${roleId}`,
        method:'get'
    })
}

//给角色分配权限
export const doAssign = async (data:any)=>{
    return await requests({
        url:'/admin/system/sys-menu/doAssign',
        method:'post',
        data
    })
}
