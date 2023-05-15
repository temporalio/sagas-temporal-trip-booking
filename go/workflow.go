package app

import (
	"time"

	"go.temporal.io/sdk/temporal"
	"go.temporal.io/sdk/workflow"
)

func BreakfastWorkflow(ctx workflow.Context, parallelCompensations bool) (err error) {
	options := workflow.ActivityOptions{
		StartToCloseTimeout: time.Second * 5,
		RetryPolicy:         &temporal.RetryPolicy{MaximumAttempts: 1},
	}

	ctx = workflow.WithActivityOptions(ctx, options)

	var compensations Compensations

	defer func() {
		// Defer is at the top so that it is executed regardless of which step might fail.
		if err != nil {
			// activity failed, and workflow context is canceled
			disconnectedCtx, _ := workflow.NewDisconnectedContext(ctx)
			compensations.Compensate(disconnectedCtx, parallelCompensations)
		}
	}()

	compensations.AddCompensation(PutBowlAwayIfPresent)
	err = workflow.ExecuteActivity(ctx, GetBowl).Get(ctx, nil)
	if err != nil {
		return err
	}

	compensations.AddCompensation(PutCerealBackInBoxIfPresent)
	err = workflow.ExecuteActivity(ctx, AddCereal).Get(ctx, nil)
	if err != nil {
		return err
	}

	err = workflow.ExecuteActivity(ctx, AddMilk).Get(ctx, nil)

	return err
}
