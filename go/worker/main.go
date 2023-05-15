package main

import (
	"breakfast/app"
	"log"

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
	w := worker.New(c, app.BreakfastTaskQueue, worker.Options{})
	w.RegisterWorkflow(app.BreakfastWorkflow)

	w.RegisterActivity(app.GetBowl)
	w.RegisterActivity(app.PutBowlAwayIfPresent)
	w.RegisterActivity(app.AddCereal)
	w.RegisterActivity(app.PutCerealBackInBoxIfPresent)
	w.RegisterActivity(app.AddMilk)

	// Start listening to the Task Queue
	err = w.Run(worker.InterruptCh())
	if err != nil {
		log.Fatalln("unable to start Worker", err)
	}
}
