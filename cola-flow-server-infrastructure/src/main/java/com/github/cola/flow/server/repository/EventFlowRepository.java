package com.github.cola.flow.server.repository;

import com.alibaba.fastjson.JSON;
import com.github.cola.flow.server.common.DbConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author doufuche
 * @date 2020/11/16
 */
@Slf4j
@Service
public class EventFlowRepository {
    @Autowired
    private EventFlowDoMapper eventFlowDoMapper;

    /**
     * 根据id查订单信息  查询直接走这里
     *
     * @param bizId 订单号
     * @param nodeName 流程节点名称
     * @return
     */
    public EventFlowDO get(Long bizId, String nodeName){
        log.info("select EventFlowDoMapper order id is {}",bizId);
        Example example = new Example(EventFlowDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bizId", bizId);
        criteria.andEqualTo("nodeName", nodeName);
        criteria.andEqualTo("isDelete", DbConstant.UPDATE_NO);
        return eventFlowDoMapper.selectOneByExample(example);
    }

    /**
     * 删除该业务hold的订单流程
     * @param bizId
     * @param nodeName
     * @return
     */
    public Integer deleteEventFlow(Long bizId, String nodeName){
        EventFlowDO eventFlowDO = new EventFlowDO();
        eventFlowDO.setBizId(bizId);
        eventFlowDO.setIsDelete(DbConstant.UPDATE_YES.byteValue());

        Example example = new Example(EventStateDO.class);
        // where 条件
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bizId", bizId);
        if(!StringUtils.isEmpty(nodeName)) {
            criteria.andEqualTo("nodeName", nodeName);
            eventFlowDO.setNodeName(nodeName);
        }
        return eventFlowDoMapper.updateByExampleSelective(eventFlowDO, example);

    }

    /**
     * 写入流程记录，标记该hold的订单流程
     * @param bizId
     * @param nodeName
     * @param flowInfo
     * @param traceType
     * @return
     */
    public Integer insertOrUpdateEventFlow(Long bizId, String nodeName, String flowInfo, Integer traceType){
        EventFlowDO eventFlowDO = new EventFlowDO();
        eventFlowDO.setBizId(bizId);
        eventFlowDO.setNodeName(nodeName);

        //
        EventFlowDO selectResult = eventFlowDoMapper.selectOne(eventFlowDO);
        eventFlowDO.setIsDelete(DbConstant.UPDATE_NO.byteValue());
        eventFlowDO.setTraceType(traceType);

        if (Objects.isNull(selectResult)) {

            eventFlowDO.setFlowInfo(flowInfo);
            return eventFlowDoMapper.insertSelective(eventFlowDO);
        } else {
            Example example = new Example(EventStateDO.class);
            // where 条件
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("bizId", bizId);
            criteria.andEqualTo("nodeName", nodeName);

            return eventFlowDoMapper.updateByExampleSelective(eventFlowDO, example);
        }

    }

    /**
     * 插入事件流程数据
     * @param eventFlowDO
     * @return
     */
    public Integer insertEventFlow(EventFlowDO eventFlowDO){
        return eventFlowDoMapper.insert(eventFlowDO);
    }

    /**
     * 查询总数
     * @param eventFlowDO
     * @return
     */
    public Integer selectCount(EventFlowDO eventFlowDO){
        if(Objects.isNull(eventFlowDO)){
            return 0;
        }
        return eventFlowDoMapper.selectCount(eventFlowDO);
    }

    /**
     * 查询error类型的
     * @param offset 表示开始位置
     * @param limit 表示要取的记录的数目
     * @return
     */
    public List<EventFlowDO> selectList(EventFlowDO eventFlowDO, int offset, int limit){
        RowBounds rowBounds = new RowBounds(offset, limit);

        return eventFlowDoMapper.selectByRowBounds(eventFlowDO, rowBounds);

    }


    public List<Long> selectByTimeRange(LocalDateTime beginTime,
                                                      LocalDateTime endTime) {
        log.info("eventFlow selectByTimeRange db,beginTime {}, endTime {}", beginTime, endTime);
        List<Long> resultList = new ArrayList<>();
        try {
            resultList = eventFlowDoMapper.selectByTimeRange(beginTime, endTime);
        }catch (Exception e){
            log.error("eventFlow selectByTimeRange db  select error ",e);
        }
        return resultList;
    }

    public List<EventFlowDO> selectBybizIdList(List<Long> bizIdList) {
        log.info("eventFlow selectBybizIdList db, idList {}", JSON.toJSONString(bizIdList));
        List<EventFlowDO> resultList = new ArrayList<>();
        try {
            String collect = bizIdList.stream().map(Object::toString).collect(Collectors.joining(","));
            resultList = eventFlowDoMapper.selectByBizIdList(collect);
        }catch (Exception e){
            log.error("eventFlow selectBybizIdList db select error",e);
        }
        return resultList;
    }


}
