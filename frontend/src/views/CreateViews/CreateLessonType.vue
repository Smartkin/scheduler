<template>
  <v-container fluid>
    <v-row justify="center">
      <v-card class="ma-2">
        <back-button text/>
      </v-card>
    </v-row>
    <v-row>
      <v-col>
        <validation-provider
          rules="required|min:1|max:255"
          name="наименование типа"
          v-slot="{ errors, valid }"
        >
          <v-text-field
            label="Наименование типа"
            counter="255"
            v-model="newType.name"
            :error-messages="errors"
          />
          <v-btn block :disabled="!valid" @click="createNewType">
            Создать
          </v-btn>
        </validation-provider>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { ValidationProvider } from 'vee-validate'
import BackButton from '../../components/BackButton'
import TypeService from '../../services/type.service'
export default {
  name: 'CreateLessonType',
  components: {
    BackButton,
    ValidationProvider
  },
  data () {
    return {
      newType: {
        id: null,
        schId: null,
        name: ''
      }
    }
  },
  methods: {
    createNewType () {
      TypeService.create(this.newType).then(() => {
        this.$router.push('/types')
      })
    }
  }
}
</script>
