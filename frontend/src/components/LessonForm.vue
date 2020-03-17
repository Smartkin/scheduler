<template>
  <validation-observer
    id="create-lesson"
    v-slot="{ invalid, passes}"
  >
    <v-form>
      <v-container fluid>
        <v-row>
          <v-col md="6" cols="12">
            <v-card style="position: sticky; top: 64px">
              <v-card-title>
                Предпросмотр
              </v-card-title>
              <class-card
                class="mx-auto"
                :lesson="previewLesson"
                :has-actions="false"
              />
            </v-card>
          </v-col>
          <v-col>
            <validation-provider
              name="тип недели"
              rules="required"
              v-slot="{ errors }"
              vid="weekType"
            >
              <v-select
                v-model="weekType"
                :items="lessonRepeatType"
                label="Тип недели"
                :error-messages="errors"
                @change="onWeekTypeChanged"
              />
            </validation-provider>
            <!--       Выбор дат проведения занятий     -->
            <conditional-expand-field
              :condition="weekType"
              name="даты семестра"
              vid="useSemesterDates"
              @autoscroll="onValidateAutoscroll"
            >
              <v-row>
                <v-col>
                  <v-checkbox
                    v-model="useSemesterDates"
                    :disabled="weekType === 'Определённые даты'"
                    label="Использовать даты семестра"
                    @change="onSemesterDatesChange"
                  />
                </v-col>
                <v-col>
                  <v-btn class="mt-3" :disabled="useSemesterDates" @click="showDatesOverlay = true">
                    Выбрать даты
                  </v-btn>
                  <conditional-validation
                    :condition="!useSemesterDates"
                    name="диапазон недель"
                    rules="required|arrayMinSize:2"
                  >
                    <input type="hidden" v-model="formLesson.dates">
                  </conditional-validation>
                  <v-overlay
                    id="dateRangePickerOverlay"
                    v-if="weekType !== 'Определённые даты'"
                    v-model="showDatesOverlay"
                  >
                    <v-btn tile light @click="showDatesOverlay = false">
                      Закрыть
                    </v-btn>
                    <v-date-picker
                      v-model="formLesson.dates"
                      range
                      full-width
                      first-day-of-week="1"
                      light
                      :readonly="useSemesterDates"
                    />
                  </v-overlay>
                  <v-overlay
                    id="datesPickerOverlay"
                    v-if="weekType === 'Определённые даты'"
                    v-model="showDatesOverlay"
                  >
                    <v-btn tile light @click="showDatesOverlay = false">
                      Закрыть
                    </v-btn>
                    <conditional-validation
                      :condition="weekType === 'Определённые даты'"
                      name="перечисление дат"
                      rules="required"
                    >
                      <v-date-picker
                        v-model="formLesson.dates"
                        first-day-of-week="1"
                        full-width
                        light
                        multiple
                      />
                    </conditional-validation>
                  </v-overlay>
                </v-col>
              </v-row>
            </conditional-expand-field>
            <validated-expand-field
              :condition="weekType && weekType !== 'Определённые даты'"
              name="день недели"
              @autoscroll="onValidateAutoscroll"
              vid="dayOfTheWeek"
            >
              <v-label>День недели</v-label>
              <v-radio-group row v-model="dayOfTheWeekChoice">
                <v-radio v-for="(day, i) in lessonDays" :key="i" :label="day"/>
              </v-radio-group>
            </validated-expand-field>
            <validated-expand-field
              :condition="canChooseLessonType"
              v-slot="{ errors }"
              name="тип пары"
              vid="lessonTypeStr"
              @autoscroll="onValidateAutoscroll"
            >
              <v-select
                v-model="customType"
                :items="lessonTypes"
                label="Тип пары"
                :error-messages="errors"
                @input="onLessonTypeChanged"
              />
            </validated-expand-field>
            <validated-expand-field
              name="название типа пары"
              :condition="customType === 'Создать'"
              rules="required|min:1|max:255"
              v-slot="{ errors }"
              vid="lessonTypeStringField"
              @autoscroll="onValidateAutoscroll"
            >
              <v-text-field
                counter="255"
                label="Название типа пары"
                :error-messages="errors"
                v-model="formLesson.type.name"
              />
            </validated-expand-field>
            <validated-expand-field
              name="время пары"
              :condition="formLesson.type && (formLesson.type.name !== '' || formLesson.type.name.length >= 1)"
              v-slot="{ errors }"
              vid="lessonTimes"
              @autoscroll="onValidateAutoscroll"
            >
              <v-select
                v-model="customTimes"
                :items="lessonTimes"
                label="Время пары"
                :error-messages="errors"
                @input="onLessonTimesChanged"
              />
            </validated-expand-field>
            <!--        Создание новой пары времени для пар    -->
            <conditional-expand-field
              :condition="customTimes === 'Создать'"
              vid="lessonCustomTimes"
              @autoscroll="onValidateAutoscroll"
            >
              <v-row>
                <v-col cols="10">
                  <conditional-validation
                    :condition="customTimes === 'Создать'"
                    rules="required|min:1"
                    name="название пары времён"
                    v-slot="{ errors }"
                  >
                    <v-text-field
                      counter="255"
                      label="Название пары времён"
                      :error-messages="errors"
                      v-model="formLesson.time.name"
                    />
                  </conditional-validation>
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
            </conditional-expand-field>
            <conditional-expand-field
              :condition="!useClocksToEnterTime && customTimes === 'Создать'"
              vid="lessonCustomTimes"
              @autoscroll="onValidateAutoscroll"
            >
              <v-row no-gutters>
                <v-col cols="6">
                  <v-row no-gutters>
                    с
                    <v-col class="mx-1" cols="1">
                      <conditional-validation
                        :condition="!useClocksToEnterTime && customTimes === 'Создать'"
                        name="timeStartHours"
                        vid="textStartTimeHours"
                        rules="required|max_value:23|min_value:0|lessThanTime:@textStartTimeHours,@textStartTimeMinutes,@textEndTimeHours,@textEndTimeMinutes"
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
                      </conditional-validation>
                    </v-col>
                    :
                    <v-col class="mx-1" cols="1">
                      <conditional-validation
                        :condition="!useClocksToEnterTime && customTimes === 'Создать'"
                        name="timeStartMinutes"
                        vid="textStartTimeMinutes"
                        rules="required|max_value:59|min_value:0|lessThanTime:@textStartTimeHours,@textStartTimeMinutes,@textEndTimeHours,@textEndTimeMinutes"
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
                      </conditional-validation>
                    </v-col>
                    <span>до</span>
                    <v-col class="mx-1"  cols="1">
                      <conditional-validation
                        :condition="!useClocksToEnterTime && customTimes === 'Создать'"
                        name="timeEndHours"
                        vid="textEndTimeHours"
                        rules="required|max_value:23|min_value:0|lessThanTime:@textStartTimeHours,@textStartTimeMinutes,@textEndTimeHours,@textEndTimeMinutes"
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
                      </conditional-validation>
                    </v-col>
                    :
                    <v-col class="mx-1"  cols="1">
                      <conditional-validation
                        :condition="!useClocksToEnterTime && customTimes === 'Создать'"
                        name="timeEndMinutes"
                        vid="textEndTimeMinutes"
                        rules="required|max_value:59|min_value:0|lessThanTime:@textStartTimeHours,@textStartTimeMinutes,@textEndTimeHours,@textEndTimeMinutes"
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
                      </conditional-validation>
                    </v-col>
                  </v-row>
                </v-col>
              </v-row>
            </conditional-expand-field>
            <conditional-expand-field
              :condition="useClocksToEnterTime"
              vid="lessonCustomTimes"
              @autoscroll="onValidateAutoscroll"
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
            <!--        Список преводавателей    -->
            <validated-expand-field
              :condition="canSelectTeachers"
              name="список преподавателей"
              v-slot="{ errors }"
              vid="lessonTeachers"
              @autoscroll="onValidateAutoscroll"
            >
              <v-select
                v-model="selectedTeachers"
                multiple
                label="Список преподавателей"
                :items="teachers"
                @input="onLessonTeachersChanged"
                :error-messages="errors"
              />
            </validated-expand-field>
            <!--        Создание нового преподавателя    -->
            <validated-expand-field
              :condition="addTeachersField"
              name="ФИО преподавателя"
              rules="required|min:1|max:255"
              v-slot="{ errors }"
              vid="newTeacherName"
              @autoscroll="onValidateAutoscroll"
            >
              <v-text-field
                counter="255"
                label="ФИО преподавателя"
                v-model="newTeacher"
                :error-messages="errors"
                @input="onNewTeacherChanged"
              />
            </validated-expand-field>
            <!--        Создание дисциплин    -->
            <validated-expand-field
              :condition="displayTeachers.length !== 0 || (addTeachersField && newTeacher.length >= 1)"
              name="название предмета"
              v-slot="{ errors }"
              vid="lessonSubjects"
              @autoscroll="onValidateAutoscroll"
            >
              <v-select
                v-model="customSubject"
                :items="disciplines"
                label="Название предмета"
                @input="onLessonSubjectChanged"
                :error-messages="errors"
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="customSubject === 'Создать'"
              name="новый предмет"
              rules="required|min:1|max:255"
              v-slot="{ errors }"
              vid="lessonCustomSubject"
              @autoscroll="onValidateAutoscroll"
            >
              <v-text-field
                counter="255"
                label="Название предмета"
                v-model="formLesson.discipline.name"
                :error-messages="errors"
              />
            </validated-expand-field>
            <!--        Аудитория    -->
            <validated-expand-field
              :condition="formLesson.discipline.name !== ''"
              rules="required|min:1|max:255"
              name="аудитория"
              v-slot="{ errors }"
              vid="lessonAuditory"
              @autoscroll="onValidateAutoscroll"
            >
              <v-text-field
                counter="255"
                label="Аудитория"
                v-model="formLesson.auditory"
                :error-messages="errors"
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="!invalid"
              rules="max:1024"
              name="комментарий"
              v-slot="{ errors }"
              vid="lessonComment"
              @autoscroll="onValidateAutoscroll"
            >
              <v-textarea
                v-model="formLesson.comment"
                label="Комментарий"
                filled
                auto-grow
                counter="1024"
                placeholder="(до 1024 символов)"
                :error-messages="errors"
              />
            </validated-expand-field>
            <conditional-expand-field
              :condition="!invalid"
              vid="lessonCreateButton"
              @autoscroll="onValidateAutoscroll"
            >
              <v-btn
                block
                :disabled="invalid"
                @click="passes(convertLessonToDtoAndSubmit)"
              >
                <slot>Отправить</slot>
              </v-btn>
            </conditional-expand-field>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </validation-observer>
