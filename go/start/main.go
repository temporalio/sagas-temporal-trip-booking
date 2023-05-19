package main

import (
	"context"
	"log"
	"time"
	"vacation/app"

	"github.com/google/uuid"
	"github.com/rickb777/date"
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
		ID:        "trip-planning-workflow" + uuid.New().String(),
		TaskQueue: app.TripPlanningTaskQueue,
	}

	expiration := app.CreditCardExpiration{Year: 2023, Month: 5}
	ccInfo := app.CreditCardInfo{Number: 123456789, Ccv: 123, Expiration: expiration}
	start := date.New(1999, time.December, 31)
	end := date.New(2000, time.February, 2)
	info := app.BookingInfo{Name: "Emily", ClientId: "1234", Address: "123 Temporal Lane", CcInfo: ccInfo, Start: start, End: end}
	// Start the Workflow
	_, err = c.ExecuteWorkflow(context.Background(), options, app.TripPlanningWorkflow, info)

	if err != nil {
		log.Fatalln("unable to start Workflow", err)
	}
}
