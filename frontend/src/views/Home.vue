<template>
  <div class="home text-center">
    <HelloWorld msg="Добро пожаловать на расписание вузов"/>
  </div>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'home',
  components: {
    HelloWorld
  }
}
</script>
