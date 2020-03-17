import GenericService from './generic.service'

const API_URL = '/api/lessonDate'

class LessonDateService extends GenericService {
  create (obj) { // Not supported by the API
    return null
  }
}

export default new LessonDateService(API_URL)
