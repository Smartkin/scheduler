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
          v-for="(type, i) in types" :key="i"
          :to="'/type/' + type.id + '/modify'"
        >
          <div class="text-center">
            {{ type.name }}
          </div>
        </v-card>
        <v-card
          class="text-center white--text accent ma-auto"
          max-width="350"
          min-width="350"
          flat
          to="/create_type"
        >
          Создать тип
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import TypeService from '../services/type.service'
export default {
  data () {
    return {
      types: []
    }
  },
  mounted () {
    TypeService.get().then(types => {
      this.types = types
    })
  }
}
</script>
