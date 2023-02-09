# Temporalmental Chef: An illustration of Sagas with Temporal, by generating bad recipes

## Build the project

Open the project in IntelliJ, which will automatically build it, or in the project's root
directory run:

```
./gradlew build
```

## Run the Workflow

Run the [Temporal server](https://docs.temporal.io/docs/server/quick-install).

To start the Workflow, either run the InitiateMoneyTransfer class from IntelliJ or from the project
root run:

```
./gradlew initiateGeneration
```

To start the Worker, either run the MoneyTransferWorker class from IntelliJ or from the project root
run:

```
./gradlew startWorker --console=plain
```

It will continue to run, looking for more work, until you explicitly kill it.