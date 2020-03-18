import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Schedule from '../views/Schedule'
import goTo from 'vuetify/lib/services/goto'

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
    component: () => import('../views/CreateViews/CreateLesson')
  },
  {
    path: '/create_schedule',
    name: 'create_schedule',
    component: () => import('../views/CreateViews/CreateSchedule')
  },
  {
    path: '/teachers',
    name: 'teachers',
    component: () => import('../views/Teachers')
  },
  {
    path: '/create_teacher',
    name: 'create_teacher',
    component: () => import('../views/CreateViews/CreateTeacher')
  },
  {
    path: '/teacher/:id',
    name: 'teacher',
    component: () => import('../views/Teacher'),
    props: true
  },
  {
    path: '/teacher/:id/modify',
    name: 'teacher_modify',
    component: () => import('../views/ModifyViews/ModifyTeacher'),
    props: true
  },
  {
    path: '/lesson/:id',
    name: 'lessonView',
    component: () => import('../views/LessonView'),
    props: true
  },
  {
    path: '/lesson/:id/modify',
    name: 'lesson_modify',
    component: () => import('../views/ModifyViews/ModifyLesson'),
    props: true
  },
  {
    path: '/lesson_date/:id',
    name: 'lesson_date',
    component: () => import('../views/LessonDateView'),
    props: true
  },
  {
    path: '/types',
    name: 'lesson_types',
    component: () => import('../views/LessonTypes')
  },
  {
    path: '/create_type',
    name: 'create_type',
    component: () => import('../views/CreateViews/CreateLessonType')
  },
  {
    path: '/type/:id/modify',
    name: 'modify_type',
    component: () => import('../views/ModifyViews/ModifyLessonType'),
    props: true
  },
  {
    path: '/create_time',
    name: 'create_time',
    component: () => import('../views/CreateViews/CreateLessonTime')
  },
  {
    path: '/times',
    name: 'lesson_times',
    component: () => import('../views/LessonTimes')
  },
  {
    path: '/time/:id/modify',
    name: 'modify_time',
    component: () => import('../views/ModifyViews/ModifyLessonTime'),
    props: true
  },
  {
    path: '/create_discipline',
    name: 'create_discipline',
    component: () => import('../views/CreateViews/CreateDiscipline')
  },
  {
    path: '/disciplines',
    name: 'disciplines',
    component: () => import('../views/Disciplines')
  },
  {
    path: '/discipline/:id',
    name: 'discipline',
    component: () => import('../views/Discipline'),
    props: true
  },
  {
    path: '/discipline/:id/modify',
    name: 'modify_discipline',
    component: () => import('../views/ModifyViews/ModifyDiscipline'),
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
