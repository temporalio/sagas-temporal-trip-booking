// @@@SNIPSTART hello-world-project-template-go-workflow-test
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
	env.OnActivity(GetBowl, mock.Anything).Return(nil)
	env.OnActivity(PutBowlAwayIfPresent, mock.Anything).Return(nil)
	env.OnActivity(AddCereal, mock.Anything).Return(nil)
	env.OnActivity(PutCerealBackInBoxIfPresent, mock.Anything).Return(nil)
	env.OnActivity(AddMilk, mock.Anything).Return(nil)

	env.ExecuteWorkflow(BreakfastWorkflow)
	require.True(t, env.IsWorkflowCompleted())
	require.NoError(t, env.GetWorkflowError())
}

// @@@SNIPEND
