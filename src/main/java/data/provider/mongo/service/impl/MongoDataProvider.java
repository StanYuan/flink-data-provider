package data.provider.mongo.service.impl;

import com.alibaba.fastjson.JSONObject;
import data.provider.entity.RiskFlowBizParam;
import data.provider.entity.StatisticsCalMeta;
import data.provider.mongo.repository.cond.PageResult;
import data.provider.mongo.repository.cond.RiskFlowBizParamDto;
import data.provider.mongo.service.DataProviderService;
import data.provider.mongo.service.RiskFlowBizParamManage;
import data.provider.utils.DateUtil;
import data.provider.utils.KafkaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/21 14:41
 */
@Service
@Slf4j
public class MongoDataProvider implements DataProviderService {

    @Autowired
    private RiskFlowBizParamManage riskFlowBizParamManage;

    @Value("${data.provider.kafka.topic:kafka-stream-in}")
    private String providerTopic;

    @Override
    public void provideDataStream(String date) {
        RiskFlowBizParamDto riskFlowBizParamDto = new RiskFlowBizParamDto();
        riskFlowBizParamDto.setBizCode("UNION_PAY");
        Date dateParam = DateUtil.formatDate(date, DateUtil.DATE_DEFAULT_FORMAT);
        riskFlowBizParamDto.setCreateTimeStart(date);
        riskFlowBizParamDto.setCreateTimeEnd(DateUtil.getDateFormat(DateUtil.getDayAfter(dateParam)));
        riskFlowBizParamDto.setPageNum(1);
        riskFlowBizParamDto.setPageSize(500);
        while (true) {
            PageResult<RiskFlowBizParam> pageResult = riskFlowBizParamManage.listPageResult(riskFlowBizParamDto);
            if (pageResult.getLists() == null || pageResult.getLists().isEmpty()) {
                break;
            }
            int count = 0;
            for (RiskFlowBizParam bizParam : pageResult.getLists()) {
                JSONObject reqObj = new JSONObject();
                reqObj.put("data", bizParam.getRiskContent());
                List<StatisticsCalMeta> metaList = new ArrayList<>();
                StatisticsCalMeta statisticsCalMeta = new StatisticsCalMeta();
                statisticsCalMeta.setCondition("openId");
                statisticsCalMeta.setField("tradeAmt");
                statisticsCalMeta.setFrequency("NONE");
                if(count % 2 ==0){
                    statisticsCalMeta.setMethod("SUM");
                }
                else{
                    statisticsCalMeta.setMethod("COUNT");
                }
                statisticsCalMeta.setRuleCode("test-rule");
                metaList.add(statisticsCalMeta);
                //TODO查询bizCode下面流量规则
                reqObj.put("metaList", metaList);
                KafkaUtil.writeToKafka(providerTopic, reqObj.toJSONString());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                count++;
            }
            riskFlowBizParamDto.setPageNum(riskFlowBizParamDto.getPageNum() + 1);
        }

    }
}
