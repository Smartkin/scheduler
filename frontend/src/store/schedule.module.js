import ScheduleService from '../services/schedule.service'

const sch = JSON.parse(localStorage.getItem('schedule'))

const initialState = sch
  ? { sch } : { schedule: null }

export const schedule = {
  namespaced: true,
  state: initialState,
  actions: {
    set ({ commit }, id) {
      return ScheduleService.get(id).then(
        schedule => {
          commit('setSuccess')
          localStorage.setItem('schedule', JSON.stringify(schedule))
          return Promise.resolve()
        },
        error => {
          commit('setFail')
          return Promise.reject(error)
        }
      )
    }
  },
  mutations: {
    setSuccess (state, schedule) {
      state.schedule = schedule
    },
    setFail (state) {
      state.schedule = null
    }
  }
}
