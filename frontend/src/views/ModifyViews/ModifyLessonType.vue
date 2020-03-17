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
            v-model="editType.name"
            :error-messages="errors"
          />
          <v-btn block :disabled="!valid" @click="createNewType">
            Сохранить
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
  name: 'ModifyLessonType',
  components: {
    BackButton,
    ValidationProvider
  },
  props: {
    id: {
      required: true
    }
  },
  data () {
    return {
      editType: {
        id: null,
        schId: null,
        name: ''
      }
    }
  },
  mounted () {
    TypeService.get(this.id).then(type => {
      this.editType = type
    })
  },
  methods: {
    createNewType () {
      TypeService.modify(this.id, this.editType).then(() => {
        this.$router.push('/types')
      })
    }
  }
}
</script>
