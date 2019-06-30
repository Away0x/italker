package routes

import (
	"italker/config"
	"italker/pkg/errno"

	"github.com/labstack/echo/v4"
)

// 未找到路由时的 handler
func notFoundHandler(c echo.Context) error {
	return errno.NotFoundErr
}

// 统一错误处理 handler
func errorHandler(c echo.Context, e *errno.Errno) error {
	// 隐藏错误详情 (默认开启)
	if !config.Bool("APP.SHOW_ERROR_DETAIL") {
		e = e.HideErrorDetail()
	}

	return c.JSON(e.HTTPCode, e)
}
