package com.onbelay.dagclient.floatindex.model;

import com.onbelay.core.entity.component.ApplicationContextFactory;
import com.onbelay.core.entity.enums.EntityState;
import com.onbelay.core.entity.model.AbstractEntity;
import com.onbelay.core.entity.snapshot.EntitySlot;
import com.onbelay.dagclient.floatindex.repository.FloatIndexRepository;
import com.onbelay.dagclient.floatindex.repositoryimpl.FloatIndexRepositoryBean;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexDetail;
import com.onbelay.dagclient.floatindex.snapshot.FloatIndexSnapshot;

import javax.persistence.*;

@Entity
@Table(name = "DC_FLOAT_INDEX")
@NamedQueries({
        @NamedQuery(
                name = FloatIndexRepositoryBean.FIND_INDEX_BY_NAME,
                query = "SELECT index " +
                        "  FROM FloatIndex index " +
                      "   WHERE index.detail.name = :name ")

})
public class FloatIndex extends AbstractEntity {

    private Integer id;

    private FloatIndex benchesToIndex;

    private FloatIndexDetail detail = new FloatIndexDetail();


    @Id
    @Column(name="FLOAT_INDEX_ID", insertable = false, updatable = false)
    @SequenceGenerator(name="indexgen", sequenceName="DC_FLOAT_INDEX_SEQ", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "indexgen")
    public Integer getId() {
        return id;
    }

    public void setId(Integer graphNodeId) {
        this.id = graphNodeId;
    }

    @ManyToOne
    @JoinColumn(name = "BENCHES_TO_FLOAT_INDEX_ID")
    public FloatIndex getBenchesToIndex() {
        return benchesToIndex;
    }

    public void setBenchesToIndex(FloatIndex benchesToIndex) {
        this.benchesToIndex = benchesToIndex;
    }

    @Embedded
    public FloatIndexDetail getDetail() {
        return detail;
    }

    public void setDetail(FloatIndexDetail detail) {
        this.detail = detail;
    }


    public EntitySlot generateSlot() {
        return new EntitySlot(
                getEntityId(),
                detail.getName());
    }


    public void createWith(FloatIndexSnapshot snapshot) {
        detail.applyDefaults();
        detail.shallowCopyFrom(snapshot.getDetail());
        updateRelationships(snapshot);
        save();
    }


    public void createWith(String name, String type) {
        detail.applyDefaults();
        detail.setName(name);
        detail.setType(type);
        save();
    }

    private void updateRelationships(FloatIndexSnapshot snapshot) {

        if (snapshot.getBenchesToFloatIndexName() != null) {
            benchesToIndex = FloatIndex.getFloatIndexRepository().findByName(snapshot.getBenchesToFloatIndexName());
        } else if (snapshot.getBenchesToFloatIndexId() != null) {
            if (snapshot.getBenchesToFloatIndexId().getEntityId().isSet())
                benchesToIndex = getFloatIndexRepository().load(snapshot.getBenchesToFloatIndexId().getEntityId());
            else
                benchesToIndex = null;
        }
    }

    public void updateWith(FloatIndexSnapshot snapshot) {
        if (snapshot.getEntityState() == EntityState.MODIFIED) {
            detail.shallowCopyFrom(snapshot.getDetail());
            updateRelationships(snapshot);
            update();
        } else if (snapshot.getEntityState() == EntityState.DELETE) {
            getEntityRepository().delete(this);
        }
    }

    public void validate() {
        detail.validate();
    }

    @Transient
    protected static FloatIndexRepository getFloatIndexRepository() {
        return (FloatIndexRepository) ApplicationContextFactory.getBean(FloatIndexRepository.NAME);
    }
}
