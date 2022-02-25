package data.provider.mongo.service.impl;

import data.provider.entity.RiskFlowBizParam;
import data.provider.mongo.repository.RiskFlowBizParamRepository;
import data.provider.mongo.repository.cond.PageResult;
import data.provider.mongo.repository.cond.RiskFlowBizParamDto;
import data.provider.mongo.repository.helper.MongoPageHelper;
import data.provider.mongo.service.RiskFlowBizParamManage;
import data.provider.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version v1.0
 * @Description:
 * @Author: dong.yuan
 * @Date: 2022/2/14 17:38
 */
@Component
public class RiskFlowMongoManageImpl implements RiskFlowBizParamManage {

    private static final Logger logger = LoggerFactory.getLogger(RiskFlowMongoManageImpl.class);

    @Resource
    MongoPageHelper mongoPageHelper;

    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired(required = false)
    private RiskFlowBizParamRepository riskFlowBizParamRepository;

    @Override
    public PageResult<RiskFlowBizParam> listPageResult(RiskFlowBizParamDto riskFlowBizParamDto) {
        Query query = new Query();
        if (StringUtils.isNotBlank(riskFlowBizParamDto.getBizCode())) {
            query.addCriteria(Criteria.where("bizCode").is(riskFlowBizParamDto.getBizCode()));
        }
        query.addCriteria(Criteria.where("createTime").gt(DateUtil.getDateFormat(riskFlowBizParamDto.getCreateTimeStart())).lt(DateUtil.getDateFormat(riskFlowBizParamDto.getCreateTimeEnd())));
        PageResult<RiskFlowBizParam> paramPageResult = mongoPageHelper.pageQuery(query, RiskFlowBizParam.class, riskFlowBizParamDto.getPageSize(), riskFlowBizParamDto.getPageNum(), riskFlowBizParamDto.getId());
        return paramPageResult;
    }

}
