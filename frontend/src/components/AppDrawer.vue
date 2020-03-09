<template>
  <v-navigation-drawer
    clipped
    app
    width="350"
    v-model="resultDrawer"
  >
    <template v-if="$vuetify.breakpoint.mdAndDown">
      <v-list-item>
        <v-list-item-content>
          <router-link to="/" style="text-decoration: none;">
            <v-list-item-title class="title">
              Расписание вузов
            </v-list-item-title>
          </router-link>
          <v-list-item-subtitle class="text-wrap">
            <p>Расписание занятий доступное не только в браузерах, но и на смартфонах.</p>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-divider/>
      <v-list
        expand
      >
        <v-list-item to="/create_lesson">
          <v-list-item-icon>
            <v-icon>mdi-calendar-plus</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>Создать пару</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item to="/about">
          <v-list-item-icon>
            <v-icon>mdi-information</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>О приложении</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </template>
    <v-list>
      <v-list-item
        v-for="(schedule, i) in schedules"
        :key="i"
        @click="onScheduleChoice(schedule)"
        v-ripple
      >
        {{ schedule.groupName }}
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
import ScheduleService from '../services/schedule.service'

export default {
  data () {
    return {
      currentDrawer: false,
      schedules: [{
        groupName: 'БСТ1602'
      }]
    }
  },
  mounted () {
    this.getNewSchedules()
  },
  computed: {
    resultDrawer: {
      get: function () {
        return this.drawer
      },
      set: function (newDrawer) {
        this.currentDrawer = newDrawer
        this.$emit('drawer-change', newDrawer)
      }
    },
    updateSchedules () {
      return this.$store.state.data.schedulesDataUpdate
    }
  },
  props: {
    drawer: {
      type: Boolean,
      required: true
    }
  },
  watch: {
    updateSchedules: function (newUpdate) {
      if (newUpdate) {
        this.getNewSchedules()
        this.$store.dispatch('data/resetUpdatedSchedulesData')
      }
    }
  },
  methods: {
    onScheduleChoice (schedule) {
      let curSchedule = this.$store.state.schedule.sch
      console.log(curSchedule)
      console.log(schedule)
      if (curSchedule.id !== schedule.id) {
        this.$store.dispatch('schedule/directSet', schedule)
      }
      if (this.$router.currentRoute.path !== '/schedule/' + schedule.id) {
        this.$router.push('/schedule/' + schedule.id)
      }
    },
    getNewSchedules () {
      ScheduleService.get().then(schedules => {
        this.schedules = schedules
      })
    }
  }
}
</script>
