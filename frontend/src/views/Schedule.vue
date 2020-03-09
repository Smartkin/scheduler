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
          {{ selectedDate.toISOString().slice(0, selectedDate.toISOString().indexOf('T')) }}
          <v-card-actions>
            <v-btn @click="prevDay" icon>
              <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
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
    currentSchedule () {
      return this.$store.state.schedule.sch
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
      let curDate = this.selectedDate.toISOString()
      LessonDateService.get(null, {
        schId: this.id,
        date: curDate.slice(0, curDate.indexOf('T')),
        count: 1
      }).then(lessons => {
        this.lessons = lessons
      })
    }
  },
  methods: {
    prevDay () {
      let date = new Date(this.selectedDate.valueOf())
      date.setDate(date.getDate() - 1)
      this.selectedDate = date
      this.getLessons()
    },
    nextDay () {
      let date = new Date(this.selectedDate.valueOf())
      date.setDate(date.getDate() + 1)
      this.selectedDate = date
      this.getLessons()
    },
    getLessons () {
      let curDate = this.selectedDate.toISOString()
      LessonDateService.get(null, {
        schId: this.id,
        date: curDate.slice(0, curDate.indexOf('T')),
        count: 1
      }).then(lessons => {
        this.lessons = lessons
      })
    }
  }
}
</script>
