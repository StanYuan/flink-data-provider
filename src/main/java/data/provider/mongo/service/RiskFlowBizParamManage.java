package data.provider.mongo.service;

import data.provider.entity.RiskFlowBizParam;
import data.provider.mongo.repository.cond.PageResult;
import data.provider.mongo.repository.cond.RiskFlowBizParamDto;

/**
 * Title:
 * Description: 业务请求数据
 * Copyright: Copyright (c) 2019
 * Company: ChinaSteel
 *
 * @className: RiskFlowBizParamManage
 * @author: youcheng
 * @create: 2019-07-04 14:42
 * @version：1.0
 */
public interface RiskFlowBizParamManage {

    /**
     * 分页查询
     *
     * @param riskFlowBizParamDto 分页参数
     * @return
     */
    PageResult<RiskFlowBizParam> listPageResult(RiskFlowBizParamDto riskFlowBizParamDto);

}