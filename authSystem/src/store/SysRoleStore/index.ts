import {defineStore} from "pinia";

export const sysRoleStore = defineStore('sysRoleStore',{
    state: () => {
        return {
            counter: 0,
            list:[] as any,//角色列表
            total:0,
            page:1,
            limit:3,
            roleName:"",
            curRowId:"-1"
        }
    },
    getters:{

    },
    actions:{

    }
})
