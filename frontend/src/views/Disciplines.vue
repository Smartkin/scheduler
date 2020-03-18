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
          Дисциплины
        </v-card>
        <v-card
          flat
          outlined
          max-width="350"
          min-width="350"
          class="mx-auto"
          v-for="(discipline, i) in disciplines" :key="i"
          :to="'/discipline/' + discipline.id"
        >
          <div class="text-center">
            {{ discipline.name }}
          </div>
        </v-card>
        <v-card
          class="text-center white--text accent ma-auto"
          max-width="350"
          min-width="350"
          flat
          to="/create_discipline"
        >
          Создать дисциплину
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import DisciplineService from '../services/discipline.service'
export default {
  data () {
    return {
      disciplines: []
    }
  },
  mounted () {
    DisciplineService.get().then(disciplines => {
      this.disciplines = disciplines
    })
  }
}
</script>
