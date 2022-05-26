const {defineConfig} = require('@vue/cli-service')
module.exports = {
    devServer: {
        //поменять на единичку
        proxy: 'http://localhost:8080/'
    }
}
