import { defineLoader } from 'vitepress'

export interface Datacenter {
  Name: string;
  DisplayName: string;
}

declare const data: Datacenter[]
export { data }

export default defineLoader({
  watch: ['./azure_datacenter_list.json'],
  load(): Promise<Datacenter[]> {
    return import('./azure_datacenter_list.json').then((m) => m.default)
  }
})