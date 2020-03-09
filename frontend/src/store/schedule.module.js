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
          commit('setSuccess', schedule)
          localStorage.setItem('schedule', JSON.stringify(schedule))
          return Promise.resolve()
        },
        error => {
          commit('setFail')
          return Promise.reject(error)
        }
      )
    },
    directSet ({ commit }, schedule) {
      console.log('Called direct set')
      commit('setSuccess', schedule)
      localStorage.setItem('schedule', JSON.stringify(schedule))
    }
  },
  mutations: {
    setSuccess (state, schedule) {
      console.log(schedule)
      state.sch = schedule
    },
    setFail (state) {
      state.sch = null
    }
  }
}
