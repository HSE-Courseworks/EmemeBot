
package ru.mamakapa.ememebot.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "emails_table", uniqueConstraints = @UniqueConstraint(columnNames = {"imapEmailId"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailMessage {
    @Id
    @SequenceGenerator(
            name = "emailID_sequence",
            sequenceName = "emailID_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "emailID_sequence"
    )
    private Long id;
    private String imapEmailId;
    private Date sendDate;

    public EmailMessage(String imapEmailId, Date sentDate) {
        this.imapEmailId = imapEmailId;
        this.sendDate = sentDate;
    }
}

