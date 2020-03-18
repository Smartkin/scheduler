<template>
  <v-container fluid>
    <v-row justify="center">
      <v-card class="ma-2">
        <back-button text/>
      </v-card>
    </v-row>
    <validation-observer
      v-slot="{ passes, invalid }"
    >
      <v-form>
        <v-row no-gutters>
          <v-col>
            <validation-provider
              rules="required|min:1|max:255"
              name="наименование дисциплины"
              v-slot="{ errors }"
            >
              <v-text-field
                label="Наименование дисциплины"
                counter="255"
                v-model="newDiscipline.name"
                :error-messages="errors"
                @change="onAutogenerateChanged"
                @input="onAutogenerateChanged"
              />
            </validation-provider>
            <v-row no-gutters justify="center" align="center">
              <v-col md="8" sm="12" cols="5">
                <validation-provider
                  rules="required|min:1|max:255"
                  name="короткое название"
                  v-slot="{ errors }"
                >
                  <v-text-field
                    label="Короткое название"
                    counter="255"
                    v-model="newDiscipline.shortName"
                    :readonly="autoGenerateShort"
                    :disabled="autoGenerateShort"
                    :error-messages="errors"
                  />
                </validation-provider>
              </v-col>
              <v-col md="2" cols="7">
                <v-row justify="center">
                  <v-card flat tile>
                    <v-checkbox
                      v-model="autoGenerateShort"
                      label="Автогенерация"
                      @change="onAutogenerateChanged"
                    />
                  </v-card>
                </v-row>
              </v-col>
              <v-col>
                <validation-provider
                  rules="required"
                  name="тип аттестации"
                  v-slot="{ errors }"
                >
                  <v-select
                    label="Тип аттестации"
                    :items="certificationTypes"
                    :error-messages="errors"
                    v-model="newDiscipline.certificationType"
                  />
                </validation-provider>
              </v-col>
            </v-row>
            <v-btn block :disabled="invalid" @click="passes(createDiscipline)">
              Создать
            </v-btn>
          </v-col>
        </v-row>
      </v-form>
    </validation-observer>
  </v-container>
</template>

<script>
import { ValidationProvider, ValidationObserver } from 'vee-validate'
import BackButton from '../../components/BackButton'
import DisciplineService from '../../services/discipline.service'
export default {
  name: 'CreateDiscipline',
  components: {
    BackButton,
    ValidationProvider,
    ValidationObserver
  },
  data () {
    return {
      certificationTypes: [
        {
          text: 'Отсутствует',
          value: 0
        },
        {
          text: 'Зачёт',
          value: 1
        },
        {
          text: 'Экзамен',
          value: 2
        }
      ],
      certificationTypesNames: [
        'Отсутствует',
        'Зачёт',
        'Экзамен'
      ],
      certificationTypesValues: [0, 1, 2],
      autoGenerateShort: true,
      newDiscipline: {
        id: null,
        schId: null,
        name: '',
        shortName: '',
        certificationType: 0
      }
    }
  },
  methods: {
    createDiscipline () {
      DisciplineService.create(this.newDiscipline).then(() => {
        this.$router.push('/disciplines')
      })
    },
    onAutogenerateChanged () {
      if (this.autoGenerateShort && this.newDiscipline.name !== '') {
        this.newDiscipline.shortName = ''
        let firstLettersArray = this.newDiscipline.name.split(' ').map(val => { return val[0] ? val[0] : '' })
        for (let letter of firstLettersArray) {
          this.newDiscipline.shortName += letter
        }
      } else {
        this.newDiscipline.shortName = ''
      }
    }
  }
}
</script>
