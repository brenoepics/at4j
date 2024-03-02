import { defineConfigWithTheme } from 'vitepress'
import type { ThemeConfig } from 'vitepress-carbon'
import baseConfig from 'vitepress-carbon/config'

const nav = [
  {
    text: 'Docs',
    activeMatch: `^/(guide|examples)/`,
    items: [
      { text: 'Guide', link: '/guide/introduction' },
      { text: 'Examples', link: '/examples/' },
      { text: 'Error Reference', link: '/error-reference/' },
      { text: 'JavaDoc', link: 'https://brenoepics.github.io/at4j/javadoc/' }
    ]
  },
  {
    text: 'About',
    activeMatch: `^/about/`,
    items: [
      { text: 'FAQ', link: '/about/faq' },
      { text: 'Releases', link: '/about/releases' },
      { text: 'Code of Conduct', link: '/about/coc' }
    ]
  },
]
const sidebar = {
  '/guide/': [
    {
      text: 'Getting Started',
      items: [
        { text: 'Introduction', link: '/guide/introduction' },
        {
          text: 'Download/Installation',
          link: '/guide/installation'
        },
        {
          text: `Generating an Azure Translator Key`,
          link: '/guide/azure-subscription'
        },
        {
          text: 'Basic Usage',
          link: '/guide/basic-usage'
        },
        {
          text: 'Examples',
          link: '/examples/'
        }
      ]
    }
  ],
  '/examples/': [
    {
      text: 'Basic',
      items: [
        {
          text: 'Translate Hello World',
          link: '/examples/#translator'
        },
        {
          text: 'Detect Language',
          link: '/examples/#detect'
        },
        {
          text: 'Guide',
          link: '/guide/introduction'
        }
      ]
    }
  ]
}

// https://vitepress.dev/reference/site-config
export default defineConfigWithTheme<ThemeConfig>({
  extends: baseConfig,
  lang: 'en-US',
  title: 'AT4J',
  description: 'AT4J - Azure Translator for Java',
  srcDir: 'src',
  base: '/at4j/',

  head: [
    ['meta', { name: 'theme-color', content: '#3c8772' }],
    ['meta', { property: 'og:url', content: 'https://github.com/brenoepics/at4j' }],
    ['meta', { property: 'og:type', content: 'Repository' }],
    ['meta', { property: 'og:title', content: 'AT4J' }],
    [
      'meta',
      {
        property: 'og:description',
        content: 'AT4J - Azure Translator for Java'
      }
    ]
  ],

  themeConfig: {
    nav,
    sidebar,

    search: {
      provider: 'local'
    },

    outline: [2, 3],

    socialLinks: [
      {
        icon: {
          svg: '<svg role="img" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path fill="#EF5B25" d="M13.527.099C6.955-.744.942 3.9.099 10.473c-.843 6.572 3.8 12.584 10.373 13.428 6.573.843 12.587-3.801 13.428-10.374C24.744 6.955 20.101.943 13.527.099zm2.471 7.485a.855.855 0 0 0-.593.25l-4.453 4.453-.307-.307-.643-.643c4.389-4.376 5.18-4.418 5.996-3.753zm-4.863 4.861l4.44-4.44a.62.62 0 1 1 .847.903l-4.699 4.125-.588-.588zm.33.694l-1.1.238a.06.06 0 0 1-.067-.032.06.06 0 0 1 .01-.073l.645-.645.512.512zm-2.803-.459l1.172-1.172.879.878-1.979.426a.074.074 0 0 1-.085-.039.072.072 0 0 1 .013-.093zm-3.646 6.058a.076.076 0 0 1-.069-.083.077.077 0 0 1 .022-.046h.002l.946-.946 1.222 1.222-2.123-.147zm2.425-1.256a.228.228 0 0 0-.117.256l.203.865a.125.125 0 0 1-.211.117h-.003l-.934-.934-.294-.295 3.762-3.758 1.82-.393.874.874c-1.255 1.102-2.971 2.201-5.1 3.268zm5.279-3.428h-.002l-.839-.839 4.699-4.125a.952.952 0 0 0 .119-.127c-.148 1.345-2.029 3.245-3.977 5.091zm3.657-6.46l-.003-.002a1.822 1.822 0 0 1 2.459-2.684l-1.61 1.613a.119.119 0 0 0 0 .169l1.247 1.247a1.817 1.817 0 0 1-2.093-.343zm2.578 0a1.714 1.714 0 0 1-.271.218h-.001l-1.207-1.207 1.533-1.533c.661.72.637 1.832-.054 2.522zM18.855 6.05a.143.143 0 0 0-.053.157.416.416 0 0 1-.053.45.14.14 0 0 0 .023.197.141.141 0 0 0 .084.03.14.14 0 0 0 .106-.05.691.691 0 0 0 .087-.751.138.138 0 0 0-.194-.033z"/></svg>'
        },
        link: 'https://www.postman.com/maintenance-astronaut-2993290/workspace/brenoepics/collection/18589822-dfe7a640-9b94-47a8-b19f-46cb9cc8843e?action=share&creator=18589822'
      },
      { icon: 'github', link: 'https://github.com/brenoepics/at4j' }

    ],

    editLink: {
      pattern: 'https://github.com/brenoepics/at4j/edit/main/docs/src/:path',
      text: 'Edit this page on GitHub'
    },

    footer: {
      message: 'Apache 2.0 Licensed'
    }
  },
})
