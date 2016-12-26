package com.slut.simrec.share;

/**
 * Created by 七月在线科技 on 2016/12/26.
 */

public class ShareManager {

    private static volatile ShareManager instances;

    public static ShareManager getInstances() {
        if(instances == null){
            synchronized (ShareManager.class){
                if(instances == null){
                    instances = new ShareManager();
                }
            }
        }
        return instances;
    }

    private ShareManager(){

    }



}
