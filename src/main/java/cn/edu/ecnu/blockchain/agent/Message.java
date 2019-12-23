package cn.edu.ecnu.blockchain.agent;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    int sender;
    int receiver;
    MESSAGE_TYPE type;
    List<Block> blocks;
    List<Transaction> transactions;

    public enum MESSAGE_TYPE {
        READY, INFO_NEW_BLOCK, REQ_ALL_BLOCKS, RSP_ALL_BLOCKS, INFO_NEW_TRANSACTION
    }

    @Override
    public String toString() {
        return String.format("Message {type=%s, sender=%d, receiver=%d, blocks=%s}", type, sender, receiver, blocks);
    }

    static class MessageBuilder {
        private final Message message = new Message();

        MessageBuilder withSender(final int sender) {
            message.sender = sender;
            return this;
        }

        MessageBuilder withReceiver(final int receiver) {
            message.receiver = receiver;
            return this;
        }

        MessageBuilder withType(final MESSAGE_TYPE type) {
            message.type = type;
            return this;
        }

        MessageBuilder withBlocks(final List<Block> blocks) {
            message.blocks = blocks;
            return this;
        }

        MessageBuilder withTransactions(final List<Transaction> transactions) {
            message.transactions = transactions;
            return this;
        }

        Message build() {
            return message;
        }

    }
}