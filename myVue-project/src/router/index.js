import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/LogIn'
import Home from '@/components/Home'
import Setting from "@/components/Setting"
import Log from "@/components/Log"
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      hidden: true
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      hidden: true
    },
    {
      path: '/setting',
      name: 'Setting',
      component: Setting,
      hidden: true
    },
    {
      path: '/log',
      name: 'Log',
      component: Log,
      hidden: true
    },
  ]
})
