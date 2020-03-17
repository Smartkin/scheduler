<template>
  <v-row dense>
    <v-col>
      <v-btn :text="text" @click="$router.back()">
        Вернуться
      </v-btn>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'back-button',
  props: {
    text: {
      type: Boolean,
      default: false
    }
  }
}
</script>
