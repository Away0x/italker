package context

import (
	"net/http"
)

// RenderJson -
func (a *AppContext) RenderJson(data map[string]interface{}) error {
	return a.JSON(http.StatusOK, data)
}
