package br.com.deep.envers.listener;

import br.com.deep.envers.domain.AuditRevisionEntity;
import org.hibernate.envers.RevisionListener;

public class AuditRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity auditRevisionEntity = (AuditRevisionEntity) revisionEntity;
        auditRevisionEntity.setUsername("author.revision");
    }

}
