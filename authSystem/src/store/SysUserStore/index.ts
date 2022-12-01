import {defineStore} from "pinia";

export const sysUserStore = defineStore('sysUserStore',{
    state: () => {
        return {
            list:[] as any,//用户列表
            total:0,
            page:1,
            limit:3,
            keyword:"",
            curRowId:"-1"
        }
    },
    getters:{

    },
    actions:{
        aaa(){
            this.keyword = ""
        }
    }
})
