package data.provider.mongo.repository;

import data.provider.entity.RiskFlowBizParam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Title:
 * Description: 业务数据
 * Copyright: Copyright (c) 2019
 * Company: ChinaSteel
 *
 * @className: RiskFlowBizParamRepository
 * @author: youcheng
 * @create: 2019-07-04 13:48
 * @version：1.0
 */
@Repository
public interface RiskFlowBizParamRepository extends MongoRepository<RiskFlowBizParam,String> {

    RiskFlowBizParam findByReqNoAndReqSource(String reqNo, String reqSource);
}
