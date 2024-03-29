# Vacation Booking Saga app: An illustration of Sagas with Temporal by a vacation booking service

## Instructions

Ensure you have Go 1.16 or later installed locally, and that you have Docker installed to run the Temporal Cluster.

Clone this repository:

```bash
git clone https://github.com/efortuna/sagas-temporal-trip-booking
```

[Install and run the Temporal Server](https://docs.temporal.io/docs/server/quick-install) using `docker compose`.

```bash
git clone https://github.com/temporalio/docker-compose.git
cd docker-compose
docker compose up
```

You can now view Temporal Web at <http://localhost:8080>.

Run the worker and starter included in the project.

```bash
go run worker/main.go
go run start/main.go
```

If you have [`nodemon`](https://nodemon.io/) installed, you can automatically reload when you change any files: `nodemon --watch './**/*.go' --signal SIGTERM --exec 'go' run worker/main.go`
