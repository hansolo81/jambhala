package id.co.rimaubank.jambhala.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushNotification {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "custNo", nullable = false)
    private String custNo;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "read", nullable = false)
    private Boolean read;
}
