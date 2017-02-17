package com.dongnao.zookeeper.demo.zkClient;

import com.dongnao.zookeeper.MyZkSerializer;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

/**
 * @author qingyin
 * @date 2016/12/13
 */
public class ZkClientDemo {

    private static String CONNECT_STRING="120.77.22.187:2181,120.77.22.187:2182,120.77.22.187:2183";

    private static int SESSION_TIMEOUT=3000;

    public static void main(String[] args) {
        ZkClient zkClient=new ZkClient(CONNECT_STRING,SESSION_TIMEOUT,SESSION_TIMEOUT,new MyZkSerializer());
        try {
            zkClient.subscribeChildChanges("/configuration", new IZkChildListener() {
                @Override
                public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                    System.out.println("触发事件："+parentPath);
                    for(String str:currentChilds){
                        System.out.println(str);
                    }
                }
            });
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            zkClient.close();
        }
    }
    private static void create(ZkClient zk){
        zk.createPersistent("/node_11/node_11_1/node_11_1_1",true); //递归创建节点
    }

    private static void update(ZkClient zk){
        zk.writeData("/node_11","zyz");
    }

    private static void delete(ZkClient zk){
        boolean bool=zk.deleteRecursive("/node_11");
        System.out.println(bool);
    }

    private static void subWatch(ZkClient zk){
        if(!zk.exists("/node_11")) {
            zk.createPersistent("/node_11");
        }
        //数据订阅事件
        zk.subscribeDataChanges("/node_11", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("触发事件："+dataPath+"->"+data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("触发删除事件:"+dataPath);
            }
        });
    }
}