</template>
<script>
import ClassCard from './ClassCard'
import ConditionalExpandField from './ConditionalExpandField'
import ConditionalValidation from './ConditionalValidation'
import ValidatedExpandField from './ValidatedExpandField'
import { ValidationObserver, ValidationProvider } from 'vee-validate'
import TeacherService from '../services/teacher.service'
import DisciplineService from '../services/discipline.service'
import TimeService from '../services/time.service'
import TypeService from '../services/type.service'

export default {
  name: 'lesson-form',
  components: {
    ClassCard,
    ConditionalExpandField,
    ConditionalValidation,
    ValidatedExpandField,
    ValidationObserver,
    ValidationProvider
  },
  data () {
    return {
      currentSchedule: this.$store.state.schedule.sch,
      lessonRepeatType: ['Каждую', 'Нечётные', 'Чётные', 'Определённые даты'],
      lessonDays: ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота', 'Воскресенье'],
      lessonTypes: ['Лекция', 'Практика', 'Лабораторные работы', 'Создать'],
      lessonTimes: ['9:30-11:05', '11:15-12:50', '13:00-14:35', '15:00-16:30', 'Создать'],
      teachers: ['Гадасин Д. В.', 'Беленькая М. Н.', 'Создать'],
      disciplines: ['Технологии и средства облачных сервисов', 'Создать'],
      textTimesStart: ['', ''],
      textTimesEnd: ['', ''],
      customType: '',
      weekType: '',
      customTimes: '',
      customSubject: '',
      startTime: null,
      endTime: null,
      addTeachersField: false,
      selectedTeachers: [],
      displayTeachers: [],
      newTeacher: '',
      dayOfTheWeekChoice: '',
      showDatesOverlay: false,
      fetchedBackEndData: {},
      useSemesterDates: false,
      useClocksToEnterTime: false,
      test: false // Флаг на осуществление тестирования с тестовыми данными
    }
  },
  props: {
    formLesson: {
      type: Object,
      required: true
    },
    submitLesson: {
      type: Function,
      required: true
    }
  },
  computed: {
    previewLesson () {
      let teacherNames = []
      this.formLesson.teacherList.forEach(teacher => {
        teacherNames.push(teacher.name)
      })
      return {
        start: this.formLesson.time.begin,
        end: this.formLesson.time.end,
        discipline: this.formLesson.discipline.name,
        type: this.formLesson.type.name,
        teachers: teacherNames,
        auditorium: this.formLesson.auditory,
        comment: this.formLesson.comment
      }
    },
    canChooseLessonType () {
      if (this.weekType !== 'Определённые даты') {
        return this.dayOfTheWeekChoice !== '' && this.formLesson.dates.length === 2
      }
      return this.formLesson.dates.length !== 0
    },
    canSelectTeachers () {
      return (this.textTimesStart[0] !== '' && this.textTimesStart[1] !== '' &&
        this.textTimesEnd[0] !== '' && this.textTimesEnd[1] !== '') || (
        this.formLesson.time.begin !== '' && this.formLesson.time.end !== '')
    }
  },
  mounted () {
    if (!this.test) {
      TeacherService.get().then(teachers => {
        let teacherNames = []
        this.fetchedBackEndData.teachers = teachers
        teachers.forEach(teacher => { teacherNames.push(teacher.name) })
        this.teachers = teacherNames
        this.teachers.push('Создать')
      }, error => {
        console.error(error)
      })
      DisciplineService.get().then(disciplines => {
        let disciplineNames = []
        this.fetchedBackEndData.disciplines = disciplines
        disciplines.forEach(discipline => { disciplineNames.push(discipline.name) })
        this.disciplines = disciplineNames
        this.disciplines.push('Создать')
      })
      TimeService.get().then(times => {
        let timeNames = []
        this.fetchedBackEndData.times = times
        times.forEach(time => { timeNames.push(time.name) })
        this.lessonTimes = timeNames
        this.lessonTimes.push('Создать')
      })
      TypeService.get().then(types => {
        let typeNames = []
        this.fetchedBackEndData.types = types
        types.forEach(type => { typeNames.push(type.name) })
        this.lessonTypes = typeNames
        this.lessonTypes.push('Создать')
      })
    }
  },
  methods: {
    convertLessonToDtoAndSubmit () {
      this.formLesson.weekType = this.convertWeekType(this.weekType)
      this.formLesson.weekdays = this.dayOfTheWeekChoice
      // Скопировать ид расписания
      let schId = this.currentSchedule.id
      this.formLesson.schId = schId
      this.formLesson.type.schId = schId
      this.formLesson.discipline.schId = schId
      this.formLesson.time.schId = schId
      if (this.addTeachersField) {
        this.formLesson.teacherList.forEach(teacher => {
          if (teacher.name === this.newTeacher) {
            teacher.schId = schId
          }
        })
      }
      if (this.weekType !== 'Определённые даты') {
        this.formLesson.startDate = this.formLesson.dates[0]
        this.formLesson.endDate = this.formLesson.dates[1]
        this.formLesson.dates.splice(0, this.formLesson.dates.length)
      }
      console.log('Submit lesson')
      console.log(this.formLesson)
      this.submitLesson()
      // Вернуть даты на место
      this.formLesson.dates.push(this.formLesson.startDate)
      this.formLesson.dates.push(this.formLesson.endDate)
    },
    onValidateAutoscroll (elem) {
      this.$vuetify.goTo(elem.$el, elem.options)
    },
    convertWeekType (weekType) {
      switch (weekType) {
        case 'Каждую':
          return 2 // WeekType.Any
        case 'Чётные':
          return 0 // WeekType.Even
        case 'Нечётные':
          return 1 // WeekType.Odd
        default:
          return 3 // WeekType.Dates
      }
    },
    onWeekTypeChanged () {
      this.formLesson.dates.splice(0, this.formLesson.dates.length)
      this.dayOfTheWeekChoice = ''
      this.useSemesterDates = false
    },
    onSemesterDatesChange () {
      if (this.useSemesterDates) {
        this.formLesson.dates.splice(0, this.formLesson.dates.length)
        this.formLesson.dates.push(this.currentSchedule.start)
        this.formLesson.dates.push(this.currentSchedule.stop)
      } else {
        this.formLesson.dates.splice(0, this.formLesson.dates.length)
      }
    },
    onLessonTypeChanged () {
      if (this.customType !== 'Создать') {
        this.fetchedBackEndData.types.forEach(type => {
          if (type.name === this.customType) {
            this.formLesson.type.id = type.id
          }
        })
        this.formLesson.type.name = this.customType
      } else {
        this.formLesson.type.id = null
        this.formLesson.type.name = ''
      }
    },
    resetTimeChoice () {
      this.startTime = null
      this.endTime = null
      this.formLesson.time.id = null
      this.formLesson.time.name = ''
      this.formLesson.time.begin = ''
      this.formLesson.time.end = ''
    },
    onTextTimesChanged (valid) {
      if (valid) {
        if (this.textTimesStart[0] !== '' && this.textTimesStart[1] !== '') {
          if (this.textTimesStart[0].length === 1) {
            this.textTimesStart[0] = 0 + this.textTimesStart[0]
          }
          if (this.textTimesStart[1].length === 1) {
            this.textTimesStart[1] = 0 + this.textTimesStart[1]
          }
          this.formLesson.time.begin = this.textTimesStart[1] + ':' + this.textTimesStart[0]
          this.startTime = this.formLesson.time.begin
        }
        if (this.textTimesEnd[0] !== '' && this.textTimesEnd[1] !== '') {
          if (this.textTimesEnd[0].length === 1) {
            this.textTimesEnd[0] = 0 + this.textTimesEnd[0]
          }
          if (this.textTimesEnd[1].length === 1) {
            this.textTimesEnd[1] = 0 + this.textTimesEnd[1]
          }
          this.formLesson.time.end = this.textTimesEnd[1] + ':' + this.textTimesEnd[0]
          this.endTime = this.formLesson.time.end
        }
      } else {
        this.formLesson.time.begin = ''
        this.formLesson.time.end = ''
      }
    },
    onLessonTimesChanged () {
      if (this.customTimes !== 'Создать') {
        let begin = ''
        let end = ''
        let timeId = null
        let name = ''
        this.fetchedBackEndData.times.forEach(time => {
          if (this.customTimes === time.name) {
            begin = time.begin
            end = time.end
            timeId = time.id
            name = time.name
          }
        })
        this.useClocksToEnterTime = false
        this.formLesson.time.name = name
        this.formLesson.time.id = timeId
        this.formLesson.time.begin = begin
        this.formLesson.time.end = end
      } else {
        this.resetTimeChoice()
      }
    },
    onLessonStartTimeChanged () {
      this.formLesson.time.begin = this.startTime
    },
    onLessonEndTimeChanged () {
      this.formLesson.time.end = this.endTime
    },
    onLessonTeachersChanged () {
      this.displayTeachers.splice(0, this.displayTeachers.length)
      this.selectedTeachers.forEach(teacher => {
        if (teacher !== 'Создать') {
          this.displayTeachers.push(this.convertTeacherNameToObject(teacher))
        }
      })
      if (this.selectedTeachers.find((teacherName) => { return teacherName === 'Создать' })) {
        this.addTeachersField = true
        this.displayTeachers.push(this.convertTeacherNameToObject(this.newTeacher))
      } else {
        this.addTeachersField = false
      }
      this.formLesson.teacherList = this.displayTeachers
    },
    convertTeacherNameToObject (teacherName) {
      // Конвертирование из имени препода в его объект
      for (let teacher of this.fetchedBackEndData.teachers) {
        if (teacher.name === teacherName) {
          return {
            id: teacher.id,
            schId: teacher.schedule,
            name: teacher.name,
            info: teacher.info
          }
        }
      }
      return {
        id: null,
        schId: null,
        name: teacherName,
        info: ''
      }
    },
    onNewTeacherChanged () {
      this.displayTeachers.splice(0, this.displayTeachers.length)
      this.selectedTeachers.forEach(teacher => {
        if (teacher !== 'Создать') {
          this.displayTeachers.push(this.convertTeacherNameToObject(teacher))
        }
      })
      this.displayTeachers.push(this.convertTeacherNameToObject(this.newTeacher))
      this.formLesson.teacherList = this.displayTeachers
    },
    onLessonSubjectChanged () {
      if (this.customSubject !== 'Создать') {
        this.fetchedBackEndData.disciplines.forEach(discipline => {
          if (discipline.name === this.customSubject) {
            this.formLesson.discipline.id = discipline.id
          }
        })
        this.formLesson.discipline.name = this.customSubject
      } else {
        this.formLesson.discipline.id = null
        this.formLesson.discipline.name = ''
      }
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
