package data.provider.mongo.service.impl;

import data.provider.entity.RiskFlowBizParam;
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

import java.util.Date;

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
            for (RiskFlowBizParam bizParam : pageResult.getLists()) {
                KafkaUtil.writeToKafka(providerTopic, bizParam.getRiskContent());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
            riskFlowBizParamDto.setPageNum(riskFlowBizParamDto.getPageNum() + 1);
        }

    }
}
