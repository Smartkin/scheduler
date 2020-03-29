import UserConfig from '../models/user-config'

const userConfig = JSON.parse(localStorage.getItem('userConfig'))
const initState = { userConfig: null, scheduleDateUpdate: false }
if (userConfig) {
  initState.userConfig = userConfig
} else {
  initState.userConfig = new UserConfig()
  localStorage.setItem('userConfig', JSON.stringify(initState.userConfig))
}

export const data = {
  namespaced: true,
  state: initState,
  getters: {
    lastSelectedDate: state => {
      return state.userConfig.lastSelectedDate
    }
  },
  actions: {
    setUpdatedSchedulesData ({ commit }) {
      commit('setSchedulesDataUpdate')
    },
    resetUpdatedSchedulesData ({ commit }) {
      commit('resetSchedulesDataUpdate')
    },
    setLastSelectedDate ({ commit }, newDate) {
      commit('setLastSelectedDate', newDate)
      console.log(this.state)
      localStorage.setItem('userConfig', JSON.stringify(this.state.data.userConfig))
    }
  },
  mutations: {
    setSchedulesDataUpdate (state) {
      state.schedulesDataUpdate = true
    },
    resetSchedulesDataUpdate (state) {
      state.schedulesDataUpdate = false
    },
    setLastSelectedDate (state, newDate) {
      state.userConfig.lastSelectedDate = newDate
    }
  }
}
