<template>
  <v-row dense>
    <v-col>
      <v-btn append :text="text" to="modify">
        Редактировать
      </v-btn>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'modify-button',
  props: {
    text: {
      type: Boolean,
      default: false
    }
  }
}
</script>
