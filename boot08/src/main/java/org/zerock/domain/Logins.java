package org.zerock.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "persistent_logins")
@ToString
public class Logins {
    @Id
    private String series;
    @NotNull
    private String username;
    @NotNull
    private String token;
    @UpdateTimestamp
    private LocalDateTime last_used;
}
