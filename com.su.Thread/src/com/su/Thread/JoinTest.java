package com.su.Thread;

public class JoinTest extends Thread {
	public JoinTest(String name) {
		super(name);
	}

	public void run() {// 重写父类Thread的run方法
		System.out.println(1);
		try {
			this.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 1; i <= 10; i++) {
			System.out.println(getName() + i);
		}
	}

	public static void main(String[] args) {
		for (int i = 1; i <= 10; i++) {
			if (i == 6) {
				Thread one = new JoinTest("半路杀出线程" + i);
				one.start();
				try {
					one.join();
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + i);
		}
	}
}