<template>
  <v-container flud>
    <v-row justify="center">
      <v-card class="ma-2">
        <back-button text/>
      </v-card>
      <v-card class="ma-2">
        <modify-button text/>
      </v-card>
    </v-row>
    <v-row>
      <v-col>
        <v-card
          max-width="400px"
          class="mx-auto"
        >
          <v-card-title>
            <v-row no-gutters>
              <v-col>
                {{ lessonDate.discipline }}
              </v-col>
              <v-col class="text-right accent--text">
                {{ lessonDate.auditorium }}
              </v-col>
            </v-row>
          </v-card-title>
          <v-card-subtitle>
            {{ lessonDate.type }}
          </v-card-subtitle>
          <v-card-text>
            <span class="red--text">
              {{ lessonTimeSlice(lessonDate.start) }}
            </span>
              -
            <span class="green--text">
              {{ lessonTimeSlice(lessonDate.end) }}
            </span>
          </v-card-text>
          <v-list-item
            v-for="(teacher, i) in lessonDate.teachers"
            :key="i"
            class="black--text"
          >
            <v-list-item-title> {{ teacher }} </v-list-item-title>
          </v-list-item>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import BackButton from '../components/BackButton'
import LessonDateService from '../services/lessonDate.service'
import ModifyButton from '../components/ModifyButton'
export default {
  name: 'LessonDateView',
  components: { ModifyButton, BackButton },
  props: {
    id: {
      required: true
    }
  },
  data () {
    return {
      lessonDate: {
        id: null,
        lesson: null,
        date: '',
        start: '',
        end: '',
        discipline: '',
        type: '',
        teachers: [],
        auditorium: '',
        modifiedField: null,
        comment: null
      }
    }
  },
  mounted () {
    LessonDateService.get(this.id).then(lessonDate => {
      this.lessonDate = lessonDate
    })
  },
  methods: {
    lessonTimeSlice (timeString) {
      if (timeString.split(':').length > 2) {
        return timeString.slice(0, timeString.lastIndexOf(':'))
      }
      return timeString
    }
  }
}
</script>
