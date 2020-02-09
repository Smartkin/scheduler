module.exports = {
  'transpileDependencies': [
    'vuetify',
    '@mdi/font',
    'roboto-fontface'
  ],

  devServer: {
    proxy: {
      '^/api': {
        target: 'http://localhost:8080'
      }
    },
    port: 80
  }
}
