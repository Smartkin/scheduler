<template>
  <v-container fluid>
    <validation-observer ref="form" v-slot="{ passes, invalid }">
      <v-form>
        <v-row>
          <v-col cols="10">
            <validation-provider
              rules="required|min:1"
              name="название пары времён"
              v-slot="{ errors }"
            >
              <v-text-field
                counter="255"
                label="Название пары времён"
                :error-messages="errors"
                v-model="editLessonTimes.name"
              />
            </validation-provider>
          </v-col>
          <v-col>
            <v-checkbox
              hint="Использовать часы"
              :persistent-hint="!useClocksToEnterTime"
              off-icon="mdi-alarm-off"
              on-icon="mdi-alarm-multiple"
              v-model="useClocksToEnterTime"
            />
          </v-col>
        </v-row>
        <v-row no-gutters>
          <v-col cols="6">
            <conditional-expand-field
              :condition="!useClocksToEnterTime"
              vid="lessonCustomTimes"
            >
              <v-row no-gutters>
                <v-col cols="6">
                  <v-row no-gutters>
                    с
                    <v-col class="mx-1" cols="1">
                      <validation-provider
                        name="timeStartHours"
                        vid="textStartTimeHours"
                        :rules="(!useClocksToEnterTime) ? hoursRules : {}"
                        v-slot="{ errors, valid }"
                      >
                        <v-text-field
                          class="no-spinners"
                          v-model="textTimesStart[1]"
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
                          v-model="textTimesStart[0]"
                          dense
                          :error="errors.length !== 0"
                          @input="onTextTimesChanged(valid)"
                          @change="onTextTimesChanged(valid)"
                        />
                      </validation-provider>
                    </v-col>
                    <span>до</span>
                    <v-col class="mx-1"  cols="1">
                      <validation-provider
                        name="timeEndHours"
                        vid="textEndTimeHours"
                        :rules="(!useClocksToEnterTime) ? hoursRules: {}"
                        v-slot="{ errors, valid }"
                      >
                        <v-text-field
                          class="no-spinners"
                          v-model="textTimesEnd[1]"
                          type="number"
                          dense
                          :error="errors.length !== 0"
                          @input="onTextTimesChanged(valid)"
                          @change="onTextTimesChanged(valid)"
                        />
                      </validation-provider>
                    </v-col>
                    :
                    <v-col class="mx-1"  cols="1">
                      <validation-provider
                        name="timeEndMinutes"
                        vid="textEndTimeMinutes"
                        :rules="(!useClocksToEnterTime) ? minutesRules: {}"
                        v-slot="{ errors, valid }"
                      >
                        <v-text-field
                          class="no-spinners"
                          v-model="textTimesEnd[0]"
                          type="number"
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
                <v-col md="6" cols="12">
                  <v-label>Начало</v-label>
                  <conditional-validation
                    :condition="useClocksToEnterTime"
                    name="время начала"
                    v-slot="{ errors }"
                  >
                    <v-time-picker
                      full-width
                      scrollable
                      format="24hr"
                      v-model="startTime"
                      :max="endTime"
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
                <v-col>
                  <v-label>Конец</v-label>
                  <conditional-validation
                    :condition="useClocksToEnterTime"
                    name="время конца"
                    v-slot="{ errors }"
                  >
                    <v-time-picker
                      full-width
                      scrollable
                      format="24hr"
                      v-model="endTime"
                      :min="startTime"
                      @input="onLessonEndTimeChanged"
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
        <v-btn block :disabled="invalid" @click="passes(modifyNewTimes)">
          Сохранить
        </v-btn>
      </v-form>
    </validation-observer>
  </v-container>
</template>

<script>
import { ValidationProvider, ValidationObserver } from 'vee-validate'
import ConditionalValidation from '../../components/ConditionalValidation'
import ConditionalExpandField from '../../components/ConditionalExpandField'
import TimesService from '../../services/time.service'
export default {
  name: 'CreateLessonTime',
  components: {
    ValidationProvider,
    ValidationObserver,
    ConditionalValidation,
    ConditionalExpandField
  },
  data () {
    return {
      useClocksToEnterTime: false,
      editLessonTimes: {
        id: null,
        schId: null,
        name: '',
        begin: '',
        end: ''
      },
      textTimesStart: ['', ''],
      textTimesEnd: ['', ''],
      minutesRules: 'required|max_value:59|min_value:0|lessThanTime:@textStartTimeHours,@textStartTimeMinutes,@textEndTimeHours,@textEndTimeMinutes',
      hoursRules: 'required|max_value:23|min_value:0|lessThanTime:@textStartTimeHours,@textStartTimeMinutes,@textEndTimeHours,@textEndTimeMinutes',
      startTime: '',
      endTime: ''
    }
  },
  props: {
    id: {
      required: true
    }
  },
  mounted () {
    TimesService.get(this.id).then(time => {
      this.editLessonTimes = time
      this.startTime = this.lessonTimeSlice(this.editLessonTimes.begin)
      this.endTime = this.lessonTimeSlice(this.editLessonTimes.end)
      let startTimeArray = this.startTime.split(':').reverse()
      let endTimeArray = this.endTime.split(':').reverse()
      this.textTimesStart = startTimeArray
      this.textTimesEnd = endTimeArray
      // Можно это костылём назвать или нет не знаю, но работает
      setTimeout(() => {
        this.$refs.form.validate()
      }, 10)
    })
  },
  methods: {
    onTextTimesChanged (valid) {
      console.log(this.textTimesStart)
      console.log(this.textTimesEnd)
      if (valid) {
        if (this.textTimesStart[0] !== '' && this.textTimesStart[1] !== '') {
          if (this.textTimesStart[0].length === 1) {
            this.textTimesStart[0] = 0 + this.textTimesStart[0]
          }
          if (this.textTimesStart[1].length === 1) {
            this.textTimesStart[1] = 0 + this.textTimesStart[1]
          }
          this.editLessonTimes.begin = this.textTimesStart[1] + ':' + this.textTimesStart[0]
          this.startTime = this.editLessonTimes.begin
        }
        if (this.textTimesEnd[0] !== '' && this.textTimesEnd[1] !== '') {
          if (this.textTimesEnd[0].length === 1) {
            this.textTimesEnd[0] = 0 + this.textTimesEnd[0]
          }
          if (this.textTimesEnd[1].length === 1) {
            this.textTimesEnd[1] = 0 + this.textTimesEnd[1]
          }
          this.editLessonTimes.end = this.textTimesEnd[1] + ':' + this.textTimesEnd[0]
          this.endTime = this.editLessonTimes.end
        }
      } else {
        this.editLessonTimes.begin = ''
        this.editLessonTimes.end = ''
      }
    },
    onLessonStartTimeChanged () {
      this.editLessonTimes.begin = this.startTime
    },
    onLessonEndTimeChanged () {
      this.editLessonTimes.end = this.endTime
    },
    modifyNewTimes () {
      TimesService.modify(this.id, this.editLessonTimes).then(() => {
        this.$router.push('/times')
      })
    },
    lessonTimeSlice (timeString) {
      if (timeString.split(':').length > 2) {
        return timeString.slice(0, timeString.lastIndexOf(':'))
      }
      return timeString
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
