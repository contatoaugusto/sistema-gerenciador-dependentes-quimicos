package br.com.sgdq.app.util;

import org.hibernate.engine.spi.SessionImplementor;

public class HibernateProxy {
	/**
     * Detach an entity from the session as it would be if the session was closed.
     *
     * @param entity the hibernate entity.
     */
    public void detach(Object entity) {
 
//        // Check for lazy-loading proxies
//        if (entity instanceof HibernateProxy) {
//            SessionImplementor sessionImplementor = ((HibernateProxy) entity)..getSession();
//            if (sessionImplementor instanceof Session) {
//                ((Session) sessionImplementor).evict(entity);
//            }
// 
//            return;
//        }
// 
//        // processing queue
//        Queue<Object> entities = new LinkedList<Object>();
//        Set<Object> processedEntities = new IdentitySet();
// 
//        entities.add(entity);
// 
//        while ((entity = entities.poll()) != null) {
// 
//            // Skip already processed entities
//            if (processedEntities.contains(entity)) {
//                continue;
//            }
// 
//            ClassMetadata classMetadata = getSessionFactory()
//                    .getAllClassMetadata().get(entity.getClass().getName());
//            String[] propertyNames = classMetadata.getPropertyNames();
//            Session session = null;
// 
//            // Iterate through all persistent properties
//            for (String propertyName : propertyNames) {
// 
//                Object propertyValue = classMetadata.getPropertyValue(entity,
//                        propertyName, EntityMode.POJO);
//                Type propertyType = classMetadata.getPropertyType(propertyName);
// 
//                // Handle entity types
//                if (propertyType.isEntityType()) {
// 
//                    // Detach proxies
//                    if (propertyValue instanceof HibernateProxy) {
//                        SessionImplementor sessionImplementor = ((HibernateProxy) propertyValue)
//                                .getHibernateLazyInitializer().getSession();
// 
//                        if (sessionImplementor instanceof Session) {
//                            ((Session) sessionImplementor).evict(propertyValue);
// 
//                            // If we don't yet have a session for this entity save it for later use.
//                            if (session == null)
//                                session = (Session) sessionImplementor;
//                        }
// 
//                    } else {
//                        // Add entities to the processing queue.
//                        entities.add(propertyValue);
//                    }
// 
//                }
//                // Handle collection types
//                else if (propertyType.isCollectionType()) {
// 
//                    if (propertyValue instanceof AbstractPersistentCollection) {
//                        SessionImplementor sessionImplementor =
//                                ((AbstractPersistentCollection) propertyValue).getSession();
// 
//                        // If we don't yet have a session for this entity save it for later use.
//                        if (sessionImplementor instanceof Session && session == null) {
//                            session = (Session) sessionImplementor;
//                        }
//                    }
//                }
//            }
// 
//            // Evict the entity and associated collections.
//            if (session != null) {
//                session.evict(entity);
//            }
// 
//            processedEntities.add(entity);
//        }
    }

}
