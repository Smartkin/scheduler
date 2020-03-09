import axios from 'axios'
import GenericService from './generic.service'

const API_URL = '/api/schedule'

class ScheduleService extends GenericService {
  get (id) {
    let params = {}
    if (id) {
      params['id'] = id
    }
    return axios.get(this.API_URL, {
      params: params
    })
      .then(this.handleResponse)
      .then(response => {
        let schedule = response.data
        return Promise.resolve(schedule)
      }, error => {
        return Promise.reject(error)
      })
  }
}

export default new ScheduleService(API_URL)
