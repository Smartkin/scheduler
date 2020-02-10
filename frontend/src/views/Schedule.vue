<template>
  <v-container fluid>
    <v-row dense>
      <v-col>
        <v-card
          class="text-center white--text success ma-auto"
          max-width="350"
          min-width="350"
          flat
        >
          {{ new Date().toISOString().slice(0, new Date().toISOString().indexOf('T')) }}
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

export default {
  name: 'schedule',
  data () {
    return {
      lessons: []
    }
  },
  components: {
    ClassCard
  },
  mounted () {
    // Тестовые данные
    this.lessons.push(new Lesson({
      date: new Date(),
      type: 'Лекция',
      name: 'Технологии и средства облачных сервисов',
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
  }
}
</script>
