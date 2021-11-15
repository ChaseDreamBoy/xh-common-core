package com.xh.common.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * AbObjectPool
 *
 * @author xh
 * @date 2021/11/15
 */
@Slf4j
public abstract class AbstractObjectPool<T> {

    /**
     * 池Map
     */
    public static Map<String, AbstractObjectPool > poolMap = new HashMap<>();

    /**
     * 空闲对象池
     */
    protected ConcurrentLinkedQueue<T> freePool = new ConcurrentLinkedQueue<>();

    /**
     * 繁忙对象池
     */
    protected ConcurrentLinkedQueue<T> usedPool = new ConcurrentLinkedQueue<>();

    /**
     * 定时器
     */
    private Timer timer = null;

    /**
     * 最小对象数量
     */
    protected int minNum;

    /**
     * 最大对象数量
     */
    protected int maxNum;

    /**
     * 对象类型
     */
    protected Class classType;

    /**
     * 构造方法
     *
     * @param minNum    最小值
     * @param maxNum    最大值
     * @param classType 类型
     * @throws Exception 异常
     */
    protected AbstractObjectPool(int minNum, int maxNum, Class classType)
            throws Exception {
        this.minNum = minNum;
        this.maxNum = maxNum;
        this.classType = classType;
        createObjects(minNum);
        // 初始化定时器,1秒后，每隔60秒定时执行对象回收
        timer = new Timer("obj_pool-" + classType.getName());
        timer.schedule(new CheckPool(), 1000, 60000);
    }

//    /**
//     * 创建对象池
//     * @param minNum 最小值
//     * @param maxNum 最大值
//     * @param classType 调用入参为空的构造函数来创建对象
//     * @return 对象池
//     * @throws Exception 异常
//     */
//    public static synchronized ObjectPool getInstance(int minNum, int maxNum, Class classType)
//            throws Exception {
//        String className = classType.getName();
//        AbstractObjectPool instance = poolMap.get(className);
//        if (instance == null) {
//            instance = new AbstractObjectPool(minNum, maxNum, classType);
//        }
//        poolMap.put(className, instance);
//        return instance;
//    }

    /**
     * 创建对象
     *
     * @return 对象
     * @throws Exception 异常
     */
    protected abstract T createObject() throws Exception;

    /**
     * 获取对象
     *
     * @return 对象
     */
    public synchronized T getObject() {
        T obj = null;
        // 对象池使用完时，可试图获取连接次数
        int tryNum = 3;
        try {
            while (obj == null && tryNum-- > 0) {
                // 移动并获取队列头元素
                do {
                    obj = freePool.poll();
                } while (obj == null && freePool.size() > 0);
                if (obj == null) {
                    if (maxNum == 0 || (usedPool.size() + freePool.size()) < maxNum) {
                        // 没有空闲连接，且当前连接小于最大允许值，最大值为0则不限制
                        obj = createObject();
                    } else {
                        // 达到对象池上限，获取失败
                        log.error("达到对象池上限,等待100毫秒后再试" + tryNum + "次,上限:" + maxNum + ";当前正在使用数量:" + usedPool.size() + ",空闲数量:" + freePool.size());
                        // 等待100毫秒
                        Thread.sleep(100);
                    }
                }
            }

            if (obj != null) {
                usedPool.add(obj);
                if (log.isDebugEnabled()) {
                    log.debug("对象池中获取对象成功,上限:" + maxNum + ";当前正在使用数量:" + usedPool.size() + ",空闲数量:" + freePool.size());
                }
            } else {
                log.error("对象池中获取对象失败,上限:" + maxNum + ";当前正在使用数量:" + usedPool.size() + ",空闲数量:" + freePool.size());
            }
        } catch (Exception ex) {
            log.error("获取对象失败", ex);
        }
        return obj;
    }

    /**
     * 归还对象
     *
     * @param obj 对象
     */
    public synchronized void returnObject(T obj) {
        usedPool.remove(obj);
        freePool.add(obj);
        if (log.isDebugEnabled()) {
            log.debug("对象池中释放对象成功,上限:" + maxNum + ";当前正在使用数量:" + usedPool.size() + ",空闲数量:" + freePool.size());
        }
    }

    /**
     * 返回当前空闲数
     *
     * @return 空闲数量
     */
    public int getNum() {
        return freePool.size();
    }

    /**
     * 返回当前正使用数
     *
     * @return 正使用数量
     */
    public int getNumActive() {
        return usedPool.size();
    }

    /**
     * 释放所有对象
     */
    public synchronized void release() {
        timer.cancel();
        timer = null;
        releaseFreePool(0);
        releaseUsedPool();
    }

    /**
     * 回收空闲对象
     *
     * @param liveNum 保留剩余空闲对象数量
     */
    protected synchronized void releaseFreePool(int liveNum) {
        Object obj;
        while (freePool.size() > liveNum && (obj = freePool.peek()) != null) {
            freePool.remove(obj);
            log.info("成功释放空闲对象1个");
        }
    }

    /**
     * 创建多个对象
     *
     * @param minNum 对象个数
     * @throws Exception 异常
     */
    protected synchronized void createObjects(int minNum) throws Exception {
        // 当前数量
        int currentNum = getNum() + getNumActive();
        // 初始minNum个连接
        for (int i = currentNum; i < minNum; i++) {
            T obj = createObject();
            if (obj != null) {
                freePool.add(obj);
                if (log.isDebugEnabled()) {
                    log.debug("对象池中初始化对象成功,上限:" + maxNum + ";当前正在使用数量:" + usedPool.size() + ",空闲数量:" + freePool.size());
                }
            }
        }
    }

    /**
     * 释放所有在用对象
     */
    private synchronized void releaseUsedPool() {
        Object obj;
        while ((obj = usedPool.peek()) != null) {
            usedPool.remove(obj);
        }
    }

    class CheckPool extends TimerTask {
        @Override
        public void run() {
            try {
                releaseFreePool(minNum);
                if (log.isDebugEnabled()) {
                    log.debug("定时检查对象池,上限:" + maxNum + ";当前正在使用数量:" + usedPool.size() + ",空闲数量:" + freePool.size());
                }
            } catch (Exception ex) {
                log.error("error.", ex);
            }
        }
    }
}
