<template>
  <v-expand-transition>
    <v-card
      class="transparent"
      flat
      v-show="condition"
    >
      <slot/>
    </v-card>
  </v-expand-transition>
</template>
<script>
export default {
  name: 'conditional-expand-field',
  props: {
    condition: {
      required: true
    }
  }
}
</script>
