package account

import (
	"italker/app/models"
	"italker/context"
)

func Login(c *context.AppContext) error {
	user := &models.User{
		Name: "wutong",
		Sex:  1,
	}
	return c.RenderJson(user.Serialize())
}
