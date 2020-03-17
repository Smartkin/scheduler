import { extend } from 'vee-validate'
import * as rules from 'vee-validate/dist/rules'
import { messages } from 'vee-validate/dist/locale/ru'

Object.keys(rules).forEach(rule => {
  extend(rule, {
    ...rules[rule],
    message: messages[rule]
  })
})

extend('lessThanTime', {
  params: ['minHours', 'minMinutes', 'maxHours', 'maxMinutes'],
  validate (value, { minHours, minMinutes, maxHours, maxMinutes }) {
    if (minHours === '' || minMinutes === '' || maxHours === '' || maxMinutes === '') {
      return false
    }
    console.log(minHours + ':' + minMinutes + ' - ' + maxHours + ':' + maxMinutes)
    let numMinHours = Number(minHours)
    let numMinMinutes = Number(minMinutes)
    let numMaxHours = Number(maxHours)
    let numMaxMinutes = Number(maxMinutes)
    if (numMinHours < numMaxHours) {
      return true
    }
    if (numMinHours === numMaxHours) {
      return numMinMinutes < numMaxMinutes
    }
    return false
  }
})

extend('arrayMinSize', {
  validate (value, min) {
    return value.length >= min
  }
})
