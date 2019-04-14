package com.poc.thorntail.resource;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JPAFactory {

  private EntityManager _entityManager;

  public EntityManager getEntityManager() {

    if (_entityManager != null) return _entityManager;

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("poc");
    _entityManager = factory.createEntityManager();
    return _entityManager;
 }
}