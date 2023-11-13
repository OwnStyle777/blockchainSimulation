package blockchain;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Miner implements Runnable {
    private final BlockChain blockChain;
    private String name;
    private int virtualCoins;

    public Miner(BlockChain blockChain) {
        this.blockChain = blockChain;
    }

    @Override
    public void run() {
        synchronized (blockChain) {
            while (blockChain.getBlockChain().size() < 15) {
                int id = blockChain.getAndIncrementId();
                Block block = generateBlock(id);
                addVirtualCoins(100);
                    blockChain.addBlock(block);
                try {
                    blockChain.notifyAll();
                    blockChain.wait(10);
                } catch (InterruptedException e) {

                }
            }
        }
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVirtualCoins() {
        return virtualCoins;
    }

    public void addVirtualCoins(int VC) {
        this.virtualCoins += VC;
    }
    public void removeVirtualCoins(int VC){
        this.virtualCoins -= VC;
    }

    public  Block generateBlock (int id) {
        long startTime = System.currentTimeMillis();
        long timeStamp = new Date().getTime();
        String previousHash = "0";
        String hash;
        int magicNumber;

        List<Block> currentBlockChain = blockChain.getBlockChain();

        if (!currentBlockChain.isEmpty()) {
            Block previousBlock = currentBlockChain.get(currentBlockChain.size() - 1);
            previousHash = previousBlock.getHash();
        }


            magicNumber = blockChain.generateMagicNumber();
            hash = StringUtil.applySha256(id + timeStamp + previousHash + magicNumber);
            StringBuilder builder = new StringBuilder(hash);
            int leadingZeros = blockChain.calculateTargetPrefix();
            builder.replace(0, leadingZeros ,"0".repeat(leadingZeros));
            hash = builder.toString();


        synchronized (blockChain) {
            Block block = new Block(id, timeStamp, hash, previousHash, magicNumber, this);
            System.out.println(block);
            System.out.println("N was increased to " + leadingZeros);
            System.out.println();
            long endTime = System.currentTimeMillis();
            long generationTime = endTime - startTime;
            block.setGenerationTime(generationTime);
            return block;
        }

    }
}
