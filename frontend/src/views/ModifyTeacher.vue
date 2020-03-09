<template>
  <v-container fluid id="create-teacher">
    <back-button/>
    <v-row dense>
      <v-col>
        <validation-observer
          v-slot="{ invalid, passes }"
        >
          <v-form>
            <validation-provider
              rules="required|min:1|max:255"
              name="ФИО преподавателя"
              v-slot="{ errors }"
            >
              <v-text-field
                label="ФИО преподавателя"
                counter="255"
                v-model="editTeacher.name"
                :error-messages="errors"
              />
            </validation-provider>
            <validation-provider
              rules="max:1024"
              v-slot="{ errors }"
            >
              <v-textarea
                label="Информация"
                counter="1024"
                v-model="editTeacher.info"
                :error-messages="errors"
                filled
                auto-grow
              />
            </validation-provider>
            <v-btn
              block
              :disabled="invalid"
              @click="passes(modifyTeacher)"
            >
              Сохранить
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
import TeacherService from '../services/teacher.service'
import { ValidationObserver, ValidationProvider } from 'vee-validate'
import BackButton from '../components/BackButton'

export default {
  name: 'modify-teacher',
  data () {
    return {
      editTeacher: {
        name: '',
        info: ''
      },
      message: '',
      showError: false
    }
  },
  props: {
    id: {
      required: true
    }
  },
  components: {
    BackButton,
    ValidationObserver,
    ValidationProvider
  },
  mounted () {
    TeacherService.get(this.id).then(teacher => {
      this.editTeacher = teacher
    })
  },
  methods: {
    modifyTeacher () {
      TeacherService.modify(this.id, this.editTeacher).then(() => {
        this.$router.push('/teachers')
      }, error => {
        console.error(error)
        this.message = error.message
        this.showError = true
      })
    }
  }
}
</script>
