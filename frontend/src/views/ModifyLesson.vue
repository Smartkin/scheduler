<template>
  <v-container fluid>
    <back-button/>
    <lesson-form
      v-if="requestsDone"
      :form-lesson="editLesson"
      :submit-lesson="modifyLesson"
      :modify-mode="requestsDone"
      :error-message="errorMessage"
      @close-error="onCloseError"
    >
      Сохранить
    </lesson-form>
  </v-container>
</template>

<script>
import BackButton from '../components/BackButton'
import LessonService from '../services/lesson.service'
import DisciplineService from '../services/discipline.service'
import TimeService from '../services/time.service'
import TypeService from '../services/type.service'
import TeacherService from '../services/teacher.service'
import LessonForm from '../components/LessonForm'

export default {
  name: 'modify-lesson',
  data () {
    return {
      requestsFinished: [],
      requestsDone: false,
      errorMessage: '',
      editLesson: {
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
  props: {
    id: {
      required: true
    }
  },
  components: {
    LessonForm,
    BackButton
  },
  mounted () {
    let lessonServiceReqId = this.awaitRequest()
    LessonService.get(this.id).then(lesson => {
      this.editLesson = lesson
      this.editLesson.weekType = this.translateWeekType(this.editLesson.weekType)
      this.editLesson.weekdays = this.getWeekdayNum(this.editLesson.weekdays)
      this.editLesson.dates = this.getDates(this.editLesson.weekType)
      let disServiceReqId = this.awaitRequest()
      DisciplineService.get(lesson.discipline).then(discipline => {
        this.editLesson.discipline = discipline
        this.finishRequest(disServiceReqId)
      })
      let timeServReqId = this.awaitRequest()
      TimeService.get(lesson.time).then(time => {
        this.editLesson.time = time
        this.finishRequest(timeServReqId)
      })
      let typeServReqId = this.awaitRequest()
      TypeService.get(lesson.type).then(type => {
        this.editLesson.type = type
        this.finishRequest(typeServReqId)
      })
      let teachers = []
      let teacherServReqId = this.awaitRequest()
      for (let teachId of this.editLesson.teacherList) {
        TeacherService.get(teachId).then(teacher => {
          teachers.push(teacher)
          if (teachers.length === this.editLesson.teacherList.length) {
            this.editLesson.teacherList = teachers
            this.finishRequest(teacherServReqId)
          }
        })
      }
      this.finishRequest(lessonServiceReqId)
    })
  },
  computed: {
    previewLesson () {
      let teacherNames = []
      this.editLesson.teacherList.forEach(teacher => {
        teacherNames.push(teacher.name)
      })
      return {
        start: this.editLesson.time.begin,
        end: this.editLesson.time.end,
        discipline: this.editLesson.discipline.name,
        type: this.editLesson.type.name,
        teachers: teacherNames,
        auditorium: this.editLesson.auditory,
        comment: this.editLesson.comment
      }
    }
  },
  methods: {
    modifyLesson () {
      console.log('Modify lesson:')
      console.log(this.editLesson)
      LessonService.modify(this.id, this.editLesson).then(() => {
        this.$router.push('/lesson/' + this.id)
      }, error => {
        this.errorMessage = error.message
      })
    },
    translateWeekType (weekType) {
      switch (weekType) {
        case 'Any':
          return 'Каждую'
        case 'Odd':
          return 'Нечётные'
        case 'Even':
          return 'Чётные'
        case 'Dates':
          return 'Определённые даты'
        default:
          return 'Неизвестно'
      }
    },
    getDates (weekType) {
      switch (weekType) {
        case 'Каждую':
        case 'Чётные':
        case 'Нечётные':
          let dates = Array(2)
          dates[0] = this.editLesson.startDate
          dates[1] = this.editLesson.endDate
          return dates
        default:
          return this.editLesson.dates
      }
    },
    getWeekdayNum (weekDay) {
      switch (weekDay) {
        case 'MONDAY':
          return 0
        case 'TUESDAY':
          return 1
        case 'WEDNESDAY':
          return 2
        case 'THURSDAY':
          return 3
        case 'FRIDAY':
          return 4
        case 'SATURDAY':
          return 5
        case 'SUNDAY':
          return 6
        default:
          return 0
      }
    },
    awaitRequest () {
      let reqId = this.requestsFinished.length
      this.requestsFinished.push(false)
      return reqId
    },
    finishRequest (reqId) {
      this.requestsFinished[reqId] = true
      for (let i in this.requestsFinished) {
        if (!this.requestsFinished[i]) {
          this.requestsDone = false
          return
        }
      }
      this.requestsDone = true
      console.log('Requests done')
    },
    onCloseError () {
      this.errorMessage = ''
    }
  }
}
</script>
