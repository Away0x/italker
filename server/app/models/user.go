package models

type User struct {
	Name string
	Sex  int
}

func (u *User) Serialize() map[string]interface{} {
	return map[string]interface{}{
		"name": u.Name,
		"sex":  u.Sex,
	}
}
