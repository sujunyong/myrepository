package com.su.Thread;

public class ThreadTest {
	static Thread t1 = new Thread(new Runnable() {
		@Override       
		public void run() {
			System.out.println("t1");
		}});    
	static Thread t2 = new Thread(new Runnable() {
		@Override        
		public void run() {  
			System.out.println("你好");
			System.out.println("你好");
			try {
				t1.join();
				Thread.sleep(100);
			} catch (InterruptedException e) {               
				e.printStackTrace();
				}System.out.println("t2");
				}});    
	static Thread t3 = new Thread(new Runnable() {
		@Override       
		public void run() {            
			try {
				System.out.println(1);
				t2.join();
				Thread.sleep(500);
			} catch (InterruptedException e) {              
				
				}System.out.println("t3");
				}});   
	public static void main(String[] args) {
		
		t3.start();
		t1.start();
		t2.start();
		
		}
}
