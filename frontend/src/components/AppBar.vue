<template>
  <v-app-bar
    app
    dense
    clipped-left
  >
    <v-app-bar-nav-icon @click.stop="$emit('drawer-change', !drawer)"/>
    <router-link to="/" style="text-decoration: none;" v-if="$vuetify.breakpoint.lgAndUp">
      <v-toolbar-title class="mr-4">Расписание вузов</v-toolbar-title>
    </router-link>
    <v-toolbar-items v-if="$vuetify.breakpoint.lgAndUp">
      <v-btn depressed to="/about">О приложении</v-btn>
    </v-toolbar-items>
  </v-app-bar>
</template>

<script>
export default {
  name: 'app-bar',
  data () {
    return {
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
