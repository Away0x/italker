package factory

import (
	"italker/bootstrap"
	"italker/database"
)

func dropAndCreateTable(table interface{}) {
	database.DBManager().DropTable(table)
	database.DBManager().CreateTable(table)
}

// Run run mock
func Run() {
	db, _ := bootstrap.SetupDB()
	defer db.Close()

}
