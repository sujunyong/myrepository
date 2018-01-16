package com.su.Thread;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadTeest  {

/**
 * 使用 wait notify 实现一个队列，队列有2个方法，add 和 get 。
 * add方法往队列中添加元素，get方法往队列中获得元素。队列必须是线程安全的。如果get执行时，队列为空，线程必须阻塞等待，直到有队列有数据。
 * 如果add时，队列已经满，则add线程要等待，直到队列有空闲空间。使他工作在多线程的环境下，证明，它的工作是正确的。给出程序和运行的截图。	
 */
	
	public static void main(String[] args) {
		
		//测试线程的执行
		final ThreadTeest test = new ThreadTeest();
	
		//调用第二个线程
		for(int index = 0; index < 10; index++){
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					//重复调用线程
					while(true){
						test.get();
					}
				}
			}).start();;
			
		}
		
		for(int index = 0; index < 10; index++){
		//创建线程执行
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				//重复调用线程
				while(true){
					test.add();
				}
			}
		}).start();;
	}
}
	
	//创建一个不可变的参数
	private static final int maxLength=10;
	
	//创建一个对象 使用锁的方法保证线程的安全
	//是的这个锁是唯一的存在 
	static Object lock = new Object();
	
	
	//创建一个集合用于存储 add和get的方法测试
	private Queue<Integer>list = new LinkedList<>();
	
	private Integer i = 0;
	//创建add方法
	public void add(){
		//获取对象锁
		synchronized(lock){
			
			//先让当前线程睡眠一下 
			try {
			
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//判断集合中的数据 如果没满就进行添加
			if(list.size()==maxLength){
				//如果进来就是判断成功 add队列已经满了,则让add线程等待 ,知道队列空间有空闲空间
				try {
					lock.wait(); //等待并且释放当前锁
				} catch (InterruptedException e) {
					System.out.println("当前线程的锁已经释放"+list.size());
					e.printStackTrace();
					//喚醒其他线程
					lock.notifyAll();
				}
			}
			i++;
			//如果没有执行try则进行添加的功能
			list.add(i);
			System.out.println("加入i的值"+i);
			//添加之后 释放锁
			lock.notifyAll();
		}
	}
	
	//创建get方法
	public Integer get(){
	
		//得到对象锁 如果能获得对象锁 表示add方法已经添加满了,并且释放了锁
		synchronized(lock){
			
			//让当前线程执行睡眠
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//判断list中是否有数据
			if(list.size()==0){
				//如果list中没有数据  则释放当前线程 让add线程进行添加
				try {
					
					lock.wait();
				} catch (InterruptedException e) {
					System.out.println("get方法调用list中是为空的,所以交出线程的执行权,让别的线程进行执行");
					e.printStackTrace();
					lock.notifyAll();
				}
				//如果是为空的 则直接技术该线程
				return null;
			}
			
			//如果该线程不为空
			Integer poll = list.poll();
			
			System.out.println("当前线程获取的值是: "+poll);
			//释放锁
			lock.notifyAll();
			return poll;
		}
		
	}
}
