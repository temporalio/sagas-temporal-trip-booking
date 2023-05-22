package app

import (
	"time"

	"go.temporal.io/sdk/temporal"
	"go.temporal.io/sdk/workflow"
)

func TripPlanningWorkflow(ctx workflow.Context, info BookingInfo) (err error) {
	options := workflow.ActivityOptions{
		StartToCloseTimeout: time.Second * 5,
		RetryPolicy:         &temporal.RetryPolicy{MaximumAttempts: 2},
	}

	ctx = workflow.WithActivityOptions(ctx, options)

	var compensations Compensations

	defer func() {
		if err != nil {
			// activity failed, and workflow context is canceled
			disconnectedCtx, _ := workflow.NewDisconnectedContext(ctx)
			compensations.Compensate(disconnectedCtx, true)
		}
	}()

	compensations.AddCompensation(CancelHotel)
	err = workflow.ExecuteActivity(ctx, BookHotel, info).Get(ctx, nil)
	if err != nil {
		return err
	}

	compensations.AddCompensation(CancelFlight)
	err = workflow.ExecuteActivity(ctx, BookFlight, info).Get(ctx, nil)
	if err != nil {
		return err
	}

	compensations.AddCompensation(CancelExcursion)
	err = workflow.ExecuteActivity(ctx, BookExcursion, info).Get(ctx, nil)
	if err != nil {
		return err
	}

	return err
}
