<template>
  <validation-observer
    id="create-lesson"
    v-slot="{ invalid, passes }"
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
                @input="onWeekTypeChanged"
              />
            </validation-provider>
<!--       Выбор дат проведения занятий     -->
            <validated-expand-field
              :condition="weekType && weekType !== 'Определённые даты'"
              name="период"
              vid="lessonPeriod"
              :rules="{}"
              @autoscroll="onValidateAutoscroll"
            >
              <v-date-picker
                v-model="newLesson.dates"
                range
                full-width
                reactive
                first-day-of-week="1"
                :readonly="readOnlyCalendar"
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="weekType && weekType !== 'Определённые даты'"
              name="даты семестра"
              vid="useSemesterDates"
              :rules="{}"
              @autoscroll="onValidateAutoscroll"
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
              @autoscroll="onValidateAutoscroll"
            >
              <v-date-picker
                v-model="newLesson.dates"
                first-day-of-week="1"
                multiple
                full-width
              />
            </validated-expand-field>
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
              :condition="newLesson.dates.length !== 0 || dayOfTheWeekChoice !== ''"
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
                v-model="newLesson.type.name"
              />
            </validated-expand-field>
            <validated-expand-field
              name="время пары"
              :condition="newLesson.type && (newLesson.type.name !== '' || newLesson.type.name.length >= 1)"
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
                      v-model="newLesson.time.name"
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
              :condition="newLesson.time.begin && newLesson.time.end"
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
                v-model="newLesson.discipline.name"
                :error-messages="errors"
              />
            </validated-expand-field>
<!--        Аудитория    -->
            <validated-expand-field
              :condition="newLesson.discipline.name !== ''"
              rules="required|min:1|max:255"
              name="аудитория"
              v-slot="{ errors }"
              vid="lessonAuditory"
              @autoscroll="onValidateAutoscroll"
            >
              <v-text-field
                counter="255"
                label="Аудитория"
                v-model="newLesson.auditory"
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
                v-model="newLesson.comment"
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
                @click="passes(createLesson)"
              >
                Создать
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
import TeacherService from '../services/teacher.service'
import DisciplineService from '../services/discipline.service'
import TimeService from '../services/time.service'
import TypeService from '../services/type.service'
import LessonService from '../services/lesson.service'

