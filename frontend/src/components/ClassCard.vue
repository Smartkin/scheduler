<template>
  <v-card
    id="class-card"
    max-width="350"
    min-width="350"
    flat
    outlined
  >
    <v-container class="fill-height" fluid>
        <v-row no-gutters class="primary--text">
          <v-col>
            {{ lesson.type }}
          </v-col>
          <v-spacer/>
          <v-col>
            {{ lesson.auditorium }}
          </v-col>
        </v-row>
      <v-row class="flex-nowrap">
        <v-col cols="3">
          <div class="red--text">
            {{ lessonTimeBegin }}
          </div>
          <div class="green--text">
            {{ lessonTimeEnd }}
          </div>
        </v-col>
        <v-col v-ripple @click="goToSubjectInfo" cols="9">
          {{ lesson.discipline }}
        </v-col>
      </v-row>
      <v-row>
        <!-- Список преподов -->
        <v-list>
          <v-list-item
            v-for="(teacher, i) in lesson.teachers"
            :key="i"
            class="black--text"
            @click="goToTeacherInfo(teacher)"
          >
            <v-list-item-title> {{ teacher }} </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
export default {
  props: {
    lesson: {
      type: Object
    }
  },
  computed: {
    currentLesson () {
      console.log(this.lesson)
      return this.lesson
    },
    lessonTimeBegin () {
      return this.lessonTimeSlice(this.lesson.start)
    },
    lessonTimeEnd () {
      return this.lessonTimeSlice(this.lesson.end)
    }
  },
  mounted () {
    console.log(this.lesson)
  },
  methods: {
    goToTeacherInfo (teacher) {
      console.log('Went to teacher page: ')
      console.log(teacher)
    },
    goToSubjectInfo () {
      console.log('Went to subject')
    },
    lessonTimeSlice (timeString) {
      if (timeString.split(':').length > 2) {
        return timeString.slice(0, timeString.lastIndexOf(':'))
      }
      return timeString
    }
  }
}
</script>
