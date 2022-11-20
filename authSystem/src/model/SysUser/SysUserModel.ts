export interface SysUserModel{
    userName:String,
    pwd:String
}

export interface SysUserByQuery{
    "keyword": string,
    "page": number,
    "size": number
}

export interface SysUserByAddOrUpdate{
        "id":string,
        "deptName": string,
        "description": string,
        "headUrl": string,
        "name": string,
        "password": string,
        "phone": string,
        "username": string
}

export type SysUserByAdd = Omit<SysUserByAddOrUpdate,'id'>
