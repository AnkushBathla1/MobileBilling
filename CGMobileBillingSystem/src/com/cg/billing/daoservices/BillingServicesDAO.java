package com.cg.billing.daoservices;
import java.util.List;

import com.cg.billing.beans.Bill;
import com.cg.billing.beans.Customer;
import com.cg.billing.beans.Plan;
import com.cg.billing.beans.PostpaidAccount;

public interface BillingServicesDAO {
public Customer saveCustomer(Customer customer); 
public Customer updateCustomer(Customer customer);
public Customer findCustomer(int customerID);
public Plan findPlan(int planID);
public List<Customer> findAllCustomer();
public PostpaidAccount findPostPaidAccount(long mobileNo);
public List<PostpaidAccount> findAllPostpaidAccounts();
public List<Bill> findAllBills(long mobileNo);
public PostpaidAccount savePostpaidAccount(PostpaidAccount postPaidAccount);
public PostpaidAccount updatePostpaidAccount(PostpaidAccount postPaidAccount);
public Plan savePlan(Plan plan);
public Plan updatePlan(Plan plan);
public boolean removeCustomer(long customerID);
public boolean removePostpaidAccountDetails(long mobileNo);
}
