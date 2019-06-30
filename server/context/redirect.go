package context

import (
	"italker/config"
	"net/http"
)

// RedirectTo 重定向
func (a *AppContext) RedirectTo(path string) error {
	return a.Context.Redirect(http.StatusFound, path)
}

// RedirectByName 重定向到指定路由 (routeName: 路由名)
func (a *AppContext) RedirectByName(routeName string) error {
	return a.Context.Redirect(http.StatusFound, config.Application.Reverse(routeName))
}
