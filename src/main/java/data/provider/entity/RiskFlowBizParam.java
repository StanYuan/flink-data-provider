package data.provider.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class RiskFlowBizParam {

    private String id;

    private Long cursorId;

    private String reqNo;

    private String reqOrderNo;

    private String reqSource;

    private String bizCode;

    private String tradeTime;

    private Integer tradeStatus;

    private String createTime;

    private String createBy;

    private String updateTime;

    private String updateBy;

    private String riskContent;

}