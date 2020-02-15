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
            {{ lesson.auditory }}
          </v-col>
        </v-row>
      <v-row class="flex-nowrap">
        <v-col cols="3">
          <div class="red--text">
            {{ lesson.startTime }}
          </div>
          <div class="green--text">
            {{ lesson.endTime }}
          </div>
        </v-col>
        <v-col v-ripple @click="goToSubjectInfo" cols="9">
          {{ lesson.name }}
        </v-col>
      </v-row>
      <v-row>
        <!-- Список преподов -->
        <v-list>
          <v-list-item
            v-for="(name, i) in lesson.teachers"
            :key="i"
            class="black--text"
            @click="goToTeacherInfo(name)"
          >
            <v-list-item-title> {{ name }} </v-list-item-title>
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
    }
  },
  mounted () {
    console.log(this.lesson)
  },
  methods: {
    goToTeacherInfo (teacherName) {
      console.log('Went to teacher page: ' + teacherName)
    },
    goToSubjectInfo () {
      console.log('Went to subject')
    }
  }
}
</script>
