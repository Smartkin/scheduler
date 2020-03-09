import Vue from 'vue'
import Vuex from 'vuex'
import { schedule } from './schedule.module'
import { data } from './data.module'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    schedule,
    data
  }
})
