# cola-flow-server
站在前辈的肩膀上眺望远方，cola框架https://github.com/alibaba/COLA  
本代碼庫是在cola框架基礎上實現的流程編排引擎和組件，最新版是基于cola3.0的版本； 
本代码库是应用层代码，底层引擎代码见https://github.com/doufuche/cola-flow  

簡單描述下背景  
COLA是DDD領域驅動框架，提供了DDD、CQRS、擴展點等功能和規範，具有很好的學習和使用價值  
基于COLA規範，實施過程中可能會遇到以下几方面的問題：   

1，一個業務流程可能有好幾個步驟需要執行，可以封裝為Event執行，或者代碼組裝Event執行流程，但是過程執行失敗怎麽處理?  
2，Event粒度拆分問題，粒度細的話邏輯更清晰，粒度粗的話代碼更簡單  
3，一個業務流程中可能需要修改多個業務數據，并且需要保證修改的一致性  
 
基于COLA框架實現的流程編排 基於DDD領域驅動和COLA框架，按照COLA規範劃分擴展點，梳理每個領域的鏈路和業務場景，抽取業務身份和業務節點概念，按業務身份串聯業務流程  
1，每個功能為一個節點Event，實現該節點Event的邏輯需要做冪等處理；該Event節點後續還可以升級為MQ節點  
2，每個節點Event對象需要的參數實現從Event上下文獲取，或從DB/RPC獲取，解耦節點與節點之間的強依賴  
3，抽象業務身份和節點Event的關係，通過業務身份去串聯節點Event流程鏈  
4，實現節點Event和流程鏈的管理，後續可實現動態調整或新增節點Event，以快速支持不同行業（業務場景）的目的  

# 架構設計和使用姿勢   
## 业务流程链逻辑图  
![image](https://github.com/doufuche/cola-flow-server/blob/main/imagesForReadme/%E4%B8%9A%E5%8A%A1%E6%B5%81%E7%A8%8B%E9%93%BE%E9%80%BB%E8%BE%91%E5%9B%BE.png)


上面流程链示意图中，流程1数据的定义需要业务自己配置实现，将定义好的输入传入ColaEventFlowServiceI.eventFlowInit()方法既可  
需注意：业务流程定义中用到的class，定义在@Value("${eventFlow.classes.config}")，格式如下：  
{"StartEvent":"com.github.cola.flow.client.baseevent.StartEvent","EndEvent":"com.github.cola.flow.client.baseevent.EndEvent"}  

## 系统架构图.  

![image](https://github.com/doufuche/cola-flow-server/blob/main/imagesForReadme/%E5%B9%B3%E5%8F%B0%E5%8C%96.png)    

## 流程引擎核心设计  

![image](https://github.com/doufuche/cola-flow-server/blob/main/imagesForReadme/DAG.png)    

## 项目中如何使用流程引擎  
```
<dependency>    
   <groupId>com.github.assembledflow</groupId>
   <artifactId>cola-flow-server</artifactId>
   <version>1.0.0-SNAPSHOT</version>
</dependency>
```
  
### Event节点对象定义
参考com.github.cola.flow.client.baseevent.StartEvent ,
需继承com.github.cola.flow.client.baseevent.FlowBaseEvent
  
### Event节点监听定义  
参考com.github.cola.flow.server.app.eventhandler.EventFlowDeleteHandler，
需将implements EventHandlerI 改为 extends BaseEventFlowExecutor

### BaseEventFlowExecutor定义    
1, 判断该节点是否执行完成  
2, 检查该订单是否已取消，是则终止流程  
3, 判断业务流程中，当前执行Event节点是否暂停节点，是则暂停流程；这时等待业务流程触发继续执行，下篇介绍   
4, 执行业务流程中，当前Event节点   
5, 记录流程执行结果，包括异常情况(异常会定时任务自动重试)   
6, 继续执行业务流程   


### 流程暂停Event处理  
前面"到店自提业务流程链" 讲过当前执行Event节点是暂停节点，则暂停流程；这时等待业务流程触发继续执行，  
调用ColaEventFlowServiceI.continueEventFlow(FlowBaseEvent suspendEvent) 即可触发流程继续执行  
注意设置deliveryStateReturnSuspendEvent.setExecuted(Boolean.TRUE);     表示该event可跳过了   


# 关于测试.  
## 单元测试   
1，单元测试时，如正向流程串联没有业务暂停场景的，下单时参数获取从当前event对象中获取startEvent对象，startEvent对象 即下单时参数对象，然后单元测试可自行构造该对象去执行业务逻辑  
   代码: StartEvent startEvent = (StartEvent)endEvent.getStartEvent();  

## 流程测试  
1，正向流程串联测试，定义流程信息eventFlowInfo，然后执行colaEventFlowService.eventFlowInit方法即可

2，业务暂停场景串联测试，上一步执行的流程中包含业务暂停场景Event，然后执行到该Event时业务流程会暂停，此时调用该测试中colaEventFlowService.continueEventFlow方法即可，入参是该业务暂停场景Event对象






 



