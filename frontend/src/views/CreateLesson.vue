<template>
  <lesson-form :form-lesson="newLesson" :submit-lesson="createLesson">
    Создать
  </lesson-form>
</template>

<script>
import LessonService from '../services/lesson.service'
import LessonForm from '../components/LessonForm'

export default {
  name: 'create-lesson',
  data () {
    return {
      currentSchedule: this.$store.state.schedule.sch,
      newLesson: {
        schId: null,
        weekType: '',
        dates: [],
        type: {
          id: null,
          schId: null,
          name: ''
        },
        discipline: {
          id: null,
          schId: null,
          name: ''
        },
        teacherList: [],
        auditory: '',
        time: {
          id: null,
          schId: null,
          name: '',
          begin: '',
          end: ''
        },
        comment: '',
        weekdays: 0,
        startDate: null,
        endDate: null
      }
    }
  },
  components: {
    LessonForm
  },
  methods: {
    createLesson () {
      console.log('Modify lesson:')
      console.log(this.newLesson)
      LessonService.create(this.newLesson).then(() => {
        this.$router.push('/schedule/' + this.currentSchedule.id)
      })
    },
    convertWeekType (weekType) {
      switch (weekType) {
        case 'Каждую':
          return 2 // WeekType.Any
        case 'Чётные':
          return 0 // WeekType.Even
        case 'Нечётные':
          return 1 // WeekType.Odd
        default:
          return 3 // WeekType.Dates
      }
    }
  }
}
</script>
