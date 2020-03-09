import axios from 'axios'

class GenericService {
  constructor (apiStr = null) {
    if (!apiStr) {
      throw Error('Can\'t create a pure generic service')
    }
    this.API_URL = apiStr
  }

  get (id = null, customParams = null) {
    let schedule = JSON.parse(localStorage.getItem('schedule'))
    if (schedule) {
      if (id) {
        let params = !customParams ? {
          id: id
        } : customParams
        return axios.get(this.API_URL, {
          params: params
        })
          .then(this.handleResponse)
          .then(response => {
            let obj = response.data
            return Promise.resolve(obj)
          }, error => {
            return Promise.reject(error)
          })
      } else {
        let params = !customParams ? {
          schId: schedule.id
        } : customParams
        console.log('Object list request')
        return axios.get(this.API_URL + '/list', {
          params: params
        })
          .then(this.handleResponse)
          .then(response => {
            let objList = response.data
            return Promise.resolve(objList)
          }, error => {
            return Promise.reject(error)
          })
      }
    } else {
      return Promise.reject(Error('Не было выбрано расписание!'))
    }
  }

  create (obj) {
    let schedule = JSON.parse(localStorage.getItem('schedule'))
    if (!schedule) {
      return Promise.reject(Error('Не было выбрано расписание!'))
    }
    obj.schId = schedule.id
    console.log('Create params:')
    console.log(obj)
    return axios.post(this.API_URL + '/create', obj)
      .then(this.handleResponse)
      .then(response => {
        return Promise.resolve(response.data)
      }, error => {
        return Promise.reject(error)
      })
  }

  handleResponse (response) {
    if (response.status !== 200 && response.status !== 204) {
      console.log('Error by performed request with code ' + response.status)
      const error = response.data && response.data.message
      return Promise.reject(error)
    }
    console.log(response)
    return Promise.resolve(response)
  }
}

export default GenericService
