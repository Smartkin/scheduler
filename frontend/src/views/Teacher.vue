<template>
  <v-container fluid>
    <back-button/>
    <v-card>
      <v-card-title>
        {{ teacher.name }}
      </v-card-title>
      <v-card-text>
        {{ teacher.info }}
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import TeacherService from '../services/teacher.service'
import BackButton from './BackButton'
export default {
  name: 'Teacher',
  components: { BackButton },
  data () {
    return {
      teacher: {
        name: '',
        info: ''
      }
    }
  },
  props: {
    id: {
      type: Number,
      required: true
    }
  },
  mounted () {
    TeacherService.get(this.id).then(teacher => {
      this.teacher = teacher
    })
  }
}
</script>
