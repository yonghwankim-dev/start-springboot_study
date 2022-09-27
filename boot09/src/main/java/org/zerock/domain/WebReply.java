package org.zerock.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "tbl_webreplies")
@EqualsAndHashCode(of = "rno")
@ToString(exclude = "board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String replyText;
    private String replyer;
    @CreationTimestamp
    private Timestamp regdate;
    @UpdateTimestamp
    private Timestamp updatedate;

    @JsonIgnore // 객체를 JSON 형태로 만들때 제외시켜줌
    @ManyToOne(fetch = FetchType.LAZY)
    private WebBoard board;
}
