import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";

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

router.beforeEach((to, from) => {

    // 路由守卫逻辑


    // 通过 return turn;
    // 不通过 return false;
    return true;
});

export default router;
