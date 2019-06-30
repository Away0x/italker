package routes

import (
	"italker/app/controllers/api/account"
	"italker/context"

	"github.com/labstack/echo/v4"
)

func registerAPI(e *echo.Echo, apiPrefix string) {
	ee := e.Group(apiPrefix)

	accountRouter := ee.Group("/account")
	{
		context.RegisterHandler(accountRouter.GET, "/login", account.Login).Name = "api.account.login"
	}
}
