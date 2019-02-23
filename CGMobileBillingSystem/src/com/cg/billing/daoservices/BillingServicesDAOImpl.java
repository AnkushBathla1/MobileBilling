package com.cg.billing.daoservices;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.cg.billing.beans.Bill;
import com.cg.billing.beans.Customer;
import com.cg.billing.beans.Plan;
import com.cg.billing.beans.PostpaidAccount;

public class BillingServicesDAOImpl implements BillingServicesDAO{
EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory("JPA-PU");
@Override
public Customer saveCustomer(Customer customer) {
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	entityManager.persist(customer);
	entityManager.getTransaction().commit();
	entityManager.close();
	return customer;
}
@Override
public Customer updateCustomer(Customer customer) {
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	entityManager.merge(customer);
	entityManager.getTransaction().commit();
	entityManager.close();
	return customer;
}
@Override
public Customer findCustomer(int customerID) {
    return entityManagerFactory.createEntityManager().find(Customer.class, customerID);
}
@Override
public Plan findPlan(int planID) {
	return entityManagerFactory.createEntityManager().find(Plan.class, planID);
}
@SuppressWarnings("unchecked")
@Override
public List<Customer> findAllCustomer() {
	Query query =entityManagerFactory.createEntityManager().createQuery("from Customer c");
	return query.getResultList();
}
@Override
public PostpaidAccount findPostPaidAccount(long mobileNo) {
	return entityManagerFactory.createEntityManager().find(PostpaidAccount.class, mobileNo);
}
@SuppressWarnings("unchecked")
@Override
public List<PostpaidAccount> findAllPostpaidAccounts() {
	Query query =entityManagerFactory.createEntityManager().createQuery("from PostpaidAccount p");
	return query.getResultList();
}
@SuppressWarnings("unchecked")
@Override
public List<Bill> findAllBills(long mobileNo) {
	PostpaidAccount postpaidAccount=findPostPaidAccount(mobileNo);
	Query query =entityManagerFactory.createEntityManager().createQuery("from Bill b where b.postpaidAccount="+mobileNo);
	return query.getResultList();
}
@Override
public PostpaidAccount savePostpaidAccount(PostpaidAccount postPaidAccount) {
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	entityManager.persist(postPaidAccount);
	entityManager.getTransaction().commit();
	entityManager.close();
	return postPaidAccount;
}
@Override
public PostpaidAccount updatePostpaidAccount(PostpaidAccount postPaidAccount) {
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	entityManager.merge(postPaidAccount);
	entityManager.getTransaction().commit();
	entityManager.close();
	return postPaidAccount;
}
@Override
public Plan savePlan(Plan plan) {
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	entityManager.persist(plan);
	entityManager.getTransaction().commit();
	entityManager.close();
	return plan;
}
@Override
public Plan updatePlan(Plan plan) {
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	entityManager.merge(plan);
	entityManager.getTransaction().commit();
	entityManager.close();
	return plan;
}
@Override
public boolean removeCustomer(long customerID) {
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	entityManager.find(Customer.class, customerID);
	entityManager.remove(customerID);
	entityManager.getTransaction().commit();
	entityManager.close();
	return true;
}
@Override
public boolean removePostpaidAccountDetails(long mobileNo) {
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	entityManager.getTransaction().begin();
	entityManager.find(PostpaidAccount.class,mobileNo);
	entityManager.remove(mobileNo);
	entityManager.getTransaction().commit();
	entityManager.close();
	return true;
}
}

