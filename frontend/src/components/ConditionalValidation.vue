<template>
  <validation-provider
    :rules="condition ? rules : ''"
    :name="name"
    v-slot="{ errors }"
  >
    <slot v-bind:errors="errors"/>
  </validation-provider>
</template>
<script>
import { ValidationProvider } from 'vee-validate'
export default {
  name: 'conditional-validation',
  components: {
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
    }
  }
}
</script>
