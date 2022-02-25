package data.provider.mongo.repository.cond;

import data.provider.entity.RiskFlowBizParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Title:
 * Description: 用于分页查询 RiskFlowBizParam 数据的条件
 * Copyright: Copyright (c) 2019
 * Company: ChinaSteel
 *
 * @className: RiskFlowBizParamDto
 * @author: youcheng
 * @create: 2019-07-04 15:42
 * @version：1.0
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class RiskFlowBizParamDto extends RiskFlowBizParam {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 查询条件
     */
    private String createTimeStart;

    /**
     * 查询条件
     */
    private String createTimeEnd;
}
