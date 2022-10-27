package com.betswap.market.infrastruture.team.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

//团队的成员信息
@Entity
@Table(name = "team_member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMemberEntity {

    @Id
    @Column(unique = true)
    private String id;

    private String teamId; //团队id

    private String userId;  //用户id

    @Builder.Default
    private Integer lowerCount=0;  //下级人数

    private String parentId; //上级用户id

    private Long createTime;//创建时间

    @Builder.Default
    private Integer  payCount=0; //交易次数

    @Builder.Default
    private BigDecimal  paySumUSDT= new BigDecimal(0);  //交易的USDT 总额

    @Builder.Default
    private BigDecimal  profitUSDT= new BigDecimal(0);  //收益的USDT

    @Builder.Default
    private BigDecimal betNumber = new BigDecimal(0);   //交易产生的bet(实际)

    @Builder.Default
    private BigDecimal betReturnNumber = new BigDecimal(0);   //交易下级返回的bet(代理收益)


}
