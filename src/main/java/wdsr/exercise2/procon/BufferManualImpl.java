package wdsr.exercise2.procon;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Task: implement Buffer interface without using any *Queue classes from java.util.concurrent package.
 * Any combination of "synchronized", *Lock, *Semaphore, *Condition, *Barrier, *Latch is allowed.
 */
public class BufferManualImpl implements Buffer {
	
	private final Lock lock = new ReentrantLock();
	private final Condition notFull = lock.newCondition();
	private final Condition notEmpty = lock.newCondition();
	private ArrayList<Order> orders = new ArrayList<Order>(100000);
	
	public void submitOrder(Order order) throws InterruptedException {
		lock.lock();
		try{
			while (orders.size() == 100000) {
				notFull.await();
			}
			orders.add(order);
			notEmpty.signal();
			} finally {
			lock.unlock();
			}
	}
	
	public Order consumeNextOrder() throws InterruptedException {
		// TODO
		Order consumed = null;
		lock.lock();
		try{
			while(orders.isEmpty()){
				notEmpty.await();
			}
			consumed = orders.remove(0);
			notFull.signal();
			return consumed;
		}finally{
			lock.unlock();
		}
	}
}
