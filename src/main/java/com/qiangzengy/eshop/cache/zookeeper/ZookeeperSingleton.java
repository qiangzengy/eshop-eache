package com.qiangzengy.eshop.cache.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZookeeperSingleton {

    /**
     * countDownLatch，java多线程并发的同步一个工具类，
     * 会传递进去一些数字，比如1，2，3都可以，然后await(),
     * 如果数字不是0就卡主等待
     *
     * 其他线程可以调用countDown()，给数字减1，如果数字减为0，
     * 那么之前所有在await的线程，都会逃出阻塞的状态，继续向下运行
     *
     *
     */
    private static CountDownLatch countDownLatch=new CountDownLatch(1);

    private ZooKeeper zooKeeper;

    public ZookeeperSingleton(){
        /**
         * watchwer ,去连接zookeeper server，创建会话的时候，是异步执行的，
         * 所以要给一个监听器，告诉我们什么时候才是真正完成了跟zk server的连接
         */
        try {
            this.zooKeeper=new ZooKeeper("192.168.1.51:2181,192.168.1.52:2181,192.168.1.53:2181",
                    5000,
                    new ZookeeperWatcher());
            //连接中
            System.out.println(zooKeeper.getState());


            try {
                // CountDownLatch
                // java多线程并发同步的一个工具类
                // 会传递进去一些数字，比如说1,2 ，3 都可以
                // 然后await()，如果数字不是0，那么久卡住，等待

                // 其他的线程可以调用coutnDown()，减1
                // 如果数字减到0，那么之前所有在await的线程，都会逃出阻塞的状态
                // 继续向下运行

                countDownLatch.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("ZooKeeper session established......");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ZookeeperWatcher implements Watcher{

        @Override
        public void process(WatchedEvent event) {

            System.out.println("=========");
            if (Event.KeeperState.SyncConnected == event.getState())
                countDownLatch.countDown();
        }
    }


    /**
     * 获取锁
     */

    public void acquireDistributedLock(Long productId) {
        String path = "/product-lock-" + productId;

        try {
            zooKeeper.create(path, "".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("success to acquire lock for product[id=" + productId + "]");
        } catch (Exception e) {
            // 如果那个商品对应的锁的node，已经存在了，就是已经被别人加锁了，那么就这里就会报错
            // NodeExistsException
            int count = 0;
            while(true) {
                try {
                    Thread.sleep(20);
                    zooKeeper.create(path, "".getBytes(),
                            ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    count++;
                    continue;
                }
                System.out.println("success to acquire lock for product[id=" + productId + "] after " + count + " times try......");
                break;
            }
        }
    }

    /**
     * 释放锁
     */

    public void releaseDistributedLock(Long productId) {
        String path = "/product-lock-" + productId;
        try {
            zooKeeper.delete(path, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Singleton {

        private static ZookeeperSingleton instance;

        static {
            instance = new ZookeeperSingleton();
        }

        public static ZookeeperSingleton getInstance(){
            return instance;
        }
    }

    /**
     * 获取单例
     * @return
     */
    public static ZookeeperSingleton getInstance(){
       return Singleton.getInstance();
    }

    /**
     * 初始化
     */
    public void init(){
        getInstance();
    }


}
