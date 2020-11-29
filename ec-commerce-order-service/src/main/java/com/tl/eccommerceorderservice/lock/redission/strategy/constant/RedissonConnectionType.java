package com.tl.eccommerceorderservice.lock.redission.strategy.constant;

/**
 * redisson 连接类型枚举
 * @author  lintan 2020/11/29
 */
public enum RedissonConnectionType {

    STANDALONE("standalone", "单节点部署方式","RedissonConfigImpl"),
    SENTINEL("sentinel", "哨兵部署方式","SentineRedissonConfigImpl"),
    CLUSTER("cluster", "集群方式","ClusterRedissonConfigImpl"),
    MASTER_SLAVE("master-slave", "主从部署方式","MasterSlaveRedissonConfigImpl");

    private String type;

    private String description;

    private String className;

    private RedissonConnectionType(String type, String description, String className) {
        this.type = type;
        this.description = description;
        this.className = className;
    }

    public static RedissonConnectionType getType(String type){
        for (RedissonConnectionType redissonConnectionType :RedissonConnectionType.values()){
            if(redissonConnectionType.type.equals(type)){
                return redissonConnectionType;
            }
        }
        return null;
    }


    public String getType() {
        return type;
    }


    public String getDescription() {
        return description;
    }

    public String getClassName() {
        return className;
    }
}
