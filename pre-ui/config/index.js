'use strict'
const path = require('path')

module.exports = {

  dev: {
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {
      '/pre': {
        target: 'http://10.14.3.89:8081',//后端接口地址
        // target: 'http://10.14.3.158:8081',//后端接口地址
        // target: 'http://192.168.104.140:8081',//后端接口地址
        changeOrigin: true,  //是否跨域
        pathRewrite: {
          '^/pre': '/',//重写,
        }
      }

    },

    // 本地开发使用
    //host: 'localhost',
    host: '10.14.3.89',
    port: 9527,
    autoOpenBrowser: true,
    errorOverlay: true,
    notifyOnErrors: false,
    poll: false,
    useEslint: true,
    showEslintErrorsInOverlay: false,
    devtool: 'cheap-source-map',
    cssSourceMap: false
  },

  build: {
    index: path.resolve(__dirname, '../dist/index.html'),
    assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',

    productionSourceMap: false,
    devtool: 'source-map',
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],
    bundleAnalyzerReport: process.env.npm_config_report || false,
    generateAnalyzerReport: process.env.npm_config_generate_report || false
  }
}
