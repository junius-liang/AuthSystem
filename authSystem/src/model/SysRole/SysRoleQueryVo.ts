export interface SysRoleQueryVo{
    limit:Number,
    page:Number,
    roleName:String,
}

export interface SysRoleAddVo{
    description: String,
    roleCode: String,
    roleName: String
}

export interface SysRoleUpdateVo{
    "description": String,
    "id": String,
    "roleCode": String,
    "roleName": String
}
