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
        <v-col cols="9">
          <v-list-item
            class="black--text"
            @click="goToSubjectInfo"
          >
            {{ lesson.discipline }}
          </v-list-item>
        </v-col>
      </v-row>
      <v-row>
        <!-- Список преподов -->
        <v-list>
          <v-list-item
            v-for="(teacher, i) in lesson.teachers"
            :key="i"
            class="black--text"
          >
            <v-list-item-title> {{ teacher }} </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-row>
    </v-container>
    <v-card-actions v-if="hasActions">
      <v-btn text>Подробно</v-btn>
      <v-btn text>Изменить</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  props: {
    lesson: {
      type: Object
    },
    hasActions: {
      type: Boolean,
      default: true
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
    goToSubjectInfo () {
      if (this.hasActions) {
        console.log('Went to subject')
        this.$router.push('/lesson/' + this.lesson.lesson)
      }
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
