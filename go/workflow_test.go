package app

import (
	"testing"

	"github.com/stretchr/testify/mock"
	"github.com/stretchr/testify/require"
	"go.temporal.io/sdk/testsuite"
)

func Test_Workflow(t *testing.T) {
	// Set up the test suite and testing execution environment
	testSuite := &testsuite.WorkflowTestSuite{}
	env := testSuite.NewTestWorkflowEnvironment()

	// Mock activity implementation
	env.OnActivity(BookHotel, mock.Anything).Return(nil)
	env.OnActivity(CancelHotel, mock.Anything).Return(nil)
	env.OnActivity(BookFlight, mock.Anything).Return(nil)
	env.OnActivity(CancelFlight, mock.Anything).Return(nil)
	env.OnActivity(BookExcursion, mock.Anything).Return(nil)
	env.OnActivity(CancelExcursion, mock.Anything).Return(nil)

	env.ExecuteWorkflow(TripPlanningWorkflow)
	require.True(t, env.IsWorkflowCompleted())
	require.NoError(t, env.GetWorkflowError())
}
