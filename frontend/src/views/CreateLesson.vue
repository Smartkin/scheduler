<template>
  <validation-observer id="create-lesson" v-slot="{ invalid, validated, passes, validate, errors }">
    <v-form>
      <v-container fluid>
        <v-row>
          <v-col md="6" cols="12">
            <v-card min-height="150px">
              <v-card-title>
                Предпросмотр
              </v-card-title>
              <class-card
                class="mx-auto"
                :lesson="newLesson"
              />
            </v-card>
          </v-col>
          <v-col style="max-height: 800px" class="overflow-y-auto">
            <validation-provider
              name="тип недели"
              rules="required"
              v-slot="{ errors }"
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
            >
              <v-date-picker
                v-model="newLesson.dates"
                multiple
                full-width
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="newLesson.dates.length !== 0 || (newLesson.weekType && newLesson.weekType !== 'Период' && newLesson.weekType !== 'Определённые даты')"
              v-slot="{ errors }"
              name="тип пары"
            >
              <v-select
                v-model="customType"
                :items="lessonType"
                label="Тип пары"
                :error-messages="errors"
                @input="onLessonTypeChanged"
              />
            </validated-expand-field>
            <validated-expand-field
              name="название типа пары"
              :condition="customType === 'Другое'"
              rules="required|min:6|max:255"
              v-slot="{ errors }"
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
              :condition="newLesson.type && (newLesson.type !== '' || newLesson.type.length() >= 6)"
              v-slot="{ errors }"
            >
              <v-select
                v-model="customTimes"
                :items="lessonTimes"
                label="Время пары"
                :error-messages="errors"
                @input="onLessonTimesChanged"
              />
            </validated-expand-field>
            <conditional-expand-field
              :condition="customTimes === 'Другое'"
            >
              <v-row>
                <v-col md="6" cols="12">
                  <v-label>Начало</v-label>
                  <conditional-validation
                    :condition="customTimes === 'Другое'"
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
                    :condition="customTimes === 'Другое'"
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
            <validated-expand-field
              :condition="newLesson.endTime && newLesson.startTime"
              name="список преподавателей"
              v-slot="{ errors }"
            >
              <v-select
                v-model="newLesson.teachers"
                multiple
                label="Список преподавателей"
                :items="teachers"
                @input="onLessonTeachersChanged"
                :error-messages="errors"
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="newLesson.teachers.length !== 0"
              name="название предмета"
              v-slot="{ errors }"
            >
              <v-select
                v-model="customSubject"
                :items="subjects"
                label="Название предмета"
                @input="onLessonSubjectChanged"
                :error-messages="errors"
              />
            </validated-expand-field>
            <validated-expand-field
              :condition="customSubject === 'Другое'"
              name="новый предмет"
              rules="required|min:1|max:255"
              v-slot="{ errors }"
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

export default {
  name: 'create-lesson',
  data () {
    return {
      lessonRepeatType: ['Каждую', 'Чётные', 'Нечётные', 'Период', 'Определённые даты'],
      lessonType: ['Лекция', 'Практика', 'Лабораторные работы', 'Другое'],
      lessonTimes: ['9:30-11:05', '11:15-12:50', '13:00-14:35', '15:00-16:30', 'Другое'],
      teachers: ['Гадасин Д. В.', 'Беленькая М. Н.', '+'],
      subjects: ['Технологии и средства облачных сервисов', 'Другое'],
      customType: '',
      customTimes: '',
      customSubject: '',
      startTime: null,
      endTime: null,
      addTeachersField: false,
      newLesson: {
        dates: [],
        date: new Date(),
        type: '',
        name: '',
        teachers: [],
        auditory: '',
        startTime: '',
        endTime: '',
        comment: ''
      }
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
  methods: {
    createLesson () {
      console.log('Created lesson: ')
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
      this.newLesson.dates.length = 0
    },
    resetCustomSubject () {
      this.customSubject = ''
      this.newLesson.name = ''
    },
    resetTeachers () {
      this.newLesson.teachers = []
    },
    resetAuditory () {
      this.newLesson.auditory = ''
    },
    resetComment () {
      this.newLesson.comment = ''
    },
    onLessonTypeChanged () {
      if (this.customType !== 'Другое') {
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
      if (this.customTimes !== 'Другое') {
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
      if (this.newLesson.teachers.find((teacherName) => { return teacherName === '+' }) === '+') {
        this.addTeachersField = true
      }
      this.newLesson.name = ''
      this.resetCustomSubject()
      this.resetAuditory()
      this.resetComment()
    },
    onLessonSubjectChanged () {
      if (this.customSubject !== 'Другое') {
        this.newLesson.name = this.customSubject
      } else {
        this.newLesson.name = ''
      }
      this.resetAuditory()
      this.resetComment()
    }
  }
}
</script>
