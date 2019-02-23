package com.cg.billing.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.cg.billing.beans.Address;
import com.cg.billing.beans.Bill;
import com.cg.billing.beans.Customer;
import com.cg.billing.beans.Plan;
import com.cg.billing.beans.PostpaidAccount;
import com.cg.billing.daoservices.BillingServicesDAO;
import com.cg.billing.daoservices.BillingServicesDAOImpl;
import com.cg.billing.exceptions.BillDetailsNotFoundException;
import com.cg.billing.exceptions.CustomerDetailsNotFoundException;
import com.cg.billing.exceptions.InvalidBillMonthException;
import com.cg.billing.exceptions.PlanDetailsNotFoundException;
import com.cg.billing.exceptions.PostpaidAccountNotFoundException;

public class BillingServicesImpl implements BillingServices {
    BillingServicesDAO billingServicesDAO = new BillingServicesDAOImpl();
	@Override
	public List<Plan> getPlanAllDetails() {
		
		return null;}
   
	@Override
	public int acceptCustomerDetails(String firstName, String lastName, String emailID, String dateOfBirth,
			String billingAddressCity, String billingAddressState, int billingAddressPinCode, String homeAddressCity,
			String homeAddressState, int homeAddressPinCode) {
		Address homeAddress= new Address(homeAddressCity,homeAddressState,homeAddressPinCode);
		Address billingAddress=new Address(billingAddressCity,billingAddressState,billingAddressPinCode);
		List<Address> address=new ArrayList<Address>();
		address.add(homeAddress);
		address.add(billingAddress);
        Customer customer= new Customer(firstName, lastName, emailID, dateOfBirth, address);	
        billingServicesDAO.saveCustomer(customer);
		return customer.getCustomerID();
	}

	@Override
	public long openPostpaidMobileAccount(int customerID, int planID)
			throws PlanDetailsNotFoundException, CustomerDetailsNotFoundException {
	     Plan plan = getPlanDetails(planID);
	     if(plan ==null)
	     throw new PlanDetailsNotFoundException("Please enter correct plan id.");
	     Customer customer= getCustomerDetails(customerID);
		 PostpaidAccount postPaidAccount = new PostpaidAccount(plan, customer);
		 billingServicesDAO.savePostpaidAccount(postPaidAccount);
		 return postPaidAccount.getMobileNo();
	}

	@Override
	public double generateMonthlyMobileBill(int customerID, long mobileNo, String billMonth, int noOfLocalSMS,
			int noOfStdSMS, int noOfLocalCalls, int noOfStdCalls, int internetDataUsageUnits)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException {
         
		return 0;
	}

	@Override
	public Customer getCustomerDetails(int customerID) throws CustomerDetailsNotFoundException {
	   return billingServicesDAO.findCustomer(customerID);
	}

	@Override
	public List<Customer> getAllCustomerDetails() {
		return billingServicesDAO.findAllCustomer();
	}

	@Override
	public PostpaidAccount getPostPaidAccountDetails(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException{
		Customer customer = getCustomerDetails(customerID);
		if(customer==null)
		throw new CustomerDetailsNotFoundException("Sorry no customer with this id found");
		 if(!customer.getPostpaidAccounts().containsKey(mobileNo))
			 throw new PostpaidAccountNotFoundException("Customer has no plans");
		return billingServicesDAO.findPostPaidAccount(mobileNo);
	}

	@Override
	public List<PostpaidAccount> getCustomerAllPostpaidAccountsDetails(int customerID)
			throws CustomerDetailsNotFoundException {
		Customer customer = getCustomerDetails(customerID);
		if(customer==null)
		throw new CustomerDetailsNotFoundException("Sorry no customer found");
		return billingServicesDAO.findAllPostpaidAccounts();
	}

	@Override
	public Bill getMobileBillDetails(int customerID, long mobileNo, String billMonth)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, InvalidBillMonthException,
			BillDetailsNotFoundException {
	
		return null;
	}

	@Override
	public List<Bill> getCustomerPostPaidAccountAllBillDetails(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException {
		Customer customer = getCustomerDetails(customerID);
		if(customer==null)
		throw new CustomerDetailsNotFoundException("Sorry no customer found");
		Map<Long, PostpaidAccount> postPaidAccount = customer.getPostpaidAccounts();
		if(postPaidAccount==null)
		throw new PostpaidAccountNotFoundException("Customer has no plans");
		PostpaidAccount postpaidAcc=postPaidAccount.get(mobileNo);		
		return billingServicesDAO.findAllBills(mobileNo);
	}

	@Override
	public boolean changePlan(int customerID, long mobileNo, int planID)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, PlanDetailsNotFoundException {
		Plan plan = getPlanDetails(planID);
		if(plan==null)
			throw new PlanDetailsNotFoundException("No Plan with this Id exist");
		PostpaidAccount postpaidAccount =getPostPaidAccountDetails(customerID, mobileNo);
		postpaidAccount.setPlan(plan);
		billingServicesDAO.savePostpaidAccount(	postpaidAccount);
		return true;
	}
    @Override
	public boolean closeCustomerPostPaidAccount(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException {
		Customer customer = getCustomerDetails(customerID);
		if(customer==null)
		throw new CustomerDetailsNotFoundException("Sorry no customer found");
		if(customer.getPostpaidAccounts().containsKey(mobileNo))
		   billingServicesDAO.removePostpaidAccountDetails(mobileNo);
		 else throw new PostpaidAccountNotFoundException("Customer has no plans");
		 return true;
	}
    @Override
	public boolean removeCustomerDetails(int customerID) throws CustomerDetailsNotFoundException {
    	Customer customer = getCustomerDetails(customerID);
		if(customer==null)
		throw new CustomerDetailsNotFoundException("Sorry no customer found");
		billingServicesDAO.removeCustomer(customerID);
		return true;
	}
    @Override
	public Plan getCustomerPostPaidAccountPlanDetails(int customerID, long mobileNo)
			throws CustomerDetailsNotFoundException, PostpaidAccountNotFoundException, PlanDetailsNotFoundException {
			 PostpaidAccount postPaidAccount = getPostPaidAccountDetails(customerID, mobileNo);
		return postPaidAccount.getPlan();
	}

	@Override
	public int createPlan(int monthlyRental, int freeLocalCalls, int freeStdCalls, int freeLocalSMS, int freeStdSMS,
			int freeInternetDataUsageUnits, float localCallRate, float stdCallRate, float localSMSRate,
			float stdSMSRate, float internetDataUsageRate, String planCircle, String planName) {
		Plan plan= new Plan(monthlyRental, freeLocalCalls, freeStdCalls, freeLocalSMS, freeStdSMS, freeInternetDataUsageUnits, localCallRate, stdCallRate, localSMSRate, stdSMSRate, internetDataUsageRate, planCircle, planName);
		return plan.getPlanID();
	}

	@Override
	public Plan getPlanDetails(int planID) throws PlanDetailsNotFoundException {
		return billingServicesDAO.findPlan(planID);
	}

}
