package blockchain;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Block {
    private final int id;
    private final long timestamp;
    private final String hash;
    private final String previousHash;
    private final int magicNumber;
    private long generationTime;
    private Miner createdBy;


    public Block(int id, long timestamp, String hash, String previousHash, int magicNumber, Miner createdBy) {
        this.id = id;
        this.timestamp = timestamp;
        this.hash = hash;
        this.previousHash = previousHash;
        this.magicNumber = magicNumber;
        this.createdBy = createdBy;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }


    @Override
    public String toString() {

        return "Block:\n" + "Created by: " + createdBy.getName() + "\n" + createdBy.getName() + " gets 100 VC" + "\n" + "Id: " + id + "\n" + "Timestamp: " + timestamp + "\n" + "Magic number: " + magicNumber + "\n" + "Hash of the previous block:\n" + previousHash + "\n" + "Hash of the block:\n" + hash + "\n" + "Block data: " + "\n" + generateBlockData(id) + "Block was generating for " + generationTime + " seconds";
    }


    public String generateBlockData(int id) {
        StringBuilder blockData = new StringBuilder();
        // Simulate transactions for every created Block
        int numberOfTransactions = (int) (Math.random() * 1) + 5; // random number of transactions from 1 to 5
        if (id == 1) {
            return "No transactions";
        }
        for (int j = 0; j < numberOfTransactions; j++) {
            int recipient = getRandomNumber(1, Main.getMiners().size());//random recipient from miners
            int amount = getRandomNumber(1, 100); // random amount
            int sender = getRandomNumber(1, Main.getMiners().size()); //random sender

            Miner recipientMiner = Main.getMiners().get(recipient - 1);
            Miner senderMiner = Main.getMiners().get(sender - 1);
            makeTransaction(senderMiner, recipientMiner, amount, blockData);

        }

        return blockData.toString();
    }

    private void makeTransaction(Miner sender, Miner recipient, int amount, StringBuilder blockData) {
        if (!recipient.equals(sender)) {
            if (sender.getVirtualCoins() >= amount) {
                sender.removeVirtualCoins(amount);
                recipient.addVirtualCoins(amount);
                blockData.append(sender.getName()).append(" sent ").append(amount).append(" VC to ").append(recipient.getName()).append("\n");
            }
        }
    }

    private int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public void setGenerationTime(long generationTime) {
        this.generationTime = generationTime;
    }
}
