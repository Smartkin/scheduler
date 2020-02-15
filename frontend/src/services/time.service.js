import GenericService from './generic.service'

const API_URL = '/api/time'

class TimeService extends GenericService {
}

export default new TimeService(API_URL)
