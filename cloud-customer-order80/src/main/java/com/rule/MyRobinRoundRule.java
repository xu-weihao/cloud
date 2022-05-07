package com.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MyRobinRoundRule extends AbstractLoadBalancerRule {

    AtomicInteger atomicInteger = new AtomicInteger(0);
    
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {


        return choose(key, getLoadBalancer());
    }

    private Server choose(Object key, ILoadBalancer loadBalancer) {
        if(loadBalancer ==  null){
            return null;
        }

        List<Server> allServers = loadBalancer.getAllServers();

        int serverSize = allServers.size();

        int index = getIndex(serverSize);

        return allServers.get(index);
    }

    private int getIndex(int count) {
        while(true){
            int value = atomicInteger.get();
            int index = value + 1;
            index = (index % count);
            if(atomicInteger.compareAndSet(value, index)){
                return index;
            }
        }
    }
}
