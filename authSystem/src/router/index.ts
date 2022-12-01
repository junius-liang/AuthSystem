import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import {getStorage} from "../api/storage"
import {login, expire, getUserInfo} from "../api/sysUser/Login";


const routes: Array<RouteRecordRaw> = [
    {
        path: "/",
        name: "home",
        component: () => import("../components/Home.vue"),
        children:[
            {
                path: "sysRole",
                name: "sysRole",
                component: () => import("../pages/SysRole.vue"),
            },
            {
                path: "sysUser",
                name: "sysUser",
                component: () => import("../pages/SysUser.vue"),
            },
            {
                path:"sysMunusList",
                name:"sysMunusList",
                component:()=>import("../pages/Menus/List.vue")
            },
            {
                path:"sysRoleAssgin/:id/:name",
                name:"sysRoleAssgin",
                component:()=>import("../pages/Menus/RoleAssgin.vue")
            }
        ]
    },
    {
        path: "/login",
        name: "login",
        component: () => import("../pages/Login.vue"),
    },
    {
        path: "/register",
        name: "register",
        component: () => import("../pages/Register.vue"),
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

router.beforeEach(async (to, from,next) => {
    const a = getStorage("token")
    //false 代表token过期
    const res = expire(a)

    console.log(a)
    console.log(res)
    // 路由守卫逻辑
    if(a==null&&to.name!="login"&&to.name!="register"){
        router.push('/login')
        window.alert('请先登录')
    }
    else if(!res&&to.name!="login"&&to.name!="register"){
        next({ name: 'login' })
        window.alert('请先登录')
    }
    else{
        getUserInfo().then(res=>{
            console.log(res)
        })
    }



    return next();
});

export default router;
