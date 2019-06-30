package bootstrap

import (
	"italker/bootstrap/echoinit"
	"italker/config"

	"github.com/labstack/echo/v4"
)

// SetupEcho -
func SetupEcho() *echo.Echo {
	e := echo.New()
	e.Debug = config.IsDev()
	e.HideBanner = true

	specialHandlers := echoinit.SetupRoute(e) // 配置路由
	echoinit.SetupError(e, specialHandlers)   // 配置统一错误处理

	return e
}

// RunEcho -
func RunEcho() {
	e := SetupEcho()
	config.SaveApplication(e)

	// 如果提供了 TLS 证书和私钥则启动 HTTPS 端口
	certFile := config.String("TLS.CERT_FILE")
	keyFile := config.String("TLS.KEY_FILE")
	if certFile != "" && keyFile != "" {
		go func() {
			e.Logger.Fatal(e.StartTLS(config.String("TLS.ADDR"), certFile, keyFile))
		}()
	}

	// 启动 app
	e.Logger.Fatal(e.Start(config.String("APP.ADDR")))
}
