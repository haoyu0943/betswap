package com.betswap.market.infrastruture.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_question")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionId;
    private String  userId;
    private String  userName;
    private String title;
    //private String question;
    //private String  answer;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="Text", nullable=true)
    private String      question;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition="Text", nullable=true)
    private String      answer;

    private Long createTime; //创建时间
    private Long replyTime;  //回复时间
    private String  replyUserId;
    private String  replyUserName;
    private Integer type;         //1问题反馈2问题和建议
    @Builder.Default
    private Integer stadu=0;
}


