import Main from "@/components/main";
/**
 * iview-admin中meta除了原生参数外可配置的参数:
 * meta: {
 *  title: { String|Number|Function }
 *         显示在侧边栏、面包屑和标签栏的文字
 *         使用'{{ 多语言字段 }}'形式结合多语言使用，例子看多语言的路由配置;
 *         可以传入一个回调函数，参数是当前路由对象，例子看动态路由和带参路由
 *  hideInBread: (false) 设为true后此级路由将不会出现在面包屑中，示例看QQ群路由配置
 *  hideInMenu: (false) 设为true后在左侧菜单不会显示该页面选项
 *  notCache: (false) 设为true后页面在切换标签后不会缓存，如果需要缓存，无需设置这个字段，而且需要设置页面组件name属性和路由配置的name一致
 *  access: (null) 可访问该页面的权限数组，当前路由设置的权限会影响子路由
 *  icon: (-) 该页面在左侧菜单、面包屑和标签导航处显示的图标，如果是自定义图标，需要在图标名称前加下划线'_'
 *  beforeCloseName: (-) 设置该字段，则在关闭当前tab页时会去'@/router/before-close.js'里寻找该字段名对应的方法，作为关闭前的钩子函数
 * }
 */
export default [
    //登录界面
    {
        path: "/login",
        name: "login",
        meta: {
            title: "Login - 登录",
            hideInMenu: true
        },
        component: () => import("@/view/login/login.vue")
    },
    //首页
    {
        path: "/",
        name: "_home",
        redirect: "/home",
        component: Main,
        meta: {
            hideInMenu: true,
            notCache: true
        },
        children: [
            {
                path: "/home",
                name: "home",
                meta: {
                hideInMenu: true,
                title: "首页",
                notCache: true,
                icon: "md-home"
                },
                component: () => import("@/view/IndexPage/home.vue")
            }
        ]
    },

    //系统管理
    {
        path: "/sys",
        name: "sys",
        title: "系统管理",
        component: Main,
        meta: {
            hideInMenu: false,
            title: "系统管理",
            notCache: true,
            icon: "ios-apps"
        },
        children: [
            {
                path: "menulistctrl",
                title: "菜单管理",
                name: "menulistctrl",
                meta: {
                hideInMenu: false,
                title: "菜单管理",
                notCache: true,
                icon: "ios-apps-outline"
                },
                component: () => import("@/view/sys/menulistctrl/menulistctrl.vue")
            },
            {
                path: "rolectrl",
                title: "角色管理",
                name: "rolectrl",
                meta: {
                hideInMenu: false,
                title: "角色管理",
                notCache: true,
                icon: "ios-people-outline"
                },
                component: () => import("@/view/sys/role/rolectrl.vue")
            }
        ]
    },

    //用户管理
    {
        path: "/user",
        name: "user",
        title: "用户",
        meta: {
            hideInMenu: false,
            title: "用户",
            notCache: true,
            icon: "ios-contacts"
        },
        component: Main,
        children: [
            {
                path: "organuserctrl",
                title: "用户管理",
                name: "organuserctrl",
                meta: {
                hideInMenu: false,
                title: "用户管理",
                notCache: true,
                icon: "ios-contacts"
                },
                component: () => import("@/view/user/userctrl/userctrl.vue")
            }
        ]
    },

    {
        path: "/application",
        name: "application",
        title: "轮播",
        meta: {
            hideInMenu: false,
            title: "App管理",
            notCache: true,
            icon: "ios-compass"
        },
        component: Main,
        children: [
            {
                path: "swiperctrl",
                title: "轮播管理",
                name: "swiperctrl",
                meta: {
                hideInMenu: false,
                title: "轮播管理",
                notCache: true,
                icon: "ios-compass"
                },
                component: () => import("@/view/appctrl/swiperctrl/carouselManagement.vue")
            }
        ]
    },

    //错误界面
    {
        path: "/401",
        name: "error_401",
        meta: {
        hideInMenu: true
        },
        component: () => import("@/view/error-page/401.vue")
    },
    {
        path: "/500",
        name: "error_500",
        meta: {
        hideInMenu: true
        },
        component: () => import("@/view/error-page/500.vue")
    },
    {
        path: "*",
        name: "error_404",
        meta: {
        hideInMenu: true
        },
        component: () => import("@/view/error-page/404.vue")
    }
];
