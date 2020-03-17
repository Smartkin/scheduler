<template>
  <validation-observer
    v-if="requestsDone"
    id="modify-lesson"
    v-slot="{ invalid, passes }"
  >
    <v-row>
      <v-col class="ml-3">
        <back-button/>
      </v-col>
    </v-row>
    <v-form>
      <v-container fluid>
        <v-row>
          <v-col md="6" cols="12">
            <v-card>
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
              />
            </validation-provider>
<!--       Выбор дат проведения занятий     -->
            <validated-expand-field
              :condition="weekType && weekType !== 'Определённые даты'"
              name="период"
              vid="lessonPeriod"
              :rules="{}"
            >
              <v-date-picker
                v-model="editLesson.dates"
                range
                full-width
                reactive
                first-day-of-week="1"
                :readonly="readOnlyCalendar"
              >
                Период проведения занятия
              </v-date-picker>
            </validated-expand-field>
            <validated-expand-field
              :condition="weekType && weekType !== 'Определённые даты'"
              name="даты семестра"
              vid="useSemesterDates"
              :rules="{}"
            >
              <v-checkbox
                v-model="useSemesterDates"
                @change="onSemesterDatesChange"
                label="Использовать даты семестра"
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="weekType === 'Определённые даты'"
              name="даты"
              vid="lessonDates"
              :rules="{}"
            >
              <v-date-picker
                v-model="editLesson.dates"
                first-day-of-week="1"
                multiple
                full-width
              >
                Даты занятий
              </v-date-picker>
            </validated-expand-field>
            <validated-expand-field
              :condition="weekType && weekType !== 'Определённые даты'"
              name="день недели"
              vid="dayOfTheWeek"
            >
              <v-label>День недели</v-label>
              <v-radio-group row v-model="dayOfTheWeekChoice">
                <v-radio v-for="(day, i) in lessonDays" :key="i" :label="day"/>
              </v-radio-group>
            </validated-expand-field>
            <validated-expand-field
              :condition="editLesson.dates.length !== 0 || dayOfTheWeekChoice !== ''"
              v-slot="{ errors }"
              name="тип пары"
              vid="lessonTypeStr"
            >
              <v-select
                v-model="customType"
                :items="lessonTypes"
                label="Тип пары"
                :error-messages="errors"
              />
            </validated-expand-field>
            <validated-expand-field
              name="название типа пары"
              :condition="customType === 'Создать'"
              rules="required|min:1|max:255"
              v-slot="{ errors }"
              vid="lessonTypeStringField"
            >
              <v-text-field
                counter="255"
                label="Название типа пары"
                :error-messages="errors"
                v-model="editLesson.type.name"
              />
            </validated-expand-field>
            <validated-expand-field
              name="время пары"
              :condition="editLesson.type && (editLesson.type.name !== '' || editLesson.type.name.length >= 1)"
              v-slot="{ errors }"
              vid="lessonTimes"
            >
              <v-select
                v-model="customTimes"
                :items="lessonTimes"
                label="Время пары"
                :error-messages="errors"
              />
            </validated-expand-field>
<!--        Создание новой пары времени для пар    -->
            <conditional-expand-field
              :condition="customTimes === 'Создать'"
              vid="lessonCustomTimes"
            >
              <v-row>
                <v-col cols="12">
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
                      v-model="editLesson.time.name"
                    />
                  </conditional-validation>
                </v-col>
                <v-col md="6" cols="12">
                  <v-label>Начало</v-label>
                  <conditional-validation
                    :condition="customTimes === 'Создать'"
                    rules="required"
                    name="время начала"
                    v-slot="{ errors }"
                  >
                    <v-time-picker
                      full-width
                      scrollable
                      format="24hr"
                      v-model="startTime"
                      :max="endTime"
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
                    :condition="customTimes === 'Создать'"
                    rules="required"
                    name="время конца"
                    v-slot="{ errors }"
                  >
                    <v-time-picker
                      full-width
                      scrollable
                      format="24hr"
                      v-model="endTime"
                      :min="startTime"
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
              :condition="editLesson.time.begin && editLesson.time.end"
              name="список преподавателей"
              v-slot="{ errors }"
              vid="lessonTeachers"
            >
              <v-select
                v-model="selectedTeachers"
                multiple
                label="Список преподавателей"
                :items="teachers"
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
            >
              <v-text-field
                counter="255"
                label="ФИО преподавателя"
                v-model="newTeacher"
                :error-messages="errors"
              />
            </validated-expand-field>
<!--        Создание дисциплин    -->
            <validated-expand-field
              :condition="displayTeachers.length !== 0 || (addTeachersField && newTeacher.length >= 1)"
              name="название предмета"
              v-slot="{ errors }"
              vid="lessonSubjects"
            >
              <v-select
                v-model="customSubject"
                :items="disciplines"
                label="Название предмета"
                :error-messages="errors"
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="customSubject === 'Создать'"
              name="новый предмет"
              rules="required|min:1|max:255"
              v-slot="{ errors }"
              vid="lessonCustomSubject"
            >
              <v-text-field
                counter="255"
                label="Название предмета"
                v-model="editLesson.discipline.name"
                :error-messages="errors"
              />
            </validated-expand-field>
