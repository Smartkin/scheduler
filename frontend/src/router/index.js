import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Schedule from '../views/Schedule'
import CreateLesson from '../views/CreateLesson'
import goTo from 'vuetify/lib/services/goto'
import Teachers from '../views/Teachers'
import CreateTeacher from '../views/CreateTeacher'
import Teacher from '../views/Teacher'
import ModifyTeacher from '../views/ModifyTeacher'
import CreateSchedule from '../views/CreateSchedule'
import LessonView from '../views/LessonView'
import ModifyLesson from '../views/ModifyLesson'

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
    path: '/schedule/:id',
    name: 'schedule',
    component: Schedule,
    props: true
  },
  {
    path: '/create_lesson',
    name: 'create_lesson',
    component: CreateLesson
  },
  {
    path: '/create_schedule',
    name: 'create_schedule',
    component: CreateSchedule
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
  },
  {
    path: '/teacher/:id/modify',
    name: 'teacher_modify',
    component: ModifyTeacher,
    props: true
  },
  {
    path: '/lesson/:id',
    name: 'lessonView',
    component: LessonView,
    props: true
  },
  {
    path: '/lesson/:id/modify',
    name: 'lesson_modify',
    component: ModifyLesson,
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
