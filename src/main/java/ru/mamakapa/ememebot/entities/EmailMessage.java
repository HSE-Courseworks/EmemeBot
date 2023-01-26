
package ru.mamakapa.ememebot.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "emails_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailMessage {
    @Id
    String messageId;
    Date sendDate;
}

