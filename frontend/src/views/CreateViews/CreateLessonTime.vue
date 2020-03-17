<template>
  <v-container fluid>
    <validation-observer v-slot="{ passes, invalid }">
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
                v-model="newLessonTimes.name"
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
        <v-btn block :disabled="invalid" @click="passes(createNewTimes)">
          Создать
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
      newLessonTimes: {
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
          this.newLessonTimes.begin = this.textTimesStart[1] + ':' + this.textTimesStart[0]
          this.startTime = this.newLessonTimes.begin
        }
        if (this.textTimesEnd[0] !== '' && this.textTimesEnd[1] !== '') {
          if (this.textTimesEnd[0].length === 1) {
            this.textTimesEnd[0] = 0 + this.textTimesEnd[0]
          }
          if (this.textTimesEnd[1].length === 1) {
            this.textTimesEnd[1] = 0 + this.textTimesEnd[1]
          }
          this.newLessonTimes.end = this.textTimesEnd[1] + ':' + this.textTimesEnd[0]
          this.endTime = this.newLessonTimes.end
        }
      } else {
        this.newLessonTimes.begin = ''
        this.newLessonTimes.end = ''
      }
    },
    onLessonStartTimeChanged () {
      this.newLessonTimes.begin = this.startTime
    },
    onLessonEndTimeChanged () {
      this.newLessonTimes.end = this.endTime
    },
    createNewTimes () {
      TimesService.create(this.newLessonTimes).then(() => {
        this.$router.push('')
      })
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
