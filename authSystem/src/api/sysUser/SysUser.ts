import { SysUserByAdd, SysUserByAddOrUpdate, SysUserByQuery, SysUserModel } from "../../model/SysUser/SysUserModel";
import requests from "../index";
import {SysUserRole} from "../../model/SysRole/SysUserRole";

//添加用户
export const addUserApi = async (data:SysUserByAdd) => {
  return await requests({
    url:'/admin/system/sysUser/addUser',
    method:'post',
    data
  })
}

//删除用户
export const delUserApi = async (id:string) => {
    return await requests({
      url:`/admin/system/sysUser/remove/${id}`,
      method:'post',
    })
  }


//更新用户
export const modUserApi = async (data:SysUserByAddOrUpdate) => {
  return await requests({
    url:'/admin/system/sysUser/updateUser',
    method:'post',
    data:data
  })
}

//根据Id查询
export const finUserByIdApi = async (id:string) => {
  return await requests({
    url:`/admin/system/sysUser/getUserById/${id}`,
    method:'post',
  })
}

//条件分页查询
export const finUserByQuery = async (data:SysUserByQuery) => {
  return await requests({
    url:'/admin/system/sysUser/queryPageUser',
    method:'post',
    data
  })
}

//更改用户状态
export const changeUserStatusApi = async (isActive:boolean,id:string) => {
  let active:number = isActive?1:0;
  return await requests({
    url:`/admin/system/sysUser/modState/${id}/${active}`,
    method:'get'
  })
}

//根据用户查询角色
export const toAssign = async (userId:string) => {
  return await requests({
    url:`/admin/system/sysRole/toAssign/${userId}`,
    method:'get'
  })
}


//用户分配角色
export const doAssignApi = async (vo:SysUserRole) => {
  return await requests({
    url:`/admin/system/sysRole/doAssign`,
    method:'post',
    data:vo
  })
}
