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
          Времена пар
        </v-card>
        <v-card
          flat
          outlined
          max-width="350"
          min-width="350"
          class="mx-auto"
          v-for="(time, i) in times" :key="i"
          :to="'/time/' + time.id + '/modify'"
        >
          <div class="text-center">
            {{ time.name }}
          </div>
        </v-card>
        <v-card
          class="text-center white--text accent ma-auto"
          max-width="350"
          min-width="350"
          flat
          to="/create_time"
        >
          Создать время
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import TimeService from '../services/time.service'
export default {
  data () {
    return {
      times: []
    }
  },
  mounted () {
    TimeService.get().then(times => {
      this.times = times
    })
  }
}
</script>
