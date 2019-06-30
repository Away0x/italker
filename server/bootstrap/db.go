package bootstrap

import (
	"italker/database"

	"github.com/jinzhu/gorm"
)

// SetupDB 初始化数据库
func SetupDB() (*gorm.DB, error) {
	database.InitDB()
	db := database.DBManager()

	db.AutoMigrate()

	return db, nil
}
