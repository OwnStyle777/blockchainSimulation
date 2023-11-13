package blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
   private static final BlockChain blockChain = new BlockChain();

    private static  List<Miner> miners = blockChain.getMiners();

        public static void main(String[] args) {


            Miner miner1 = new Miner(blockChain);
            miner1.setName("miner1");
            blockChain.addMiner(miner1);
            Miner miner2 = new Miner(blockChain);
            blockChain.addMiner(miner2);
            miner2.setName("miner2");
            Miner miner3 = new Miner(blockChain);
            blockChain.addMiner(miner3);
            miner3.setName("miner3");

            Thread thread1 = new Thread(miner1);
            Thread thread2 = new Thread(miner2);
            Thread thread3 = new Thread(miner3);

            thread1.start();
            thread2.start();
            thread3.start();

            //main thread waiting ,until another threads make their job
            try {
                thread1.join();
                thread2.join();
                thread3.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(Miner miner : miners){
                System.out.println(miner.getName() + " " +  miner.getVirtualCoins());
            }

        }
    public static List<Miner> getMiners() {
        return miners;
    }
    }