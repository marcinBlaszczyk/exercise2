package wdsr.exercise2.counter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Marek on 05.03.2016.
 * 
 * Task: use {@see java.util.concurrent.locks.Lock} to make CountingFacadeWithLockTest pass. 
 */
public class CountingFacadeWithLock implements CountingFacade {
	private final BusinessService businessService;
	private final Lock lock = new ReentrantLock();
	private int invocationCounter;
	
	public CountingFacadeWithLock(BusinessService businessService) {
		this.businessService = businessService;
	}
		
	public void countAndInvoke() {
		try {
			lock.tryLock(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		invocationCounter++;
		businessService.executeAction();
		lock.unlock();
		}
	}
	
	public int getCount() {
		return invocationCounter;
	}
}
