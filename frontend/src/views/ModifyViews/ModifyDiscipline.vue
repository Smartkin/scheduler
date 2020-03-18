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
                v-model="editDiscipline.name"
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
                    v-model="editDiscipline.shortName"
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
                    v-model="editDiscipline.certificationType"
                  />
                </validation-provider>
              </v-col>
            </v-row>
            <v-btn block :disabled="invalid" @click="passes(modifyDiscipline)">
              Сохранить
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
  name: 'ModifyDiscipline',
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
      certificationTypesTranslations: {
        'Nope': 0,
        'Test': 1,
        'Exam': 2
      },
      certificationTypesNames: [
        'Отсутствует',
        'Зачёт',
        'Экзамен'
      ],
      certificationTypesValues: [0, 1, 2],
      autoGenerateShort: true,
      editDiscipline: {
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
      this.editDiscipline = discipline
      this.editDiscipline.certificationType = this.certificationTypesTranslations[this.editDiscipline.certificationType]
      console.log(this.editDiscipline)
      this.onAutogenerateChanged()
    })
  },
  methods: {
    modifyDiscipline () {
      DisciplineService.modify(this.id, this.editDiscipline).then(() => {
        this.$router.push('/discipline/' + this.id)
      })
    },
    onAutogenerateChanged () {
      if (this.autoGenerateShort && this.editDiscipline.name !== '') {
        this.editDiscipline.shortName = ''
        let firstLettersArray = this.editDiscipline.name.split(' ').map(val => { return val[0] ? val[0] : '' })
        for (let letter of firstLettersArray) {
          this.editDiscipline.shortName += letter
        }
      } else {
        this.editDiscipline.shortName = ''
      }
    }
  }
}
</script>
