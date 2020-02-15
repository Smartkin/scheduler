<template>
  <v-app id="scheduler">
    <app-bar :drawer="drawer" @drawer-change="onDrawerChange"/>
    <app-drawer :drawer="drawer" @drawer-change="onDrawerChange"/>
    <v-content>
      <router-view/>
    </v-content>
  </v-app>
</template>

<script>
import AppBar from './components/AppBar'
import AppDrawer from './components/AppDrawer'

export default {
  name: 'app',
  components: {
    AppBar,
    AppDrawer
  },
  data () {
    return {
      drawer: false
    }
  },
  mounted () {
    if (!this.$store.state.schedule.schedule) {
      this.$store.dispatch('schedule/set', 1)
    }
  },
  methods: {
    onDrawerChange: function (newDrawer) {
      this.drawer = newDrawer
    }
  }
}
</script>
