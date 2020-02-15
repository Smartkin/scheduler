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
          Преподаватели
        </v-card>
        <v-card
          flat
          outlined
          max-width="350"
          min-width="350"
          class="mx-auto"
          v-for="(teacher, i) in teachers" :key="i"
          :to="'/teacher/' + teacher.id"
        >
          <div class="text-center">
            {{ teacher.name }}
          </div>
        </v-card>
        <v-card
          class="text-center white--text accent ma-auto"
          max-width="350"
          min-width="350"
          flat
          to="/create_teacher"
        >
          Создать преподавателя
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import TeacherService from '../services/teacher.service'
export default {
  data () {
    return {
      teachers: []
    }
  },
  mounted () {
    TeacherService.get().then(teachers => {
      this.teachers = teachers
    })
  }
}
</script>
