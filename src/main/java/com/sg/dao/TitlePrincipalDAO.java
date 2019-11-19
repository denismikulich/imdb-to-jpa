package com.sg.dao;

import com.sg.domain.TitlePrincipal;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class TitlePrincipalDAO extends AbstractJpaDAO<TitlePrincipal> {

    public TitlePrincipalDAO() {
        setClazz(TitlePrincipal.class);
    }

    public boolean hasTitle(String title) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countPrincipalsWithTitle = cb.createQuery(Long.class);
        Root<TitlePrincipal> principalsRoot = countPrincipalsWithTitle.from(TitlePrincipal.class);
        countPrincipalsWithTitle.select(cb.count(principalsRoot))
                .where(cb.equal(principalsRoot.get("tid").get("tconst"), title));
        return entityManager.createQuery(countPrincipalsWithTitle).getSingleResult() > 0;
    }

    public boolean hasName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countPrincipalsWithName = cb.createQuery(Long.class);
        Root<TitlePrincipal> principalsRoot = countPrincipalsWithName.from(TitlePrincipal.class);
        countPrincipalsWithName.select(cb.count(principalsRoot))
                .where(cb.equal(principalsRoot.get("tid").get("nconst"), name));
        return entityManager.createQuery(countPrincipalsWithName).getSingleResult() > 0;
    }
}
