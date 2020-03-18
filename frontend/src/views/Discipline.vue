<template>
  <v-container fluid>
    <v-card
      class="mx-auto"
      max-width="750px"
    >
      <v-card-title>
        <v-row>
          <v-col cols="9">
            {{ discipline.name }}
          </v-col>
          <v-spacer/>
          <v-col class="accent--text">
            {{ certificationTypes[discipline.certificationType] }}
          </v-col>
        </v-row>
      </v-card-title>
      <v-card-text class="text--primary">
        {{ discipline.shortName }}
      </v-card-text>
      <v-card-actions>
        <back-button/>
        <modify-button/>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import DisciplineService from '../services/discipline.service'
import ModifyButton from '../components/ModifyButton'
import BackButton from '../components/BackButton'
export default {
  components: { BackButton, ModifyButton },
  data () {
    return {
      certificationTypes: {
        'Nope': 'Отсутствует',
        'Test': 'Зачёт',
        'Exam': 'Экзамен'
      },
      discipline: {
        id: null,
        schId: null,
        name: '',
        shortName: '',
        certificationType: 0
      }
    }
  },
  props: {
    id: {
      required: true
    }
  },
  mounted () {
    DisciplineService.get(this.id).then(discipline => {
      this.discipline = discipline
      console.log(discipline)
    })
  }
}
</script>
