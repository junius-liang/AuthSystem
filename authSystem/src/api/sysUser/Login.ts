import requests from "../index";
import {SysUserModel} from '../../model/SysUser/SysUserModel'
import router from "../../router";
import {setStorage} from '../storage'


export const login = (params:SysUserModel)=>{
    requests({
        url: '/admin/system/sysUser/login',
        method: 'post',
        data: params
    }).then(res => { // 处理响应结果
        let data = res.data;
        console.log(data.data)
        setStorage('token',data.data)
        router.push('/')
    })
        .catch(err => {
            console.log(err)
        });
}

export const expire = async (token:string)=>{
    let result;
     await requests({
        url:'/admin/system/sysUser/check?token='+token,
        method:'get'
    }).then(res=>{
        result = res.data.data
    })

    return result
}

export const getUserInfo = async ()=>{
    return await requests({
        url:'/admin/system/sysUser/info',
        method:'get'
    })
}
