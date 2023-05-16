package main

import (
	"breakfast/app"
	"context"
	"log"

	"go.temporal.io/sdk/client"
)

func main() {

	// Create the client object just once per process
	c, err := client.Dial(client.Options{})
	if err != nil {
		log.Fatalln("unable to create Temporal client", err)
	}
	defer c.Close()

	options := client.StartWorkflowOptions{
		ID:        "trip-planning-workflow",
		TaskQueue: app.TripPlanningTaskQueue,
	}

	expiration := app.CreditCardExpiration{Year: 2023, Month: 5}
	ccInfo := app.CreditCardInfo{Number: 123456789, Ccv: 123, Expiration: expiration}
	info := app.BookingInfo{Name: "Emily", Address: "123 Temporal Lane", CcInfo: ccInfo}
	// Start the Workflow
	_, err = c.ExecuteWorkflow(context.Background(), options, app.TripPlanningWorkflow, info)

	if err != nil {
		log.Fatalln("unable to start Workflow", err)
	}
}
