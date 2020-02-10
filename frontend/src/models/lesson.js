export default class Lesson {
  constructor (classJson = {}) {
    this.date = new Date(classJson.date)
    this.type = classJson.type
    this.name = classJson.name
    this.teachers = classJson.teachers
    this.auditory = classJson.auditory
    this.status = classJson.status
    this.startTime = classJson.startTime
    this.endTime = classJson.endTime
    this.comment = classJson.comment
  }
}
