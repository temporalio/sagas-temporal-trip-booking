package app

import (
	"github.com/rickb777/date"
	"go.temporal.io/sdk/workflow"
)

const TripPlanningTaskQueue = "TRIP_PLANNING_TASK_QUEUE"

// Distinct from golang's Time because we just want year and month
type CreditCardExpiration struct {
	Year  int
	Month int
}

type CreditCardInfo struct {
	Number     int
	Ccv        int
	Expiration CreditCardExpiration
}

type Compensations struct {
	compensations []any
	arguments     [][]any
}

type BookingInfo struct {
	Name     string
	ClientId string
	Address  string
	CcInfo   CreditCardInfo
	Start    date.Date
	End      date.Date
}

func (s *Compensations) AddCompensation(activity any, parameters ...any) {
	s.compensations = append(s.compensations, activity)
	s.arguments = append(s.arguments, parameters)
}

func (s Compensations) Compensate(ctx workflow.Context, inParallel bool) {
	if !inParallel {
		// Compensate in Last-In-First-Out order, to undo in the reverse order that activies were applied.
		for i := len(s.compensations) - 1; i >= 0; i-- {
			errCompensation := workflow.ExecuteActivity(ctx, s.compensations[i], s.arguments[i]...).Get(ctx, nil)
			if errCompensation != nil {
				workflow.GetLogger(ctx).Error("Executing compensation failed", "Error", errCompensation)
			}
		}
	} else {
		selector := workflow.NewSelector(ctx)
		for i := 0; i < len(s.compensations); i++ {
			execution := workflow.ExecuteActivity(ctx, s.compensations[i], s.arguments[i]...)
			selector.AddFuture(execution, func(f workflow.Future) {
				if errCompensation := f.Get(ctx, nil); errCompensation != nil {
					workflow.GetLogger(ctx).Error("Executing compensation failed", "Error", errCompensation)
				}
			})
		}
		for range s.compensations {
			selector.Select(ctx)
		}

	}
}
