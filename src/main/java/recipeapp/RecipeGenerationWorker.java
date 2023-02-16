package recipeapp;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class RecipeGenerationWorker {

    public static void main(String[] args) {

        // WorkflowServiceStubs is a gRPC stubs wrapper that talks to the local Docker instance of the Temporal server.
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        // Worker factory is used to create Workers that poll specific Task Queues.
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(Shared.RECIPE_GENERATION_TASK_QUEUE);
        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful so a type is needed to create instances.
        worker.registerWorkflowImplementationTypes(RecipeGenerationWorkflowImpl.class);
        // Uncomment to do with Saga pattern:
        //worker.registerWorkflowImplementationTypes(RecipeGenerationWorkflowWithSagaImpl.class);
        worker.registerActivitiesImplementations(new MoneyImpl());
        worker.registerActivitiesImplementations(new RecipeCreatorImpl());
        worker.registerActivitiesImplementations(new GroceryBrokerImpl());
        // Start listening to the Task Queue.
        factory.start();
        //System.exit(0);
    }
}
