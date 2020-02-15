<template>
  <conditional-expand-field
    :condition="condition"
    @autoscroll="onAutoscroll"
  >
    <validation-provider
      :rules="condition ? rules : ''"
      :name="name"
      v-slot="{ errors }"
      :vid="vid"
    >
      <slot :errors="errors"/>
    </validation-provider>
  </conditional-expand-field>
</template>
<script>
import ConditionalExpandField from './ConditionalExpandField'
import { ValidationProvider } from 'vee-validate'
export default {
  name: 'validated-expand-field',
  components: {
    ConditionalExpandField,
    ValidationProvider
  },
  props: {
    condition: {
      required: true
    },
    rules: {
      type: [Object, String],
      default: 'required'
    },
    name: {
      type: String,
      required: true
    },
    vid: {
      type: String,
      required: true
    }
  },
  methods: {
    onAutoscroll (elem) {
      console.log('Propagating autoscroll')
      console.log(elem)
      this.$emit('autoscroll', elem)
    }
  }
}
</script>
