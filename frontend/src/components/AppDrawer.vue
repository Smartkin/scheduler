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
    </template>
    <v-list>
      <v-list-item
        v-for="(schedule, i) in schedules"
        :key="i"
        to="/schedule"
        v-ripple
      >
        {{ schedule.name }}
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  data () {
    return {
      currentDrawer: false,
      schedules: [{
        name: 'БСТ1602'
      }]
    }
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
    }
  },
  props: {
    drawer: {
      type: Boolean,
      required: true
    }
  }
}
</script>
