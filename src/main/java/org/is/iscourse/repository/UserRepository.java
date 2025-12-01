package org.is.iscourse.repository;

import org.is.iscourse.model.entity.User;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public User save(User user) {
        if (user.getUserId() == null) {
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }

    public Optional<User> findById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public Optional<User> findByEmail(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        
        try {
            User user = entityManager.createQuery(jpql, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<User> findAll() {
        String jpql = "SELECT u FROM User u";
        return entityManager.createQuery(jpql, User.class)
                .getResultList();
    }

    public boolean existsByEmail(String email) {
        String jpql = "SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email";
        
        return entityManager.createQuery(jpql, Boolean.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public boolean existsByUsername(String username) {
        String jpql = "SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username";
        
        return entityManager.createQuery(jpql, Boolean.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public User update(User user) {
        return entityManager.merge(user);
    }

    public void delete(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    public void deleteById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}