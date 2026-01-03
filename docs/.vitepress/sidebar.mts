
import type { DefaultTheme } from 'vitepress'


const guideGroupItems: DefaultTheme.NavItem[] = [
  {
    text: 'Introduction',
    link: '/guide/introduction'
  },
  {
    text: 'Installation',
    link: '/guide/installation'
  },
  {
    text: 'Basic Usage',
    link: '/guide/basic-usage'
  }
]

const examplesItems: DefaultTheme.NavItem[] = [
  {
    text: 'Translator',
    link: '/examples/#translator'
  },
  {
    text: 'Detect',
    link: '/examples/#detect'
  },
  {
    text: 'Translate JSON',
    link: '/examples/#json'
  }
]

const essentialItems: DefaultTheme.NavItem[] = [
  {
    text: 'Completable Futures',
    link: '/essential/completable-futures'
  },
  {
    text: 'Optionals',
    link: '/essential/optionals'
  },
  {
    text: 'Error Handling',
    link: '/essential/error-handling'
  },
  {
    text: 'Logging',
    link: '/essential/logging'
  }
]

const azureItems: DefaultTheme.NavItem[] = [
  {
    text: 'Azure Subscription',
    link: '/guide/azure-subscription'
  },
  {
    text: 'Available Languages',
    link: '/azure-reference/languages'
  },
  {
    text: 'Datacenter List',
    link: '/azure-reference/datacenter-list'
  },
  {
    text: 'Error Reference',
    link: '/error-reference/'
  }
]

const advancedItems: DefaultTheme.NavItem[] = [
  {
    text: 'Threading',
    link: '/advanced/threading'
  },
  {
    text: 'Caching',
    link: '/advanced/caching'
  }
]
export const sidebar: DefaultTheme.Sidebar = [
  { text: 'Guide', items: guideGroupItems },
  { text: 'Essential Knowledge', collapsed: true, items: essentialItems },
  { text: 'Examples', collapsed: true, items: examplesItems },
  { text: 'Advanced Usage', collapsed: true, items: advancedItems },
  { text: 'Azure Reference', collapsed: true, items: azureItems },
]
