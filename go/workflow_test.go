package app

import (
	"errors"
	"testing"
	"time"

	"github.com/rickb777/date"
	"github.com/stretchr/testify/mock"
	"github.com/stretchr/testify/require"
	"go.temporal.io/sdk/testsuite"
)

func Test_Workflow(t *testing.T) {
	// Set up the test suite and testing execution environment
	testSuite := &testsuite.WorkflowTestSuite{}
	env := testSuite.NewTestWorkflowEnvironment()

	expiration := CreditCardExpiration{Year: 1843, Month: 3}
	ccInfo := CreditCardInfo{Number: 4524413, Ccv: 333, Expiration: expiration}
	start := date.New(2023, time.October, 8)
	end := date.New(2023, time.November, 30)
	info := BookingInfo{Name: "you", ClientId: "321313", Address: "123 Temporal Lane", CcInfo: ccInfo, Start: start, End: end}

	// Mock activity implementation
	env.OnActivity(BookHotel, mock.Anything, mock.Anything).Return(nil)
	env.OnActivity(BookFlight, mock.Anything, mock.Anything).Return(nil)
	env.OnActivity(BookExcursion, mock.Anything, mock.Anything).Return(nil)

	env.ExecuteWorkflow(TripPlanningWorkflow, info)
	require.True(t, env.IsWorkflowCompleted())
	require.NoError(t, env.GetWorkflowError())
}

func Test_Compensations(t *testing.T) {
	// Set up the test suite and testing execution environment
	testSuite := &testsuite.WorkflowTestSuite{}
	env := testSuite.NewTestWorkflowEnvironment()

	expiration := CreditCardExpiration{Year: 1843, Month: 3}
	ccInfo := CreditCardInfo{Number: 4524413, Ccv: 333, Expiration: expiration}
	start := date.New(2023, time.October, 8)
	end := date.New(2023, time.November, 30)
	info := BookingInfo{Name: "you", ClientId: "321313", Address: "123 Temporal Lane", CcInfo: ccInfo, Start: start, End: end}

	// Mock activity implementation
	env.OnActivity(BookHotel, mock.Anything, mock.Anything).Return(nil)
	env.OnActivity(CancelHotel, mock.Anything, mock.Anything).Return(nil)
	env.OnActivity(BookFlight, mock.Anything, mock.Anything).Return(nil)
	env.OnActivity(CancelFlight, mock.Anything, mock.Anything).Return(nil)
	env.OnActivity(BookExcursion, mock.Anything, mock.Anything).Return(errors.New("some error"))
	env.OnActivity(CancelExcursion, mock.Anything, mock.Anything).Return(nil)

	env.ExecuteWorkflow(TripPlanningWorkflow, info)
	require.True(t, env.IsWorkflowCompleted())
	require.Error(t, env.GetWorkflowError())
}
