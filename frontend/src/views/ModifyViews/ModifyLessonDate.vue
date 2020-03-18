<template>
  <v-container fluid id="create-teacher">
    <back-button/>
    <v-row dense>
      <v-col>
        <validation-observer
          v-slot="{ invalid, passes }"
        >
          <v-form>
            <v-row>
              <v-col cols="10">
                <validation-provider
                  rules="required"
                >
                  <v-select
                    label="Редактируемое поле"
                    :items="noteTypes"
                    v-model="editLessonDate.noteType"
                  />
                </validation-provider>
              </v-col>
              <conditional-expand-field
                :condition="editLessonDate.noteType >= 3"
              >
                <v-col>
                  <v-checkbox
                    hint="Использовать часы"
                    :persistent-hint="!useClocksToEnterTime"
                    off-icon="mdi-alarm-off"
                    on-icon="mdi-alarm-multiple"
                    v-model="useClocksToEnterTime"
                  />
                </v-col>
              </conditional-expand-field>
            </v-row>
            <conditional-expand-field
              :condition="editLessonDate.noteType >= 0 && editLessonDate.noteType <= 2"
            >
              <validation-provider
                rules="required|min:1|max:255"
                name="значение"
                v-slot="{ errors }"
              >
                <v-text-field
                  label="Значение выбранного поля"
                  counter="255"
                  v-model="editLessonDate.value"
                  :error-messages="errors"
                />
              </validation-provider>
            </conditional-expand-field>
            <conditional-expand-field
              :condition="editLessonDate.noteType >= 3"
            >
              <v-row no-gutters>
                <v-col cols="6">
                  <conditional-expand-field
                    :condition="!useClocksToEnterTime"
                    vid="lessonCustomTimes"
                  >
                    <v-row no-gutters>
                      <v-col cols="6">
                        <v-row no-gutters>
                          <v-col class="mx-1" cols="1">
                            <validation-provider
                              name="timeStartHours"
                              vid="textStartTimeHours"
                              :rules="(!useClocksToEnterTime) ? hoursRules : {}"
                              v-slot="{ errors, valid }"
                            >
                              <v-text-field
                                class="no-spinners"
                                v-model="textTimes[1]"
                                type="number"
                                dense
                                :error="errors.length !== 0"
                                @input="onTextTimesChanged(valid)"
                                @change="onTextTimesChanged(valid)"
                              />
                            </validation-provider>
                          </v-col>
                          :
                          <v-col class="mx-1" cols="1">
                            <validation-provider
                              name="timeStartMinutes"
                              vid="textStartTimeMinutes"
                              :rules="(!useClocksToEnterTime) ? minutesRules : {}"
                              v-slot="{ errors, valid }"
                            >
                              <v-text-field
                                class="no-spinners"
                                type="number"
                                v-model="textTimes[0]"
                                dense
                                :error="errors.length !== 0"
                                @input="onTextTimesChanged(valid)"
                                @change="onTextTimesChanged(valid)"
                              />
                            </validation-provider>
                          </v-col>
                        </v-row>
                      </v-col>
                    </v-row>
                  </conditional-expand-field>
                  <conditional-expand-field
                    :condition="useClocksToEnterTime"
                    vid="lessonCustomTimes"
                  >
                    <v-row>
                      <v-col>
                        <conditional-validation
                          :condition="useClocksToEnterTime"
                          name="время начала"
                          v-slot="{ errors }"
                        >
                          <v-time-picker
                            full-width
                            scrollable
                            format="24hr"
                            v-model="time"
                            @input="onLessonStartTimeChanged"
                          />
                          <v-alert
                            dismissible
                            dense
                            type="error"
                            v-if="errors[0]"
                          >
                            {{ errors[0] }}
                          </v-alert>
                        </conditional-validation>
                      </v-col>
                    </v-row>
                  </conditional-expand-field>
                </v-col>
              </v-row>
            </conditional-expand-field>
            <validation-provider
              rules="max:1024"
              name="Комментарий"
              v-slot="{ errors }"
            >
              <v-textarea
                label="Комментарий"
                counter="1024"
                v-model="editLessonDate.text"
                :error-messages="errors"
                filled
                auto-grow
              />
            </validation-provider>
            <v-btn
              block
              :disabled="invalid"
              @click="passes(modifyLessonDate)"
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
import LessonDateService from '../../services/lessonDate.service'
import { ValidationObserver, ValidationProvider } from 'vee-validate'
import BackButton from '../../components/BackButton'
import ConditionalExpandField from '../../components/ConditionalExpandField'
import ConditionalValidation from '../../components/ConditionalValidation'

export default {
  name: 'modify-lesson-date',
  data () {
    return {
      useClocksToEnterTime: false,
      noteTypes: [
        {
          text: 'Аудитория',
          value: 0
        },
        {
          text: 'Преподаватель',
          value: 1
        },
        {
          text: 'Дисцпилина',
          value: 2
        },
        {
          text: 'Время начала',
          value: 3
        },
        {
          text: 'Время конца',
          value: 4
        }
      ],
      editLessonDate: {
        id: null,
        lessonDate: null,
        noteType: 0,
        value: '',
        text: ''
      },
      message: '',
      textTimes: ['', ''],
      minutesRules: 'required|max_value:59|min_value:0',
      hoursRules: 'required|max_value:23|min_value:0',
      time: '',
      showError: false
    }
  },
  props: {
    id: {
      required: true
    }
  },
  components: {
    ConditionalExpandField,
    ConditionalValidation,
    BackButton,
    ValidationObserver,
    ValidationProvider
  },
  methods: {
    modifyLessonDate () {
      this.editLessonDate.lessonDate = this.id
      LessonDateService.modify(this.id, this.editLessonDate).then(() => {
        this.$router.push('/lesson_date/' + this.id)
      }, error => {
        console.error(error)
        this.message = error.message
        this.showError = true
      })
    },
    onTextTimesChanged (valid) {
      if (valid) {
        if (this.textTimes[0] !== '' && this.textTimes[1] !== '') {
          if (this.textTimes[0].length === 1) {
            this.textTimes[0] = 0 + this.textTimes[0]
          }
          if (this.textTimes[1].length === 1) {
            this.textTimes[1] = 0 + this.textTimes[1]
          }
          this.editLessonDate.value = this.textTimes[1] + ':' + this.textTimes[0]
          this.time = this.editLessonDate.value
        }
      } else {
        this.editLessonDate.value = ''
      }
    },
    onLessonStartTimeChanged () {
      this.editLessonDate.value = this.time
    }
  }
}
</script>

<style>
  .no-spinners [type='number'] {
    appearance: none;
  }
  .no-spinners input::-webkit-inner-spin-button,
  .no-spinners input::-webkit-outer-spin-button{
    appearance: none;
  }
</style>
