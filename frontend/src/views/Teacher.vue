<template>
  <v-container fluid>
    <v-row dense>
      <v-col>
        <v-row>
          <v-col>
            <back-button/>
          </v-col>
          <v-spacer/>
          <v-col>
            <modify-button/>
          </v-col>
        </v-row>
        <v-card>
          <v-card-title>
            {{ teacher.name }}
          </v-card-title>
          <v-card-text>
            {{ teacher.info }}
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import TeacherService from '../services/teacher.service'
import BackButton from '../components/BackButton'
import ModifyButton from '../components/ModifyButton'
export default {
  name: 'Teacher',
  components: { ModifyButton, BackButton },
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
