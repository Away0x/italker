APP_NAME = "italker"

default:
	go build -o ${APP_NAME}
	# env GOOS=linux GOARCH=amd64 go build -o ${APP_NAME}

install:
	env GOPROXY=https://goproxy.io go mod download

dev:
  # go get -u github.com/Away0x/fresh
	env italker_APP_RUNMODE=development fresh -c ./fresh.conf

mock:
	env italker_APP_RUNMODE=development go run main.go mock

api-doc:
  # go get -u github.com/swaggo/swag/cmd/swag
	swag init

clean:
	if [ -f ${APP_NAME} ]; then rm ${APP_NAME}; fi

help:
	@echo "make - compile the source code"
	@echo "make install - install dep"
	@echo "make dev - run go fresh"
	@echo "make api-doc - generate swagger api docs"
	@echo "make mock - mock data"
	@echo "make clean - remove binary file"
