package routes

import (
	"italker/context"
	"italker/config"
	"italker/pkg/errno"
	mymiddleware "italker/routes/middleware"
	"net/http"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
)

type (
	// SpecialHandlers -
	SpecialHandlers struct {
		// 错误处理的 handler
		ErrorHandler func(echo.Context, *errno.Errno) error
	}
)

const (
	restfulAPIPrefix = "/api"
)

// Register 注册路由
func Register(e *echo.Echo) *SpecialHandlers {
	// 自定义 context
	e.Use(context.WrapContextMiddleware)

	// recover
	// e.Use(middleware.Recover())
	e.Use(mymiddleware.Recover())

	if config.IsDev() {
		// log
		e.Use(middleware.LoggerWithConfig(middleware.LoggerConfig{
			Format: "${status}  ${method}   ${uri}\n",
		}))
	} else {
		// gzip
		e.Use(middleware.Gzip())
	}

	// 去除 url 尾部 /
	e.Pre(middleware.RemoveTrailingSlashWithConfig(middleware.TrailingSlashConfig{
		RedirectCode: http.StatusMovedPermanently,
	}))

	// 注册 api routes
	registerAPI(e, restfulAPIPrefix)

	// 注册 error handler
	echo.NotFoundHandler = notFoundHandler
	echo.MethodNotAllowedHandler = notFoundHandler

	return &SpecialHandlers{
		ErrorHandler: errorHandler,
	}
}
