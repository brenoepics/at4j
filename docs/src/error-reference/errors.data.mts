import { defineLoader } from 'vitepress'
import data from './azure.errors.json'

export default defineLoader({
  load() {
    return {
      azure_response: data
    }
  }
})
