package br.com.deep.envers.domain;

import br.com.deep.envers.listener.AuditRevisionListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "revinfo", schema = "audit")
@AttributeOverrides({
    @AttributeOverride(name = "timestamp", column = @Column(name = "revtstmp")),
    @AttributeOverride(name = "id", column = @Column(name = "rev"))
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@RevisionEntity(AuditRevisionListener.class)
public class AuditRevisionEntity extends DefaultRevisionEntity {

    @NotBlank
    private String username;

}
