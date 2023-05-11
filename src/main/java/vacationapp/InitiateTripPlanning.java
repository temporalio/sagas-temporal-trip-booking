package vacationapp;

/*import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.codingrodent.jackson.crypto.CryptoModule;
import com.codingrodent.jackson.crypto.EncryptionService;
import com.codingrodent.jackson.crypto.PasswordCryptoContext;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.jackson.JsonCloudEventData;*/

import com.fasterxml.jackson.databind.ObjectMapper;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.common.converter.DefaultDataConverter;
import io.temporal.common.converter.JacksonJsonPayloadConverter;
import io.temporal.serviceclient.WorkflowServiceStubs;

import java.time.LocalDate;
import java.time.YearMonth;

public class InitiateTripPlanning {

    public static void main(String[] args) throws Exception {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(
                Shared.TRIP_PLANNING_TASK_QUEUE).setWorkflowId(
                "trip-planning-workflow").build();
        /*DefaultDataConverter ddc =
                DefaultDataConverter.newDefaultInstance().withPayloadConverterOverrides(
                new CloudEventsPayloadConverter());

        WorkflowClientOptions workflowClientOptions = WorkflowClientOptions.newBuilder().setDataConverter(
                ddc).build();*/

        WorkflowClient client = WorkflowClient.newInstance(service);
        //workflowClientOptions);

        IWorkflow workflow = client.newWorkflowStub(IWorkflow.class, options);
        BookingInfo info = new BookingInfo(
                new CreditCardInfo(1, YearMonth.of(2023, 3), 123),
                "Emily Fortuna", "123 Temporal Lane");
        LocalDate start = LocalDate.of(2023, 3, 1);
        LocalDate end = LocalDate.of(2023, 3, 15);
        String idempotencyId = "1";
        WorkflowExecution we = WorkflowClient.start(workflow::bookVacation,
                                                    info, start, end,
                                                    idempotencyId);
        System.out.printf("\nPlanning vacation is processing %s %s\n", start,
                          end);
        System.out.printf("\nWorkflowID: %s RunID: %s", we.getWorkflowId(),
                          we.getRunId());
        System.exit(0);
    }

    /*private static JacksonJsonPayloadConverter
    getCryptoJacksonJsonPayloadConverter() {

        ObjectMapper objectMapper = new ObjectMapper();

        // Create the Crypto Context (password based)
        PasswordCryptoContext cryptoContext = new PasswordCryptoContext(
                "secure password", // decrypt password
                "secure decryption password", // encrypt password
                PasswordCryptoContext.CIPHER_NAME, // cipher name
                PasswordCryptoContext.KEY_NAME); // key generator names
        EncryptionService encryptionService = new EncryptionService(
                objectMapper, cryptoContext);
        objectMapper.registerModule(
                new CryptoModule().addEncryptionService(encryptionService));
        objectMapper.registerModule(new JavaTimeModule());

        return new JacksonJsonPayloadConverter(objectMapper);
    }*/
}
