package main

import (
	"log"
	"vacation/app"

	"go.temporal.io/sdk/client"
	"go.temporal.io/sdk/worker"
)

func main() {
	// Create the client object just once per process
	c, err := client.Dial(client.Options{})
	if err != nil {
		log.Fatalln("unable to create Temporal client", err)
	}
	defer c.Close()

	// This worker hosts both Workflow and Activity functions
	w := worker.New(c, app.TripPlanningTaskQueue, worker.Options{})
	w.RegisterWorkflow(app.TripPlanningWorkflow)

	w.RegisterActivity(app.BookHotel)
	w.RegisterActivity(app.CancelHotel)
	w.RegisterActivity(app.BookFlight)
	w.RegisterActivity(app.CancelFlight)
	w.RegisterActivity(app.BookExcursion)
	w.RegisterActivity(app.CancelExcursion)

	// Start listening to the Task Queue
	err = w.Run(worker.InterruptCh())
	if err != nil {
		log.Fatalln("unable to start Worker", err)
	}
}
