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
  data: function () {
    return {
      currentAutoscroll: true
    }
  },
  props: {
    condition: {
      required: true
    },
    autoscroll: {
      type: Boolean,
      default: true
    },
    options: {
      type: Object,
      default: function () {
        return {
          duration: 500,
          offset: 0,
          easing: 'easeInCubic'
        }
      }
    }
  },
  mounted () {
    this.currentAutoscroll = this.autoscroll
  },
  watch: {
    condition: function (newCond, oldCond) {
      if (newCond && this.currentAutoscroll) {
        console.log('Autoscrolled to new element')
        this.currentAutoscroll = false
        setTimeout(() => {
          this.$emit('autoscroll', this)
        }, 100)
      }
    }
  }
}
</script>