export default {
  name: 'create-lesson',
  data () {
    return {
      lessonRepeatType: ['Каждую', 'Нечётные', 'Чётные', 'Определённые даты'],
      lessonDays: ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота', 'Воскресенье'],
      lessonTypes: ['Лекция', 'Практика', 'Лабораторные работы', 'Создать'],
      lessonTimes: ['9:30-11:05', '11:15-12:50', '13:00-14:35', '15:00-16:30', 'Создать'],
      teachers: ['Гадасин Д. В.', 'Беленькая М. Н.', 'Создать'],
      disciplines: ['Технологии и средства облачных сервисов', 'Создать'],
      customType: '',
      weekType: '',
      customTimes: '',
      customSubject: '',
      startTime: null,
      endTime: null,
      addTeachersField: false,
      readOnlyCalendar: false,
      selectedTeachers: [],
      displayTeachers: [],
      newTeacher: '',
      dayOfTheWeekChoice: '',
      fetchedBackEndData: {},
      useSemesterDates: false,
      currentSchedule: this.$store.state.schedule.sch,
      newLesson: {
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
      },
      test: false // Флаг на осуществление тестирования с тестовыми данными
    }
  },
  components: {
    ConditionalValidation,
    ConditionalExpandField,
    ValidatedExpandField,
    ClassCard,
    ValidationObserver,
    ValidationProvider
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
  computed: {
    previewLesson () {
      let teacherNames = []
      this.newLesson.teacherList.forEach(teacher => {
        teacherNames.push(teacher.name)
      })
      return {
        start: this.newLesson.time.begin,
        end: this.newLesson.time.end,
        discipline: this.newLesson.discipline.name,
        type: this.newLesson.type.name,
        teachers: teacherNames,
        auditorium: this.newLesson.auditory,
        comment: this.newLesson.comment
      }
    }
  },
  methods: {
    createLesson () {
      this.newLesson.weekType = this.convertWeekType(this.weekType)
      this.newLesson.weekdays = this.dayOfTheWeekChoice
      // Скопировать ид расписания
      let schId = this.$store.state.schedule.sch.id
      this.newLesson.schId = schId
      this.newLesson.type.schId = schId
      this.newLesson.discipline.schId = schId
      this.newLesson.time.schId = schId
      if (this.addTeachersField) {
        this.newLesson.teacherList.forEach(teacher => {
          if (teacher.name === this.newTeacher) {
            teacher.schId = schId
          }
        })
      }
      if (this.weekType !== 'Определённые даты') {
        this.newLesson.startDate = this.newLesson.dates[0]
        this.newLesson.endDate = this.newLesson.dates[1]
        this.newLesson.dates.splice(0, this.newLesson.dates.length)
      }
      console.log('Created lesson: ')
      console.log(this.newLesson)
      LessonService.create(this.newLesson).then(() => {
        this.$router.push('/schedule/' + schId)
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
    onSemesterDatesChange () {
      if (this.useSemesterDates) {
        this.newLesson.dates.splice(0, this.newLesson.dates.length)
        this.newLesson.dates.push(this.currentSchedule.start)
        this.newLesson.dates.push(this.currentSchedule.stop)
        this.readOnlyCalendar = true
      } else {
        this.newLesson.dates.splice(0, this.newLesson.dates.length)
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
    onWeekTypeChanged () {
      this.resetAllInputs()
    },
    resetAllInputs () {
      this.resetType()
      this.resetCustomTimes()
      this.resetTimeChoice()
      this.resetChosenDates()
      this.resetCustomSubject()
      this.resetTeachers()
      this.resetAuditory()
      this.resetComment()
    },
    resetType () {
      this.customType = ''
      this.newLesson.type.id = null
      this.newLesson.type.name = ''
    },
    resetCustomTimes () {
      this.customTimes = ''
      this.newLesson.time.id = null
      this.newLesson.time.begin = ''
      this.newLesson.time.end = ''
    },
    resetTimeChoice () {
      this.startTime = null
      this.endTime = null
      this.newLesson.time.id = null
      this.newLesson.time.begin = ''
      this.newLesson.time.end = ''
    },
    resetChosenDates () {
      this.newLesson.dates.splice(0, this.newLesson.dates.length)
      this.newLesson.day = ''
      this.useSemesterDates = false
    },
    resetCustomSubject () {
      this.customSubject = ''
      this.newLesson.discipline.id = null
      this.newLesson.discipline.name = ''
    },
    resetTeachers () {
      this.addTeachersField = false
      this.selectedTeachers.splice(0, this.selectedTeachers.length)
      this.displayTeachers.splice(0, this.displayTeachers.length)
      this.newTeacher = ''
      this.newLesson.teacherList = []
    },
    resetAuditory () {
      this.newLesson.auditory = ''
    },
    resetComment () {
      this.newLesson.comment = ''
    },
    onLessonTypeChanged () {
      if (this.customType !== 'Создать') {
        this.fetchedBackEndData.types.forEach(type => {
          if (type.name === this.customType) {
            this.newLesson.type.id = type.id
          }
        })
        this.newLesson.type.name = this.customType
      } else {
        this.newLesson.type.id = null
        this.newLesson.type.name = ''
      }
      this.resetCustomTimes()
      this.resetTeachers()
      this.resetCustomSubject()
      this.resetAuditory()
      this.resetComment()
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
        this.newLesson.time.name = name
        this.newLesson.time.id = timeId
        this.newLesson.time.begin = begin
        this.newLesson.time.end = end
      } else {
        this.resetTimeChoice()
      }
      this.resetTeachers()
      this.resetCustomSubject()
      this.resetAuditory()
      this.resetComment()
    },
    onLessonStartTimeChanged () {
      this.newLesson.time.begin = this.startTime
    },
    onLessonEndTimeChanged () {
      this.newLesson.time.end = this.endTime
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
        console.log('Add teachers triggered')
      } else {
        this.addTeachersField = false
        console.log('Add teachers field disabled')
      }
      this.newLesson.teacherList = this.displayTeachers
      this.newLesson.discipline.name = ''
      this.resetCustomSubject()
      this.resetAuditory()
      this.resetComment()
    },
    onNewTeacherChanged () {
      this.displayTeachers.splice(0, this.displayTeachers.length)
      this.selectedTeachers.forEach(teacher => {
        if (teacher !== 'Создать') {
          this.displayTeachers.push(this.convertTeacherNameToObject(teacher))
        }
      })
      this.displayTeachers.push(this.convertTeacherNameToObject(this.newTeacher))
      this.newLesson.teacherList = this.displayTeachers
    },
    onLessonSubjectChanged () {
      if (this.customSubject !== 'Создать') {
        this.fetchedBackEndData.disciplines.forEach(discipline => {
          if (discipline.name === this.customSubject) {
            this.newLesson.discipline.id = discipline.id
          }
        })
        this.newLesson.discipline.name = this.customSubject
      } else {
        this.newLesson.discipline.id = null
        this.newLesson.discipline.name = ''
      }
      this.resetAuditory()
      this.resetComment()
    },
    onValidateAutoscroll (elem) {
      console.log('Received autoscroll request with options:')
      console.log(elem.options)
      this.$vuetify.goTo(elem.$el, elem.options)
    }
  }
}
</script>
