package com.github.cola.flow.server.repository;

import com.github.cola.flow.client.constants.Constants;
import com.github.cola.flow.server.common.DbConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;
/**
 * Project Name: ele-newretail-trade
 * Confidential and Proprietary
 * Copyright 2020 By alibaba-inc.com
 * All Rights Reserved
 * Desc: 流程节点状态记录表操作
 *
 * @author lj235678
 * @date 2020/2/17
 */
@Slf4j
@Service
public class EventStateRepository {
    @Autowired
    private EventStateDoMapper eventStateDoMapper;

    /**
     * 根据id查订单信息  查询直接走这里
     *
     * @param orderId 订单号
     * @param nodeName 流程节点名称
     * @return NewretailOrderDO
     */
    public EventStateDO get(Long orderId, String nodeName){
        log.info("select EventStateDoMapper order id is {}",orderId);
        Example example = new Example(EventStateDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bizId", orderId);
        criteria.andEqualTo("nodeName", nodeName);
        criteria.andEqualTo("isDelete", DbConstant.UPDATE_NO);
        return eventStateDoMapper.selectOneByExample(example);
    }

    /**
     * 更新
     * @param orderId
     * @param nodeName
     * @param state 该node执行状态，0表示成功，1表示失败
     * @return
     */
    public Integer insertOrupdate(Long orderId, String nodeName, Integer state){

        EventStateDO eventStateDO = new EventStateDO();
        eventStateDO.setBizId(orderId);
        eventStateDO.setNodeName(nodeName);

        //
        EventStateDO selectResult = eventStateDoMapper.selectOne(eventStateDO);
        if (Objects.isNull(selectResult)) {
            eventStateDO.setState(state);
            return eventStateDoMapper.insertSelective(eventStateDO);
        } else {
            Example example = new Example(EventStateDO.class);
            // where 条件
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("bizId", orderId);
            criteria.andEqualTo("nodeName", nodeName);

            return eventStateDoMapper.updateByExampleSelective(eventStateDO, example);
        }

    }

    /**
     * 根据id查订单信息  查询直接走这里
     *
     * @param orderId 订单号
     * @param nodeName 流程节点名称
     * @return NewretailOrderDO
     */
    public Boolean isOkParentState(Long orderId, String nodeName, String parentNames){
        log.info("select isOkParentState order:{}, nodeName:{}, parentNames:{}",orderId, nodeName, parentNames);
        List<Integer> parentStatus = eventStateDoMapper.isOkParentState(orderId, parentNames);

        for(Integer state : parentStatus){
            if(!Objects.equals(Constants.EVENT_STATE_SUCCESS, state) ){
                log.info("select isOkParentState fail, order:{}", orderId);
//                return false;
                //此处待后续补充返回false，主要针对异步发event，无法控制前后顺序消费的情况
                return true;
            }
        }

        return true;
    }

}
