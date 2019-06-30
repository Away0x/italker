package config

import (
	"time"

	"github.com/spf13/viper"
)

const (
	defaultTempDir = "storage"
	defaultAppPort = ":9000"
	defaultAppName = "app"
)

var now = time.Now()

// 默认配置
var defaultConfigMap = map[string]interface{}{
	// app
	"APP.NAME":              defaultAppName,
	"APP.RUNMODE":           "production", // 环境
	"APP.ADDR":              defaultAppPort,
	"APP.URL":               "http://localhost" + defaultAppPort,
	"APP.KEY":               "Rtg8BPKNEf2mB4mgvKONGPZZQSaJWNLijxR42qRgq0iBb5",
	"APP.TEMP_DIR":          defaultTempDir,    // 临时文件存储位置 (log ...)
	"APP.PUBLIC_DIR":        "public",          // public 文件夹
	"APP.TEMPLATE_DIR":      "resources/views", // 模板文件存放文件夹
	"APP.SHOW_ERROR_DETAIL": true,              // response 是否会输出错误详情

	// https
	"TLS.ADDR": ":9001",

	// db
	"DB.CONNECTION": "mysql",
	"DB.HOST":       "127.0.0.1",
	"DB.PORT":       "3306",
	"DB.DATABASE":   defaultAppName,
	"DB.USERNAME":   "root",
	"DB.PASSWORD":   "",

	// log
	"LOG.WRITERS":          "file,stdout",
	"LOG.LOGGER_LEVEL":     "DEBUG",
	"LOG.LOGGER_FILE":      defaultTempDir + "/logs/" + defaultAppName + ".log",
	"LOG.LOG_FORMAT_TEXT":  false,
	"LOG.ROLLING_POLICY":   "size",
	"LOG.LOG_ROTATE_DATE":  1,
	"LOG.LOG_ROTATE_SIZE":  1,
	"LOG.LOG_BACKUP_COUNT": 7,
}

// 设置配置默认值
func setupDefaultConfig() {
	for k, v := range defaultConfigMap {
		viper.SetDefault(k, v)
	}
}
