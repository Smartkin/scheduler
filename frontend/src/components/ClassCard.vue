<template>
  <v-card
    id="class-card"
    max-width="350"
    min-width="350"
    flat
    outlined
  >
    <v-row dense>
      <v-col class="ml-3 primary--text" cols="12">
        <v-row dense justify="space-between">
          <v-col>
            {{ lesson.type }}
          </v-col>
          <v-col>
            {{ lesson.auditory }}
          </v-col>
        </v-row>
      </v-col>
      <v-col class="ml-3">
        <div class="red--text">
          {{ lesson.startTime }}
        </div>
        <div class="green--text">
          {{ lesson.endTime }}
        </div>
      </v-col>
      <v-col class="text-wrap">
        {{ lesson.name }}
      </v-col>
    </v-row>
<!-- Список преподов -->
    <v-list class="mt-n9">
      <v-list-item v-for="(name, i) in lesson.teachers" :key="i" class="mb-n5 ml-n1 black--text">
        <v-list-item-title> {{ name }} </v-list-item-title>
      </v-list-item>
    </v-list>
  </v-card>
</template>

<script>
export default {
  props: {
    lesson: {
      type: Object
    }
  },
  computed: {
    currentLesson () {
      console.log(this.lesson)
      return this.lesson
    }
  },
  mounted () {
    console.log(this.lesson)
  }
}
</script>
