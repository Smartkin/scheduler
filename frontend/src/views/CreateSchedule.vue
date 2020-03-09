<template>
  <v-container fluid>
    <v-row dense>
      <v-col>
        <validation-observer
          v-slot="{ invalid, passes }"
        >
          <v-form>
            <validation-provider
              rules="required|min:1|max:255"
              name="университет"
              v-slot="{ errors }"
            >
              <v-text-field
                label="Наименование университета"
                counter="255"
                v-model="newSchedule.university"
                :error-messages="errors"
              />
            </validation-provider>
            <validation-provider
              rules="required|min:1|max:255"
              name="факультет"
              v-slot="{ errors }"
            >
              <v-text-field
                label="Наименование факультета"
                counter="255"
                v-model="newSchedule.faculty"
                :error-messages="errors"
              />
            </validation-provider>
            <validation-provider
              rules="required|min:1|max:255"
              name="группа"
              v-slot="{ errors }"
            >
              <v-text-field
                label="Наименование группы"
                counter="255"
                v-model="newSchedule.groupName"
                :error-messages="errors"
              />
            </validation-provider>
            <validation-provider
              rules="required|min:1|max:255"
              name="семестр"
              v-slot="{ errors }"
            >
              <v-text-field
                label="Номер семестра"
                counter="255"
                v-model="newSchedule.semester"
                :error-messages="errors"
              />
            </validation-provider>
            <validation-provider
              rules="required|min:1|max:255"
              name="даты"
              v-slot="{ errors }"
            >
              <v-date-picker
                v-model="newSchedule.dates"
                range
                full-width
              />
            </validation-provider>
            <v-btn
              class="mt-3"
              block
              :disabled="invalid || newSchedule.dates.length < 2"
              @click="passes(createSchedule)"
            >
              Создать
            </v-btn>
            <v-alert
              type="error"
              dense
              class="mt-3"
              elevation="4"
              dismissible
              v-model="showError"
            >
              {{ message }}
            </v-alert>
          </v-form>
        </validation-observer>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { ValidationObserver, ValidationProvider } from 'vee-validate'
import ScheduleService from '../services/schedule.service'
export default {
  components: {
    ValidationObserver,
    ValidationProvider
  },
  name: 'CreateSchedule',
  data () {
    return {
      newSchedule: {
        university: '',
        faculty: '',
        groupName: '',
        semester: '',
        start: '',
        stop: '',
        dates: []
      },
      message: '',
      showError: false
    }
  },
  methods: {
    createSchedule () {
      this.newSchedule.start = this.newSchedule.dates[0]
      this.newSchedule.stop = this.newSchedule.dates[1]
      ScheduleService.create(this.newSchedule).then(() => {
        this.$router.push('/')
        this.$store.dispatch('data/setUpdatedSchedulesData')
      }, error => {
        console.error(error)
        this.message = error.message
        this.showError = true
      })
    }
  }
}
</script>