<!--        Аудитория    -->
            <validated-expand-field
              :condition="editLesson.discipline.name !== ''"
              rules="required|min:1|max:255"
              name="аудитория"
              v-slot="{ errors }"
              vid="lessonAuditory"
            >
              <v-text-field
                counter="255"
                label="Аудитория"
                v-model="editLesson.auditory"
                :error-messages="errors"
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="!invalid"
              rules="max:1024"
              name="комментарий"
              v-slot="{ errors }"
              vid="lessonComment"
            >
              <v-textarea
                v-model="editLesson.comment"
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
            >
              <v-btn
                block
                :disabled="invalid"
                @click="passes(modifyLesson)"
              >
                Сохранить
              </v-btn>
            </conditional-expand-field>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </validation-observer>
</template>

<script>
import { ValidationObserver, ValidationProvider } from 'vee-validate'
import ClassCard from '../components/ClassCard'
import ValidatedExpandField from '../components/ValidatedExpandField'
import ConditionalExpandField from '../components/ConditionalExpandField'
import ConditionalValidation from '../components/ConditionalValidation'
import BackButton from '../components/BackButton'
import LessonService from '../services/lesson.service'
import DisciplineService from '../services/discipline.service'
import TimeService from '../services/time.service'
import TypeService from '../services/type.service'
import TeacherService from '../services/teacher.service'

