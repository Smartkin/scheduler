<template>
  <v-container v-if="requestsDone" fluid>
<!--    {{ lesson }}-->
    <v-row>
      <v-col>
        <back-button/>
      </v-col>
      <v-col>
        <modify-button/>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" md="4">
        <v-card
          class="pa-2"
        >
          Недели:
          <v-label>
            {{ lessonWeekType }}
          </v-label>
          <div/>
          День недели:
          <v-label>
            {{ lessonWeekday }}
          </v-label>
          <div/>
          Период:
          <span class="primary--text">
            {{ lesson.startDate }}
          </span>
          по
          <span class="primary--text">
            {{ lesson.endDate }}
          </span>
          <div/>
          Время пары:
          <span class="red--text">
            {{ lessonTimeBegin }}
          </span>
          -
          <span class="green--text">
            {{ lessonTimeEnd }}
          </span>
        </v-card>
      </v-col>
      <v-col>
        <v-card>
          <v-card-title>
            <v-row no-gutters>
              <v-col>
                {{ lesson.discipline.name }}
              </v-col>
              <v-spacer/>
              <v-col class="accent--text">
                {{ lesson.auditory }}
              </v-col>
            </v-row>
          </v-card-title>
          <v-card-text class="primary--text">
            {{ lesson.type.name }}
          </v-card-text>
          <v-list-item
            v-for="(teacher, i) in lesson.teacherList"
            :key="i"
            class="black--text"
            @click="goToTeacherPage(teacher.id)"
          >
            <v-list-item-title> {{ teacher.name }} </v-list-item-title>
          </v-list-item>
          <v-card-subtitle v-if="lesson.comment">
            {{ lesson.comment }}
          </v-card-subtitle>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import LessonService from '../services/lesson.service'
import DisciplineService from '../services/discipline.service'
import TimeService from '../services/time.service'
import TypeService from '../services/type.service'
import TeacherService from '../services/teacher.service'
import BackButton from '../components/BackButton'
import ModifyButton from '../components/ModifyButton'
export default {
  name: 'LessonView',
  components: { ModifyButton, BackButton },
  data () {
    return {
      lesson: {
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
      },
      requestsFinished: [],
      requestsDone: false
    }
  },
  props: {
    id: {
      required: true
    }
  },
  computed: {
    lessonWeekType () {
      switch (this.lesson.weekType) {
        case 'Any':
          return 'Каждая'
        case 'Odd':
          return 'Нечётная'
        case 'Even':
          return 'Чётная'
        case 'Dates':
          return 'Определённые даты'
        default:
          return 'Неизвестно'
      }
    },
    lessonWeekday () {
      switch (this.lesson.weekdays) {
        case 'MONDAY':
          return 'Понедельник'
        case 'TUESDAY':
          return 'Вторник'
        case 'WEDNESDAY':
          return 'Среда'
        case 'THURSDAY':
          return 'Четверг'
        case 'FRIDAY':
          return 'Пятница'
        case 'SATURDAY':
          return 'Суббота'
        case 'SUNDAY':
          return 'Воскресенье'
        default:
          return 'Непонятный день недели'
      }
    },
    lessonTimeBegin () {
      return this.lessonTimeSlice(this.lesson.time.begin)
    },
    lessonTimeEnd () {
      return this.lessonTimeSlice(this.lesson.time.end)
    }
  },
  mounted () {
    let lessonServiceReqId = this.awaitRequest()
    LessonService.get(this.id).then(lesson => {
      this.lesson = lesson
      let disServiceReqId = this.awaitRequest()
      DisciplineService.get(lesson.discipline).then(discipline => {
        this.lesson.discipline = discipline
        this.finishRequest(disServiceReqId)
      })
      let timeServReqId = this.awaitRequest()
      TimeService.get(lesson.time).then(time => {
        this.lesson.time = time
        this.finishRequest(timeServReqId)
      })
      let typeServReqId = this.awaitRequest()
      TypeService.get(lesson.type).then(type => {
        this.lesson.type = type
        this.finishRequest(typeServReqId)
      })
      let teachers = []
      let teacherServReqId = this.awaitRequest()
      for (let teachId of this.lesson.teacherList) {
        TeacherService.get(teachId).then(teacher => {
          teachers.push(teacher)
          if (teachers.length === this.lesson.teacherList.length) {
            this.lesson.teacherList = teachers
            this.finishRequest(teacherServReqId)
          }
        })
      }
      this.finishRequest(lessonServiceReqId)
    })
  },
  methods: {
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
      console.log(this.requestsFinished)
    },
    lessonTimeSlice (timeString) {
      if (timeString.split(':').length > 2) {
        return timeString.slice(0, timeString.lastIndexOf(':'))
      }
      return timeString
    },
    goToTeacherPage (teachId) {
      this.$router.push('/teacher/' + teachId)
    }
  }
}
</script>
