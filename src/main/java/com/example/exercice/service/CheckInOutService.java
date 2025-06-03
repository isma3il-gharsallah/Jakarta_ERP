package com.example.exercice.service;

import com.example.exercice.entity.CheckInOut;
import com.example.exercice.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CheckInOutService {

    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

    public List<CheckInOut> findByUser(User user) {
                                //SELECT * FROM checkinout WHERE USER_ID
        return em.createQuery("SELECT c FROM CheckInOut c WHERE c.user = :user", CheckInOut.class)
                .setParameter("user", user)
                .getResultList();
    }
}
