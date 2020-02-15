import Vue from 'vue'
import Vuex from 'vuex'
import { schedule } from './schedule.module'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    schedule
  }
})