export default {
  name: 'modify-lesson',
  data () {
    return {
      lessonRepeatType: ['Каждую', 'Нечётные', 'Чётные', 'Определённые даты'],
      lessonDays: ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота', 'Воскресенье'],
      lessonTypes: [],
      lessonTimes: [],
      teachers: [],
      disciplines: [],
      customType: '',
      weekType: '',
      customTimes: '',
      customSubject: '',
      startTime: null,
      endTime: null,
      requestsFinished: [],
      requestsDone: false,
      addTeachersField: false,
      readOnlyCalendar: false,
      selectedTeachers: [],
      displayTeachers: [],
      newTeacher: '',
      dayOfTheWeekChoice: '',
      fetchedBackEndData: {},
      useSemesterDates: false,
      currentSchedule: this.$store.state.schedule.sch,
      editLesson: {
        schId: null,
        weekType: '',
        dates: [],
        type: {
          id: null,
          schId: null,
          name: ''
        },
        discipline: {
          id: null,
          schId: null,
          name: ''
        },
        teacherList: [],
        auditory: '',
        time: {
          id: null,
          schId: null,
          name: '',
          begin: '',
          end: ''
        },
        comment: '',
        weekdays: 0,
        startDate: null,
        endDate: null
      }
    }
  },
  props: {
    id: {
      required: true
    }
  },
  components: {
    BackButton,
    ConditionalValidation,
    ConditionalExpandField,
    ValidatedExpandField,
    ClassCard,
    ValidationObserver,
    ValidationProvider
  },
  mounted () {
    this.getBackendData()
    let lessonServiceReqId = this.awaitRequest()
    LessonService.get(this.id).then(lesson => {
      this.editLesson = lesson
      this.editLesson.weekType = this.translateWeekType(this.editLesson.weekType)
      this.weekType = this.editLesson.weekType
      this.dayOfTheWeekChoice = this.getWeekdayNum(this.editLesson.weekdays)
      this.editLesson.dates = this.getDates(this.weekType)
      let disServiceReqId = this.awaitRequest()
      DisciplineService.get(lesson.discipline).then(discipline => {
        this.editLesson.discipline = discipline
        this.customSubject = discipline.name
        this.finishRequest(disServiceReqId)
      })
      let timeServReqId = this.awaitRequest()
      TimeService.get(lesson.time).then(time => {
        this.editLesson.time = time
        this.customTimes = this.editLesson.time.name
        this.finishRequest(timeServReqId)
      })
      let typeServReqId = this.awaitRequest()
      TypeService.get(lesson.type).then(type => {
        this.editLesson.type = type
        this.customType = this.editLesson.type.name
        this.finishRequest(typeServReqId)
      })
      let teachers = []
      let teacherServReqId = this.awaitRequest()
      for (let teachId of this.editLesson.teacherList) {
        TeacherService.get(teachId).then(teacher => {
          teachers.push(teacher)
          this.selectedTeachers.push(teacher.name)
          if (teachers.length === this.editLesson.teacherList.length) {
            this.editLesson.teacherList = teachers
            this.displayTeachers = this.editLesson.teacherList
            this.finishRequest(teacherServReqId)
          }
        })
      }
      this.finishRequest(lessonServiceReqId)
    })
  },
  computed: {
    previewLesson () {
      let teacherNames = []
      this.editLesson.teacherList.forEach(teacher => {
        teacherNames.push(teacher.name)
      })
      return {
        start: this.editLesson.time.begin,
        end: this.editLesson.time.end,
        discipline: this.editLesson.discipline.name,
        type: this.editLesson.type.name,
        teachers: teacherNames,
        auditorium: this.editLesson.auditory,
        comment: this.editLesson.comment
      }
    }
  },
  methods: {
    getBackendData () {
      let tsRID = this.awaitRequest()
      let dsRID = this.awaitRequest()
      let tisRID = this.awaitRequest()
      let tysRID = this.awaitRequest()
      TeacherService.get().then(teachers => {
        let teacherNames = []
        this.fetchedBackEndData.teachers = teachers
        teachers.forEach(teacher => { teacherNames.push(teacher.name) })
        this.teachers = teacherNames
        this.teachers.push('Создать')
        this.finishRequest(tsRID)
      }, error => {
        console.error(error)
      })
      DisciplineService.get().then(disciplines => {
        let disciplineNames = []
        this.fetchedBackEndData.disciplines = disciplines
        disciplines.forEach(discipline => { disciplineNames.push(discipline.name) })
        this.disciplines = disciplineNames
        this.disciplines.push('Создать')
        this.finishRequest(dsRID)
      })
      TimeService.get().then(times => {
        let timeNames = []
        this.fetchedBackEndData.times = times
        times.forEach(time => { timeNames.push(time.name) })
        this.lessonTimes = timeNames
        this.lessonTimes.push('Создать')
        this.finishRequest(tisRID)
      })
      TypeService.get().then(types => {
        let typeNames = []
        this.fetchedBackEndData.types = types
        types.forEach(type => { typeNames.push(type.name) })
        this.lessonTypes = typeNames
        this.lessonTypes.push('Создать')
        this.finishRequest(tysRID)
      })
    },
    modifyLesson () {
      this.editLesson.weekType = this.convertWeekType(this.weekType)
      this.editLesson.weekdays = this.dayOfTheWeekChoice
      // Скопировать ид расписания
      let schId = this.$store.state.schedule.sch.id
      this.editLesson.schId = schId
      this.editLesson.type.schId = schId
      this.editLesson.discipline.schId = schId
      this.editLesson.time.schId = schId
      if (this.addTeachersField) {
        this.editLesson.teacherList.forEach(teacher => {
          if (teacher.name === this.newTeacher) {
            teacher.schId = schId
          }
        })
      }
      if (this.weekType !== 'Определённые даты') {
        this.editLesson.startDate = this.editLesson.dates[0]
        this.editLesson.endDate = this.editLesson.dates[1]
        this.editLesson.dates.splice(0, this.editLesson.dates.length)
      }
      console.log('Modified lesson: ')
      console.log(this.editLesson)
      LessonService.modify(this.id, this.editLesson).then(() => {
        this.$router.push('/lesson/' + this.id)
      })
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
    translateWeekType (weekType) {
      switch (weekType) {
        case 'Any':
          return 'Каждую'
        case 'Odd':
          return 'Нечётные'
        case 'Even':
          return 'Чётные'
        case 'Dates':
          return 'Определённые даты'
        default:
          return 'Неизвестно'
      }
    },
    getDates (weekType) {
      switch (weekType) {
        case 'Каждую':
        case 'Чётные':
        case 'Нечётные':
          let dates = Array(2)
          dates[0] = this.editLesson.startDate
          dates[1] = this.editLesson.endDate
          return dates
        default:
          return this.editLesson.dates
      }
    },
    getWeekdayNum (weekDay) {
      switch (weekDay) {
        case 'MONDAY':
          return 0
        case 'TUESDAY':
          return 1
        case 'WEDNESDAY':
          return 2
        case 'THURSDAY':
          return 3
        case 'FRIDAY':
          return 4
        case 'SATURDAY':
          return 5
        case 'SUNDAY':
          return 6
        default:
          return 0
      }
    },
    onSemesterDatesChange () {
      if (this.useSemesterDates) {
        this.editLesson.dates.splice(0, this.editLesson.dates.length)
        this.editLesson.dates.push(this.currentSchedule.start)
        this.editLesson.dates.push(this.currentSchedule.stop)
        this.readOnlyCalendar = true
      } else {
        this.editLesson.dates.splice(0, this.editLesson.dates.length)
        this.readOnlyCalendar = false
      }
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
    awaitRequest () {
      let reqId = this.requestsFinished.length
      this.requestsFinished.push(false)
      return reqId
    },
    finishRequest (reqId) {
      this.requestsFinished[reqId] = true
      for (let i in this.requestsFinished) {
        if (!this.requestsFinished[i]) {
          this.requestsDone = false
          return
        }
      }
      this.requestsDone = true
      console.log(this.requestsFinished)
    }
  }
}
</script>
