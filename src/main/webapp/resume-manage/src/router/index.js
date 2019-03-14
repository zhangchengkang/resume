import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import login from '../pages/login'
import Home from '../pages/Home'
import homepage from '../pages/homepage'
import projectexoerience from '../pages/projectexoerience'
import skills from '../pages/skills'
import sellmyself from '../pages/sellmyself'
import userinfo from '../pages/userinfo'
import workexprience from '../pages/workexprience'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: login
    },
    {
      path: '/Home',
      name: 'Home',
      component: Home,
      children:[{
        path:'/Home/homepage',
        name:'homepage',
        component:homepage
      }, 
      {
        path:'/Home/userinfo',
        name:'userinfo',
        component:userinfo
      },{
        path:'/Home/workexprience',
        name:'workexprience',
        component:workexprience
      },
      {
        path:'/Home/projectexoerience',
        name:'projectexoerience',
        component:projectexoerience
      },
        {
          path:'/Home/skills',
          name:'skills',
          component:skills
        },
        {
          path:'/Home/sellmyself',
          name:'sellmyself',
          component:sellmyself
        }
       ]
    }
  ]
})
