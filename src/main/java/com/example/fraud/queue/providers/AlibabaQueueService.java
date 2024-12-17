package com.example.fraud.queue.providers;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.example.fraud.model.Transaction;
import com.example.fraud.queue.spi.QueueService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AlibabaQueueService implements QueueService {

    private final String accessKeyId;
    private final String accessKeySecret;
    private final String endpoint;
    private final String queueName;

    private MNSClient mnsClient;
    private CloudQueue cloudQueue;

    public AlibabaQueueService(String accessKeyId, String accessKeySecret, String endpoint, String queueName) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.endpoint = endpoint;
        this.queueName = queueName;
    }

    @Override
    public void initialize() {
        try {
            System.out.println("Initializing Alibaba MNS Client...");
            CloudAccount account = new CloudAccount(accessKeyId, accessKeySecret, endpoint);
            mnsClient = account.getMNSClient();
            cloudQueue = mnsClient.getQueueRef(queueName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Alibaba MNS client: " + e.getMessage(), e);
        }
    }

    @Override
    public Transaction receiveTransaction() {
        try {
            Message message = cloudQueue.popMessage(10); // Wait up to 10 seconds
            if (message != null) {
                System.out.println("Received Alibaba MNS Message: " + message.getMessageBodyAsString());
                ObjectMapper mapper = new ObjectMapper();
                Transaction transaction = mapper.readValue(message.getMessageBodyAsString(), Transaction.class);
                transaction.setTransactionId(message.getMessageId());
                return transaction;
            }
        } catch (Exception e) {
            System.err.println("Error receiving MNS message: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void acknowledgeTransaction(String messageId) {
        try {
            cloudQueue.deleteMessage(messageId);
            System.out.println("Acknowledged Alibaba MNS Message ID: " + messageId);
        } catch (Exception e) {
            System.err.println("Error acknowledging MNS message: " + e.getMessage());
        }
    }
}
