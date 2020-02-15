import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Schedule from '../views/Schedule'
import CreateLesson from '../views/CreateLesson'
import goTo from 'vuetify/lib/services/goto'
import Teachers from '../views/Teachers'
import CreateTeacher from '../views/CreateTeacher'
import Teacher from '../views/Teacher'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/about',
    name: 'about',
    // Ленивая подгрузка компонента
    component: () => import('../views/About.vue')
  },
  {
    path: '/schedule',
    name: 'schedule',
    component: Schedule
  },
  {
    path: '/create_lesson',
    name: 'create_lesson',
    component: CreateLesson
  },
  {
    path: '/teachers',
    name: 'teachers',
    component: Teachers
  },
  {
    path: '/create_teacher',
    name: 'create_teacher',
    component: CreateTeacher
  },
  {
    path: '/teacher/:id',
    name: 'teacher',
    component: Teacher,
    props: true
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior: (to, from, savedPosition) => {
    let scrollTo = 0
    console.log(to)
    if (to.hash) {
      scrollTo = to.hash
    } else if (savedPosition) {
      scrollTo = savedPosition.y
    }

    return goTo(scrollTo)
  }
})

export default router
