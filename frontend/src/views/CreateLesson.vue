<template>
  <validation-observer
    id="create-lesson"
    v-slot="{ invalid, validated, passes, validate, errors }"
  >
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
                :lesson="newLesson"
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
                v-model="newLesson.weekType"
                :items="lessonRepeatType"
                label="Тип недели"
                :error-messages="errors"
                @input="onWeekTypeChanged"
              />
            </validation-provider>
            <validated-expand-field
              :condition="newLesson.weekType === 'Период'"
              name="период"
              vid="lessonPeriod"
              @autoscroll="onValidateAutoscroll"
            >
              <v-date-picker
                v-model="newLesson.dates"
                range
                full-width
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="newLesson.weekType === 'Определённые даты'"
              name="даты"
              vid="lessonDates"
              @autoscroll="onValidateAutoscroll"
            >
              <v-date-picker
                v-model="newLesson.dates"
                multiple
                full-width
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="newLesson.weekType && newLesson.weekType !== 'Период' && newLesson.weekType !== 'Определённые даты'"
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
                v-model="newLesson.type"
              />
            </validated-expand-field>
            <validated-expand-field
              name="время пары"
              :condition="newLesson.type && (newLesson.type !== '' || newLesson.type.length >= 1)"
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
              :condition="newLesson.endTime && newLesson.startTime"
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
                v-model="newLesson.name"
                :error-messages="errors"
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="newLesson.name !== ''"
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

export default {
  name: 'create-lesson',
  data () {
    return {
      lessonRepeatType: ['Каждую', 'Чётные', 'Нечётные', 'Период', 'Определённые даты'],
      lessonDays: ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота', 'Воскресенье'],
      lessonTypes: ['Лекция', 'Практика', 'Лабораторные работы', 'Создать'],
      lessonTimes: ['9:30-11:05', '11:15-12:50', '13:00-14:35', '15:00-16:30', 'Создать'],
      teachers: ['Гадасин Д. В.', 'Беленькая М. Н.', 'Создать'],
      disciplines: ['Технологии и средства облачных сервисов', 'Создать'],
      customType: '',
      customTimes: '',
      customSubject: '',
      startTime: null,
      endTime: null,
      addTeachersField: false,
      selectedTeachers: [],
      displayTeachers: [],
      newTeacher: '',
      dayOfTheWeekChoice: '',
      newLesson: {
        weekType: '',
        dates: [],
        date: new Date(),
        type: '',
        name: '',
        teachers: [],
        auditory: '',
        startTime: '',
        endTime: '',
        comment: '',
        day: ''
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
        this.teachers = teachers
        this.teachers.push('Создать')
      }, error => {
        console.error(error)
      })
      DisciplineService.get().then(disciplines => {
        this.disciplines = disciplines
        this.disciplines.push('Создать')
      })
      TimeService.get().then(times => {
        this.lessonTimes = times
        this.lessonTimes.push('Создать')
      })
      TypeService.get().then(types => {
        this.lessonTypes = types
        this.lessonTypes.push('Создать')
      })
    }
  },
  methods: {
    createLesson () {
      console.log('Created lesson: ')
      this.newLesson.day = this.lessonDays[this.dayOfTheWeekChoice]
      console.log(this.newLesson)
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
      this.newLesson.type = ''
    },
    resetCustomTimes () {
      this.customTimes = ''
      this.newLesson.startTime = ''
      this.newLesson.endTime = ''
    },
    resetTimeChoice () {
      this.startTime = null
      this.endTime = null
      this.newLesson.startTime = ''
      this.newLesson.endTime = ''
    },
    resetChosenDates () {
      this.newLesson.dates.splice(0, this.newLesson.dates.length)
      this.newLesson.day = ''
    },
    resetCustomSubject () {
      this.customSubject = ''
      this.newLesson.name = ''
    },
    resetTeachers () {
      this.addTeachersField = false
      this.selectedTeachers.splice(0, this.selectedTeachers.length)
      this.displayTeachers.splice(0, this.displayTeachers.length)
      this.newTeacher = ''
      this.newLesson.teachers = []
    },
    resetAuditory () {
      this.newLesson.auditory = ''
    },
    resetComment () {
      this.newLesson.comment = ''
    },
    onLessonTypeChanged () {
      if (this.customType !== 'Создать') {
        this.newLesson.type = this.customType
      } else {
        this.newLesson.type = ''
      }
      this.resetCustomTimes()
      this.resetTeachers()
      this.resetCustomSubject()
      this.resetAuditory()
      this.resetComment()
    },
    onLessonTimesChanged () {
      if (this.customTimes !== 'Создать') {
        this.newLesson.startTime = this.customTimes.slice(0, this.customTimes.indexOf('-'))
        this.newLesson.endTime = this.customTimes.slice(this.customTimes.indexOf('-') + 1)
      } else {
        this.resetTimeChoice()
      }
      this.resetTeachers()
      this.resetCustomSubject()
      this.resetAuditory()
      this.resetComment()
    },
    onLessonStartTimeChanged () {
      this.newLesson.startTime = this.startTime
    },
    onLessonEndTimeChanged () {
      this.newLesson.endTime = this.endTime
    },
    onLessonTeachersChanged () {
      this.displayTeachers.splice(0, this.displayTeachers.length)
      this.selectedTeachers.forEach(teacher => { this.displayTeachers.push(teacher) })
      if (this.selectedTeachers.find((teacherName) => { return teacherName === 'Создать' })) {
        this.addTeachersField = true
        this.displayTeachers.splice(this.newLesson.teachers.indexOf('Создать'), 1)
        this.displayTeachers.push(this.newTeacher)
        console.log('Add teachers triggered')
      } else {
        this.addTeachersField = false
        console.log('Add teachers field disabled')
      }
      this.newLesson.teachers = this.displayTeachers
      this.newLesson.name = ''
      this.resetCustomSubject()
      this.resetAuditory()
      this.resetComment()
    },
    onNewTeacherChanged () {
      this.displayTeachers.splice(0, this.displayTeachers.length)
      this.selectedTeachers.forEach(teacher => { this.displayTeachers.push(teacher) })
      this.displayTeachers.splice(this.newLesson.teachers.indexOf('Создать'), 1)
      this.displayTeachers.push(this.newTeacher)
      this.newLesson.teachers = this.displayTeachers
    },
    onLessonSubjectChanged () {
      if (this.customSubject !== 'Создать') {
        this.newLesson.name = this.customSubject
      } else {
        this.newLesson.name = ''
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
