package blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockChain {
    private  static List<Block> blockList = new ArrayList<>();
    private List<Miner> miners = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);

    public List<Miner> getMiners() {
        return miners;
    }


    public   void addBlock(Block block) {

        blockList.add(block);
    }

    public AtomicInteger getIdCounter() {
        return idCounter;
    }

    public  void addMiner(Miner miner) {
        miners.add(miner);
    }


    public int calculateTargetPrefix() {
        int numberOfMiners = miners.size();

        int leadingZeros = 0;

        if(numberOfMiners == 1){
            leadingZeros = 0;
        }else if ( numberOfMiners > 1){
            leadingZeros = miners.size() -1;
        }

        return leadingZeros;
    }

    public boolean isValid(List<Block> blockChain){
        for (int i = 1; i < blockChain.size(); i++) {
            Block currentBlock = blockChain.get(i);
            Block previousBlock = blockChain.get(i - 1);


            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
            if (currentBlock.getPreviousHash().equals(previousBlock.getPreviousHash())){
                return false;
            }
        }
        return true;
    }
    public  List<Block> getBlockChain() {
        return blockList;
    }

    public int generateMagicNumber (){

        Random random = new Random();

        return random.nextInt(999999);
    }
    public int getAndIncrementId() {
        return idCounter.getAndIncrement();
    }
}
