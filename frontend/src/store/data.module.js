export const data = {
  namespaced: true,
  state: {
    schedulesDataUpdate: false
  },
  actions: {
    setUpdatedSchedulesData ({ commit }) {
      commit('setSchedulesDataUpdate')
    },
    resetUpdatedSchedulesData ({ commit }) {
      commit('resetSchedulesDataUpdate')
    }
  },
  mutations: {
    setSchedulesDataUpdate (state) {
      state.schedulesDataUpdate = true
    },
    resetSchedulesDataUpdate (state) {
      state.schedulesDataUpdate = false
    }
  }
}
