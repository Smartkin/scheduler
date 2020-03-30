<template>
  <v-container fluid>
    <v-row dense>
      <v-col>
        <v-card
          class="text-center white--text success ma-auto"
          max-width="350"
          min-width="350"
          dark
          flat
        >
          <v-btn block text @click="selectDateOverlay = true">
            {{ getCurrentDate }}
          </v-btn>
          <v-divider/>
          {{ dayOfTheWeekString }}
          <v-overlay v-model="selectDateOverlay">
            <v-btn color="primary" tile block light @click="selectDateOverlay = false">
              Закрыть
            </v-btn>
            <v-date-picker
              v-model="customSelectDate"
              style="border-top-left-radius: 0; border-top-right-radius: 0; max-width: 400px"
              full-width
              :min="currentSchedule ? currentSchedule.start : null"
              :max="currentSchedule ? currentSchedule.stop : null"
              first-day-of-week="1"
              @click:date="updateDatesAndLessons"
              light
            />
          </v-overlay>
          <v-card-actions>
            <v-btn @click="prevDay" icon>
              <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
            <v-spacer/>
              {{ weekNum }} неделя
            <v-spacer/>
            <v-btn @click="nextDay" icon>
              <v-icon>mdi-chevron-right</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
        <div
          v-for="(lesson, i) in lessons"
          :key="i"
        >
          <class-card
            class="mx-auto"
            :lesson="lesson"
          />
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import ClassCard from '../components/ClassCard'
import Lesson from '../models/lesson'
import LessonDateService from '../services/lessonDate.service'

export default {
  name: 'schedule',
  data () {
    return {
      lessons: [],
      selectedDate: new Date(),
      customSelectDate: new Date().toISOString().slice(0, new Date().toISOString().indexOf('T')),
      selectDateOverlay: false,
      test: false
    }
  },
  props: {
    id: {
      required: true
    }
  },
  components: {
    ClassCard
  },
  computed: {
    getCurrentDate () {
      let date = new Date(this.customSelectDate)
      if (this.selectDateOverlay || this.customSelectDate !== this.getLocalDateString(this.selectedDate)) {
        return this.getLocalDateString(date)
      }
      return this.getLocalDateString(this.selectedDate)
    },
    currentSchedule () {
      return this.$store.state.schedule.sch
    },
    weekNum () {
      if (this.currentSchedule) {
        let scheduleStartDate = new Date(this.currentSchedule.start)
        scheduleStartDate.setDate(scheduleStartDate.getDate() - scheduleStartDate.getDay() + 1)
        let selDate = new Date(this.getCurrentDate)
        let elapsedTime = selDate.getTime() - scheduleStartDate.getTime()
        return Math.floor(elapsedTime / (1000 * 60 * 60 * 24 * 7)) + 1
      }
      return 0
    },
    dayOfTheWeekString () {
      let date = new Date(this.getCurrentDate)
      switch (date.getDay()) {
        case 1:
          return 'Понедельник'
        case 2:
          return 'Вторник'
        case 3:
          return 'Среда'
        case 4:
          return 'Четверг'
        case 5:
          return 'Пятница'
        case 6:
          return 'Суббота'
        case 0:
          return 'Воскресенье'
        default:
          return 'Понедельник'
      }
    }
  },
  watch: {
    currentSchedule: function () {
      this.getLessons()
    }
  },
  mounted () {
    if (this.test) {
      // Тестовые данные
      this.lessons.push(new Lesson({
        date: new Date(),
        type: 'Лекция',
        name: 'dfasdhfashfiuasdhgfiuashgfiasghfaghsufasgfasufgasasdasdadasdadadaasdadadasddgfasdfagsdiukfgasugfasf',
        teachers: ['Гадасин Д. В.'],
        auditory: 'А-310',
        status: 'Идёт',
        startTime: '9:30',
        endTime: '11:05',
        comment: 'Принести прототип концептуального проекта'
      })
      )
      this.lessons.push(new Lesson({
        date: new Date(),
        type: 'Лабораторные работы',
        name: 'Технологии и средства облачных сервисов',
        teachers: ['Гадасин Д. В.', 'Беленькая М. Н.'],
        auditory: 'Л-211',
        status: 'Скоро',
        startTime: '11:15',
        endTime: '12:50',
        comment: 'Принести прототип концептуального проекта'
      })
      )
      this.lessons.push(new Lesson({
        date: new Date(),
        type: 'Практика',
        name: 'Технологии и средства облачных сервисов',
        teachers: ['Гадасин Д. В.'],
        auditory: 'А-315',
        status: 'Не скоро',
        startTime: '13:00',
        endTime: '14:35',
        comment: 'Принести прототип концептуального проекта'
      })
      )
    } else {
      this.selectedDate = new Date(this.$store.getters['data/lastSelectedDate'])
      let curDate = this.getLocalDateString(this.selectedDate)
      this.customSelectDate = curDate
      LessonDateService.get(null, {
        schId: this.id,
        date: curDate,
        count: 1
      }).then(lessons => {
        this.lessons = lessons
      }, _ => {
        this.$router.push('/')
      })
    }
  },
  methods: {
    getLocalDateString (date) {
      let dateString = date.toISOString()
      return dateString.slice(0, dateString.indexOf('T'))
    },
    prevDay () {
      let date = new Date(this.getCurrentDate)
      let curDateString = this.getCurrentDate
      date.setDate(date.getDate() - 1)
      if (curDateString !== this.currentSchedule.start) {
        this.selectedDate = date
        this.customSelectDate = this.getLocalDateString(date)
        this.$store.dispatch('data/setLastSelectedDate', this.customSelectDate)
        this.getLessons()
      }
    },
    nextDay () {
      let date = new Date(this.getCurrentDate)
      let curDateString = this.getCurrentDate
      date.setDate(date.getDate() + 1)
      if (curDateString !== this.currentSchedule.stop) {
        this.selectedDate = date
        this.customSelectDate = this.getLocalDateString(date)
        this.$store.dispatch('data/setLastSelectedDate', this.customSelectDate)
        this.getLessons()
      }
    },
    getLessons () {
      let curDate = this.getCurrentDate
      LessonDateService.get(null, {
        schId: this.id,
        date: curDate,
        count: 1
      }).then(lessons => {
        this.lessons = lessons
      })
    },
    updateDatesAndLessons () {
      this.$store.dispatch('data/setLastSelectedDate', this.customSelectDate)
      this.getLessons()
    }
  }
}
</script>
