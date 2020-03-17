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
      <v-list-group>
      <template v-slot:activator>
        <v-list-item-title>Создать</v-list-item-title>
      </template>
      <v-card tile flat>
        <v-list-item
          v-for="(createItem, i) in createLinks"
          :key="i"
          link
          :to="createItem.link"
        >
          <v-list-item-title v-text="createItem.name"/>
        </v-list-item>
      </v-card>
    </v-list-group>
      <v-list-group>
        <template v-slot:activator>
          <v-list-item-title>Список</v-list-item-title>
        </template>
        <v-card tile flat>
          <v-list-item
            v-for="(listingItem, i) in listingLinks"
            :key="i"
            link
            :to="listingItem.link"
          >
              <v-list-item-title v-text="listingItem.name"/>
          </v-list-item>
        </v-card>
      </v-list-group>
<!--      <v-btn depressed to="/teachers">Список преподавателей</v-btn>-->
      <v-btn depressed to="/about">О приложении</v-btn>
    </v-toolbar-items>
  </v-app-bar>
</template>

<script>
export default {
  name: 'app-bar',
  data () {
    return {
      createLinks: [
        {
          name: 'Расписание',
          link: '/create_schedule'
        },
        {
          name: 'Пару',
          link: '/create_lesson'
        },
        {
          name: 'Преподавателя',
          link: '/create_teacher'
        },
        {
          name: 'Тип пары',
          link: '/create_type'
        }
      ],
      listingLinks: [
        {
          name: 'Преподавателей',
          link: '/teachers'
        },
        {
          name: 'Тип пар',
          link: '/types'
        }
      ]
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
