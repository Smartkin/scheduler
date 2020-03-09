<template>
  <v-row dense>
    <v-col>
      <v-btn @click="$router.back()">
        Вернуться
      </v-btn>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'back-button'
}
</script>
